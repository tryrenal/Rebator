package com.redveloper.rebator.domain.repository

import com.redveloper.rebator.data.network.auth.AuthFirebase
import com.redveloper.rebator.data.network.auth.model.request.LoginRequest
import com.redveloper.rebator.data.network.auth.model.request.RegisterRequest
import com.redveloper.rebator.utils.dispatchers.CrDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.tasks.await

class UserRepository (
    private val authFirebase: AuthFirebase,
    private val crDispatcher: CrDispatcher
){

    fun loginEmail(email: String, password: String): Flow<Result<String>>{
        val request = LoginRequest(email, password)
        return flow {
            emit(authFirebase.loginEmail(request))
        }.flowOn(crDispatcher.network())
    }

    fun registerEmail(email: String, password: String): Flow<Result<String>>{
        return flow {
            emit(authFirebase.registerEmail(email, password))
        }.flowOn(crDispatcher.network())
    }

    fun saveUserData(documentId: String, request: RegisterRequest): Flow<Boolean>{
        return flow {
            val data = authFirebase.saveUserData(documentId, request).isSuccessful
            emit(data)
        }.flowOn(crDispatcher.network())
    }
}