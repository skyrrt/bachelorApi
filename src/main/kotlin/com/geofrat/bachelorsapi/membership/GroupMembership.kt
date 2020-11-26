package com.geofrat.bachelorsapi.membership

import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.mapping.Document

data class GroupMembership (
        val id: ObjectId = ObjectId.get(),
        val groupId: ObjectId,
        val userId: ObjectId
)