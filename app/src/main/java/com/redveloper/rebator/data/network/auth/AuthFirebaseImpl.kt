package com.redveloper.rebator.data.network.auth

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import com.redveloper.rebator.data.network.auth.model.request.LoginRequest
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class AuthFirebaseImpl (
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val storage: FirebaseStorage
): AuthFirebase {

    private val collectionUser = firestore.collection("users")
    private val storageRef = storage.reference

    override suspend fun loginEmail(request: LoginRequest): String? {
        return suspendCoroutine { continuation ->
            auth.signInWithEmailAndPassword(request.email, request.password)
                .addOnFailureListener {
                    continuation.resumeWithException(it)
                }
                .addOnSuccessListener {
                    val userId = it.user?.uid
                    continuation.resume(userId)
                }
        }
    }

    override suspend fun registerEmail(email: String, password: String): String?{
        return suspendCoroutine { continuation ->
            auth.createUserWithEmailAndPassword(email, password)
                .addOnFailureListener {
                    continuation.resumeWithException(it)
                }
                .addOnSuccessListener {
                    val userId = it.user?.uid
                    continuation.resume(userId)
                }
        }
    }

    override suspend fun checkDocumentIsExist(documentId: String): Boolean {
        return collectionUser
            .document(documentId)
            .get()
            .await()
            .exists()
    }

    override suspend fun setUser(documentId: String, data: HashMap<String, Any>): Boolean {
        return suspendCoroutine { continuation ->
            collectionUser
                .document(documentId)
                .set(data)
                .addOnFailureListener {
                    continuation.resumeWithException(it)
                }
                .addOnSuccessListener {
                    continuation.resume(true)
                }
        }
    }

    override suspend fun updateUser(documentId: String, data: HashMap<String, Any>): Boolean {
        return suspendCoroutine { continuation ->
            collectionUser
                .document(documentId)
                .update(data)
                .addOnFailureListener {
                    continuation.resumeWithException(it)
                }
                .addOnSuccessListener {
                    continuation.resume(true)
                }
        }
    }

    override suspend fun savePhotoUser(documentId: String, uri: Uri): String {
        return suspendCoroutine { continuation ->
            val storageUser = storageRef.child("users/$documentId")
            val uploadTask: UploadTask = storageUser.putFile(uri)

            uploadTask.addOnFailureListener {
                continuation.resumeWithException(it)
            }.addOnSuccessListener{
                it.task.continueWithTask { task ->
                    if (!task.isSuccessful){
                        task.exception?.let { exception ->
                            continuation.resumeWithException(exception)
                        }
                    }
                    return@continueWithTask storageUser.downloadUrl
                }.addOnCompleteListener{
                        if (it.isSuccessful){
                            val downloadUrl = it.result
                            continuation.resume(downloadUrl.toString())
                        }
                    }
            }
        }
    }

    override suspend fun logout(){
        auth.signOut()
    }
}