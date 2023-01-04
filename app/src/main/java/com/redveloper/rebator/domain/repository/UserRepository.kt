package com.redveloper.rebator.domain.repository

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

    suspend fun loginEmail(email: String, password: String): Result<String>{
        val request = LoginRequest(email, password)
        return CoroutineScope(crDispatcher.network()).async {
            authFirebase.loginEmail(request)
        }.await()
    }

    suspend fun registerEmail(email: String, password: String): Result<String>{
        return CoroutineScope(crDispatcher.network()).async {
            authFirebase.registerEmail(email, password)
        }.await()
    }

    suspend fun saveUserData(documentId: String, data: HashMap<String, Any>){
        CoroutineScope(crDispatcher.network()).launch {
            if (authFirebase.checkDocumentIsExist(documentId)){
                authFirebase.updateUser(documentId, data)
            } else {
                authFirebase.setUser(documentId, data)
            }
        }
    }
}