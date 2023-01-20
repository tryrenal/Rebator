package com.redveloper.akusisi.ui.detailseller

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.redveloper.akusisi.domain.entity.Seller
import com.redveloper.akusisi.domain.usecase.seller.GetDetailSellerUseCase
import com.redveloper.rebator.utils.Event
import com.redveloper.rebator.utils.State
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DetailSellerViewModel(
    private val getDetailSellerUseCase: GetDetailSellerUseCase
): ViewModel() {

    init {
        getDetailSellerUseCase.error = GetDetailSellerUseCase.Error(
            errorTiktokId = {
                errorTiktokIdEvent.value = Event(it)
            }
        )
    }

    val loadingEvent = MutableLiveData<Event<Boolean>>()
    val errorTiktokIdEvent = MutableLiveData<Event<String>>()
    val errorDetailEvent = MutableLiveData<Event<String>>()
    val successDetailEvent = MutableLiveData<Event<Seller>>()

    fun getDetailSeller(tiktokId: String?){
        viewModelScope.launch {
            getDetailSellerUseCase.setTiktokId(tiktokId)
            getDetailSellerUseCase.launch()

            getDetailSellerUseCase.resultFlow.collect{ state ->
                when(state){
                    is State.Loading -> loadingEvent.value = Event(true)
                    is State.Failed -> {
                        loadingEvent.value = Event(false)
                        errorDetailEvent.value = Event(state.message)
                    }
                    is State.Success -> {
                        loadingEvent.value = Event(false)
                        successDetailEvent.value = Event(state.data)
                    }
                }
            }
        }
    }
}