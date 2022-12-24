package com.redveloper.rebator.data.network.auth

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.redveloper.rebator.data.network.auth.model.LoginRequest

class AuthFirebaseImpl (
    private val auth: FirebaseAuth
): AuthFirebase {
    override fun loginEmail(request: LoginRequest): Task<AuthResult> {
        return auth.signInWithEmailAndPassword(request.email, request.password)
    }

    override fun logout() {

    }
}