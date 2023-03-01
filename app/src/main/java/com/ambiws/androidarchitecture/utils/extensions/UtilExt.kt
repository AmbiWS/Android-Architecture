package com.ambiws.androidarchitecture.utils.extensions

val <T : Any> T.className: String
    get() = this::class.java.simpleName
