package com.geofrat.bachelorsapi.request

import com.geofrat.bachelorsapi.group.GroupService
import com.geofrat.bachelorsapi.membership.GroupMembership
import com.geofrat.bachelorsapi.membership.GroupMembershipRepository
import com.geofrat.bachelorsapi.user.UserService
import org.bson.types.ObjectId
import org.springframework.stereotype.Service

@Service
class GroupRequestService(
        private val groupRequestRepository: GroupRequestRepository,
        private val userService: UserService,
        private val groupMembershipRepository: GroupMembershipRepository,
        private val groupService: GroupService
) {
    fun createRequest(request: GroupRequestDto): GroupRequestDto {
        val user = userService.findByUid(request.userId)
        val requestDoc = GroupRequestDoc(groupId = ObjectId(request.groupId), userId = user.id)
        val newRequest = groupRequestRepository.save(requestDoc)
        return GroupRequestDto(newRequest.id.toHexString(), newRequest.groupId.toHexString(), newRequest.userId.toHexString())
    }

    fun getRequests(userUid: String): List<GroupRequestDetails> {
        val groups = groupService.getOwnedGroups(userUid)
        return groupRequestRepository.findAllByGroupIdIn(groups)
                .map {
                    val group = groupService.findById(it.groupId)
                    val user = userService.findByUserId(it.userId)
                    GroupRequestDetails(it.id.toHexString(), group.groupName, user.email)
                }
    }

    fun processRequest(requestId: String, accepted: Boolean) {
        val groupRequest = groupRequestRepository.findById(ObjectId(requestId))
        if (accepted) {
            if (groupRequest.isPresent) {
                val groupReq = groupRequest.get()
                groupMembershipRepository.save(GroupMembership(groupId = groupReq.groupId, userId = groupReq.userId))
                groupRequestRepository.delete(groupReq)
            }
        } else {
            if (groupRequest.isPresent) {
                val groupReq = groupRequest.get()
                groupRequestRepository.delete(groupReq)
            }
        }
    }

}