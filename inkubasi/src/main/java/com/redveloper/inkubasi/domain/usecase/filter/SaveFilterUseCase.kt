package com.redveloper.inkubasi.domain.usecase.filter

import com.redveloper.inkubasi.data.local.preference.filter.FilterPreference
import com.redveloper.inkubasi.data.local.preference.filter.request.RequestFilterModel
import com.redveloper.rebator.domain.entity.Gender
import com.redveloper.rebator.domain.entity.StatusSeller
import com.redveloper.rebator.domain.usecase.FlowUseCase
import com.redveloper.rebator.utils.State
import com.redveloper.rebator.utils.dispatchers.CrDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class SaveFilterUseCase(
    private val filterPreference: FilterPreference,
    private val crDispatcher: CrDispatcher
) : FlowUseCase<State<Any>>(){

    private var idProvince: Int? = null
    private var idCity: Int? = null
    private var idDistrict: Int? = null
    private var listGender: List<String>? = null
    private var listStatus: List<String>? = null

    override fun perfomAction(): Flow<State<Any>> {
        return flow {
            emit(State.loading())

            val data = RequestFilterModel(
                genders = listGender,
                status = listStatus,
                provinceId = idProvince,
                cityId = idCity,
                districtId = idDistrict
            )

            filterPreference.saveFilter(data)
            emit(State.success(Any()))

        }.catch {
            emit(State.failed(it.message.toString()))
        }.flowOn(crDispatcher.ui())
    }

    fun setGender(value: List<Gender>?){
        this.listGender = value?.map { it.name }
    }

    fun setStatus(value: List<StatusSeller>?){
        this.listStatus = value?.map { it.name }
    }

    fun provinceId(value: Int?){
        this.idProvince = value
    }

    fun cityId(value: Int?){
        this.idCity = value
    }

    fun districtId(value: Int?){
        this.idDistrict = value
    }
}