package com.fadlurahmanfdev.feature_platform.constant

import com.fadlurahmanfdev.feature_platform.exception.FeaturePlatformException

object PlatformExceptionConstant {
    val BLUETOOTH_NOT_ENABLED = FeaturePlatformException("BLUETOOTH_NOT_ENABLED", "Bluetooth service is not active")
}