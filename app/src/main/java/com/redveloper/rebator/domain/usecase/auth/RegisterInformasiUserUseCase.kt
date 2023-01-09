package com.redveloper.rebator.domain.usecase.auth

import com.redveloper.rebator.data.local.preference.UserPreference
import com.redveloper.rebator.data.network.auth.model.request.RegisterRequest
import com.redveloper.rebator.domain.entity.Position
import com.redveloper.rebator.domain.repository.AuthRepository
import com.redveloper.rebator.domain.repository.UserRepository
import com.redveloper.rebator.domain.usecase.FlowUseCase
import com.redveloper.rebator.utils.State
import com.redveloper.rebator.utils.dispatchers.CrDispatcher
import kotlinx.coroutines.flow.*

class RegisterInformasiUserUseCase(
    private val crDispatcher: CrDispatcher,
    private val authRepository: AuthRepository,
    private val userPreference: UserPreference
): FlowUseCase<State<Boolean>>() {

    private var name : Pair<String?, Boolean> = Pair(null, false)
    private var phoneNumber: Pair<String?, Boolean> = Pair(null, false)
    private var position: Pair<Position?, Boolean> =Pair(null, false)

    var output: Output? = null

    override fun perfomAction(): Flow<State<Boolean>> {
        return flow <State<Boolean>>{
            if (checkValidation()){
                emit(State.loading())

                val mapData = hashMapOf<String, Any>(
                    "name" to name.first!!,
                    "phoneNumber" to phoneNumber.first!!,
                    "position" to position.first!!.name
                )

                userPreference.getUserID().collect{ docId ->
                    docId?.let {
                        val saveData = authRepository.saveUserData(docId, mapData)
                        emit(State.success(saveData))
                    }
                }
            }
        }.catch {
            emit(State.failed(it.message.toString()))
        }.flowOn(crDispatcher.network())
    }

    private fun checkValidation(): Boolean{
        return name.second && phoneNumber.second && position.second
    }

    fun setName(value: String?){
        if (value.isNullOrBlank()){
            output?.errorName?.invoke("name masih kosong")
            name = Pair(null, false)
        }else {
            name = Pair(value, true)
        }
    }

    fun setPhoneNumber(value: String?){
        if (value.isNullOrBlank()){
            output?.errorPhoneNumber?.invoke("phone number masih kosong")
            phoneNumber = Pair(null, false)
        } else {
            phoneNumber = Pair(value, true)
        }
    }

    fun setPosition(value: Position?){
        if (value == null){
            output?.errorPosition?.invoke("position masih kosong")
            position = Pair(null, false)
        }else {
            when(value){
                Position.AKUSISI -> position = Pair(Position.AKUSISI, true)
                Position.INKUBASI -> position = Pair(Position.INKUBASI, true)
                else -> {
                    output?.errorPosition?.invoke("position tidak tepat")
                    position = Pair(null, false)
                }
            }
        }
    }

    data class Output(
        val errorName: ((String) -> Unit)? = null,
        val errorPhoneNumber: ((String) -> Unit)? = null,
        val errorPosition: ((String) -> Unit)? = null
    )
}