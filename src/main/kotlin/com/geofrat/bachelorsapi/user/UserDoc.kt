package com.geofrat.bachelorsapi.user

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class UserDoc (
        @Id
        val id: ObjectId = ObjectId.get(),
        val email: String,
        val uid: String
)