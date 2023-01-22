package com.redveloper.inkubasi.data.network.updateseller

import android.net.Uri
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class UpdateSellerImpl(
    private val firestore: FirebaseFirestore,
    private val storage: FirebaseStorage
) : UpdateSeller{

    private val collectionInkubasi = firestore.collection("inkubasi")
    private val storageRef = storage.reference

    override suspend fun addPhoto(tiktokId: String, uri: Uri, inkubasiName: String): String {
        return suspendCoroutine { continuation ->
            val storageSeller = storageRef.child("sellers/$tiktokId/$inkubasiName")
            val uploadTask: UploadTask = storageSeller.putFile(uri)

            uploadTask.addOnFailureListener{
                continuation.resumeWithException(it)
            }.addOnSuccessListener {
                it.task.continueWithTask { task ->
                    if (!task.isSuccessful){
                        task.exception?.let{
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

    override suspend fun updateSeller(tiktokId: String, data: HashMap<String, Any>): Boolean {
        return suspendCoroutine { continuation ->
            collectionInkubasi
                .document(tiktokId)
                .set(data)
                .addOnFailureListener {
                    continuation.resumeWithException(it)
                }
                .addOnSuccessListener {
                    continuation.resume(true)
                }
        }
    }
}