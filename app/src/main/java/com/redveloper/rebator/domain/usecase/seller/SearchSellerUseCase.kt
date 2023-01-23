package com.redveloper.rebator.domain.usecase.seller

import com.redveloper.rebator.domain.entity.Seller
import com.redveloper.rebator.domain.repository.AppSellerRepository
import com.redveloper.rebator.domain.usecase.FlowUseCase
import com.redveloper.rebator.utils.State
import com.redveloper.rebator.utils.dispatchers.CrDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class SearchSellerUseCase(
    private val sellerRepository: AppSellerRepository,
    private val crDispatcher: CrDispatcher
): FlowUseCase<State<List<Seller>>>() {

    private var query: String? = null

    override fun perfomAction(): Flow<State<List<Seller>>> {
        return flow {
            emit(State.loading())

            emit(State.success(sellerRepository.searchSellers(query)))
        }.catch {
            emit(State.failed(it.message.toString()))
        }.flowOn(crDispatcher.ui())
    }

    fun setQuery(value: String?){
        this.query = value
    }
}