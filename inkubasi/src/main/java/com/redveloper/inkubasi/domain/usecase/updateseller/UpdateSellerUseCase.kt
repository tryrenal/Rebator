package com.redveloper.inkubasi.domain.usecase.updateseller

import android.net.Uri
import com.redveloper.inkubasi.domain.entity.SellerInkubasi
import com.redveloper.inkubasi.domain.repository.InkubasiSellerRepository
import com.redveloper.rebator.domain.entity.StatusSeller
import com.redveloper.rebator.domain.usecase.FlowUseCase
import com.redveloper.rebator.utils.State
import com.redveloper.rebator.utils.date.DateUtils
import com.redveloper.rebator.utils.dispatchers.CrDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.File
import java.util.*

class UpdateSellerUseCase(
    private val inkubasiSellerRepository: InkubasiSellerRepository,
    private val crDispatcher: CrDispatcher
): FlowUseCase<State<Boolean>>() {

    private var tiktokId: Pair<String?, Boolean> = Pair(null, false)
    private var photo: Pair<File?, Boolean> = Pair(null, false)
    private var status: Pair<String?, Boolean> = Pair(null, false)
    private var visit: Pair<String?, Boolean> = Pair(null, false)
    private var potential: Pair<String?, Boolean> = Pair(null, false)
    private var inkubasiName: Pair<String?, Boolean> = Pair(null, false)
    private var inkubasiDate: Pair<String?, Boolean> = Pair(null, false)
    private var note: Pair<String, Boolean> = Pair("", true)

    var error: Error? = null

    override fun perfomAction(): Flow<State<Boolean>> {
        return flow {
            if (canExecute()){
                emit(State.loading())

                val photoUrl = inkubasiSellerRepository.addPhotoSeller(
                    docId = tiktokId.first!!,
                    photoUri = Uri.fromFile(photo.first),
                    inkubasiName = inkubasiName.first!!
                )
                val sellerInkubasi = SellerInkubasi(
                    inkubasiName = inkubasiName.first!!,
                    photoUrl = photoUrl,
                    inkubasiDate = inkubasiDate.first!!,
                    tiktokId = tiktokId.first!!,
                    resultVisit = visit.first!!,
                    sellerPotential = potential.first!!,
                    note = note.first
                )
                inkubasiSellerRepository.updateStatus(tiktokId.first!!, StatusSeller.valueOf(status.first!!))
                emit(State.success(inkubasiSellerRepository.addInkubasi(sellerInkubasi)))
            }
        }.catch {
            emit(State.failed(it.message.toString()))
        }.flowOn(crDispatcher.ui())
    }

    private fun canExecute(): Boolean{
        return tiktokId.second && photo.second && status.second
                && visit.second && potential.second && inkubasiDate.second && inkubasiName.second
    }

    fun setInkubasiName(value: String?){
        if (value.isNullOrBlank()){
            inkubasiName = Pair(null, false)
            error?.errorInkubasiName?.invoke("inkubasi name masih salah")
        }else {
            inkubasiName = Pair(value, true)
        }
    }

    fun setInkubasiDate(value: String?){
        if (value.isNullOrBlank()){
            inkubasiDate = Pair(null, false)
            error?.errorInkubasiDate?.invoke("inkubasi date masih salah")
        }else {
            inkubasiDate = Pair(value, true)
        }
    }

    fun setTiktokId(value: String?){
        if (value.isNullOrBlank()){
            tiktokId = Pair(null, false)
            error?.errorTiktokId?.invoke("tiktokid masih salah")
        } else {
            tiktokId = Pair(value, true)
        }
    }

    fun setPhoto(value: File?){
        if (value == null){
            photo = Pair(null, false)
            error?.errorPhoto?.invoke("photo masih salah")
        } else {
            photo = Pair(value, true)
        }
    }

    fun setStatus(value: String?){
        if (value.isNullOrBlank()){
            status = Pair(null, false)
            error?.errorStatus?.invoke("status masih salah")
        } else {
            status = Pair(value, true)
        }
    }

    fun setVisit(value: String?){
        if (value.isNullOrBlank()){
            visit = Pair(null, false)
            error?.errorVisit?.invoke("visit masih salah")
        } else {
            visit = Pair(value, true)
        }
    }

    fun setPotential(value: String?){
        if (value.isNullOrBlank()){
            potential = Pair(null, false)
            error?.errorPotential?.invoke("potensial masih salah")
        } else {
            potential = Pair(value, true)
        }
    }

    fun setNote(value: String?){
        if (!value.isNullOrBlank()){
            note = Pair(value, true)
        }
    }

    data class Error(
        val errorPhoto: ((String) -> Unit)? = null,
        val errorStatus: ((String) -> Unit)? = null,
        val errorVisit: ((String) -> Unit)? = null,
        val errorPotential: ((String) -> Unit)? = null,
        val errorTiktokId: ((String) -> Unit)? = null,
        val errorInkubasiName: ((String) -> Unit)? = null,
        val errorInkubasiDate: ((String) -> Unit)? = null,
    )
}