package com.redveloper.akusisi.ui.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.redveloper.rebator.utils.Event

class ProfileViewModel: ViewModel() {

    val textEvent = MutableLiveData<Event<String>>()

    fun setText(text: String){
        textEvent.value = Event(text)
    }
}