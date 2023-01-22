package com.redveloper.inkubasi.ui.updateseller

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.redveloper.inkubasi.domain.usecase.updateseller.GetPotentialSellerUseCase
import com.redveloper.inkubasi.domain.usecase.updateseller.GetResultVisitUseCase
import com.redveloper.inkubasi.domain.usecase.updateseller.GetStatusSellerUseCase
import com.redveloper.rebator.utils.Event

class UpdateSellerViewModel(
    private val getPotentialSellerUseCase: GetPotentialSellerUseCase,
    private val getResultVisitUseCase: GetResultVisitUseCase,
    private val getStatusSellerUseCase: GetStatusSellerUseCase
) : ViewModel(){

    val listPotentialEvent = MutableLiveData<Event<ArrayList<Pair<String, String>>>>()
    var potentialSellerSelected: Pair<String, String>? = null
    val listResultVisitEvent = MutableLiveData<Event<ArrayList<Pair<String, String>>>>()
    var resultVisitSelected: Pair<String, String>? = null
    val listStatusSellerEvent = MutableLiveData<Event<ArrayList<Pair<String, String>>>>()
    var statusSellerSelected: Pair<String, String>? = null

    fun getListPotential(){
        getPotentialSellerUseCase.getListPotential {
            listPotentialEvent.value = Event(it)
        }
    }

    fun getListResultVisit(){
        getResultVisitUseCase.getListResultVisit {
            listResultVisitEvent.value = Event(it)
        }
    }

    fun getListStatus(){
        getStatusSellerUseCase.getListStatus {
            listStatusSellerEvent.value = Event(it)
        }
    }
}