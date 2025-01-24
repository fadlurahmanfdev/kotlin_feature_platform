package com.fadlurahmanfdev.example.presentation

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.fadlurahmanfdev.example.R
import com.fadlurahmanfdev.example.data.FeatureModel
import com.fadlurahmanfdev.example.domain.ExampleCorePlatformUseCaseImpl
import com.fadlurahmanfdev.example.service.DiscoverBluetoothReceiver
import com.fadlurahmanfdev.feature_platform.repository.FeatureBluetoothRepository
import com.fadlurahmanfdev.feature_platform.FeatureBluetooth
import com.fadlurahmanfdev.feature_platform.FeaturePlatform
import com.fadlurahmanfdev.feature_platform.repository.FeaturePlatformRepository

class MainActivity : AppCompatActivity(), ListExampleAdapter.Callback {
    lateinit var viewModel: MainViewModel
    lateinit var featurePlatform: FeaturePlatformRepository
    lateinit var featureBluetooth: FeatureBluetoothRepository

    private val features: List<FeatureModel> = listOf<FeatureModel>(
        FeatureModel(
            featureIcon = R.drawable.baseline_developer_mode_24,
            title = "Get Device ID",
            desc = "Get Unique Identifier of Device",
            enum = "GET_DEVICE_ID"
        ),
        FeatureModel(
            featureIcon = R.drawable.baseline_developer_mode_24,
            title = "Check Rooted Device",
            desc = "Check whether device is rooted/jailbreak",
            enum = "CHECK_ROOTED_DEVICE"
        ),
        FeatureModel(
            featureIcon = R.drawable.baseline_developer_mode_24,
            title = "BLUETOOTH FEATURE",
            desc = "----------------------------",
            enum = "BLUETOOTH_DIVIDER"
        ),
        FeatureModel(
            featureIcon = R.drawable.baseline_developer_mode_24,
            title = "Check Bluetooth Permission",
            desc = "Check whether bluetooth permission granted",
            enum = "IS_BLUETOOTH_GRANTED"
        ),
        FeatureModel(
            featureIcon = R.drawable.baseline_developer_mode_24,
            title = "Request Bluetooth Permission",
            desc = "Request bluetooth permission",
            enum = "REQUEST_BLUETOOTH_PERMISSION"
        ),
        FeatureModel(
            featureIcon = R.drawable.baseline_developer_mode_24,
            title = "Check Bluetooth Service Enabled",
            desc = "Check whether bluetooth service enabled",
            enum = "IS_BLUETOOTH_ENABLED"
        ),
        FeatureModel(
            featureIcon = R.drawable.baseline_developer_mode_24,
            title = "Request Enabled Bluetooth Service",
            desc = "Request Enabled Bluetooth Service",
            enum = "REQUEST_ENABLED_BLUETOOTH_SERVICE"
        ),
        FeatureModel(
            featureIcon = R.drawable.baseline_developer_mode_24,
            title = "List Paired Bluetooth Device",
            desc = "Get list of paired bluetooth device",
            enum = "LIST_PAIRED_BLUETOOTH_DEVICE"
        ),
        FeatureModel(
            featureIcon = R.drawable.baseline_developer_mode_24,
            title = "Discover Nearby Bluetooth Device",
            desc = "Discover Nearby Bluetooth Device",
            enum = "DISCOVER_NEARBY_BLUETOOTH_DEVICE"
        ),
        FeatureModel(
            featureIcon = R.drawable.baseline_developer_mode_24,
            title = "Connect with device",
            desc = "Connect with device",
            enum = "CONNECT_WITH_DEVICE"
        ),
        FeatureModel(
            featureIcon = R.drawable.baseline_developer_mode_24,
            title = "Disconnect with device",
            desc = "Disconnect with device",
            enum = "DISCONNECT_WITH_DEVICE"
        ),
    )

    private lateinit var rv: RecyclerView

    private lateinit var adapter: ListExampleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        rv = findViewById<RecyclerView>(R.id.rv)

        viewModel = MainViewModel(
            exampleCorePlatformUseCase = ExampleCorePlatformUseCaseImpl()
        )

        featurePlatform = FeaturePlatform(applicationContext)
        featureBluetooth = FeatureBluetooth(applicationContext)

