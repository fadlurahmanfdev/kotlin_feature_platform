package com.fadlurahmanfdev.feature_platform.exception

data class CorePlatformException(
    val code: String,
    override val message: String
) : Throwable(message = message)
