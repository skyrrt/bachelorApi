package com.geofrat.bachelorsapi.password

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
class PasswordController (
        private val passwordService: PasswordService
) {
    @GetMapping("/passwords")
    fun getMyAllPasswordHashes(@RequestParam(value="appleId", required = true ) appleId: String) : List<PasswordDoc> {
        val passwordHashes = passwordService.getAllMyPasswords(appleId)
        if (passwordHashes.isEmpty()) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "No passwords saved yet")
        }
        return passwordHashes
    }

    @PostMapping("/passwords")
    fun createNewPassword(@RequestBody password: PasswordDto) : PasswordDoc {
        try {
            return passwordService.createNewPassword(password)
        } catch (e: PasswordAlreadyExistsException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message)
        }
    }

    @PatchMapping("/passwords")
    fun changePasswordHash(@RequestBody password: PasswordDto) : PasswordDoc {
        try {
            return passwordService.modifyPassword(password)
        } catch (e: PasswordNotFoundException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message)
        }
    }

    @DeleteMapping("/passwords")
    fun deletePassword(@RequestBody password: PasswordDto) : ResponseEntity<Any> {
        try {
            passwordService.deletePassword(password)
            return ResponseEntity.ok().build()
        } catch (e: PasswordNotFoundException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message)
        }
    }

}