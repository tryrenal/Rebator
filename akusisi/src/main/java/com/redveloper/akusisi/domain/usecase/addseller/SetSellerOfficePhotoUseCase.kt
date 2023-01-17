package com.redveloper.akusisi.domain.usecase.addseller

import android.net.Uri
import com.redveloper.akusisi.domain.repository.SellerRepository
import com.redveloper.rebator.domain.usecase.FlowUseCase
import com.redveloper.rebator.utils.State
import com.redveloper.rebator.utils.dispatchers.CrDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.File

class SetSellerOfficePhotoUseCase(
    private val crDispatcher: CrDispatcher,
    private val sellerRepository: SellerRepository
): FlowUseCase<State<Uri>>(){

    private var photo: Pair<File?, Boolean> = Pair(null, false)

    var error: Error? = null

    override fun perfomAction(): Flow<State<Uri>> {
        return flow {
            if (photo.second){
                emit(State.loading())

                emit(State.success(Uri.fromFile(photo.first)))
            }
        }.catch {
            emit(State.failed(it.message.toString()))
        }.flowOn(crDispatcher.network())
    }

    fun setPhoto(file: File?){
        if (file == null){
            error?.errorPhoto?.invoke("photo masih kosong")
            photo = Pair(null, false)
        } else {
            photo = Pair(file,true)
        }
    }

    data class Error(
        val errorPhoto: ((String) -> Unit)? = null
    )
}