package com.fadlurahmanfdev.feature_platform.repository

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.Context
import android.provider.Settings.Secure
import com.fadlurahmanfdev.feature_platform.constant.PlatformExceptionConstant
import com.fadlurahmanfdev.feature_platform.exception.FeaturePlatformException
import com.scottyab.rootbeer.RootBeer

class FeaturePlatformRepositoryImpl(private val context: Context) : FeaturePlatformRepository {
    private val rootBeer = RootBeer(context)
    private val bluetoothManager =
        context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager

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

    /**
     * Check bluetooth service enabled
     * */
    override fun isBluetoothEnabled(): Boolean {
        return bluetoothManager.adapter.isEnabled
    }

    override fun getPairedBluetoothDevices(): List<BluetoothDevice> {
        if (!isBluetoothEnabled()) {
            throw FeaturePlatformException(
                code = PlatformExceptionConstant.BLUETOOTH_NOT_ENABLED.code,
                message = PlatformExceptionConstant.BLUETOOTH_NOT_ENABLED.message,
            )
        }
        return bluetoothManager.adapter.bondedDevices.toList()
    }
}