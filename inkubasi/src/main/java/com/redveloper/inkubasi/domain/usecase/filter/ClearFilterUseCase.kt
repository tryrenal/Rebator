package com.redveloper.inkubasi.domain.usecase.filter

import com.redveloper.inkubasi.data.local.preference.filter.FilterPreference
import com.redveloper.rebator.domain.usecase.FlowUseCase
import com.redveloper.rebator.utils.State
import com.redveloper.rebator.utils.dispatchers.CrDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ClearFilterUseCase(
    private val filterPreference: FilterPreference,
    private val crDispatcher: CrDispatcher
) : FlowUseCase<State<Any>>(){
    override fun perfomAction(): Flow<State<Any>> {
        return flow {
            emit(State.loading())

            filterPreference.deleteFilter()
            emit(State.success(Any()))
        }.catch {
            emit(State.failed(it.message.toString()))
        }.flowOn(crDispatcher.ui())
    }
}