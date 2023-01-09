package com.redveloper.rebator.ui.register.informasiuser

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.redveloper.rebator.domain.entity.Position
import com.redveloper.rebator.domain.usecase.auth.RegisterInformasiUserUseCase
import com.redveloper.rebator.domain.usecase.user.GetUserUseCase
import com.redveloper.rebator.ui.register.informasiuser.model.RegisterInformasiUserModel
import com.redveloper.rebator.utils.Event
import com.redveloper.rebator.utils.State
import kotlinx.coroutines.launch

class RegisterInformasiUserViewModel(
    private val informasiUserUseCase: RegisterInformasiUserUseCase,
    private val getUserUseCase: GetUserUseCase
): ViewModel() {

    init {
        informasiUserUseCase.output = RegisterInformasiUserUseCase.Output(
            errorName = {
                errorNameEvent.value = Event(it)
            },
            errorPhoneNumber = {
                errorPhoneNumberEvent.value = Event(it)
            },
            errorPosition = {
                errorPositionEvent.value = Event(it)
            }
        )
    }

    val errorSubmitEvent = MutableLiveData<Event<String>>()
    val errorNameEvent = MutableLiveData<Event<String>>()
    val errorPhoneNumberEvent = MutableLiveData<Event<String>>()
    val errorPositionEvent = MutableLiveData<Event<String>>()

    val loadingEvent = MutableLiveData<Event<Boolean>>()

    val errorGetUserEvent = MutableLiveData<Event<String>>()
    val toUserAkusisiEvent = MutableLiveData<Event<Any>>()
    val toUserInkubasiEvent = MutableLiveData<Event<Any>>()

    var positionSelected: Position? = null
        set(value) {field = value}

    fun submit(data: RegisterInformasiUserModel){
        informasiUserUseCase.setName(data.name)
        informasiUserUseCase.setPhoneNumber(data.phoneNumber)
        informasiUserUseCase.setPosition(data.position)

        viewModelScope.launch {
            informasiUserUseCase.launch()

            informasiUserUseCase.resultFlow.collect { state ->
                when(state){
                    is State.Loading -> loadingEvent.value = Event(true)
                    is State.Failed -> {
                        loadingEvent.value = Event(false)
                        errorSubmitEvent.value = Event(state.message)
                    }
                    is State.Success -> {
                        loadingEvent.value = Event(false)
                        getUser()
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