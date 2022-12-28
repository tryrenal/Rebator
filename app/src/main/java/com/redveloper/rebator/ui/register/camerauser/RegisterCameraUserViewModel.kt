package com.redveloper.rebator.ui.register.camerauser

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.redveloper.rebator.domain.usecase.auth.RegisterCameraUserUseCase
import com.redveloper.rebator.utils.Event
import kotlinx.coroutines.launch
import java.io.File

class RegisterCameraUserViewModel(
    private val cameraUserUseCase: RegisterCameraUserUseCase
) : ViewModel() {

    init {
        cameraUserUseCase.output = RegisterCameraUserUseCase.Output(
            errorPhoto = {
                errorPhotoEvent.value = Event(it)
            }
        )
    }

    val errorPhotoEvent = MutableLiveData<Event<String>>()

    fun submit(file: File?){
        cameraUserUseCase.setPhoto(file)

        viewModelScope.launch {

        }
    }
}