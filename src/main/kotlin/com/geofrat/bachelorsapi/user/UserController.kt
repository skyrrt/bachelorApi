package com.geofrat.bachelorsapi.user

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class UserController (
        private val userService: UserService
) {
    @PostMapping("/users")
    fun saveNewUser(@RequestBody user: UserDto) : ResponseEntity<UserDto> {
        return ResponseEntity.ok(userService.saveNewUser(user))
    }

    @DeleteMapping("/users/{userUid}")
    fun deleteUser(@PathVariable(value = "userUid") userUid: String) {
        userService.deleteUser(userUid)
    }
}