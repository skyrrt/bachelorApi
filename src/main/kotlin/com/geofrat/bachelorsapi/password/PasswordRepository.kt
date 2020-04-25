package com.geofrat.bachelorsapi.password

import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface PasswordRepository : MongoRepository<PasswordDoc, ObjectId> {
    fun findAllByAppleId(appleId: String) : List<PasswordDoc>
    fun findByAppleIdAndDescription(appleId: String, description: String) : PasswordDoc
    fun existsByAppleIdAndDescription(appleId: String, description: String) : Boolean
}