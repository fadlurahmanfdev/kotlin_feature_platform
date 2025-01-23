package com.fadlurahmanfdev.example.service

import android.bluetooth.BluetoothDevice
import android.util.Log
import com.fadlurahmanfdev.feature_platform.service.FeatureDiscoverBluetoothReceiver

class DiscoverBluetoothReceiver : FeatureDiscoverBluetoothReceiver() {
    override fun onBluetoothDeviceDiscover(device: BluetoothDevice) {
        Log.d(
            this::class.java.simpleName, "found bluetooth device: \n" +
                    "Name: ${device.name}\n" +
                    "Mac Address: ${device.address}"
        )
    }
}