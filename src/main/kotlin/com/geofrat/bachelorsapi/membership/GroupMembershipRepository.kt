package com.geofrat.bachelorsapi.membership

import com.geofrat.bachelorsapi.membership.GroupMembership
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface GroupMembershipRepository: MongoRepository<GroupMembership, ObjectId> {
    fun findAllByUserId(userId: ObjectId) : List<GroupMembership>
    fun findAllByGroupId(groupId: ObjectId) : List<GroupMembership>
    fun findByGroupIdAndUserId(groupId: ObjectId, userId: ObjectId): GroupMembership
    fun deleteAllByGroupIdIn(groupIds: List<ObjectId>)
    fun deleteAllByGroupId(groupId: ObjectId)
}
