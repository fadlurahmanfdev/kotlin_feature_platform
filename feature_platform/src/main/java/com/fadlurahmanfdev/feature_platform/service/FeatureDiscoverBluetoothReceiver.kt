package com.fadlurahmanfdev.feature_platform.service

import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build

abstract class FeatureDiscoverBluetoothReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        when (intent?.action) {
            BluetoothDevice.ACTION_FOUND -> {
                var device: BluetoothDevice? = null
                when {
                    Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> {
                        device = intent.getParcelableExtra(
                            BluetoothDevice.EXTRA_DEVICE,
                            BluetoothDevice::class.java
                        )
                    }

                    else -> {
                        device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
                    }
                }

                if (device != null) {
                    onBluetoothDeviceDiscover(device)
                }
            }
        }
    }

    abstract fun onBluetoothDeviceDiscover(device: BluetoothDevice)
}