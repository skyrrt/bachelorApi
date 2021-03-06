package com.geofrat.bachelorsapi.user

import com.geofrat.bachelorsapi.group.GroupRepository
import com.geofrat.bachelorsapi.membership.GroupMembershipRepository
import com.geofrat.bachelorsapi.password.PasswordRepository
import com.geofrat.bachelorsapi.request.GroupRequestRepository
import org.bson.types.ObjectId
import org.springframework.stereotype.Service

@Service
class UserService (
        private val userRepository: UserRepository,
        private val passwordRepository: PasswordRepository,
        private val groupRepository: GroupRepository,
        private val groupMembershipRepository: GroupMembershipRepository,
        private val groupRequestRepository: GroupRequestRepository
) {
    fun getUsersByIds(uids: List<ObjectId>) : List<UserDoc> {
        return userRepository.findAllByIdIn(uids)
    }

    fun saveNewUser(user: UserDto): UserDto {
        val userDoc = UserDoc(uid = user.uid,
                            email = user.email)
        val newUser = userRepository.save(userDoc)
        return UserDto(newUser.email, newUser.uid)
    }

    fun findByUid(userUid: String): UserDoc {
        return userRepository.findByUid(userUid)
    }

    fun findByUserId(userId: ObjectId): UserDoc {
        return userRepository.findById(userId).get()
    }

    fun deleteUser(userUid: String) {
        val user = userRepository.findByUid(userUid)
        val groupIdsToDelete = groupRepository.findAllByCreatedBy(userUid).map { it.id }
        passwordRepository.deleteAllByUserId(user.id.toHexString())
        groupRepository.deleteAllByCreatedBy(userUid)
        groupRequestRepository.deleteAllByUserId(user.id)
        groupMembershipRepository.deleteAllByGroupIdIn(groupIdsToDelete)
        userRepository.delete(user)
    }
}