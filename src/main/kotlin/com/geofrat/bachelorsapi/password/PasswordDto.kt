package com.geofrat.bachelorsapi.password

data class PasswordDto (
        val id: String?,
        val passwordHash: String,
        val vendorName: String,
        val userAccount: String,
        val userId: String,
        val groupId: String?
)