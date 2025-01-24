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

    /**
     * Connect with device using mac address.
     * if mac address in the parameter different with currently connected mac adress,
     * it will disconnect the old connection then connect to the new mac address
     *
     * @param macAddress mac address get from [FeatureBluetooth.getPairedBluetoothDevices]
     * */
    override fun connect(macAddress: String): Boolean {
        if (macAddress == _macAddressConnected && _bluetoothSocket?.isConnected == true) {
            _isConnected = true
            Log.i(
                this::class.java.simpleName,
                "bluetooth socket already connected with the same mac address"
            )
        }

        if (_isConnected) {
            return true
        }

        if (_macAddressConnected != null && _bluetoothSocket != null && macAddress != _macAddressConnected && _bluetoothSocket?.isConnected == true) {
            Log.i(
                this::class.java.simpleName,
                "bluetooth socket connected with different mac address, trying to disconnect"
            )
            disconnect()
        }

        _isConnected = false
        val device = bluetoothAdapter.getRemoteDevice(macAddress)
        _bluetoothSocket =
            device.createRfcommSocketToServiceRecord(device.uuids.firstOrNull()?.uuid ?: SPP_UUID)
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

    /**
     * Check whether there is connection to a bluetooth device
     * */
    override fun isConnected(): Boolean = _bluetoothSocket?.isConnected ?: false

    /**
     * Check mac address connected if currently is connected with device
     * */
    override fun getMacAddressConnected(): String? = _macAddressConnected

    /**
     * Disconnect connection bluetooth to device, if there any connection
     * */
    override fun disconnect() {
        if (_bluetoothSocket == null) {
            Log.i(
                this::class.java.simpleName,
                "failed to disconnect, either already disconnected or socket is missing"
            )
            _isConnected = false
            _macAddressConnected = null
            return
        }

        try {
            _bluetoothSocket!!.close()
            Log.i(this::class.java.simpleName, "successfully disconnected")
        } catch (e: Throwable) {
            Log.e(this::class.java.simpleName, "failed to disconnect: ${e.message}")
        }
        _bluetoothSocket = null
        _isConnected = false
        _macAddressConnected = null
    }

    /**
     * Check if the local Bluetooth adapter is currently in the device discovery process
     * */
    override fun isDiscovering(): Boolean = bluetoothManager.adapter.isDiscovering

    /**
     * Cancel discovery process
     * */
    override fun cancelDiscovery() {
        if (!isDiscovering()) {
            Log.i(this::class.java.simpleName, "bluetooth is no in discovery process")
            return
        }

        bluetoothManager.adapter.cancelDiscovery()
    }
}