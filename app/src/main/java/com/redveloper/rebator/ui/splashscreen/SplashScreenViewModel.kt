package com.redveloper.rebator.ui.splashscreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.redveloper.rebator.domain.usecase.auth.CheckLoginUseCase
import com.redveloper.rebator.domain.usecase.onboarding.GetOnBoardingUseCase
import com.redveloper.rebator.utils.Event
import com.redveloper.rebator.utils.State
import kotlinx.coroutines.launch

class SplashScreenViewModel(
    private val checkLoginUseCase: CheckLoginUseCase,
    private val getOnBoardingUseCase: GetOnBoardingUseCase
): ViewModel() {

    val loadingEvent = MutableLiveData<Event<Boolean>>()
    val isLoginEvent = MutableLiveData<Event<Boolean>>()
    val errorEvent = MutableLiveData<Event<String>>()
    val toOnBoardingEvent = MutableLiveData<Event<Any>>()

    fun checkLoginAndOnBoardingStatus(){
        viewModelScope.launch {
            getOnBoardingUseCase.getOnBoardingStatus { done ->
                if (done){
                    launch {
                        checkLogin()
                    }
                } else {
                    toOnBoardingEvent.value = Event(Any())
                }
            }
        }
    }

    private suspend fun checkLogin(){
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