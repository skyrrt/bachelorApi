package com.geofrat.bachelorsapi.password

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class PasswordDoc (
        @Id
        val id: ObjectId = ObjectId.get(),
        val passwordHash: String,
        val vendorName: String,
        val userAccount: String,
        val userId: String,
        val groupId: String?
)