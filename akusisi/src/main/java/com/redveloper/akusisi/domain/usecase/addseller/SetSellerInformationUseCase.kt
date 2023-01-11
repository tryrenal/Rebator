package com.redveloper.akusisi.domain.usecase.addseller

import com.redveloper.akusisi.domain.repository.SellerRepository
import com.redveloper.rebator.domain.usecase.FlowUseCase
import com.redveloper.rebator.utils.State
import com.redveloper.rebator.utils.dispatchers.CrDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class SetSellerInformationUseCase(
    private val sellerRepository: SellerRepository,
    private val crDispatcher: CrDispatcher
): FlowUseCase<State<Any>>() {

    private var akusisiName: Pair<String?, Boolean> = Pair(null, false)
    private var officeName: Pair<String?, Boolean> = Pair(null, false)
    private var tiktokId: Pair<String?, Boolean> = Pair(null, false)

    var error: Error? = null

    override fun perfomAction(): Flow<State<Any>> {
        return flow <State<Any>>{
            if (canExecute()){
                emit(State.loading())
                if (!sellerRepository.checkSellerIsExist(tiktokId.first!!)){
                    emit(State.success(Any()))
                } else {
                    emit(State.failed("seller sudah exist"))
                }
            }

        }.catch {
            emit(State.failed(it.message.toString()))
        }.flowOn(crDispatcher.ui())
    }

    private fun canExecute(): Boolean {
        return akusisiName.second && officeName.second && tiktokId.second
    }

    fun setAkusisiName(value: String?){
        if (value.isNullOrBlank()){
            akusisiName = Pair(null, false)
            error?.errorAkusisiName?.invoke("akusisi name kosong")
        } else {
            akusisiName = Pair(value, true)
        }
    }

    fun setOfficeName(value: String?){
        if (value.isNullOrBlank()){
            officeName = Pair(null, false)
            error?.errorOfficeName?.invoke("office name kosong")
        } else {
            officeName = Pair(value, true)
        }
    }

    fun setTiktokId(value: String?){
        if (value.isNullOrBlank()){
            tiktokId = Pair(null, false)
            error?.errorTiktokId?.invoke("tiktok id kosong")
        } else {
            tiktokId = Pair(value, true)
        }
    }

    data class Error(
        val errorAkusisiName: ((String) -> Unit)? = null,
        val errorOfficeName: ((String) -> Unit)? = null,
        val errorTiktokId: ((String) -> Unit)? = null,
    )
}