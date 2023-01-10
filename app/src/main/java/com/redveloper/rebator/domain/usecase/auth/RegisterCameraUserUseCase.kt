package com.redveloper.rebator.domain.usecase.auth

import android.net.Uri
import com.redveloper.rebator.data.local.preference.UserPreference
import com.redveloper.rebator.domain.repository.AuthRepository
import com.redveloper.rebator.domain.repository.UserRepository
import com.redveloper.rebator.domain.usecase.FlowUseCase
import com.redveloper.rebator.utils.State
import com.redveloper.rebator.utils.dispatchers.CrDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.File

class RegisterCameraUserUseCase(
    private val crDispatcher: CrDispatcher,
    private val authRepository: AuthRepository,
    private val userPreference: UserPreference
): FlowUseCase<State<Boolean>>() {

    private var photo: Pair<File?, Boolean> = Pair(null, false)

    var output: Output? = null

    override fun perfomAction(): Flow<State<Boolean>> {
        return flow <State<Boolean>>{
            emit(State.loading())

            if (photo.second){
                userPreference.getUserID().collect{ docId ->
                    docId?.let {
                        val photoUrl = authRepository.setPhotoUser(it, Uri.fromFile(photo.first))
                        val mapData = hashMapOf<String, Any>(
                            "photoUrl" to photoUrl
                        )
                        val saveData = authRepository.saveUserData(docId, mapData)
                        emit(State.success(saveData))
                    }
                }
            }

        }.catch {
            emit(State.failed(it.message.toString()))
        }.flowOn(crDispatcher.ui())
    }

    fun setPhoto(file: File?){
        if (file == null){
            output?.errorPhoto?.invoke("photo masih kosong")
            photo = Pair(null, false)
        } else {
            photo = Pair(file, true)
        }
    }

    data class Output(
        val errorPhoto: ((String) -> Unit)? = null
    )
}



























