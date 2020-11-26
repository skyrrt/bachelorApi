package com.geofrat.bachelorsapi.group

import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface GroupRepository: MongoRepository<GroupDoc, ObjectId> {
    fun findAllByCreatedBy(userId: String): List<GroupDoc>
    fun deleteAllByCreatedBy(createdBy: String)
}
