package com.redveloper.rebator.data.network.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.redveloper.rebator.data.network.auth.model.request.LoginRequest
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

    override suspend fun checkDocumentIsExist(documentId: String): Boolean {
        return collectionUser
            .document(documentId)
            .get()
            .await()
            .exists()
    }

    override suspend fun setUser(documentId: String, data: HashMap<String, Any>) {
        collectionUser
            .document(documentId)
            .set(data)
            .await()
    }

    override suspend fun updateUser(documentId: String, data: HashMap<String, Any>) {
        collectionUser
            .document(documentId)
            .update(data)
            .await()
    }

    override fun logout() {

    }
}