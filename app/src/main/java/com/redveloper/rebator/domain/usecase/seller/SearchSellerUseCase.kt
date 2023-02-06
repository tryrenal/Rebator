package com.redveloper.rebator.domain.usecase.seller

import com.redveloper.rebator.domain.entity.Gender
import com.redveloper.rebator.domain.entity.Seller
import com.redveloper.rebator.domain.entity.StatusSeller
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
    private var genders: List<Gender>? = null
    private var status: List<StatusSeller>? = null
    private var provinceId: Int? = null
    private var cityId: Int? = null
    private var districtId: Int? = null

    override fun perfomAction(): Flow<State<List<Seller>>> {
        return flow {
            emit(State.loading())

            emit(State.success(sellerRepository.searchSellers(query, genders, status, provinceId, cityId, districtId)))
        }.catch {
            emit(State.failed(it.message.toString()))
        }.flowOn(crDispatcher.ui())
    }

    fun setQuery(value: String?){
        this.query = value
    }

    fun setFilter(filter: Filter){
        this.genders = filter.listGender
        this.status = filter.listStatusSeller
        this.provinceId = filter.provinceId
        this.cityId = filter.cityId
        this.districtId = filter.districtId
    }

    data class Filter(
        val listGender: List<Gender>? = null,
        val listStatusSeller: List<StatusSeller>? = null,
        val provinceId: Int? = null,
        val cityId: Int? = null,
        val districtId: Int? = null
    )
}