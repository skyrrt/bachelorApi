package com.geofrat.bachelorsapi.group

import com.geofrat.bachelorsapi.user.UserDoc


data class GroupDetails(
        val id: String,
        val groupName: String,
        val createdBy: String,
        val members: List<UserDoc>
)