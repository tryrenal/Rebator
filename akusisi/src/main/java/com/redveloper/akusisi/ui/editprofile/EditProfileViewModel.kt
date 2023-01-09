package com.redveloper.akusisi.ui.editprofile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.redveloper.akusisi.ui.editprofile.model.EditProfileModel
import com.redveloper.rebator.domain.entity.User
import com.redveloper.rebator.domain.usecase.user.EditUserUseCase
import com.redveloper.rebator.domain.usecase.user.GetUserUseCase
import com.redveloper.rebator.utils.Event
import com.redveloper.rebator.utils.State
import kotlinx.coroutines.launch

class EditProfileViewModel(
    private val editUserUseCase: EditUserUseCase,
    private val getUserUseCase: GetUserUseCase
): ViewModel() {

    init {
        editUserUseCase.error = EditUserUseCase.Error(
            errorName = {
                errorNameEvent.value = Event(it)
            },
            errorPhoneNumber = {
                errorPhoneNumberEvent.value = Event(it)
            },
            errorPhoto = {
                errorPhotoEvent.value = Event(it)
            },
            errorPosition = {
                errorPosition.value = Event(it)
            }
        )
    }

    val errorNameEvent = MutableLiveData<Event<String>>()
    val errorPhoneNumberEvent = MutableLiveData<Event<String>>()
    val errorPhotoEvent = MutableLiveData<Event<String>>()
    val errorPosition = MutableLiveData<Event<String>>()

    val loadingEvent = MutableLiveData<Event<Boolean>>()
    val successSubmitEvent = MutableLiveData<Event<Boolean>>()
    val errorSubmitEvent = MutableLiveData<Event<String>>()
    val getUserEvent = MutableLiveData<Event<User>>()
    val errorGetUserEvent = MutableLiveData<Event<String>>()

    var photoUri: String? = null
        set(value) {field = value}

    fun submit(data: EditProfileModel){
        viewModelScope.launch {
            editUserUseCase.launch()
            editUserUseCase.resultFlow.collect{ state ->
                when(state){
                    is State.Loading -> loadingEvent.value = Event(true)
                    is State.Failed -> {
                        loadingEvent.value = Event(false)
                        errorSubmitEvent.value = Event(state.message)
                    }
                    is State.Success -> {
                        loadingEvent.value = Event(false)
                        successSubmitEvent.value = Event(state.data)
                    }
                }
            }
        }
    }

    fun getUser(){
        viewModelScope.launch {
            getUserUseCase.launch()
            getUserUseCase.resultFlow.collect{ state ->
                when(state){
                    is State.Loading -> loadingEvent.value = Event(true)
                    is State.Failed -> {
                        loadingEvent.value = Event(false)
                        errorGetUserEvent.value = Event(state.message)
                    }
                    is State.Success -> {
                        loadingEvent.value = Event(false)
                        getUserEvent.value = Event(state.data)
                    }
                }
            }
        }
    }
}