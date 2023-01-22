package com.redveloper.akusisi.domain.usecase.addseller

import com.redveloper.akusisi.domain.repository.SellerRepository
import com.redveloper.akusisi.ui.addseller.model.AddSellerModel
import com.redveloper.rebator.domain.entity.Gender
import com.redveloper.rebator.domain.usecase.FlowUseCase
import com.redveloper.rebator.utils.State
import com.redveloper.rebator.utils.dispatchers.CrDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class SetSellerContactUseCase(
    private val sellerRepository: SellerRepository,
    private val crDispatcher: CrDispatcher
): FlowUseCase<State<Boolean>>() {

    private var name: Pair<String?, Boolean> = Pair(null, false)
    private var phoneNumber: Pair<String?, Boolean> = Pair(null, false)
    private var gender: Pair<String?, Boolean> = Pair(null, false)
    private var addSellerModel: Pair<AddSellerModel?, Boolean> = Pair(null, false)

    var error: Error? = null

    override fun perfomAction(): Flow<State<Boolean>> {
        return flow {
            if (canExecute()){
                emit(State.loading())

                val data = addSellerModel.first
                data?.let {
                    val photoUrl = sellerRepository.addPhotoSeller(
                        docId = data.tiktokID!!,
                        photoUri = data.officePhoto!!,
                        sellerName = data.sellerName!!
                    )
                    data.officePhotoUrl = photoUrl
                    data.sellerGender = gender.first

                    val addSeller = sellerRepository.addSeller(data)
                    emit(State.success(addSeller))
                }

            }
        }.catch {
            emit(State.failed(it.message.toString()))
        }.flowOn(crDispatcher.network())
    }

    private fun canExecute(): Boolean {
        return name.second && phoneNumber.second && addSellerModel.second && gender.second
    }

    fun setName(value: String?){
        if (value.isNullOrBlank()){
            name = Pair(null, false)
            error?.nameError?.invoke("nama masih salah")
        } else {
            name = Pair(value, true)
        }
    }

    fun setPhoneNumber(value: String?){
        if (value.isNullOrBlank()){
            phoneNumber = Pair(null, false)
            error?.phoneNumberError?.invoke("phone number masih salah")
        } else {
            phoneNumber = Pair(value, true)
        }
    }

    fun setGender(value: Gender?) {
        if (value == null){
            gender = Pair(null, false)
            error?.genderError?.invoke("jenis kelamin masih salah")
        } else {
            gender = Pair(value.name, true)
        }
    }

    fun setAddSellerModel(value: AddSellerModel?){
        if (value == null){
            addSellerModel = Pair(null, false)
            error?.modelError?.invoke("data model error")
        } else {
            addSellerModel = Pair(value, true)
        }
    }

    data class Error(
        val nameError: ((String) -> Unit)? = null,
        val phoneNumberError: ((String) -> Unit)? = null,
        val genderError: ((String) -> Unit)? = null,
        val modelError: ((String) -> Unit)? = null
    )
}