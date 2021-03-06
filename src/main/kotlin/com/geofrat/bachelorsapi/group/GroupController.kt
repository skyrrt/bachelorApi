package com.geofrat.bachelorsapi.group

import com.geofrat.bachelorsapi.user.UserDetails
import com.geofrat.bachelorsapi.user.UserDoc
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class GroupController (
        private val groupService: GroupService
) {
    @PostMapping("/groups")
    fun createGroup(@RequestBody groupDto: GroupDto): ResponseEntity<GroupDto> {
        return ResponseEntity.ok(groupService.createNewGroup(groupDto))
    }

    @GetMapping("/groups")
    fun getMyGroups(@RequestParam("userUid") userUid: String) : ResponseEntity<List<GroupDetails>> {
        return ResponseEntity.ok(groupService.getMyGroups(userUid))
    }

    @DeleteMapping("/groups")
    fun leaveGroup(@RequestParam(value = "groupId") groupId: String, @RequestParam(value = "userUid") userUid: String): ResponseEntity<Any> {
        groupService.leaveGroup(userUid, groupId)
        return ResponseEntity.ok().build()
    }

    @GetMapping("/groups/users")
    fun getGroupMembers(@RequestParam(value = "groupId") groupId: String, @RequestParam(value = "userUid") userUid: String): ResponseEntity<List<UserDetails>> {
        return ResponseEntity.ok(groupService.getGroupMembers(groupId,userUid))
    }

    @DeleteMapping("/groups/users")
    fun deleteUserFromGroup(@RequestParam(value = "groupId") groupId: String, @RequestParam(value = "userId") userId: String): ResponseEntity<Any> {
        groupService.deleteUserFromGroup(groupId, userId)
        return ResponseEntity.ok().build()
    }


}