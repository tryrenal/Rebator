package com.redveloper.rebator.domain.repository

import com.google.firebase.auth.AuthResult
import com.redveloper.rebator.data.network.auth.AuthFirebase
import com.redveloper.rebator.data.network.auth.model.LoginRequest
import com.redveloper.rebator.utils.dispatchers.CrDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await

class UserRepository (
    private val authFirebase: AuthFirebase,
    private val crDispatcher: CrDispatcher
){

    fun loginEmail(email: String, password: String): Flow<AuthResult>{
        val request = LoginRequest(email, password)
        return flow {
            val data = authFirebase.loginEmail(request).await()
            emit(data)
        }.flowOn(crDispatcher.network())
    }
}