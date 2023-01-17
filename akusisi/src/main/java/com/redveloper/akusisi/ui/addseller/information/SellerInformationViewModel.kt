package com.redveloper.akusisi.ui.addseller.information

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.redveloper.akusisi.domain.usecase.addseller.SetSellerInformationUseCase
import com.redveloper.akusisi.ui.addseller.model.AddSellerModel
import com.redveloper.rebator.domain.entity.User
import com.redveloper.rebator.domain.usecase.user.GetUserUseCase
import com.redveloper.rebator.utils.Event
import com.redveloper.rebator.utils.State
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SellerInformationViewModel(
    private val setSellerInformationUseCase: SetSellerInformationUseCase,
    private val getUserUseCase: GetUserUseCase
): ViewModel() {

    init {
        setSellerInformationUseCase.error = SetSellerInformationUseCase.Error (
            errorAkusisiName = {
                errorAkusisiNameEvent.value = Event(it)
            },
            errorOfficeName = {
                errorOfficeNameEvent.value = Event(it)
            },
            errorTiktokId = {
                errorTiktokIdEvent.value = Event(it)
            }
        )
    }

    val errorAkusisiNameEvent = MutableLiveData<Event<String>>()
    val errorOfficeNameEvent = MutableLiveData<Event<String>>()
    val errorTiktokIdEvent = MutableLiveData<Event<String>>()

    val loadingEvent = MutableLiveData<Event<Boolean>>()
    val errorSubmitEvent = MutableLiveData<Event<String>>()
    val successSubmitEvent = MutableLiveData<Event<AddSellerModel>>()

    val errorUserEvent = MutableLiveData<Event<String>>()
    val successUserEvent = MutableLiveData<Event<User>>()

    fun submit(data: AddSellerModel){
        viewModelScope.launch {
            setSellerInformationUseCase.setAkusisiName(data.akusisiName)
            setSellerInformationUseCase.setOfficeName(data.officeName)
            setSellerInformationUseCase.setTiktokId(data.tiktokID)

            setSellerInformationUseCase.launch()

            setSellerInformationUseCase.resultFlow.collect{ state ->
                when(state){
                    is State.Loading -> loadingEvent.value = Event(true)
                    is State.Failed -> {
                        loadingEvent.value = Event(false)
                        errorSubmitEvent.value = Event(state.message)
                    }
                    is State.Success -> {
                        loadingEvent.value = Event(false)
                        successSubmitEvent.value = Event(data)
                    }
                }
            }
        }
    }

    fun getUser(){
        viewModelScope.launch {
            getUserUseCase.launch()
            getUserUseCase.resultFlow.collect{ state ->
                when(state){
                    is State.Loading -> loadingEvent.value = Event(true)
                    is State.Failed -> {
                        loadingEvent.value = Event(false)
                        errorUserEvent.value = Event(state.message)
                    }
                    is State.Success -> {
                        loadingEvent.value = Event(false)
                        successUserEvent.value = Event(state.data)
                    }
                }
            }
        }
    }
}