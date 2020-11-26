package com.geofrat.bachelorsapi.request

import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface GroupRequestRepository: MongoRepository<GroupRequestDoc, ObjectId> {
    fun findAllByGroupIdIn(groupIds: List<ObjectId>): List<GroupRequestDoc>
    fun deleteAllByUserId(userId: ObjectId)
    fun deleteAllByGroupId(groupId: ObjectId)
}