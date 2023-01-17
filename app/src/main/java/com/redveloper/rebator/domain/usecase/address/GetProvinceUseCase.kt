package com.redveloper.rebator.domain.usecase.address

import com.redveloper.rebator.domain.entity.KeyValue
import com.redveloper.rebator.domain.repository.MasterRepository
import com.redveloper.rebator.domain.usecase.FlowUseCase
import com.redveloper.rebator.utils.State
import com.redveloper.rebator.utils.dispatchers.CrDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetProvinceUseCase(
    private val masterRepository: MasterRepository,
    private val crDispatcher: CrDispatcher
) : FlowUseCase<State<List<KeyValue>>>(){

    override fun perfomAction(): Flow<State<List<KeyValue>>> {
        return flow <State<List<KeyValue>>>{
            emit(State.loading())

            emit(State.success(masterRepository.getProvinces()))
        }.catch {
            emit(State.failed(it.message.toString()))
        }.flowOn(crDispatcher.ui())
    }
}