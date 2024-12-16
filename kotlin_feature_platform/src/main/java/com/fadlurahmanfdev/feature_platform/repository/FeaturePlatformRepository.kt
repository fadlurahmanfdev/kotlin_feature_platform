package com.fadlurahmanfdev.feature_platform.repository

import android.content.Context

interface FeaturePlatformRepository {

    /**
     * return unique identifier of deviceID
     */
    fun getDeviceId(context: Context): String
}