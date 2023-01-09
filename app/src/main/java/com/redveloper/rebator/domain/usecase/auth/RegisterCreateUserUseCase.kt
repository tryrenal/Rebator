package com.redveloper.rebator.domain.usecase.auth

import com.redveloper.rebator.data.local.preference.UserPreference
import com.redveloper.rebator.domain.repository.AuthRepository
import com.redveloper.rebator.domain.repository.UserRepository
import com.redveloper.rebator.domain.usecase.FlowUseCase
import com.redveloper.rebator.utils.State
import com.redveloper.rebator.utils.dispatchers.CrDispatcher
import kotlinx.coroutines.flow.*

class RegisterCreateUserUseCase (
    private val crDispatcher: CrDispatcher,
    private val authRepository: AuthRepository,
    private val userPreference: UserPreference
): FlowUseCase<State<Boolean>>(){

    private var email: Pair<String?, Boolean> = Pair("", false)
    private var password: Pair<String?, Boolean> = Pair("", false)

    var output: Output? = null

    override fun perfomAction(): Flow<State<Boolean>> {
        return flow<State<Boolean>> {
            if (canExecute()){
                emit(State.loading())

                val map = hashMapOf<String, Any>(
                    "email" to email.first!!
                )

                authRepository
                    .registerEmail(email = email.first!!, password = password.first!!)?.let { uid ->
                        userPreference.saveUserID(uid)
                        userPreference.getUserID()
                            .collect{ docId ->
                                if (!docId.isNullOrBlank()){
                                    val saveData = authRepository.saveUserData(docId, map)
                                    emit(State.success(saveData))
                                } else {
                                    emit(State.failed("document id kosong"))
                                }
                            }
                    }
            }
        }.catch {
            emit(State.failed(it.message.toString()))
        }.flowOn(crDispatcher.ui())
    }

    private fun canExecute(): Boolean{
        return email.second && password.second
    }

    fun setEmail(value: String?){
        if (value.isNullOrBlank()){
            output?.errorEmail?.invoke("error email kosong")
            email = Pair(null, false)
        }else {
            email = Pair(value, true)
        }
    }

    fun setPassword(value: String?){
        if (value.isNullOrBlank()){
            output?.errorPassword?.invoke("error password kosong")
            password = Pair(null, false)
        }else {
            password = Pair(value, true)
        }
    }

    data class Output(
        val errorEmail: ((String) -> Unit)? = null,
        val errorPassword: ((String) -> Unit)? = null
    )
}