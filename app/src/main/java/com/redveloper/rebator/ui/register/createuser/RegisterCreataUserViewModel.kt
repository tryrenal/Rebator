package com.redveloper.rebator.ui.register.createuser

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.redveloper.rebator.domain.usecase.auth.RegisterCreateUserUseCase
import com.redveloper.rebator.utils.Event
import com.redveloper.rebator.utils.State
import kotlinx.coroutines.flow.collect
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
    val loadingEvent = MutableLiveData<Event<Boolean>>()
    val errorEvent = MutableLiveData<Event<String>>()
    val successEvent = MutableLiveData<Event<String>>()

    fun submit(email: String?, password: String?){
        createUserUseCase.setEmail(email)
        createUserUseCase.setPassword(password)

        viewModelScope.launch {
            createUserUseCase.launch()

            createUserUseCase.resultFlow.collect{ state ->
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