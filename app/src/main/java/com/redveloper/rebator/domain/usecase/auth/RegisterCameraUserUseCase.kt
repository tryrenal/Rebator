package com.redveloper.rebator.domain.usecase.auth

import com.redveloper.rebator.domain.repository.UserRepository
import com.redveloper.rebator.utils.dispatchers.CrDispatcher
import java.io.File

class RegisterCameraUserUseCase(
    private val crDispatcher: CrDispatcher,
    private val userRepository: UserRepository
) {

    private var photo: Pair<File?, Boolean> = Pair(null, false)

    var output: Output? = null

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