package com.fadlurahmanfdev.feature_platform

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import com.fadlurahmanfdev.feature_platform.constant.PlatformExceptionConstant
import com.fadlurahmanfdev.feature_platform.exception.FeaturePlatformException
import com.fadlurahmanfdev.feature_platform.repository.FeatureBluetoothRepository
import java.util.UUID

class FeatureBluetooth(private val context: Context) : FeatureBluetoothRepository {
    private val bluetoothManager =
        context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
    private val bluetoothAdapter: BluetoothAdapter = bluetoothManager.adapter

    companion object {
        val SPP_UUID: UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb")
    }

    /**
     * Check bluetooth service enabled
     * */
    override fun isBluetoothEnabled(): Boolean {
        return bluetoothManager.adapter.isEnabled
    }

    /**
     * Fetch already paired bluetooth devices
     *
     *  @throws [FeaturePlatformException] [PlatformExceptionConstant.BLUETOOTH_NOT_ENABLED] if the bluetooth service is not enabled
     *  @see FeatureBluetooth.isBluetoothEnabled
     * */
    override fun getPairedBluetoothDevices(): List<BluetoothDevice> {
        if (!isBluetoothEnabled()) {
            throw FeaturePlatformException(
                code = PlatformExceptionConstant.BLUETOOTH_NOT_ENABLED.code,
                message = PlatformExceptionConstant.BLUETOOTH_NOT_ENABLED.message,
            )
        }
        return bluetoothManager.adapter.bondedDevices.toList()
    }

    private var _isConnected = false

    private var _bluetoothSocket: BluetoothSocket? = null

    private var _macAddressConnected: String? = null

    override fun connect(macAddress: String): Boolean {
        _isConnected = false
        val device = bluetoothAdapter.getRemoteDevice(macAddress)
        _bluetoothSocket =
            device.createRfcommSocketToServiceRecord(device.uuids.firstOrNull()?.uuid ?: SPP_UUID)

        Log.d(this::class.java.simpleName, "is connected: ${_bluetoothSocket!!.isConnected}")
        try {
            bluetoothAdapter.cancelDiscovery()
        } catch (e: Throwable) {
            Log.w(this::class.java.simpleName, "failed to cancel discovery")
        }

        try {
            _bluetoothSocket!!.connect()
            _isConnected = true
            _macAddressConnected = macAddress
        } catch (e: Throwable) {
            Log.e(this::class.java.simpleName, "failed to connect: ${e.message}")
            _isConnected = false
        }

        return _isConnected
    }

    override fun getMacAddressConnected(): String? = _macAddressConnected

    override fun disconnect() {
        if (_bluetoothSocket == null) {
            Log.d(this::class.java.simpleName, "bluetooth socket missing")
            _isConnected = false
            _macAddressConnected = null
            return
        }

        try {
            _bluetoothSocket!!.close()
            Log.i(this::class.java.simpleName, "successfully disconnect")
        } catch (e: Throwable) {
            Log.e(this::class.java.simpleName, "failed to disconnect: ${e.message}")
        }
        _bluetoothSocket = null
        _isConnected = false
        _macAddressConnected = null
    }

    override fun isDiscovering(): Boolean = bluetoothManager.adapter.isDiscovering

    override fun cancelDiscovery() {
        bluetoothManager.adapter.cancelDiscovery()
    }
}