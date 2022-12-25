package com.redveloper.rebator.domain.repository

import com.redveloper.rebator.data.network.auth.AuthFirebase
import com.redveloper.rebator.data.network.auth.model.LoginRequest
import com.redveloper.rebator.data.network.auth.model.RegisterRequest
import com.redveloper.rebator.domain.entity.User
import com.redveloper.rebator.utils.dispatchers.CrDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await

class UserRepository (
    private val authFirebase: AuthFirebase,
    private val crDispatcher: CrDispatcher
){

    fun loginEmail(email: String, password: String): Flow<User>{
        val request = LoginRequest(email, password)
        return flow {
            val data = authFirebase.loginEmail(request).await()
                .user?.email ?: ""
            emit(User(
                email = data
            ))
        }.flowOn(crDispatcher.network())
    }

    fun registerEmail(email: String, password: String): Flow<String>{
        val request = RegisterRequest("", email, password)
        return flow {
            val userId = authFirebase.registerEmail(request).await().user?.uid ?: ""
            emit(userId)
        }.flowOn(crDispatcher.network())
    }

    fun saveUserData(documentId: String, name: String, email: String): Flow<Boolean>{
        val request = RegisterRequest(name, email, "")
        return flow {
            val data = authFirebase.saveUserData(documentId, request).isSuccessful
            emit(data)
        }.flowOn(crDispatcher.network())
    }
}