package com.geofrat.bachelorsapi.password

import org.springframework.stereotype.Service

@Service
class PasswordService (
        private val passwordRepository: PasswordRepository
) {
    fun getAllMyPasswords(appleId: String) : List<PasswordDoc> {
        return passwordRepository.findAllByAppleId(appleId)
    }

    fun createNewPassword(newPassword: PasswordDto) : PasswordDoc {
        if (passwordRepository.existsByAppleIdAndDescription(newPassword.appleId, newPassword.description)) {
            throw PasswordAlreadyExistsException("Such password already exists")
        } else {
            val passwordDoc = PasswordDoc(appleId = newPassword.appleId,
                    passwordHash = newPassword.passwordHash,
                    description = newPassword.description)
            val createdPassword = passwordRepository.save(passwordDoc)
            return createdPassword
        }
    }

    fun modifyPassword(modifiedPassword: PasswordDto) : PasswordDoc {
        if(passwordRepository.existsByAppleIdAndDescription(modifiedPassword.appleId, modifiedPassword.description)) {
            val passwordDoc = passwordRepository.findByAppleIdAndDescription(modifiedPassword.appleId, modifiedPassword.description)
            val newPasswordDoc = PasswordDoc(id = passwordDoc.id,
                    passwordHash = modifiedPassword.passwordHash,
                    appleId = modifiedPassword.appleId,
                    description = modifiedPassword.description)
            return passwordRepository.save(newPasswordDoc)
        } else {
            throw PasswordNotFoundException("Cannot modify, such password doesn't exist")
        }
    }

    fun deletePassword(password: PasswordDto) {
        if(passwordRepository.existsByAppleIdAndDescription(password.appleId, password.description)) {
            val passwordDoc = passwordRepository.findByAppleIdAndDescription(password.appleId, password.description)
            passwordRepository.delete(passwordDoc)
        } else {
            throw PasswordNotFoundException("Cannot delete, such password doesn't exist")
        }
    }



}