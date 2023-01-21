package com.redveloper.akusisi.data.network.seller

import android.net.Uri
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class SellerFirebaseImpl(
    private val firestore: FirebaseFirestore,
    private val storage: FirebaseStorage
): SellerFirebase {

    private val collectionSeller = firestore.collection("sellers")
    private val storageRef = storage.reference

    override suspend fun checkTiktokIdAvailable(tiktokId: String): Boolean {
        return collectionSeller
            .document(tiktokId)
            .get()
            .await()
            .exists()
    }

    override suspend fun addSeller(documentId: String, data: HashMap<String, Any>): Boolean {
        return suspendCoroutine { continuation ->
            collectionSeller
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

    override suspend fun addPhotoSeller(documentId: String, uri: Uri): String {
        return suspendCoroutine { continuation ->
            val storageSeller = storageRef.child("sellers/$documentId")
            val uploadTask: UploadTask = storageSeller.putFile(uri)

            uploadTask.addOnFailureListener{
                continuation.resumeWithException(it)
            }.addOnSuccessListener {
                it.task.continueWithTask { task ->
                    if (!task.isSuccessful){
                        task.exception?.let {
                            continuation.resumeWithException(it)
                        }
                    }
                    return@continueWithTask storageSeller.downloadUrl
                }.addOnCompleteListener {
                    if (it.isSuccessful){
                        val downloadUrl = it.result
                        continuation.resume(downloadUrl.toString())
                    }
                }
            }
        }
    }
}