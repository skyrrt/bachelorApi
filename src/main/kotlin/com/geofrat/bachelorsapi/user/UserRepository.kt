package com.geofrat.bachelorsapi.user

import com.geofrat.bachelorsapi.password.PasswordDoc
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface UserRepository: MongoRepository<UserDoc, ObjectId> {
    fun findAllByUidIn(uids: List<ObjectId>) : List<UserDoc>
    fun findByUid(uid: String) : UserDoc
}