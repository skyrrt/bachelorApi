package com.geofrat.bachelorsapi.group

import com.geofrat.bachelorsapi.membership.GroupMembership
import com.geofrat.bachelorsapi.membership.GroupMembershipRepository
import com.geofrat.bachelorsapi.password.PasswordDoc
import com.geofrat.bachelorsapi.password.PasswordRepository
import com.geofrat.bachelorsapi.request.GroupRequestRepository
import com.geofrat.bachelorsapi.user.UserRepository
import com.geofrat.bachelorsapi.user.UserService
import org.bson.types.ObjectId
import org.springframework.stereotype.Service

@Service
class GroupService (
        private val groupRepository: GroupRepository,
        private val userService: UserService,
        private val groupMembershipRepository: GroupMembershipRepository,
        private val passwordRepository: PasswordRepository,
        private val groupRequestRepository: GroupRequestRepository
) {
    fun createNewGroup(groupDto: GroupDto): GroupDto{
        val groupDoc = GroupDoc(groupName = groupDto.groupName,
                                createdBy = groupDto.createdBy)
        val createdGroupDoc = groupRepository.save(groupDoc)
        val groupMembership = GroupMembership(groupId = createdGroupDoc.id,
                userId = ObjectId(createdGroupDoc.createdBy))
        groupMembershipRepository.save(groupMembership)
        return GroupDto(createdGroupDoc.groupName, createdGroupDoc.createdBy)
    }

    fun deleteGroup(groupId: String, userUid: String) {
        groupMembershipRepository.deleteAllByGroupId(ObjectId(groupId))
        groupRequestRepository.deleteAllByGroupId(ObjectId(groupId))
        val passwords = passwordRepository.findAllByGroupId(ObjectId(groupId))
        val newList = passwords.map {
            PasswordDoc(it.id, it.passwordHash, it.vendorName, it.userAccount, it.userId, null)
        }
        passwordRepository.saveAll(newList)
        groupRepository.deleteById(ObjectId(groupId))
    }

    fun getMyGroups(userUid: String): List<GroupDetails> {
        val owner = userService.findByUid(userUid)
        val groupMemberships = groupMembershipRepository.findAllByUserId(owner.id)
        return groupMemberships.map {
            val group = groupRepository.findById(it.groupId).get()
            val groupMembers = groupMembershipRepository.findAllByGroupId(it.groupId).map { it.userId }
            val users = userService.getUsersByUids(groupMembers).filter { !it.id.equals(group.id) }
            GroupDetails(group.id.toHexString(), group.groupName, group.createdBy, users)
        }
    }

    fun leaveGroup(userUid: String, groupId: String) {
        val owner = userService.findByUid(userUid)
        val group = groupRepository.findById(ObjectId(groupId))
        if (group.isPresent) {
            val groupDoc = group.get()
            if (groupDoc.createdBy.equals(owner.id.toHexString())) {
                deleteGroup(groupId, userUid)
            }
        } else {
            val groupMembership = groupMembershipRepository.findByGroupIdAndUserId(ObjectId(groupId), owner.id)
            groupMembershipRepository.deleteById(groupMembership.id)
        }

    }

    fun getOwnedGroups(userUid: String): List<ObjectId> {
        val owner = userService.findByUid(userUid)
        return groupRepository.findAllByCreatedBy(owner.id.toHexString()).map { it.id }
    }

    fun findById(groupId: ObjectId): GroupDoc {
        return groupRepository.findById(groupId).get()
    }

}
