package com.redveloper.rebator.ui.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.redveloper.rebator.domain.usecase.auth.RegisterUseCase
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
            }
        )
    }

    val errorNameEvent = MutableLiveData<Event<String>>()
    val errorEmailEvent = MutableLiveData<Event<String>>()
    val errorPasswordEvent = MutableLiveData<Event<String>>()

    val registerResult = registerUseCase.resultFlow.asLiveData()

    fun submit(name: String?, email: String?, password: String?){
        registerUseCase.setName(name)
        registerUseCase.setEmail(email)
        registerUseCase.setPassword(password)

        viewModelScope.launch {
            registerUseCase.launch()
        }
    }
}