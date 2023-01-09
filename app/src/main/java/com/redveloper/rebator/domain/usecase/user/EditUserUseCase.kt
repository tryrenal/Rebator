package com.redveloper.rebator.domain.usecase.user

import android.net.Uri
import com.redveloper.rebator.data.local.preference.UserPreference
import com.redveloper.rebator.domain.entity.Position
import com.redveloper.rebator.domain.repository.AuthRepository
import com.redveloper.rebator.domain.repository.UserRepository
import com.redveloper.rebator.domain.usecase.FlowUseCase
import com.redveloper.rebator.utils.State
import com.redveloper.rebator.utils.dispatchers.CrDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class EditUserUseCase(
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository,
    private val userPreference: UserPreference,
    private val crDispatcher: CrDispatcher
) : FlowUseCase<State<Boolean>>(){

    private var name: Pair<String?, Boolean> = Pair(null, false)
    private var photoFile: Pair<Uri?, Boolean> = Pair(null, false)
    private var position: Pair<Position?, Boolean> = Pair(null, false)
    private var phoneNumber: Pair<String?, Boolean> = Pair(null, false)

    val error: Error? = null

    override fun perfomAction(): Flow<State<Boolean>> {
        return flow<State<Boolean>> {
            emit(State.loading())

            if (canExecute()){
                val dataInput = hashMapOf<String, Any>(
                    "name" to name.first!!,
                    "phoneNumber" to phoneNumber.first!!,
                    "position" to position.first!!.name
                )
                userPreference.getUserID()
                    .collect{ userId ->
                        userId?.let {
                            if (photoFile.first != null){
                                val photoUrl = authRepository.setPhotoUser(userId, photoFile.first!!)
                                dataInput.put("photoUrl", photoUrl)
                            }

                            emit(State.success(userRepository.editUser(userId, dataInput)))
                        }
                    }
            } else {
                emit(State.failed("error submit value have null"))
            }
        }.catch {
            emit(State.failed(it.message.toString()))
        }.flowOn(crDispatcher.ui())
    }

    private fun canExecute(): Boolean{
        return name.second && photoFile.second && position.second && phoneNumber.second
    }

    fun setName(value: String?){
        if (value.isNullOrBlank()){
            name = Pair(null, false)
            error?.errorName?.invoke("name masih kosong")
        } else {
            name = Pair(value, true)
        }
    }

    fun setPhoto(value: String?){
        if (value.isNullOrBlank()){
            photoFile = Pair(null, false)
            error?.errorPhoto?.invoke("photo masih kosong")
        } else {
            if (value.startsWith("http")){
                photoFile = Pair(null, true)
            }else {
                photoFile = Pair(Uri.parse(value), true)
            }
        }
    }

    fun setPosition(value: Position?){
        if (value == null){
            position = Pair(null, false)
            error?.errorPosition?.invoke("position masih kosong")
        } else {
            position = Pair(value, true)
        }
    }

    fun setPhoneNumber(value: String?){
        if (value.isNullOrBlank()){
            phoneNumber = Pair(null, false)
            error?.errorPhoneNumber?.invoke("phone number masih kosong")
        } else{
            phoneNumber = Pair(value, true)
        }
    }

    data class Error(
        val errorName: ((String) -> Unit)? = null,
        val errorPhoto: ((String) -> Unit)? = null,
        val errorPosition: ((String) -> Unit)? = null,
        val errorPhoneNumber: ((String) -> Unit)? = null,
    )
}