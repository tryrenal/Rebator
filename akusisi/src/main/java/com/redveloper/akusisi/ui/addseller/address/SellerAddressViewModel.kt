package com.redveloper.akusisi.ui.addseller.address

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.redveloper.akusisi.domain.usecase.addseller.SetSellerAddressUseCase
import com.redveloper.akusisi.ui.addseller.model.AddSellerModel
import com.redveloper.rebator.domain.usecase.address.GetCitysUseCase
import com.redveloper.rebator.domain.usecase.address.GetDistrictUseCase
import com.redveloper.rebator.domain.usecase.address.GetProvinceUseCase
import com.redveloper.rebator.utils.Event
import com.redveloper.rebator.utils.State
import kotlinx.coroutines.launch

class SellerAddressViewModel(
    private val getProvinceUseCase: GetProvinceUseCase,
    private val getCitysUseCase: GetCitysUseCase,
    private val getDistrictUseCase: GetDistrictUseCase,
    private val setSellerAddressUseCase: SetSellerAddressUseCase
) : ViewModel() {

    init {
        setSellerAddressUseCase.error = SetSellerAddressUseCase.Error(
            errorAddress = {
                errorAddressEvent.value = Event(it)
            },
            errorProvince = {
                errorProvinceEvent.value = Event(it)
            },
            errorCity = {
                errorCityEvent.value = Event(it)
            },
            errorDistrict = {
                errorDistrictEvent.value = Event(it)
            }
        )
    }

    var addSellerModel: AddSellerModel? = null
        set(value) {field = value}

    val loadingEvent = MutableLiveData<Event<Boolean>>()
    val errorAddress = MutableLiveData<Event<String>>()
    val successSubmitEvent = MutableLiveData<Event<AddSellerModel>>()

    val listProvince = MutableLiveData<Event<List<Pair<String, String>>>>()
    var provinceSelected : Pair<Int, String>? = null
    val listCity = MutableLiveData<Event<List<Pair<String, String>>>>()
    var citySelected: Pair<Int, String>? = null
    val listDistrict = MutableLiveData<Event<List<Pair<String, String>>>>()
    var districtSelected: Pair<Int, String>? = null

    val errorAddressEvent = MutableLiveData<Event<String>>()
    val errorProvinceEvent = MutableLiveData<Event<String>>()
    val errorCityEvent = MutableLiveData<Event<String>>()
    val errorDistrictEvent = MutableLiveData<Event<String>>()

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
                    val data: List<Pair<String, String>> = state.data.map { Pair(it.key?: "", it.value?: "") }
                    listProvince.value = Event(data)
                }
            }
        }
    }

    suspend fun getCitys(idProvince: Int?){
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
                    val data: List<Pair<String, String>> = state.data.map { Pair(it.key?: "", it.value?: "") }
                    listCity.value = Event(data)
                }
            }
        }
    }

    suspend fun getDistrict(idCity: Int?){
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
                    val data: List<Pair<String, String>> = state.data.map { Pair(it.key?: "", it.value?: "") }
                    listDistrict.value = Event(data)
                }
            }
        }
    }

    fun submit(address: String?){
        viewModelScope.launch {
            setSellerAddressUseCase.setAddress(address)
            setSellerAddressUseCase.setProvince(provinceSelected?.first, provinceSelected?.second)
            setSellerAddressUseCase.setCity(citySelected?.first, citySelected?.second)
            setSellerAddressUseCase.setDistrict(districtSelected?.first, districtSelected?.second)

            setSellerAddressUseCase.launch()

            setSellerAddressUseCase.resultFlow.collect{ state ->
                when(state){
                    is State.Loading -> loadingEvent.value = Event(true)
                    is State.Failed -> {
                        loadingEvent.value = Event(false)
                        errorAddress.value = Event(state.message)
                    }
                    is State.Success -> {
                        loadingEvent.value = Event(false)

                        val newAddresSellerModel = addSellerModel ?: AddSellerModel()
                        newAddresSellerModel.officeAddress = address
                        newAddresSellerModel.officeProvinceId = provinceSelected?.first
                        newAddresSellerModel.officeProvinceName = provinceSelected?.second
                        newAddresSellerModel.officeCityId = citySelected?.first
                        newAddresSellerModel.officeCityName = citySelected?.second
                        newAddresSellerModel.officeDistrictId = districtSelected?.first
                        newAddresSellerModel.officeDistrictName = districtSelected?.second

                        successSubmitEvent.value = Event(newAddresSellerModel)
                    }
                }
            }
        }
    }

}