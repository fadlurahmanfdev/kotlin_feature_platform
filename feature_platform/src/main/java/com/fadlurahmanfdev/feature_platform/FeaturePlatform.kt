package com.fadlurahmanfdev.feature_platform

import android.content.Context
import android.provider.Settings.Secure
import com.fadlurahmanfdev.feature_platform.repository.FeaturePlatformRepository
import com.scottyab.rootbeer.RootBeer
import java.util.UUID

class FeaturePlatform(private val context: Context) : FeaturePlatformRepository {
    private val rootBeer = RootBeer(context)

    companion object {
        val SPP_UUID: UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb")
    }

    /**
     * return unique identifier of deviceID
     */
    override fun getDeviceId(): String {
        return Secure.getString(context.contentResolver, Secure.ANDROID_ID)
    }

    /**
     * Check whether device is in root/jailbreak mode.
     *
     * BusyBox on Android is a software application that bundles a collection
     * of basic Unix utilities into a single executable file
     * which are typically not readily available on a standard Android system
     *
     * @param withBusyBox determine to check busy box in rooted/jailbreak device
     * @return true - if device is rooted & busy box detected (if withBusyBox is checked)
     * */
    override fun isRootedApps(withBusyBox: Boolean): Boolean {
        return if (withBusyBox) {
            rootBeer.isRootedWithBusyBoxCheck
        } else {
            rootBeer.isRooted
        }
    }
}