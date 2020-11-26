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
    fun getUsersByUids(uids: List<ObjectId>) : List<UserDoc> {
        return userRepository.findAllByUidIn(uids)
    }

    fun saveNewUser(user: UserDto): UserDto {
        val userDoc = UserDoc(uid = user.uid,
                            email = user.email)
        val newUser = userRepository.save(userDoc)
        return UserDto(newUser.email, newUser.uid)
    }

    fun findByUid(userUid: String): UserDoc {
        println(userUid)
        return userRepository.findByUid(userUid)
    }

    fun findByUserId(userId: ObjectId): UserDoc {
        return userRepository.findById(userId).get()
    }

    fun deleteUser(userUid: String) {
        val user = userRepository.findByUid(userUid)
        passwordRepository.deleteAllByUserId(user.id.toHexString())
        groupRepository.deleteAllByCreatedBy(user.id.toHexString())
        groupRequestRepository.deleteAllByUserId(user.id)
        groupMembershipRepository.deleteAllByUserId(user.id)
        userRepository.delete(user)
    }
}