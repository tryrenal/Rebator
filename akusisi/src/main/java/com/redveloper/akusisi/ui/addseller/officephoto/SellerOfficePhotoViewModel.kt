package com.redveloper.akusisi.ui.addseller.officephoto

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.redveloper.akusisi.domain.usecase.addseller.SetSellerOfficePhotoUseCase
import com.redveloper.akusisi.ui.addseller.model.AddSellerModel
import com.redveloper.rebator.utils.Event
import com.redveloper.rebator.utils.State
import kotlinx.coroutines.launch
import java.io.File

class SellerOfficePhotoViewModel(
    private val setSellerOfficePhotoUseCase: SetSellerOfficePhotoUseCase
): ViewModel() {

    init {
        setSellerOfficePhotoUseCase.error = SetSellerOfficePhotoUseCase.Error(
            errorPhoto = {
                errorPhotoEvent.value = Event(it)
            }
        )
    }

    val errorPhotoEvent = MutableLiveData<Event<String>>()
    val loadingEvent = MutableLiveData<Event<Boolean>>()
    val successEvent = MutableLiveData<Event<AddSellerModel>>()

    var addSellerModel: AddSellerModel? = null
        set(value) {field = value}

    fun submit(file: File?){
        viewModelScope.launch {
            setSellerOfficePhotoUseCase.setPhoto(file)

            setSellerOfficePhotoUseCase.launch()
            setSellerOfficePhotoUseCase.resultFlow.collect{ state ->
                when(state){
                    is State.Loading -> loadingEvent.value = Event(true)
                    is State.Failed -> {
                        loadingEvent.value = Event(false)
                        errorPhotoEvent.value = Event(state.message)
                    }
                    is State.Success -> {
                        loadingEvent.value = Event(false)
                        val newAddSellerModel = addSellerModel?: AddSellerModel()
                        newAddSellerModel.officePhoto = state.data
                        successEvent.value = Event(newAddSellerModel)
                    }
                }
            }
        }
    }
}