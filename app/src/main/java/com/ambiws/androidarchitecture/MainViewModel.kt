package com.ambiws.androidarchitecture

import androidx.lifecycle.ViewModel
import com.ambiws.androidarchitecture.utils.SingleLiveEvent

class MainViewModel : ViewModel() {

    val startDestinationEvent = SingleLiveEvent<Int>()

    fun initStartDestination() {
        startDestinationEvent.value = R.id.homeFragment
    }
}
