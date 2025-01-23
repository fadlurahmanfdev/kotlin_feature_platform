# Description

Android library handle platform implementation such as (device id, bluetooth, etc)

## Key Features

### Device ID

Fetch the unique identifier of device.

```kotlin
val platform = FeaturePlatformRepositoryImpl(applicationContext)

fun screenFunction() {
    val deviceId = platform.getDeviceId()
    // process device id
}
```

### Rooted Device

Check whether device is in root/jailbreak mode.

```kotlin
val platform = FeaturePlatformRepositoryImpl(applicationContext)

fun screenFunction() {
    val isRooted = platform.isRootedApps(withBusyBox = true)
    // process rooted device
}
```

### List Paired Bluetooth Devices

Fetch list paired bluetooth

```kotlin
val platform = FeaturePlatformRepositoryImpl(applicationContext)

fun screenFunction() {
    val listPairedBluetooth = featurePlatformRepository.getPairedBluetoothDevices()
    // process list paired bluetooth device
}
```