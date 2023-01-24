package com.redveloper.inkubasi.ui.filterseller

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.redveloper.inkubasi.domain.usecase.filter.ClearFilterUseCase
import com.redveloper.inkubasi.domain.usecase.filter.SaveFilterUseCase
import com.redveloper.rebator.domain.entity.Gender
import com.redveloper.rebator.domain.entity.StatusSeller
import com.redveloper.rebator.domain.usecase.address.GetCitysUseCase
import com.redveloper.rebator.domain.usecase.address.GetDistrictUseCase
import com.redveloper.rebator.domain.usecase.address.GetProvinceUseCase
import com.redveloper.rebator.utils.Event
import com.redveloper.rebator.utils.State
import kotlinx.coroutines.flow.collect

class FilterSellerViewModel(
    private val getProvinceUseCase: GetProvinceUseCase,
    private val getCitysUseCase: GetCitysUseCase,
    private val getDistrictUseCase: GetDistrictUseCase,
    private val saveFilterUseCase: SaveFilterUseCase,
    private val clearFilterUseCase: ClearFilterUseCase
): ViewModel() {

    val loadingEvent = MutableLiveData<Event<Boolean>>()
    val errorMessageEvent = MutableLiveData<Event<String>>()
    val successSubmitEvent = MutableLiveData<Event<Any>>()
    val successResetEvent = MutableLiveData<Event<Any>>()

    val errorProvinceEvent = MutableLiveData<Event<String>>()
    val errorCityEvent = MutableLiveData<Event<String>>()
    val errorDistrictEvent = MutableLiveData<Event<String>>()

    val listProvinceEvent = MutableLiveData<Event<List<Pair<String, String>>>>()
    var provinceSelected: Pair<Int, String>? = null
    val listCityEvent = MutableLiveData<Event<List<Pair<String, String>>>>()
    var citySelected: Pair<Int, String>? = null
    val listDistrictEvent = MutableLiveData<Event<List<Pair<String, String>>>>()
    var districtSelected: Pair<Int, String>? = null

    var listGender: List<Gender>? = null
        set(value) {field = value}
    var listStatus: List<StatusSeller>? = null
        set(value) {field = value}

    suspend fun submit(){
        saveFilterUseCase.provinceId(provinceSelected?.first)
        saveFilterUseCase.cityId(citySelected?.first)
        saveFilterUseCase.districtId(districtSelected?.first)
        saveFilterUseCase.setGender(listGender)
        saveFilterUseCase.setStatus(listStatus)

        saveFilterUseCase.launch()
        saveFilterUseCase.resultFlow.collect{ state ->
            when(state){
                is State.Loading -> loadingEvent.value = Event(true)
                is State.Failed -> {
                    loadingEvent.value = Event(false)
                    errorMessageEvent.value = Event(state.message)
                }
                is State.Success -> {
                    loadingEvent.value = Event(false)
                    successSubmitEvent.value = Event(Any())
                }
            }
        }
    }

    suspend fun clearFilter(){
        clearFilterUseCase.launch()
        clearFilterUseCase.resultFlow.collect{ state ->
            when(state){
                is State.Loading -> loadingEvent.value = Event(true)
                is State.Failed -> {
                    loadingEvent.value = Event(false)
                    errorMessageEvent.value = Event(state.message)
                }
                is State.Success -> {
                    loadingEvent.value = Event(false)
                    successResetEvent.value = Event(state.data)
                }
            }
        }
    }

    suspend fun getProvinces(){
        getProvinceUseCase.launch()
        getProvinceUseCase.resultFlow.collect{ state ->
            when(state){
                is State.Loading -> loadingEvent.value = Event(true)
                is State.Failed -> {
                    loadingEvent.value = Event(false)
                    errorProvinceEvent.value = Event(state.message)
                }
                is State.Success -> {
                    loadingEvent.value = Event(false)
                    val data: List<Pair<String, String>> = state.data.map { Pair(it.key?: "", it.value?: "") }
                    listProvinceEvent.value = Event(data)
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
                    errorCityEvent.value = Event(state.message)
                }
                is State.Success -> {
                    loadingEvent.value = Event(false)
                    val data: List<Pair<String, String>> = state.data.map { Pair(it.key?: "", it.value?: "") }
                    listCityEvent.value = Event(data)
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
                    errorDistrictEvent.value = Event(state.message)
                }
                is State.Success -> {
                    loadingEvent.value = Event(false)
                    val data: List<Pair<String, String>> = state.data.map { Pair(it.key?: "", it.value?: "") }
                    listDistrictEvent.value = Event(data)
                }
            }
        }
    }
}