package com.redveloper.rebator.domain.usecase.auth

import com.redveloper.rebator.data.local.preference.UserPreference
import com.redveloper.rebator.domain.repository.UserRepository
import com.redveloper.rebator.domain.usecase.FlowUseCase
import com.redveloper.rebator.utils.State
import com.redveloper.rebator.utils.dispatchers.CrDispatcher
import kotlinx.coroutines.flow.*

class RegisterCreateUserUseCase (
    private val crDispatcher: CrDispatcher,
    private val userRepository: UserRepository,
    private val userPreference: UserPreference
): FlowUseCase<State<String>>(){

    private var email: Pair<String?, Boolean> = Pair("", false)
    private var password: Pair<String?, Boolean> = Pair("", false)

    var output: Output? = null

    override fun perfomAction(): Flow<State<String>> {
        return flow<State<String>> {
            if (canExecute()){
                emit(State.loading())

                userRepository
                    .registerEmail(email = email.first!!, password = password.first!!)
                    .onSuccess { docId ->
                        userPreference.saveUserID(docId)
                    }

                userPreference.getUserID()
                    .collect{ docId ->
                        if (!docId.isNullOrBlank()){
                            val map = hashMapOf<String, Any>(
                                "email" to email.first!!
                            )
                            userRepository.saveUserData(docId, map)
                            emit(State.success(docId))
                        } else {
                            emit(State.failed("document id kosong"))
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