package com.redveloper.rebator.domain.usecase.auth

import com.redveloper.rebator.domain.entity.User
import com.redveloper.rebator.domain.repository.UserRepository
import com.redveloper.rebator.domain.usecase.FlowUseCase
import com.redveloper.rebator.utils.State
import com.redveloper.rebator.utils.dispatchers.CrDispatcher
import kotlinx.coroutines.flow.*

class LoginUseCase(
    private val crDispatcher: CrDispatcher,
    private val userRepository: UserRepository
) : FlowUseCase<State<User>>(){

    private var email = Pair("", false)
    private var password = Pair("", false)
    var output: Output? = null

    override fun perfomAction(): Flow<State<User>> {
        return flow<State<User>> {
            if (canExecute()){
                emit(State.loading())
                val data = userRepository.loginEmail(email = email.first, password = password.first)
                    .single()
                emit(State.success(data))
            }
        }.catch {
            emit(State.failed(it.message.toString()))
        }.flowOn(crDispatcher.ui())
    }

    private fun canExecute(): Boolean {
        return email.second && password.second
    }

    fun setEmail(value: String?){
        if (value.isNullOrBlank()){
            output?.errorEmail?.invoke("email masih kosong")
            email = Pair("", false)
        } else {
            email = Pair(value, true)
        }
    }

    fun setPassword(value: String?){
        if (value.isNullOrBlank()){
            output?.errorPassword?.invoke("password masih kosong")
            password = Pair("", false)
        } else {
            password = Pair(value, true)
        }
    }

    data class Output(
        val errorEmail: ((String) -> Unit)? = null,
        val errorPassword: ((String) -> Unit)? = null,
    )
}