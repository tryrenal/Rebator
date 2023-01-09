package com.redveloper.rebator.data.network.user

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.redveloper.rebator.domain.entity.Position
import com.redveloper.rebator.domain.entity.User
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class UserFirebaseImpl (
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
): UserFirebase{

    private val collectionUser = firestore.collection("users")

    override suspend fun getUser(userId: String): User {
        return suspendCoroutine { continuation ->
            collectionUser
                .document(userId)
                .get()
                .addOnFailureListener{
                    continuation.resumeWithException(it)
                }
                .addOnSuccessListener {
                    val fieldPosition = it["position"].toString()

                    val user = User(
                        email = it["email"].toString(),
                        name = it["name"].toString(),
                        photoUrl = it["photoUrl"].toString(),
                        position = Position.valueOf(fieldPosition),
                        phoneNumber = it["phoneNumber"].toString()
                    )

                    continuation.resume(user)
                }
        }
    }

    override suspend fun editUser(userId: String, user: HashMap<String, Any>): Boolean {
        return suspendCoroutine { continuation ->
            collectionUser
                .document(userId)
                .update(user)
                .addOnFailureListener {
                    continuation.resumeWithException(it)
                }
                .addOnSuccessListener {
                    continuation.resume(true)
                }
        }
    }
}