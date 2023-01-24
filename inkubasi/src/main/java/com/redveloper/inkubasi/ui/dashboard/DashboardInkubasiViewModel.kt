package com.redveloper.inkubasi.ui.dashboard

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.redveloper.inkubasi.domain.entity.Filter
import com.redveloper.inkubasi.domain.usecase.filter.GetFilterUseCase
import com.redveloper.inkubasi.ui.dashboard.model.DashboardModel
import com.redveloper.rebator.domain.usecase.seller.GetSellerUseCase
import com.redveloper.rebator.domain.usecase.seller.SearchSellerUseCase
import com.redveloper.rebator.utils.Event
import com.redveloper.rebator.utils.State
import kotlinx.coroutines.launch

class DashboardInkubasiViewModel(
    private val getSellerUseCase: GetSellerUseCase,
    private val searchSellerUseCase: SearchSellerUseCase,
    private val getFilterUseCase: GetFilterUseCase
): ViewModel() {

    val loadingEvent = MutableLiveData<Event<Boolean>>()
    val errorMessageEvent = MutableLiveData<Event<String>>()
    val sellerEvent = MutableLiveData<Event<List<DashboardModel>>>()
    val filterEvent = MutableLiveData<Event<Filter>>()

    fun getFilter(){
        viewModelScope.launch {
            getFilterUseCase.launch()
            getFilterUseCase.resultFlow.collect{ state ->
                when(state){
                    is State.Loading -> loadingEvent.value = Event(true)
                    is State.Failed -> {
                        loadingEvent.value = Event(false)
                        errorMessageEvent.value = Event(state.message)
                    }
                    is State.Success -> {
                        loadingEvent.value = Event(false)
                        filterEvent.value = Event(state.data)
                    }
                }
            }
        }
    }

    fun searchSeller(query: String?){
        viewModelScope.launch {
            searchSellerUseCase.setQuery(query)
            searchSellerUseCase.launch()
            searchSellerUseCase.resultFlow.collect{ state ->
                when(state){
                    is State.Loading -> loadingEvent.value = Event(true)
                    is State.Failed -> {
                        loadingEvent.value = Event(false)
                        errorMessageEvent.value = Event(state.message)
                    }
                    is State.Success -> {
                        loadingEvent.value = Event(false)
                        val data = state.data.map {
                            DashboardModel(
                                tiktokId = it.tiktokId,
                                photoUrl = it.officePhotoUrl,
                                sellerName = it.sellerName,
                                cityName = it.officeCityName,
                                districtName = it.officeDistrictName,
                                status = it.status
                            )
                        }
                        sellerEvent.value = Event(data)
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
                        errorMessageEvent.value = Event(state.message)
                    }
                    is State.Success -> {
                        loadingEvent.value = Event(false)
                        val data = state.data.map {
                            DashboardModel(
                                tiktokId = it.tiktokId,
                                photoUrl = it.officePhotoUrl,
                                sellerName = it.sellerName,
                                cityName = it.officeCityName,
                                districtName = it.officeDistrictName,
                                status = it.status
                            )
                        }
                        sellerEvent.value = Event(data)
                    }
                }
            }
        }
    }
}