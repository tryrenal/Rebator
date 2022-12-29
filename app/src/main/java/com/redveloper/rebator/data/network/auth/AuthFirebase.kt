package com.redveloper.rebator.data.network.auth

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.redveloper.rebator.data.network.auth.model.request.LoginRequest
import com.redveloper.rebator.data.network.auth.model.request.RegisterRequest
import kotlinx.coroutines.flow.Flow

interface AuthFirebase {
    suspend fun loginEmail(request: LoginRequest): Result<String>
    fun registerEmail(email: String, password: String): Task<AuthResult>
    fun saveUserData(documentId: String, request: RegisterRequest): Task<Void>
    fun logout()
}