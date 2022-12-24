package com.redveloper.rebator.ui.login

import androidx.lifecycle.*
import com.google.firebase.auth.AuthResult
import com.redveloper.rebator.domain.usecase.auth.LoginUseCase
import com.redveloper.rebator.utils.Event
import com.redveloper.rebator.utils.State
import kotlinx.coroutines.launch

class LoginViewModel (
    private val loginUseCase: LoginUseCase
) : ViewModel(){

    init {
        loginUseCase.output = LoginUseCase.Output (
            errorEmail = {
                errorEmailEvent.value = Event(it)
            },
            errorPassword = {
                errorPasswordEvent.value = Event(it)
            },

        )
    }

    val errorEmailEvent = MutableLiveData<Event<String>>()
    val errorPasswordEvent = MutableLiveData<Event<String>>()
    val loginResult = loginUseCase.resultFlow.asLiveData()

    fun submit(email: String?, password: String?){
        loginUseCase.setEmail(email)
        loginUseCase.setPassword(password)

        viewModelScope.launch {
            loginUseCase.launch()
        }
    }
}