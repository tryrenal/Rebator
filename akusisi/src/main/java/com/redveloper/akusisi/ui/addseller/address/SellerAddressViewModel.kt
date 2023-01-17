package com.redveloper.akusisi.ui.addseller.address

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.redveloper.rebator.domain.entity.KeyValue
import com.redveloper.rebator.domain.usecase.address.GetProvinceUseCase
import com.redveloper.rebator.utils.Event
import com.redveloper.rebator.utils.State

class SellerAddressViewModel(
    private val getProvinceUseCase: GetProvinceUseCase
) : ViewModel() {

    val loadingEvent = MutableLiveData<Event<Boolean>>()
    val errorAddress = MutableLiveData<Event<String>>()

    val listProvince = MutableLiveData<Event<List<KeyValue>>>()

    suspend fun getProvinces(){
        getProvinceUseCase.launch()

        getProvinceUseCase.resultFlow.collect{ state ->
            when(state){
                is State.Loading -> loadingEvent.value = Event(true)
                is State.Failed -> {
                    loadingEvent.value = Event(false)
                    errorAddress.value = Event(state.message)
                }
                is State.Success -> {
                    loadingEvent.value = Event(false)
                    listProvince.value = Event(state.data)
                }
            }

        }
    }
}