package com.fadlurahmanfdev.feature_platform.repository

import android.bluetooth.BluetoothDevice

interface FeatureBluetoothRepository {
    /**
     * Check bluetooth service enabled
     * */
    fun isBluetoothEnabled(): Boolean

    fun getPairedBluetoothDevices(): List<BluetoothDevice>

    fun connect(macAddress: String): Boolean
    fun getMacAddressConnected(): String?
    fun disconnect()

    fun isDiscovering(): Boolean

    fun cancelDiscovery()
}