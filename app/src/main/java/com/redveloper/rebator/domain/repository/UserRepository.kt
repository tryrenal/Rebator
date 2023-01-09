package com.redveloper.rebator.domain.repository

import com.redveloper.rebator.data.network.user.UserFirebase
import com.redveloper.rebator.domain.entity.User
import com.redveloper.rebator.utils.dispatchers.CrDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async

class UserRepository (
    private val userFirebase: UserFirebase,
    private val crDispatcher: CrDispatcher
){

    suspend fun getUser(userId: String): User{
        return CoroutineScope(crDispatcher.network()).async {
            userFirebase.getUser(userId)
        }.await()
    }
}