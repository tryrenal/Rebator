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

class GetCitysUseCase(
    private val masterRepository: MasterRepository,
    private val crDispatcher: CrDispatcher
): FlowUseCase<State<List<KeyValue>>>() {

    private var idProvince: Pair<Int?, Boolean> = Pair(null, false)

    override fun perfomAction(): Flow<State<List<KeyValue>>> {
        return flow {
            emit(State.loading())

            if (idProvince.second){
                emit(State.success(masterRepository.getCitys(idProvince.first!!)))
            }
        }.catch {
            emit(State.failed(it.message.toString()))
        }.flowOn(crDispatcher.ui())
    }

    fun setIdProvince(value: Int?){
        if (value == null){
            idProvince = Pair(null, false)
        } else {
            idProvince = Pair(value, true)
        }
    }
}