package com.redveloper.inkubasi.domain.usecase.inkubasi

import com.redveloper.inkubasi.domain.entity.Inkubasi
import com.redveloper.inkubasi.domain.repository.InkubasiRepository
import com.redveloper.rebator.domain.usecase.FlowUseCase
import com.redveloper.rebator.utils.State
import com.redveloper.rebator.utils.dispatchers.CrDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetDetailSellerInkubasiUseCase(
    private val inkubasiRepository: InkubasiRepository,
    private val crDispatcher: CrDispatcher
) : FlowUseCase<State<Inkubasi>>(){

    private var tiktokId: Pair<String?, Boolean> = Pair(null, false)

    var error: Error? = null

    override fun perfomAction(): Flow<State<Inkubasi>> {
        return flow {
            if (tiktokId.second){
                emit(State.loading())

                val inkubasiExist = inkubasiRepository.checkIfInkubasiExist(tiktokId.first!!)
                if (inkubasiExist){
                    emit(State.success(inkubasiRepository.getInkubasi(tiktokId.first!!)))
                } else {
                    val data = Inkubasi(
                        visit = null,
                        potential = null
                    )
                    emit(State.success(data))
                }
            }
        }.catch {
            emit(State.failed(it.message.toString()))
        }.flowOn(crDispatcher.ui())
    }

    fun setTiktokId(value: String?){
        if (value.isNullOrBlank()){
            tiktokId = Pair(null, false)
            error?.errorTiktokId?.invoke("error tiktokid masih salah")
        } else {
            tiktokId = Pair(value, true)
        }
    }

    data class Error (
        val errorTiktokId: ((String) -> Unit)? = null
    )
}