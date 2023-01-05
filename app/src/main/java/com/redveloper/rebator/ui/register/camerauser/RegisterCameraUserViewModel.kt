package com.redveloper.rebator.ui.register.camerauser

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.redveloper.rebator.domain.usecase.auth.RegisterCameraUserUseCase
import com.redveloper.rebator.utils.Event
import com.redveloper.rebator.utils.State
import kotlinx.coroutines.flow.collect
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
    val loadingEvent = MutableLiveData<Event<Boolean>>()
    val successEvent = MutableLiveData<Event<Boolean>>()
    val errorEvent = MutableLiveData<Event<String>>()

    fun submit(file: File?){
        cameraUserUseCase.setPhoto(file)

        viewModelScope.launch {
            cameraUserUseCase.launch()

            cameraUserUseCase.resultFlow.collect{ state ->
                when(state){
                    is State.Loading -> loadingEvent.value = Event(true)
                    is State.Failed -> {
                        loadingEvent.value = Event(false)
                        errorEvent.value = Event(state.message)
                    }
                    is State.Success -> {
                        loadingEvent.value = Event(false)
                        successEvent.value = Event(state.data)
                    }
                }
            }
        }
    }
}