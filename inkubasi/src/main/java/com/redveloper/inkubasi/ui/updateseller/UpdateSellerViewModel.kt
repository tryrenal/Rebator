package com.redveloper.inkubasi.ui.updateseller

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.redveloper.inkubasi.domain.usecase.updateseller.GetPotentialSellerUseCase
import com.redveloper.inkubasi.domain.usecase.updateseller.GetResultVisitUseCase
import com.redveloper.inkubasi.domain.usecase.updateseller.GetStatusSellerUseCase
import com.redveloper.inkubasi.domain.usecase.updateseller.UpdateSellerUseCase
import com.redveloper.rebator.domain.entity.User
import com.redveloper.rebator.domain.usecase.user.GetUserUseCase
import com.redveloper.rebator.utils.Event
import com.redveloper.rebator.utils.State
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.io.File
import java.util.*

class UpdateSellerViewModel(
    private val getPotentialSellerUseCase: GetPotentialSellerUseCase,
    private val getResultVisitUseCase: GetResultVisitUseCase,
    private val getStatusSellerUseCase: GetStatusSellerUseCase,
    private val updateSellerUseCase: UpdateSellerUseCase,
    private val getUserUseCase: GetUserUseCase
) : ViewModel(){

    init {
        updateSellerUseCase.error = UpdateSellerUseCase.Error(
            errorTiktokId = {
                errorMessageEvent.value = Event(it)
            },
            errorPhoto = {
                errorMessageEvent.value = Event(it)
            },
            errorInkubasiDate = {
                errorMessageEvent.value = Event(it)
            },
            errorInkubasiName = {
                errorMessageEvent.value = Event(it)
            },
            errorPotential = {
                errorPotentialEvent.value = Event(it)
            },
            errorStatus = {
                errorStatusEvent.value = Event(it)
            },
            errorVisit = {
                errorVisitEvent.value = Event(it)
            }
        )
    }

    val errorStatusEvent = MutableLiveData<Event<String>>()
    val errorVisitEvent = MutableLiveData<Event<String>>()
    val errorPotentialEvent = MutableLiveData<Event<String>>()

    val listPotentialEvent = MutableLiveData<Event<ArrayList<Pair<String, String>>>>()
    var potentialSellerSelected: Pair<String, String>? = null
    val listResultVisitEvent = MutableLiveData<Event<ArrayList<Pair<String, String>>>>()
    var resultVisitSelected: Pair<String, String>? = null
    val listStatusSellerEvent = MutableLiveData<Event<ArrayList<Pair<String, String>>>>()
    var statusSellerSelected: Pair<String, String>? = null

    val loadingEvent = MutableLiveData<Event<Boolean>>()
    val errorMessageEvent = MutableLiveData<Event<String>>()
    val userEvent = MutableLiveData<Event<User>>()
    val submitSuccessEvent = MutableLiveData<Event<Boolean>>()

    fun getListPotential(){
        getPotentialSellerUseCase.getListPotential {
            listPotentialEvent.value = Event(it)
        }
    }

    fun getListResultVisit(){
        getResultVisitUseCase.getListResultVisit {
            listResultVisitEvent.value = Event(it)
        }
    }

    fun getListStatus(){
        getStatusSellerUseCase.getListStatus {
            listStatusSellerEvent.value = Event(it)
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
                        errorMessageEvent.value = Event(state.message)
                    }
                    is State.Success -> {
                        loadingEvent.value = Event(false)
                        userEvent.value = Event(state.data)
                    }
                }
            }
        }
    }

    fun submit(tiktokId: String?, inkubasiName: String?, inkubasiDate: String?, photo: File?, note: String?){
        viewModelScope.launch {
            updateSellerUseCase.setTiktokId(tiktokId)
            updateSellerUseCase.setInkubasiDate(inkubasiDate)
            updateSellerUseCase.setInkubasiName(inkubasiName)
            updateSellerUseCase.setPhoto(photo)
            updateSellerUseCase.setPotential(potentialSellerSelected?.first)
            updateSellerUseCase.setStatus(statusSellerSelected?.first)
            updateSellerUseCase.setVisit(resultVisitSelected?.first)
            updateSellerUseCase.setNote(note)

            updateSellerUseCase.launch()
            updateSellerUseCase.resultFlow.collect{ state ->
                when(state){
                    is State.Loading -> loadingEvent.value = Event(true)
                    is State.Failed -> {
                        loadingEvent.value = Event(false)
                        errorMessageEvent.value = Event(state.message)
                    }
                    is State.Success -> {
                        loadingEvent.value = Event(false)
                        submitSuccessEvent.value = Event(state.data)
                    }
                }
            }
        }
    }
}