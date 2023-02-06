package com.redveloper.inkubasi.ui.dashboard

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.redveloper.inkubasi.domain.usecase.filter.GetFilterUseCase
import com.redveloper.inkubasi.ui.dashboard.model.DashboardModel
import com.redveloper.rebator.domain.entity.Gender
import com.redveloper.rebator.domain.entity.StatusSeller
import com.redveloper.rebator.domain.usecase.seller.SearchSellerUseCase
import com.redveloper.rebator.utils.Event
import com.redveloper.rebator.utils.State
import kotlinx.coroutines.launch

class DashboardInkubasiViewModel(
    private val searchSellerUseCase: SearchSellerUseCase,
    private val getFilterUseCase: GetFilterUseCase
): ViewModel() {

    val loadingEvent = MutableLiveData<Event<Boolean>>()
    val errorMessageEvent = MutableLiveData<Event<String>>()
    val sellerEvent = MutableLiveData<Event<List<DashboardModel>>>()

    private suspend fun getFilter(callback: (filter: SearchSellerUseCase.Filter) -> Unit){
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
                    val gender = ArrayList<Gender>()
                    val status = ArrayList<StatusSeller>()
                    val data = state.data.let {
                        if (it.genderMan == true){
                            gender.add(Gender.MAN)
                        }
                        if (it.genderWoman == true){
                            gender.add(Gender.WOMAN)
                        }

                        if (it.statusDraft == true){
                            status.add(StatusSeller.DRAFT)
                        }
                        if (it.statusContacted == true){
                            status.add(StatusSeller.CONTACTED)
                        }
                        if (it.statusVisited == true){
                            status.add(StatusSeller.VISITED)
                        }

                        SearchSellerUseCase.Filter(
                            listGender = gender,
                            listStatusSeller = status,
                            provinceId = it.provinceId,
                            cityId = it.cityId,
                            districtId = it.districtId
                        )
                    }
                    callback.invoke(data)
                }
            }
        }
    }

    fun searchSeller(query: String?){
        viewModelScope.launch {
            getFilter {
                launch {
                    searchSellerUseCase.setFilter(it)
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
        }
    }
}