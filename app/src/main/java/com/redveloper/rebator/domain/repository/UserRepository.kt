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

    fun loginEmail(email: String, password: String): Flow<String>{
        val request = LoginRequest(email, password)
        return flow {
            val emailValue = authFirebase.loginEmail(request).await()
                .user?.email ?: ""
            emit(emailValue)
        }.flowOn(crDispatcher.network())
    }

    fun registerEmail(email: String, password: String): Flow<String>{
        return flow {
            val userId = authFirebase.registerEmail(email, password).await().user?.uid ?: ""
            emit(userId)
        }.flowOn(crDispatcher.network())
    }

    fun saveUserData(documentId: String, request: RegisterRequest): Flow<Boolean>{
        return flow {
            val data = authFirebase.saveUserData(documentId, request).isSuccessful
            emit(data)
        }.flowOn(crDispatcher.network())
    }
}