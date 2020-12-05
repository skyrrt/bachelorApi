package com.geofrat.bachelorsapi.password

import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface PasswordRepository : MongoRepository<PasswordDoc, ObjectId> {
    fun findAllByUserId(userId: String) : List<PasswordDoc>
    fun findAllByGroupIdIn(groupIds: List<String>): List<PasswordDoc>
    fun deleteAllByUserId(userId: String)
    fun findAllByGroupId(groupId: String): List<PasswordDoc>
}