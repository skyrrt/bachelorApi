package com.geofrat.bachelorsapi.request

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class GroupRequestController(
        private val groupRequestService: GroupRequestService
) {

    @PostMapping("/requests")
    fun sendRequest(@RequestBody request: GroupRequestDto): ResponseEntity<GroupRequestDto> {
        return ResponseEntity.ok(groupRequestService.createRequest(request))
    }

    @GetMapping("/requests")
    fun getRequests(@RequestParam(value = "userUid") userUid: String): ResponseEntity<List<GroupRequestDetails>> {
        return ResponseEntity.ok(groupRequestService.getRequests(userUid))
    }

    @PatchMapping("/requests")
    fun processRequest(@RequestParam(value = "requestId") requestId: String, @RequestParam("accepted") accepted: Boolean ): ResponseEntity<Any> {
        groupRequestService.processRequest(requestId, accepted)
        return ResponseEntity.ok().build()
    }
}