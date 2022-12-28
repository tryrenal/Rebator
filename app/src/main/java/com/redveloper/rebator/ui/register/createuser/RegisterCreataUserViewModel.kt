package com.redveloper.rebator.ui.register.createuser

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.redveloper.rebator.domain.usecase.auth.RegisterCreateUserUseCase
import com.redveloper.rebator.utils.Event
import kotlinx.coroutines.launch

class RegisterCreataUserViewModel(
    private val createUserUseCase: RegisterCreateUserUseCase
): ViewModel() {

    init {
        createUserUseCase.output = RegisterCreateUserUseCase.Output(
            errorEmail = {
                errorEmailEvent.value = Event(it)
            },
            errorPassword = {
                errorPasswordEvent.value = Event(it)
            }
        )
    }

    val errorEmailEvent = MutableLiveData<Event<String>>()
    val errorPasswordEvent = MutableLiveData<Event<String>>()

    val resultCreateUser = createUserUseCase.resultFlow.asLiveData()

    fun submit(email: String?, password: String?){
        createUserUseCase.setEmail(email)
        createUserUseCase.setPassword(password)

        viewModelScope.launch {
            createUserUseCase.launch()
        }
    }
}