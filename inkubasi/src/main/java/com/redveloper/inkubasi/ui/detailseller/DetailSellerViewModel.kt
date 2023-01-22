package com.redveloper.inkubasi.ui.detailseller

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.redveloper.inkubasi.domain.entity.StatusSeller
import com.redveloper.inkubasi.ui.detailseller.model.DetailSellerModel
import com.redveloper.rebator.domain.usecase.seller.GetDetailSellerUseCase
import com.redveloper.rebator.utils.Event
import com.redveloper.rebator.utils.State
import kotlinx.coroutines.launch

class DetailSellerViewModel(
    private val getDetailSellerUseCase: GetDetailSellerUseCase
) : ViewModel() {

    init {
        getDetailSellerUseCase.error = GetDetailSellerUseCase.Error(
            errorTiktokId = {
                errorGetUserEvent.value = Event(it)
            }
        )
    }

    var tiktokId: String? = null
        set(value) {field = value}

    val loadingEvent = MutableLiveData<Event<Boolean>>()
    val errorGetUserEvent = MutableLiveData<Event<String>>()
    val getUserEvent = MutableLiveData<Event<DetailSellerModel>>()

    fun getSeller(tiktokId: String?){
        viewModelScope.launch {
            getDetailSellerUseCase.setTiktokId(tiktokId)
            getDetailSellerUseCase.launch()
            getDetailSellerUseCase.resultFlow.collect{ state ->
                when(state){
                    is State.Loading -> loadingEvent.value = Event(true)
                    is State.Failed -> {
                        loadingEvent.value = Event(false)
                        errorGetUserEvent.value = Event(state.message)
                    }
                    is State.Success -> {
                        loadingEvent.value = Event(false)
                        val data = state.data.let {
                            DetailSellerModel(
                                photoUrl = it.officePhotoUrl,
                                status = StatusSeller.DRAFT,
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
}