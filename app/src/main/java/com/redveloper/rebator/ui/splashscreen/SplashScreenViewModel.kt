package com.redveloper.rebator.ui.splashscreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.redveloper.rebator.data.local.preference.onboarding.OnBoardingPreference
import com.redveloper.rebator.domain.entity.Position
import com.redveloper.rebator.domain.usecase.auth.CheckLoginUseCase
import com.redveloper.rebator.domain.usecase.user.GetUserUseCase
import com.redveloper.rebator.utils.Event
import com.redveloper.rebator.utils.State
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SplashScreenViewModel(
    private val checkLoginUseCase: CheckLoginUseCase,
    private val onBoardingPreference: OnBoardingPreference,
    private val getUserUseCase: GetUserUseCase
): ViewModel() {

    val loadingEvent = MutableLiveData<Event<Boolean>>()
    val errorEvent = MutableLiveData<Event<String>>()
    val toOnBoardingEvent = MutableLiveData<Event<Any>>()
    val toLoginEvent = MutableLiveData<Event<Any>>()
    val errorGetUserEvent = MutableLiveData<Event<String>>()
    val toUserAkusisiEvent = MutableLiveData<Event<Any>>()
    val toUserInkubasiEvent = MutableLiveData<Event<Any>>()

    fun checkLoginAndOnBoardingStatus(){
        viewModelScope.launch {
            onBoardingPreference.getOnBoardingStatus().collectLatest { done ->
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
                    if (state.data){
                        getUser()
                    } else {
                        toLoginEvent.value = Event(Any())
                    }
                }
            }
        }
    }

    private suspend fun getUser(){
        getUserUseCase.launch()

        getUserUseCase.resultFlow.collect{ state ->
            when(state) {
                is State.Loading -> loadingEvent.value = Event(true)
                is State.Failed -> {
                    loadingEvent.value = Event(false)
                    errorGetUserEvent.value = Event(state.message)
                }
                is State.Success -> {
                    loadingEvent.value = Event(false)
                    val userType = state.data.position
                    when(userType){
                        Position.INKUBASI -> toUserInkubasiEvent.value = Event(Any())
                        Position.AKUSISI -> toUserAkusisiEvent.value = Event(Any())
                    }
                }
            }
        }
    }
}