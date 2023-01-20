package com.redveloper.akusisi.ui.addseller.contact

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.redveloper.akusisi.domain.usecase.addseller.SetSellerContactUseCase
import com.redveloper.akusisi.ui.addseller.model.AddSellerModel
import com.redveloper.rebator.utils.Event
import com.redveloper.rebator.utils.State
import com.redveloper.rebator.utils.mapper.GenderMapper
import kotlinx.coroutines.launch

class SellerContactViewModel(
    private val setSellerContactUseCase: SetSellerContactUseCase
): ViewModel() {

    init {
        setSellerContactUseCase.error = SetSellerContactUseCase.Error(
            nameError = {
                errorNameEvent.value = Event(it)
            },
            phoneNumberError = {
                errorPhoneNumberEvent.value = Event(it)
            },
            genderError = {
                errorGenderEvent.value = Event(it)
            }
        )
    }

    val errorNameEvent = MutableLiveData<Event<String>>()
    val errorPhoneNumberEvent = MutableLiveData<Event<String>>()
    val errorGenderEvent = MutableLiveData<Event<String>>()
    val errorSubmitEvent = MutableLiveData<Event<String>>()

    val loadingEvent = MutableLiveData<Event<Boolean>>()
    val successSubmitEvent = MutableLiveData<Event<Any>>()

    var genderSelected: Pair<String, String>? = null

    fun submit(addSellerModel: AddSellerModel){
        viewModelScope.launch {
            setSellerContactUseCase.setAddSellerModel(addSellerModel)
            setSellerContactUseCase.setName(addSellerModel.sellerName)
            setSellerContactUseCase.setPhoneNumber(addSellerModel.sellerPhoneNumber)
            setSellerContactUseCase.setGender(GenderMapper.getGenderByValue(genderSelected?.second?: ""))

            setSellerContactUseCase.launch()
            setSellerContactUseCase.resultFlow.collect{ state ->
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