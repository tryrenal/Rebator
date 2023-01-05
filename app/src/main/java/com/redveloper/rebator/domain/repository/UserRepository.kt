package com.redveloper.rebator.domain.repository

import android.net.Uri
import com.redveloper.rebator.data.network.auth.AuthFirebase
import com.redveloper.rebator.data.network.auth.model.request.LoginRequest
import com.redveloper.rebator.utils.dispatchers.CrDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class UserRepository (
    private val authFirebase: AuthFirebase,
    private val crDispatcher: CrDispatcher
){

    suspend fun loginEmail(email: String, password: String): String?{
        val request = LoginRequest(email, password)
        return CoroutineScope(crDispatcher.network()).async {
            authFirebase.loginEmail(request)
        }.await()
    }

    suspend fun registerEmail(email: String, password: String): String?{
        return CoroutineScope(crDispatcher.network()).async {
            authFirebase.registerEmail(email, password)
        }.await()
    }

    suspend fun saveUserData(documentId: String, data: HashMap<String, Any>): Boolean{
        return CoroutineScope(crDispatcher.network()).async {
            if (authFirebase.checkDocumentIsExist(documentId)){
                authFirebase.updateUser(documentId, data)
            } else {
                authFirebase.setUser(documentId, data)
            }
        }.await()
    }

    suspend fun setPhotoUser(documentId: String, uri: Uri): String{
        return CoroutineScope(crDispatcher.network()).async {
            authFirebase.savePhotoUser(documentId, uri)
        }.await()
    }
}