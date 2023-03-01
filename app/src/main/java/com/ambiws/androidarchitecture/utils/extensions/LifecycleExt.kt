package com.ambiws.androidarchitecture.utils.extensions

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData

fun <T> AppCompatActivity.subscribe(liveData: (LiveData<T>)?, onNext: (t: T) -> Unit) {
    liveData?.observe(this) {
        if (it != null) {
            onNext(it)
        }
    }
}

fun <T> Fragment.subscribe(liveData: (LiveData<T>)?, onNext: (t: T) -> Unit) {
    liveData?.observe(viewLifecycleOwner) {
        if (it != null) {
            onNext(it)
        }
    }
}
