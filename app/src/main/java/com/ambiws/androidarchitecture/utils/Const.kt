package com.ambiws.androidarchitecture.utils

object Const {
    const val APP_NAME_SHORT = "AA"
    const val MAX_LOGGING_TAG_LENGTH = 23 // IllegalArgumentException is thrown if the tag.length() > 23 for Nougat (7.0) and prior releases (API <= 25)
}
