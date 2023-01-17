package com.redveloper.akusisi.ui.addseller.address

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.redveloper.rebator.domain.entity.KeyValue
import com.redveloper.rebator.domain.usecase.address.GetCitysUseCase
import com.redveloper.rebator.domain.usecase.address.GetDistrictUseCase
import com.redveloper.rebator.domain.usecase.address.GetProvinceUseCase
import com.redveloper.rebator.utils.Event
import com.redveloper.rebator.utils.State
import kotlinx.coroutines.flow.collect

class SellerAddressViewModel(
    private val getProvinceUseCase: GetProvinceUseCase,
    private val getCitysUseCase: GetCitysUseCase,
    private val getDistrictUseCase: GetDistrictUseCase
) : ViewModel() {

    val loadingEvent = MutableLiveData<Event<Boolean>>()
    val errorAddress = MutableLiveData<Event<String>>()

    val listProvince = MutableLiveData<Event<List<KeyValue>>>()
    var provinceSelected : Pair<Int, String>? = null
    val listCity = MutableLiveData<Event<List<KeyValue>>>()
    var citySelected: Pair<Int, String>? = null
    val listDistrict = MutableLiveData<Event<List<KeyValue>>>()
    var districtSelected: Pair<Int, String>? = null

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

    suspend fun getCitys(idProvince: Int){
        getCitysUseCase.setIdProvince(idProvince)
        getCitysUseCase.launch()

        getCitysUseCase.resultFlow.collect{ state ->
            when(state){
                is State.Loading -> loadingEvent.value = Event(true)
                is State.Failed -> {
                    loadingEvent.value = Event(false)
                    errorAddress.value = Event(state.message)
                }
                is State.Success -> {
                    loadingEvent.value = Event(false)
                    listCity.value = Event(state.data)
                }
            }
        }
    }

    suspend fun getDistrict(idCity: Int){
        getDistrictUseCase.setIdCity(idCity)
        getDistrictUseCase.launch()

        getDistrictUseCase.resultFlow.collect{ state ->
            when(state){
                is State.Loading -> loadingEvent.value = Event(true)
                is State.Failed  -> {
                    loadingEvent.value = Event(false)
                    errorAddress.value = Event(state.message)
                }
                is State.Success -> {
                    loadingEvent.value = Event(false)
                    listDistrict.value = Event(state.data)
                }
            }
        }
    }


}