        rv.setItemViewCacheSize(features.size)
        rv.setHasFixedSize(true)

        adapter = ListExampleAdapter()
        adapter.setCallback(this)
        adapter.setList(features)
        adapter.setHasStableIds(true)
        rv.adapter = adapter
    }

    val locationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            Log.d(MainActivity::class.java.simpleName, "IS LOCATION PERMISSION GRANTED: $it")
        }

    @SuppressLint("MissingPermission")
    override fun onClicked(item: FeatureModel) {
        when (item.enum) {
            "GET_DEVICE_ID" -> {
                val deviceId = featurePlatform.getDeviceId()
                Log.d(this::class.java.simpleName, "device id: $deviceId")
            }

            "CHECK_ROOTED_DEVICE" -> {
                val isRootedDevice = featurePlatform.isRootedApps(withBusyBox = true)
                Log.d(
                    this::class.java.simpleName,
                    "is rooted device with busy box: $isRootedDevice"
                )
            }

            "IS_BLUETOOTH_GRANTED" -> {
                var permissionResult = PackageManager.PERMISSION_DENIED
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    permissionResult = ContextCompat.checkSelfPermission(
                        applicationContext,
                        android.Manifest.permission.BLUETOOTH_SCAN
                    )
                } else {
                    TODO("VERSION.SDK_INT < S")
                }
                val isGranted = PackageManager.PERMISSION_GRANTED == permissionResult
                Log.d(this::class.java.simpleName, "is bluetooth granted: $isGranted")
            }

            "REQUEST_BLUETOOTH_PERMISSION" -> {
                bluetoothPermissionLauncher.launch(android.Manifest.permission.BLUETOOTH_SCAN)
            }

            "IS_BLUETOOTH_ENABLED" -> {
                val isEnabled = featureBluetooth.isBluetoothEnabled()
                Log.d(this::class.java.simpleName, "is bluetooth service enabled: $isEnabled")
            }

            "REQUEST_ENABLED_BLUETOOTH_SERVICE" -> {
                val isEnabled = featureBluetooth.isBluetoothEnabled()
                if (!isEnabled) {
                    val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                    bluetoothServiceLauncher.launch(enableBtIntent)
                }
            }

            "LIST_PAIRED_BLUETOOTH_DEVICE" -> {
                val pairedBluetoothDevices = featureBluetooth.getPairedBluetoothDevices()
                pairedBluetoothDevices.forEach {
                    Log.d(
                        this::class.java.simpleName, "paired bluetooth: \n" +
                                "Name: ${it.name}\n" +
                                "Address: ${it.address}\n" +
                                "Type: ${it.type}\n" +
                                "Bond State: ${it.bondState}"
                    )
                }
            }

            "DISCOVER_NEARBY_BLUETOOTH_DEVICE" -> {
                if (!featureBluetooth.isDiscovering()) {
                    val intentFilter = IntentFilter(BluetoothDevice.ACTION_FOUND)
                    registerReceiver(discoverBluetoothReceiver, intentFilter)
                    isDiscoverBluetoothReceiverActive = true
                }
            }

            "CONNECT_WITH_DEVICE" -> {
                val isConnected = featureBluetooth.connect("DC:0D:51:F5:7E:AD")
                Log.d(this::class.java.simpleName, "is successfully connected: $isConnected")
            }

            "DISCONNECT_WITH_DEVICE" -> {
                featureBluetooth.disconnect()
            }
        }
    }

    override fun onPause() {
        if (isDiscoverBluetoothReceiverActive) {
            unregisterReceiver(discoverBluetoothReceiver)
            isDiscoverBluetoothReceiverActive = false
        }
        super.onPause()
    }

    private val bluetoothPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            Log.d(this::class.java.simpleName, "bluetooth permission granted: $it")
        }

    private val bluetoothServiceLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            val isEnabled = featureBluetooth.isBluetoothEnabled()
            Log.d(this::class.java.simpleName, "bluetooth service launcher: $isEnabled")
        }

    private val discoverBluetoothReceiver = DiscoverBluetoothReceiver()
    private var isDiscoverBluetoothReceiverActive = false
}