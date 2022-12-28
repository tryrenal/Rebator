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
    val loadingEvent = MutableLiveData<Event<Boolean>>()
    val errorSubmitEvent = MutableLiveData<Event<String>>()
    val successSubmitEvent = MutableLiveData<Event<String>>()

    fun submit(email: String?, password: String?){
        loginUseCase.setEmail(email)
        loginUseCase.setPassword(password)

        viewModelScope.launch {
            loginUseCase.launch()

            loginUseCase.resultFlow.collect{ state ->
                when(state){
                    is State.Loading -> loadingEvent.value = Event(true)
                    is State.Failed -> {
                        loadingEvent.value = Event(false)
                        errorSubmitEvent.value = Event(state.message)
                    }
                    is State.Success -> {
                        loadingEvent.value = Event(false)
                        successSubmitEvent.value = Event(state.data)
                    }
                }
            }
        }
    }
}