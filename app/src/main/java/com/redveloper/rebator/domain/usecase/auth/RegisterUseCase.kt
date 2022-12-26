package com.redveloper.rebator.domain.usecase.auth

import com.redveloper.rebator.data.network.auth.model.RegisterRequest
import com.redveloper.rebator.domain.entity.Position
import com.redveloper.rebator.domain.entity.User
import com.redveloper.rebator.domain.repository.UserRepository
import com.redveloper.rebator.domain.usecase.FlowUseCase
import com.redveloper.rebator.utils.State
import com.redveloper.rebator.utils.dispatchers.CrDispatcher
import kotlinx.coroutines.flow.*

class RegisterUseCase(
    private val crDispatcher: CrDispatcher,
    private val userRepository: UserRepository
) : FlowUseCase<State<User>>(){

    private var name = Pair("", false)
    private var email = Pair("", false)
    private var password = Pair("", false)
    private var position: Pair<Position?, Boolean> = Pair(null, false)
    private var photo = Pair("", false)
    private var phoneNumber = Pair("", false)

    var output: Output? = null

    override fun perfomAction(): Flow<State<User>> {
        return flow<State<User>> {
            if (canExecute()){
                emit(State.loading())

                val registerRequest = RegisterRequest(
                    name = name.first, email = email.first, phoneNumber = phoneNumber.first,
                    photo = photo.first, posisi = position.first!!
                )

                val data = userRepository.registerEmail(
                    email = email.first,
                    password = password.first
                ).flatMapLatest { docId ->
                    userRepository.saveUserData(
                        documentId = docId,
                        request = registerRequest
                    ).map { success ->
                        User(
                            email = email.first, "", "",
                            Position.INKUBASI, "")
                    }
                }.single()
                emit(State.success(data))
            }
        }.catch{
            emit(State.failed(it.message.toString()))
        }.flowOn(crDispatcher.ui())
    }

    private fun canExecute(): Boolean {
        return name.second
                && email.second
                && password.second
                && phoneNumber.second
                && photo.second
                && position.second
    }

    fun setName(value: String?){
        if (value.isNullOrBlank()){
            output?.errorName?.invoke("name masih kosong")
            name = Pair("", false)
        } else {
            name = Pair(value, true)
        }
    }

    fun setPhoneNumber(value: String?){
        if (value.isNullOrBlank()){
            output?.errorPhoneNumber?.invoke("phone number masih kosong")
            phoneNumber = Pair("", false)
        } else {
            phoneNumber = Pair(value, true)
        }
    }

    fun setPhoto(value: String?){
        if (value.isNullOrBlank()){
            output?.errorPhoto?.invoke("photo masih kosong")
            photo = Pair("", false)
        } else {
            photo = Pair(value, true)
        }
    }

    fun setPosition(value: Position?){
        if (value == null){
            output?.errorPosition?.invoke("position masih kosong")
            position = Pair(null, false)
        } else {
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
        val errorName: ((String) -> Unit)? = null,
        val errorPassword: ((String) -> Unit)? = null,
        val errorPhoneNumber: ((String) -> Unit)? = null,
        val errorPhoto: ((String) -> Unit)? = null,
        val errorPosition: ((String) -> Unit)? = null,
    )
}