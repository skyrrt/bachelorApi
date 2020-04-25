package com.geofrat.bachelorsapi.password

data class PasswordDto (
        val passwordHash: String,
        val appleId: String,
        val description: String
)