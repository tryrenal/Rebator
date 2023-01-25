package com.redveloper.rebator.domain.usecase.address.detail

import com.redveloper.rebator.domain.entity.KeyValue
import com.redveloper.rebator.domain.repository.MasterRepository
import com.redveloper.rebator.domain.usecase.FlowUseCase
import com.redveloper.rebator.utils.State
import com.redveloper.rebator.utils.dispatchers.CrDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetDetailCityUseCase(
    private val masterRepository: MasterRepository,
    private val crDispatcher: CrDispatcher
): FlowUseCase<State<KeyValue>>() {

    private var id: Int? = null

    override fun perfomAction(): Flow<State<KeyValue>> {
        return flow {
            if (id != null){
                emit(State.loading())

                emit(State.success(masterRepository.getDetailCity(id!!)))
            }
        }.catch {
            emit(State.failed(it.message.toString()))
        }.flowOn(crDispatcher.ui())
    }

    fun setIdCity(id: Int?){
        this.id = id
    }
}