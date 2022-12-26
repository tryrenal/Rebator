package com.redveloper.rebator.ui.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.redveloper.rebator.domain.entity.Position
import com.redveloper.rebator.domain.usecase.auth.RegisterUseCase
import com.redveloper.rebator.ui.register.model.RegisterModel
import com.redveloper.rebator.utils.Event
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val registerUseCase: RegisterUseCase
): ViewModel() {

    init {
        registerUseCase.output = RegisterUseCase.Output(
            errorName = {
                errorNameEvent.value = Event(it)
            },
            errorEmail = {
                errorEmailEvent.value = Event(it)
            },
            errorPassword = {
                errorPasswordEvent.value = Event(it)
            },
            errorPhoneNumber = {
                errorPhoneNumberEvent.value = Event(it)
            },
            errorPhoto = {
                errorPhotoEvent.value = Event(it)
            },
            errorPosition = {
                errorPositionEvent.value = Event(it)
            }
        )
    }

    val errorNameEvent = MutableLiveData<Event<String>>()
    val errorEmailEvent = MutableLiveData<Event<String>>()
    val errorPasswordEvent = MutableLiveData<Event<String>>()
    val errorPhoneNumberEvent = MutableLiveData<Event<String>>()
    val errorPhotoEvent = MutableLiveData<Event<String>>()
    val errorPositionEvent = MutableLiveData<Event<String>>()

    val registerResult = registerUseCase.resultFlow.asLiveData()

    var positionSelected: Position? = null
        set(value) {field = value}

    fun submit(data: RegisterModel){
        registerUseCase.setEmail(data.email)
        registerUseCase.setPassword(data.password)

        registerUseCase.setName(data.name)
        registerUseCase.setPhoneNumber(data.phoneNumber)
        registerUseCase.setPosition(data.posisi)
        registerUseCase.setPhoto(data.photo)

        viewModelScope.launch {
            registerUseCase.launch()
        }
    }
}