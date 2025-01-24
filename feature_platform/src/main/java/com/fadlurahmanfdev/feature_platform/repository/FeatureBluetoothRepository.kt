package com.fadlurahmanfdev.feature_platform.repository

import android.bluetooth.BluetoothDevice
import com.fadlurahmanfdev.feature_platform.FeatureBluetooth
import com.fadlurahmanfdev.feature_platform.constant.PlatformExceptionConstant
import com.fadlurahmanfdev.feature_platform.exception.FeaturePlatformException

interface FeatureBluetoothRepository {
    /**
     * Check bluetooth service enabled
     * */
    fun isBluetoothEnabled(): Boolean

    /**
     * Fetch already paired bluetooth devices
     *
     *  @throws [FeaturePlatformException] [PlatformExceptionConstant.BLUETOOTH_NOT_ENABLED] if the bluetooth service is not enabled
     *  @see FeatureBluetooth.isBluetoothEnabled
     * */
    fun getPairedBluetoothDevices(): List<BluetoothDevice>

    /**
     * Connect with device using mac address.
     * if mac address in the parameter different with currently connected mac adress,
     * it will disconnect the old connection then connect to the new mac address
     *
     * @param macAddress mac address get from [FeatureBluetooth.getPairedBluetoothDevices]
     * */
    fun connect(macAddress: String): Boolean

    /**
     * Check whether there is connection to a bluetooth device
     * */
    fun isConnected(): Boolean

    /**
     * Check mac address connected if currently is connected with device
     * */
    fun getMacAddressConnected(): String?

    /**
     * Disconnect connection bluetooth to device, if there any connection
     * */
    fun disconnect()

    /**
     * Check if the local Bluetooth adapter is currently in the device discovery process
     * */
    fun isDiscovering(): Boolean

    /**
     * Cancel discovery process
     * */
    fun cancelDiscovery()
}