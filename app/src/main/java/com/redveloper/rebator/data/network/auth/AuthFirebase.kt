package com.redveloper.rebator.data.network.auth

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.firestore.DocumentReference
import com.redveloper.rebator.data.network.auth.model.LoginRequest
import com.redveloper.rebator.data.network.auth.model.RegisterRequest
import kotlinx.coroutines.flow.Flow

interface AuthFirebase {
    fun loginEmail(request: LoginRequest): Task<AuthResult>
    fun registerEmail(email: String, password: String): Task<AuthResult>
    fun saveUserData(documentId: String, request: RegisterRequest): Task<Void>
    fun logout()
}