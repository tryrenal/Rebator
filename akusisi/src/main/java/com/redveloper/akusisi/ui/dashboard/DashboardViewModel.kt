package com.redveloper.akusisi.ui.dashboard

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.redveloper.akusisi.ui.dashboard.model.SellerModel
import com.redveloper.rebator.domain.usecase.seller.GetSellerUseCase
import com.redveloper.rebator.domain.usecase.seller.SearchSellerUseCase
import com.redveloper.rebator.utils.Event
import com.redveloper.rebator.utils.State
import kotlinx.coroutines.launch

class DashboardViewModel(
    private val getSellerUseCase: GetSellerUseCase,
    private val searchSellerUseCase: SearchSellerUseCase
): ViewModel() {

    val loadingEvent = MutableLiveData<Event<Boolean>>()
    val errorGetSellerEvent = MutableLiveData<Event<String>>()
    val successGetSellerEvent = MutableLiveData<Event<List<SellerModel>>>()

    fun searchSeller(query: String?){
        viewModelScope.launch {
            searchSellerUseCase.setQuery(query)
            searchSellerUseCase.launch()
            searchSellerUseCase.resultFlow.collect{ state ->
                when(state){
                    is State.Loading -> loadingEvent.value = Event(true)
                    is State.Failed -> {
                        loadingEvent.value = Event(false)
                        errorGetSellerEvent.value = Event(state.message)
                    }
                    is State.Success -> {
                        loadingEvent.value = Event(false)
                        val data = state.data.map {
                            SellerModel(
                                tiktokId = it.tiktokId,
                                date = it.timeStamp,
                                sellerName = it.sellerName,
                                cityName = it.officeCityName,
                                districtName = it.officeDistrictName
                            )
                        }
                        successGetSellerEvent.value = Event(data)
                    }
                }
            }
        }
    }

    fun getSellers(){
        viewModelScope.launch {
            getSellerUseCase.launch()
            getSellerUseCase.resultFlow.collect{ state ->
                when(state){
                    is State.Loading -> loadingEvent.value = Event(true)
                    is State.Failed -> {
                        loadingEvent.value = Event(false)
                        errorGetSellerEvent.value = Event(state.message)
                    }
                    is State.Success -> {
                        loadingEvent.value = Event(false)
                        val data = state.data.map {
                            SellerModel(
                                tiktokId = it.tiktokId,
                                date = it.timeStamp,
                                sellerName = it.sellerName,
                                cityName = it.officeCityName,
                                districtName = it.officeDistrictName
                            )
                        }
                        successGetSellerEvent.value = Event(data)
                    }
                }
            }
        }
    }
}