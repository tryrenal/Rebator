package com.redveloper.akusisi.ui.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.redveloper.rebator.domain.entity.User
import com.redveloper.rebator.domain.usecase.auth.LogoutUseCase
import com.redveloper.rebator.domain.usecase.user.GetUserUseCase
import com.redveloper.rebator.utils.Event
import com.redveloper.rebator.utils.State
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val getUserUseCase: GetUserUseCase,
    private val logoutUseCase: LogoutUseCase
): ViewModel() {

    val loadingEvent = MutableLiveData<Event<Boolean>>()
    val errorGetUserEvent = MutableLiveData<Event<String>>()
    val userEvent = MutableLiveData<Event<User>>()

    val logoutUserEvent = MutableLiveData<Event<Boolean>>()
    val errorLogoutEvent = MutableLiveData<Event<String>>()

    fun getUser(){
        viewModelScope.launch {
            getUserUseCase.launch()

            getUserUseCase.resultFlow.collect{ state ->
                when(state){
                    is State.Loading -> loadingEvent.value = Event(true)
                    is State.Failed -> {
                        loadingEvent.value = Event(false)
                        errorGetUserEvent.value = Event(state.message)
                    }
                    is State.Success -> {
                        loadingEvent.value = Event(false)
                        userEvent.value = Event(state.data)
                    }
                }
            }
        }
    }

    fun logout(){
        viewModelScope.launch {
            logoutUseCase.launch()
            logoutUseCase.resultFlow.collect{ state ->
                when(state){
                    is State.Loading -> loadingEvent.value = Event(true)
                    is State.Failed -> {
                        loadingEvent.value = Event(false)
                        errorLogoutEvent.value = Event(state.message)
                    }
                    is State.Success -> {
                        loadingEvent.value = Event(false)
                        logoutUserEvent.value = Event(state.data)
                    }
                }
            }
        }
    }
}