# Description

Android library handle platform implementation such as (device id, bluetooth, etc)

## Key Features

## Platform Implementation

### Device ID

Fetch the unique identifier of device.

```kotlin
val platform = FeaturePlatform(applicationContext)

fun screenFunction() {
    val deviceId = platform.getDeviceId()
    // process device id
}
```

### Rooted Device

Check whether device is in root/jailbreak mode.

```kotlin
val platform = FeaturePlatform(applicationContext)

fun screenFunction() {
    val isRooted = platform.isRootedApps(withBusyBox = true)
    // process rooted device
}
```

## Bluetooth Implementation

### List Paired Bluetooth Devices

Fetch list paired bluetooth

```kotlin
val bluetooth = FeatureBluetooth(applicationContext)

fun screenFunction() {
    val listPairedBluetooth = bluetooth.getPairedBluetoothDevices()
    // process list paired bluetooth device
}
```

### Bluetooth Connection

Connect to a bluetooth device

```kotlin
val bluetooth = FeatureBluetooth(applicationContext)

fun screenFunction() {
    val isConnected = bluetooth.connect(macAddress = "{mac_address}")
    // process connected bluetooth device
}
```

Check whether already connect with a bluetooth device

```kotlin
val bluetooth = FeatureBluetooth(applicationContext)

fun screenFunction() {
    val isConnected = bluetooth.isConnected()
    // process connected bluetooth device
}
```

Check whether already connect with a bluetooth device

```kotlin
val bluetooth = FeatureBluetooth(applicationContext)

fun screenFunction() {
    val isConnected = bluetooth.isConnected()
    // process connected bluetooth device
}
```

Check currently connected mac address

```kotlin
val bluetooth = FeatureBluetooth(applicationContext)

fun screenFunction() {
    val isConnected = bluetooth.getMacAddressConnected()
    // process mac address that already connected
}
```

Disconnect connection with a bluetooth device, if there any

```kotlin
val bluetooth = FeatureBluetooth(applicationContext)

fun screenFunction() {
    bluetooth.disconnect()
    // process mac address that already connected
}
```

### Bluetooth Discovery

Check whether app is in process discover bluetooth device

```kotlin
val bluetooth = FeatureBluetooth(applicationContext)

fun screenFunction() {
    val isDiscovering = bluetooth.isDiscovering()
    // process discovery bluetooth
}
```

Stop discovery process if any

```kotlin
val bluetooth = FeatureBluetooth(applicationContext)

fun screenFunction() {
    bluetooth.cancelDiscovery()
    // process cancel process discovery bluetooth
}
```