package com.redveloper.rebator.data.network.auth

import android.net.Uri
import com.redveloper.rebator.data.network.auth.model.request.LoginRequest

interface AuthFirebase {
    suspend fun loginEmail(request: LoginRequest): String?
    suspend fun registerEmail(email: String, password: String): String?
    suspend fun checkDocumentIsExist(documentId: String): Boolean
    suspend fun setUser(documentId: String, data: HashMap<String, Any>): Boolean
    suspend fun updateUser(documentId: String, data: HashMap<String, Any>): Boolean
    suspend fun savePhotoUser(documentId: String, uri: Uri): String
    suspend fun logout()
}