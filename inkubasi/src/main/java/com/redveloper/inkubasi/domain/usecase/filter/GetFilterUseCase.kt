package com.redveloper.inkubasi.domain.usecase.filter

import com.redveloper.inkubasi.data.local.preference.filter.FilterPreference
import com.redveloper.inkubasi.domain.entity.Filter
import com.redveloper.rebator.domain.usecase.FlowUseCase
import com.redveloper.rebator.utils.State
import com.redveloper.rebator.utils.dispatchers.CrDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetFilterUseCase(
    private val filterPreference: FilterPreference,
    private val crDispatcher: CrDispatcher
) : FlowUseCase<State<Filter>>(){

    override fun perfomAction(): Flow<State<Filter>> {
        return flow{
            emit(State.loading())

            filterPreference.getFilter().collect{
                val data = Filter()
                data.provinceId = it.provinceId
                data.cityId = it.cityId
                data.districtId = it.districtId
                data.genderMan = it.genderMan
                data.genderWoman = it.genderWoman
                data.statusDraft = it.statusDraft
                data.statusContacted = it.statusContacted
                data.statusVisited = it.statusVisited

                emit(State.success(data))
            }
        }.catch {
            emit(State.failed(it.message.toString()))
        }.flowOn(crDispatcher.ui())
    }
}