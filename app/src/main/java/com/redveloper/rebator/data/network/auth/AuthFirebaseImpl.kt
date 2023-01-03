package com.redveloper.rebator.data.network.auth

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.redveloper.rebator.data.network.auth.model.request.LoginRequest
import com.redveloper.rebator.data.network.auth.model.request.RegisterRequest
import kotlinx.coroutines.tasks.await

class AuthFirebaseImpl (
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
): AuthFirebase {

    private val collectionUser = firestore.collection("users")

    override suspend fun loginEmail(request: LoginRequest): Result<String> {
        return try {
            val userId = auth.signInWithEmailAndPassword(request.email, request.password).await()
                .user?.uid ?: ""
            Result.success(userId)
        }catch (e: Exception){
            Result.failure(e)
        }
    }

    override suspend fun registerEmail(email: String, password: String): Result<String> {
        return try {
            val userId = auth.createUserWithEmailAndPassword(email, password).await()
                .user?.uid ?: ""
            Result.success(userId)
        } catch (e: Exception){
            Result.failure(e)
        }
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