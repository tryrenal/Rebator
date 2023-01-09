package com.redveloper.rebator.data.network.user

import com.redveloper.rebator.domain.entity.User

interface UserFirebase {
    suspend fun getUser(userId: String): User
    suspend fun editUser(userId: String, user: HashMap<String, Any>): Boolean
}