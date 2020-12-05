package com.geofrat.bachelorsapi.password

import com.geofrat.bachelorsapi.group.GroupRepository
import com.geofrat.bachelorsapi.membership.GroupMembershipRepository
import com.geofrat.bachelorsapi.user.UserService
import org.bson.types.ObjectId
import org.springframework.stereotype.Service

@Service
class PasswordService (
        private val passwordRepository: PasswordRepository,
        private val groupMembershipRepository: GroupMembershipRepository,
        private val userService: UserService,
        private val groupRepository: GroupRepository
) {
    fun getAllMyPasswords(userUid: String) : List<PasswordDto> {
        val user = userService.findByUid(userUid)
        return passwordRepository.findAllByUserId(user.id.toHexString())
                .map {
                    var groupName: String? = null
                    if (it.groupId != null) {
                        var groupDoc = groupRepository.findById(ObjectId(it.groupId)).get()
                        groupName = groupDoc.groupName
                    }
                    PasswordDto(it.id.toHexString(), it.passwordHash, it.vendorName, it.userAccount, it.userId, it.groupId, groupName)
                }
    }

    fun createNewPassword(newPassword: PasswordDto) : PasswordDto {
        val user = userService.findByUid(newPassword.userId)
            val passwordDoc = PasswordDoc(userId = user.id.toHexString(),
                    passwordHash = newPassword.passwordHash,
                    vendorName = newPassword.vendorName,
                    userAccount = newPassword.userAccount,
                    groupId = newPassword.groupId)
        val createdPassword =  passwordRepository.save(passwordDoc)
        return PasswordDto(createdPassword.id.toHexString(), createdPassword.passwordHash, createdPassword.vendorName, createdPassword.userAccount, createdPassword.userId, createdPassword.groupId, null)
    }

    fun modifyPassword(modifiedPassword: PasswordDto) : PasswordDto {
        if (passwordRepository.existsById(ObjectId(modifiedPassword.id))) {
            val oldPasswordDoc = passwordRepository.findById(ObjectId(modifiedPassword.id)).get()
            val newPasswordDoc = PasswordDoc(id = ObjectId(modifiedPassword.id),
                        passwordHash = modifiedPassword.passwordHash,
                        vendorName = modifiedPassword.vendorName,
                        userAccount = modifiedPassword.userAccount,
                        userId = oldPasswordDoc.userId,
                        groupId = modifiedPassword.groupId)
            val modifiedPasswordDoc = passwordRepository.save(newPasswordDoc)
            return PasswordDto(modifiedPasswordDoc.id.toHexString(), modifiedPasswordDoc.passwordHash, modifiedPasswordDoc.vendorName, modifiedPasswordDoc.userAccount, modifiedPasswordDoc.userId, modifiedPasswordDoc.groupId, null)
        } else {
            throw PasswordNotFoundException("Cannot modify, such password doesn't exist")
        }
    }

    fun deletePassword(password: PasswordDto) {
        if(passwordRepository.existsById(ObjectId(password.id))) {
            passwordRepository.deleteById(ObjectId(password.id))
        } else {
            throw PasswordNotFoundException("Cannot delete, such password doesn't exist")
        }
    }

    fun getGroupPasswords(userId: String): List<PasswordDto> {
        val user = userService.findByUid(userId)
        val groupMemberships = groupMembershipRepository.findAllByUserId(user.id).map { it.groupId.toHexString() }
        return passwordRepository.findAllByGroupIdIn(groupMemberships).map {
            PasswordDto(it.id.toHexString(), it.passwordHash, it.vendorName, it.userAccount, it.userId, it.groupId, null)
        }.filter { !it.userId.equals(user.id.toHexString()) }
    }
}