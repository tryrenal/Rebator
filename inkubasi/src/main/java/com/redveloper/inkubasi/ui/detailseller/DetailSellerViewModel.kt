package com.redveloper.inkubasi.ui.detailseller

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.redveloper.inkubasi.domain.entity.Inkubasi
import com.redveloper.inkubasi.domain.usecase.inkubasi.GetDetailSellerInkubasiUseCase
import com.redveloper.inkubasi.ui.detailseller.model.DetailSellerModel
import com.redveloper.rebator.domain.usecase.seller.GetDetailSellerUseCase
import com.redveloper.rebator.utils.Event
import com.redveloper.rebator.utils.State
import kotlinx.coroutines.launch

class DetailSellerViewModel(
    private val getDetailSellerUseCase: GetDetailSellerUseCase,
    private val getDetailSellerInkubasiUseCase: GetDetailSellerInkubasiUseCase
) : ViewModel() {

    init {
        getDetailSellerUseCase.error = GetDetailSellerUseCase.Error(
            errorTiktokId = {
                errorEvent.value = Event(it)
            }
        )

        getDetailSellerInkubasiUseCase.error = GetDetailSellerInkubasiUseCase.Error(
            errorTiktokId = {
                errorEvent.value = Event(it)
            }
        )
    }

    var tiktokId: String? = null
        set(value) {field = value}

    val loadingEvent = MutableLiveData<Event<Boolean>>()
    val errorEvent = MutableLiveData<Event<String>>()
    val getUserEvent = MutableLiveData<Event<DetailSellerModel>>()
    val inkubasiEvent = MutableLiveData<Event<Inkubasi>>()

    fun getSeller(tiktokId: String?){
        viewModelScope.launch {
            getDetailSellerUseCase.setTiktokId(tiktokId)
            getDetailSellerUseCase.launch()
            getDetailSellerUseCase.resultFlow.collect{ state ->
                when(state){
                    is State.Loading -> loadingEvent.value = Event(true)
                    is State.Failed -> {
                        loadingEvent.value = Event(false)
                        errorEvent.value = Event(state.message)
                    }
                    is State.Success -> {
                        loadingEvent.value = Event(false)
                        val data = state.data.let {
                            DetailSellerModel(
                                photoUrl = it.officePhotoUrl,
                                status = it.status,
                                joinDate = it.timeStamp,
                                officeAddress = it.officeAddress,
                                officeProvince = it.officeProvinceName,
                                officeCity = it.officeCityName,
                                officeDistrict = it.officeDistrictName,
                                sellerName = it.sellerName,
                                sellerPhoneNumber = it.sellerPhoneNumber,
                                sellerGender = it.sellerGender,
                                tiktokId = it.tiktokId
                            )
                        }
                        getUserEvent.value = Event(data)
                    }
                }
            }
        }
    }

    fun getInkubasi(tiktokId: String?){
        viewModelScope.launch {
            getDetailSellerInkubasiUseCase.setTiktokId(tiktokId)
            getDetailSellerInkubasiUseCase.launch()
            getDetailSellerInkubasiUseCase.resultFlow.collect{ state ->
                when(state){
                    is State.Loading -> loadingEvent.value = Event(true)
                    is State.Failed -> {
                        loadingEvent.value = Event(false)
                        errorEvent.value = Event(state.message)
                    }
                    is State.Success -> {
                        loadingEvent.value = Event(false)
                        inkubasiEvent.value = Event(state.data)
                    }
                }
            }
        }
    }
}