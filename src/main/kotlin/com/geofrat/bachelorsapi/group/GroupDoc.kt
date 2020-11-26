package com.geofrat.bachelorsapi.group

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class GroupDoc (
        @Id
        val id: ObjectId = ObjectId.get(),
        val groupName: String,
        val createdBy: String
)