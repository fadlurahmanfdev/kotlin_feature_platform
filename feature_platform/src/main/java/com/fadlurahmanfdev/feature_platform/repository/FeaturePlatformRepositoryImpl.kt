package com.fadlurahmanfdev.feature_platform.repository

import android.content.Context
import android.provider.Settings.Secure

class FeaturePlatformRepositoryImpl : FeaturePlatformRepository {
    override fun getDeviceId(context: Context): String {
        return Secure.getString(context.contentResolver, Secure.ANDROID_ID)
    }
}