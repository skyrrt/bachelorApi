package com.geofrat.bachelorsapi.request

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class GroupRequestDoc (
    @Id
    val id: ObjectId = ObjectId.get(),
    val groupId: ObjectId,
    val userId: ObjectId
)