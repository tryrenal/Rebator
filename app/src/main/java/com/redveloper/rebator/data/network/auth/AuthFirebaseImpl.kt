package com.redveloper.rebator.data.network.auth

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.redveloper.rebator.data.network.auth.model.LoginRequest
import com.redveloper.rebator.data.network.auth.model.RegisterRequest
import kotlinx.coroutines.tasks.await

class AuthFirebaseImpl (
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
): AuthFirebase {

    private val collectionUser = firestore.collection("users")

    override fun loginEmail(request: LoginRequest): Task<AuthResult> {
        return auth.signInWithEmailAndPassword(request.email, request.password)
    }

    override fun registerEmail(email: String, password: String): Task<AuthResult> {
        return auth.createUserWithEmailAndPassword(email, password)
    }

    override fun saveUserData(
        documentId: String,
        request: RegisterRequest
    ): Task<Void> {
        val user = hashMapOf(
            "name" to request.name,
            "email" to request.email,
            "phone_number" to request.phoneNumber,
            "position" to request.posisi.name,
            "photo" to request.photo
        )
        return collectionUser.document(documentId).set(user)
    }

    override fun logout() {

    }
}