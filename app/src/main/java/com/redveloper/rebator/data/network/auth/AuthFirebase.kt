package com.redveloper.rebator.data.network.auth

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.redveloper.rebator.data.network.auth.model.LoginRequest
import kotlinx.coroutines.flow.Flow

interface AuthFirebase {
    fun loginEmail(request: LoginRequest): Task<AuthResult>
    fun logout()
}