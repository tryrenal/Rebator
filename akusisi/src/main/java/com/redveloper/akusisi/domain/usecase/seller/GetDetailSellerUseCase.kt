package com.redveloper.akusisi.domain.usecase.seller

import com.redveloper.akusisi.domain.entity.Seller
import com.redveloper.akusisi.domain.repository.SellerRepository
import com.redveloper.rebator.domain.usecase.FlowUseCase
import com.redveloper.rebator.utils.State
import com.redveloper.rebator.utils.dispatchers.CrDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetDetailSellerUseCase (
    private val sellerRepository: SellerRepository,
    private val crDispatcher: CrDispatcher
): FlowUseCase<State<Seller>>() {

    private var tiktokId: Pair<String?, Boolean> = Pair(null, false)

    var error: Error? = null

    override fun perfomAction(): Flow<State<Seller>> {
        return flow<State<Seller>> {
            if (tiktokId.second){
                emit(State.loading())

                emit(State.success(sellerRepository.getDetailSeller(tiktokId.first!!)))
            }
        }.catch {
            emit(State.failed(it.message.toString()))
        }.flowOn(crDispatcher.ui())
    }

    fun setTiktokId(value: String?){
        if (value.isNullOrBlank()){
            tiktokId = Pair(null, false)
            error?.errorTiktokId?.invoke("error tikotk id null")
        } else {
            tiktokId = Pair(value, true)
        }
    }

    data class Error(
        val errorTiktokId: ((String) -> Unit)? = null
    )
}