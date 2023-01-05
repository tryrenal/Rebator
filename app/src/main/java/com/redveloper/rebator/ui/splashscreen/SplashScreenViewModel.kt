package com.redveloper.rebator.ui.splashscreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.redveloper.rebator.domain.usecase.auth.CheckLoginUseCase
import com.redveloper.rebator.utils.Event
import com.redveloper.rebator.utils.State
import kotlinx.coroutines.launch

class SplashScreenViewModel(
    private val checkLoginUseCase: CheckLoginUseCase
): ViewModel() {

    val loadingEvent = MutableLiveData<Event<Boolean>>()
    val isLoginEvent = MutableLiveData<Event<Boolean>>()
    val errorEvent = MutableLiveData<Event<String>>()

    fun checkLogin(){
        viewModelScope.launch {
            checkLoginUseCase.launch()

            checkLoginUseCase.resultFlow.collect{ state ->
                when(state){
                    is State.Loading -> loadingEvent.value = Event(true)
                    is State.Failed -> {
                        loadingEvent.value = Event(false)
                        errorEvent.value = Event(state.message)
                    }
                    is State.Success -> {
                        loadingEvent.value = Event(false)
                        isLoginEvent.value = Event(state.data)
                    }
                }
            }
        }
    }
}