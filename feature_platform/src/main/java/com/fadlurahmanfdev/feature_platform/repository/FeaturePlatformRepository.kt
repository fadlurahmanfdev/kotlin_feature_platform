package com.fadlurahmanfdev.feature_platform.repository

import android.bluetooth.BluetoothDevice

interface FeaturePlatformRepository {

    /**
     * return unique identifier of deviceID
     */
    fun getDeviceId(): String

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
    fun isRootedApps(withBusyBox: Boolean = false): Boolean
}