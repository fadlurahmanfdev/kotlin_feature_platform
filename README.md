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

| variable           | default | description                                                                                                                                                                                                                       |
|--------------------|---------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| withBusyBox        | false   | determine whether check for busy box in rooted/jailbreak device                                                                                                                                                                   |
| checkSu            | true    | determine whether check for SU, this attempts a 'which su'                                                                                                                                                                        |
| checkRwPath        | true    | determine whether check path that should not be writable. When you're root you can change the permissions on common system directories, this method checks if any of these path Const. pathsThatShouldNotBeWritable are writable. |
| checkSuBinary      | true    | determine whether check for su binary exist.                                                                                                                                                                                      |
| checkBusyBoxBinary | true    | determine whether check if busy binary box exist.                                                                                                                                                                                 |
| checkMagiskBinary  | true    | determine whether check if magisk binary detected.                                                                                                                                                                                |

```kotlin
val platform = FeaturePlatform(applicationContext)

fun screenFunction() {
    val isRooted = platform.isRootedApps(
        withBusyBox = true,
        checkSu = true,
        checkRwPath = true,
        checkSuBinary = true,
        checkBusyBoxBinary = true,
        checkMagiskBinary = true,
    )
    // process rooted device
}
```

Check whether device is in root/jailbreak mode.

```kotlin
val platform = FeaturePlatform(applicationContext)

fun screenFunction() {
    val isRootManagementAppsDetected = platform.isRootManagementAppsDetected()
    // process checking root management apps
}
```

Check whether any apps installed that required root apps.

```kotlin
val platform = FeaturePlatform(applicationContext)

fun screenFunction() {
    val isThereAnyAppsRequiredRootDetected = platform.isThereAnyAppsRequiredRootDetected()
    // process result
}
```

Check whether any cloaking root apps detected.

```kotlin
val platform = FeaturePlatform(applicationContext)

fun screenFunction() {
    val isRootCloakingAppsDetected = platform.isRootCloakingAppsDetected()
    // process result
}
```

### App Detection

Check whether app for specific package name detected

```kotlin
val platform = FeaturePlatform(applicationContext)

fun screenFunction() {
    val isAppInstalledOrDetected = platform.isAppInstalledOrDetected(packageName = "com.whatsapp")
    // process result
}
```

## Bluetooth Implementation

### List Paired Bluetooth Devices

Fetch list already paired bluetooth.

```kotlin
val bluetooth = FeatureBluetooth(applicationContext)

fun screenFunction() {
    val listPairedBluetooth = bluetooth.getPairedBluetoothDevices()
    // process list paired bluetooth device
}
```

### Bluetooth Connection

Connect to a bluetooth device with specify mac address.

```kotlin
val bluetooth = FeatureBluetooth(applicationContext)

fun screenFunction() {
    val isConnected = bluetooth.connect(macAddress = "{mac_address}")
    // process connected bluetooth device
}
```

Check whether already connect with a bluetooth device.

```kotlin
val bluetooth = FeatureBluetooth(applicationContext)

fun screenFunction() {
    val isConnected = bluetooth.isConnected()
    // process connected bluetooth device
}
```

Check whether already connect with a bluetooth device.

```kotlin
val bluetooth = FeatureBluetooth(applicationContext)

fun screenFunction() {
    val isConnected = bluetooth.isConnected()
    // process connected bluetooth device
}
```

Check currently connected mac address bluetooth device.

```kotlin
val bluetooth = FeatureBluetooth(applicationContext)

fun screenFunction() {
    val isConnected = bluetooth.getMacAddressConnected()
    // process mac address that already connected
}
```

Disconnect connection with a bluetooth device, if there any connection with bluetooth device.

```kotlin
val bluetooth = FeatureBluetooth(applicationContext)

fun screenFunction() {
    bluetooth.disconnect()
    // process mac address that already connected
}
```

### Bluetooth Discovery

Check whether app is in discovering bluetooth device.

```kotlin
val bluetooth = FeatureBluetooth(applicationContext)

fun screenFunction() {
    val isDiscovering = bluetooth.isDiscovering()
    // process discovery bluetooth
}
```

Stop discovery bluetooth process if device is in discovering mode.

```kotlin
val bluetooth = FeatureBluetooth(applicationContext)

fun screenFunction() {
    bluetooth.cancelDiscovery()
    // process cancel process discovery bluetooth
}
```