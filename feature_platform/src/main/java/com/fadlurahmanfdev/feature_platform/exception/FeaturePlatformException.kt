package com.fadlurahmanfdev.feature_platform.exception

data class FeaturePlatformException(
    val code: String,
    override val message: String
) : Throwable(message = message)
