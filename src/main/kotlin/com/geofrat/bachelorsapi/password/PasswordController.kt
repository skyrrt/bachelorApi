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
    fun getMyAllPasswordHashes(@RequestParam(value="userId", required = true ) userId: String) : ResponseEntity<List<PasswordDto>> {
        val passwordHashes = passwordService.getAllMyPasswords(userId)
        return ResponseEntity.ok(passwordHashes)
    }

    @PostMapping("/passwords")
    fun createNewPassword(@RequestBody password: PasswordDto) : ResponseEntity<PasswordDto> {
            return ResponseEntity.ok(passwordService.createNewPassword(password))
    }

    @PatchMapping("/passwords")
    fun changePasswordHash(@RequestBody password: PasswordDto) : ResponseEntity<PasswordDto> {
        try {
            return ResponseEntity.ok(passwordService.modifyPassword(password))
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

    @GetMapping("/passwords/groups/{userId}")
    fun getGroupPasswords(@PathVariable(required = true) userId: String): ResponseEntity<List<PasswordDto>> {
        return ResponseEntity.ok(passwordService.getGroupPasswords(userId))
    }

}