package com.redveloper.akusisi.domain.usecase.addseller

import com.redveloper.akusisi.domain.repository.SellerRepository
import com.redveloper.rebator.domain.usecase.FlowUseCase
import com.redveloper.rebator.utils.State
import com.redveloper.rebator.utils.dispatchers.CrDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class SetSellerAddressUseCase(
    private val sellerRepository: SellerRepository,
    private val crDispatcher: CrDispatcher
) : FlowUseCase<State<Any>>(){

    private var address: Pair<String?, Boolean> = Pair(null, false)
    private var provinceId: Pair<Int?, Boolean> = Pair(null, false)
    private var provinceName:String? = null
    private var cityId: Pair<Int?, Boolean> = Pair(null, false)
    private var cityName: String? = null
    private var districtId: Pair<Int?, Boolean> = Pair(null, false)
    private var districtName: String? = null

    var error: Error? = null

    override fun perfomAction(): Flow<State<Any>> {
        return flow {
            if (canExecute()){
                emit(State.loading())
                emit(State.success(Any()))
            } else {
                emit(State.failed("ada data yang kosong"))
            }
        }.catch {
            emit(State.failed(it.message.toString()))
        }.flowOn(crDispatcher.ui())
    }

    private fun canExecute(): Boolean {
        return address.second && provinceId.second && cityId.second && districtId.second
    }

    fun setAddress(value: String?){
        if (value.isNullOrBlank()){
            address = Pair(null, false)
            error?.errorAddress?.invoke("alaamt masih kosong")
        } else {
            address = Pair(value, true)
        }
    }

    fun setProvince(id: Int?, name: String?){
        if (id == null || name.isNullOrBlank()){
            provinceId = Pair(null, false)
            error?.errorProvince?.invoke("provinsi masih kosong")
        } else {
            provinceId = Pair(id, true)
            provinceName = name
        }
    }

    fun setCity(id: Int?, name: String?){
        if (id == null || name.isNullOrBlank()){
            cityId = Pair(null, false)
            error?.errorCity?.invoke("kota masih kosong")
        } else {
            cityId = Pair(id, true)
            cityName = name
        }
    }

    fun setDistrict(id: Int?, name: String?){
        if (id == null || name.isNullOrBlank()){
            districtId = Pair(null, false)
            error?.errorDistrict?.invoke("kecamatan masih kosong")
        } else {
            districtId = Pair(id, true)
            districtName = name
        }
    }

    data class Error(
        val errorAddress: ((String) -> Unit)? = null,
        val errorProvince: ((String) -> Unit)? = null,
        val errorCity: ((String) -> Unit)? = null,
        val errorDistrict: ((String) -> Unit)? = null
    )
}