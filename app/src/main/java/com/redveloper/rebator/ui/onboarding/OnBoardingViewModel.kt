package com.redveloper.rebator.ui.onboarding

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.redveloper.rebator.data.local.preference.onboarding.OnBoardingPreference
import com.redveloper.rebator.utils.Event
import kotlinx.coroutines.launch

class OnBoardingViewModel (
    private val onBoardingPreference: OnBoardingPreference
) : ViewModel() {

    val toLoginEvent = MutableLiveData<Event<Any>>()

    fun setOnBoardingStatus(status: Boolean){
        viewModelScope.launch {
            onBoardingPreference.saveOnBoardingStatus(status)
        }.invokeOnCompletion {
            if (it == null){
                toLoginEvent.value = Event(Any())
            }
        }
    }
}