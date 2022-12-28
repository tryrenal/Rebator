package com.redveloper.rebator.ui.register.informasiuser

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.redveloper.rebator.domain.entity.Position
import com.redveloper.rebator.domain.usecase.auth.RegisterInformasiUserUseCase
import com.redveloper.rebator.ui.register.informasiuser.model.RegisterInformasiUserModel
import com.redveloper.rebator.utils.Event
import kotlinx.coroutines.launch

class RegisterInformasiUserViewModel(
    private val informasiUserUseCase: RegisterInformasiUserUseCase
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

    val errorNameEvent = MutableLiveData<Event<String>>()
    val errorPhoneNumberEvent = MutableLiveData<Event<String>>()
    val errorPositionEvent = MutableLiveData<Event<String>>()

    var positionSelected: Position? = null
        set(value) {field = value}

    val resultRegister = informasiUserUseCase.resultFlow.asLiveData()

    fun submit(data: RegisterInformasiUserModel){
        informasiUserUseCase.setName(data.name)
        informasiUserUseCase.setPhoneNumber(data.phoneNumber)
        informasiUserUseCase.setPosition(data.position)

        viewModelScope.launch {
            informasiUserUseCase.launch()
        }
    }
}