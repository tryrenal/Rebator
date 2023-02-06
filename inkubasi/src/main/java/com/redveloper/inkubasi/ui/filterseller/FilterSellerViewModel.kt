package com.redveloper.inkubasi.ui.filterseller

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.redveloper.inkubasi.domain.usecase.filter.ClearFilterUseCase
import com.redveloper.inkubasi.domain.usecase.filter.GetFilterUseCase
import com.redveloper.inkubasi.domain.usecase.filter.SaveFilterUseCase
import com.redveloper.inkubasi.ui.filterseller.model.FilterSellerModel
import com.redveloper.rebator.domain.entity.Gender
import com.redveloper.rebator.domain.entity.StatusSeller
import com.redveloper.rebator.domain.usecase.address.GetCitysUseCase
import com.redveloper.rebator.domain.usecase.address.GetDistrictUseCase
import com.redveloper.rebator.domain.usecase.address.GetProvinceUseCase
import com.redveloper.rebator.domain.usecase.address.detail.GetDetailCityUseCase
import com.redveloper.rebator.domain.usecase.address.detail.GetDetailDistrictUseCase
import com.redveloper.rebator.domain.usecase.address.detail.GetDetailProvinceUseCase
import com.redveloper.rebator.utils.Event
import com.redveloper.rebator.utils.State
import com.redveloper.rebator.utils.mapper.GenderMapper
import com.redveloper.rebator.utils.mapper.StatusSellerMapper
import kotlinx.coroutines.launch

class FilterSellerViewModel(
    private val getProvinceUseCase: GetProvinceUseCase,
    private val getCitysUseCase: GetCitysUseCase,
    private val getDistrictUseCase: GetDistrictUseCase,
    private val saveFilterUseCase: SaveFilterUseCase,
    private val clearFilterUseCase: ClearFilterUseCase,
    private val getFilterUseCase: GetFilterUseCase,
    private val getDetailProvinceUseCase: GetDetailProvinceUseCase,
    private val getDetailCityUseCase: GetDetailCityUseCase,
    private val getDetailDistrictUseCase: GetDetailDistrictUseCase
): ViewModel() {

    val loadingEvent = MutableLiveData<Event<Boolean>>()
    val errorMessageEvent = MutableLiveData<Event<String>>()
    val successSubmitEvent = MutableLiveData<Event<Any>>()
    val successResetEvent = MutableLiveData<Event<Any>>()
    val defaultFilterEvent = MutableLiveData<Event<FilterSellerModel>>()

    val errorProvinceEvent = MutableLiveData<Event<String>>()
    val errorCityEvent = MutableLiveData<Event<String>>()
    val errorDistrictEvent = MutableLiveData<Event<String>>()

    val listProvinceEvent = MutableLiveData<Event<List<Pair<String, String>>>>()
    val provinceDetailEvent = MutableLiveData<Event<Pair<Int, String>>>()
    var provinceSelected: Pair<Int, String>? = null
    val listCityEvent = MutableLiveData<Event<List<Pair<String, String>>>>()
    val cityDetailEvent = MutableLiveData<Event<Pair<Int, String>>>()
    var citySelected: Pair<Int, String>? = null
    val listDistrictEvent = MutableLiveData<Event<List<Pair<String, String>>>>()
    val districtDetailEvent = MutableLiveData<Event<Pair<Int, String>>>()
    var districtSelected: Pair<Int, String>? = null

    var listGender: List<Gender>? = null
        set(value) {field = value}
    var listStatus: List<StatusSeller>? = null
        set(value) {field = value}

    fun submit(){
        viewModelScope.launch {
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
    }

    fun clearFilter(){
        viewModelScope.launch {
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
    }

    fun getDefaultFilter(){
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
                        val gender = HashSet<Pair<String, String>>()
                        val status = HashSet<Pair<String, String>>()
                        val data = state.data.let {
                            if (it.genderMan == true){
                                gender.add(Pair(Gender.MAN.name, GenderMapper.getValueOfGender(Gender.MAN)))
                            }
                            if (it.genderWoman == true){
                                gender.add(Pair(Gender.WOMAN.name, GenderMapper.getValueOfGender(Gender.WOMAN)))
                            }

                            if (it.statusDraft == true){
                                status.add(Pair(StatusSeller.DRAFT.name, StatusSellerMapper.getValueOfStatus(StatusSeller.DRAFT)))
                            }
                            if (it.statusContacted == true){
                                status.add(Pair(StatusSeller.CONTACTED.name, StatusSellerMapper.getValueOfStatus(StatusSeller.CONTACTED)))
                            }
                            if (it.statusVisited == true){
                                status.add(Pair(StatusSeller.VISITED.name, StatusSellerMapper.getValueOfStatus(StatusSeller.VISITED)))
                            }

                            FilterSellerModel(
                                genders = gender,
                                status = status,
                                idProvince = it.provinceId,
                                idCity = it.cityId,
                                idDistrict = it.districtId
                            )
                        }
                        defaultFilterEvent.value = Event(data)
                    }
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

    fun getDetailProvince(idProvince: Int?){
        viewModelScope.launch {
            getDetailProvinceUseCase.setIdProvince(idProvince)
            getDetailProvinceUseCase.launch()
            getDetailProvinceUseCase.resultFlow.collect{ state ->
                when(state){
                    is State.Loading -> loadingEvent.value = Event(true)
                    is State.Failed -> {
                        loadingEvent.value = Event(false)
                        errorMessageEvent.value = Event(state.message)
                    }
                    is State.Success -> {
                        loadingEvent.value = Event(false)
                        val data = state.data.let { Pair(it.key?.toIntOrNull() ?: -1, it.value?: "") }
                        provinceDetailEvent.value = Event(data)
                    }
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

    fun getDetailCity(idCity: Int?){
        viewModelScope.launch {
            getDetailCityUseCase.setIdCity(idCity)
            getDetailCityUseCase.launch()
            getDetailCityUseCase.resultFlow.collect{ state ->
                when(state){
                    is State.Loading -> loadingEvent.value = Event(true)
                    is State.Failed -> {
                        loadingEvent.value = Event(false)
                        errorMessageEvent.value = Event(state.message)
                    }
                    is State.Success -> {
                        loadingEvent.value = Event(false)
                        val data = state.data.let { Pair(it.key?.toIntOrNull() ?: -1, it.value?: "") }
                        cityDetailEvent.value = Event(data)
                    }
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

    fun getDetailDistrict(idDistrict: Int?){
        viewModelScope.launch {
            getDetailDistrictUseCase.setIdDistrict(idDistrict)
            getDetailDistrictUseCase.launch()
            getDetailDistrictUseCase.resultFlow.collect{ state ->
                when(state){
                    is State.Loading -> loadingEvent.value = Event(true)
                    is State.Failed -> {
                        loadingEvent.value = Event(false)
                        errorMessageEvent.value = Event(state.message)
                    }
                    is State.Success -> {
                        loadingEvent.value = Event(false)
                        val data = state.data.let { Pair(it.key?.toIntOrNull() ?: -1, it.value?: "") }
                        districtDetailEvent.value = Event(data)
                    }
                }
            }
        }
    }
}