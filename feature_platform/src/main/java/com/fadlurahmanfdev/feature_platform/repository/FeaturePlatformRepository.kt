package com.fadlurahmanfdev.feature_platform.repository

import android.content.pm.PackageManager

interface FeaturePlatformRepository {

    /**
     * return unique identifier of deviceID
     */
    fun getDeviceId(): String

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
    fun isRootedApps(withBusyBox: Boolean = false): Boolean

    /**
     * Check whether device is in root/jailbreak mode.
     *
     * BusyBox on Android is a software application that bundles a collection
     * of basic Unix utilities into a single executable file
     * which are typically not readily available on a standard Android system
     *
     * @param withBusyBox determine whether check for busy box in rooted/jailbreak device
     * @param checkSu determine whether check for SU, this attempts a 'which su'
     * @param checkRwPath determine whether check path that should not be writable. When you're root you can change the permissions on common system directories,
     * this method checks if any of these path Const. pathsThatShouldNotBeWritable are writable.
     * @param checkSuBinary determine whether check for su binary exist.
     * @param checkBusyBoxBinary determine whether check if busy binary box exist.
     * @param checkMagiskBinary determine whether check if magisk binary detected.
     * @return true - if device is rooted & busy box detected (if withBusyBox is checked)
     * */
    fun isRootedApps(
        withBusyBox: Boolean = false,
        checkSu: Boolean = true,
        checkRwPath: Boolean = true,
        checkSuBinary: Boolean = true,
        checkBusyBoxBinary: Boolean = true,
        checkMagiskBinary: Boolean = true
    ): Boolean

    /**
     * Check is there any installed apps that have access to root management
     *
     * @param additionalPackagesToCheck additional packages to check
     * @return true if there any installed of root management apps
     * * */
    fun isRootManagementAppsDetected(additionalPackagesToCheck: List<String>?): Boolean

    /**
     * Check is there any installed apps that required root access.
     *
     * This function only check list of packages that known as a dangerous apps.
     * If there any new apps that known as a dangerous apps, you must be add as a [additionalPackagesToCheck]
     *
     * @param additionalPackagesToCheck additional packages to check
     * @return true if there any installed of root management apps
     * */
    fun isThereAnyAppsRequiredRootDetected(additionalPackagesToCheck: List<String>?): Boolean

    /**
     * Check is there any installed apps that detect as a cloaking apps
     *
     * This function only check list of packages that known as a dangerous apps.
     * If there any new apps that known as a dangerous apps, you must be add as a [additionalPackagesToCheck]
     *
     * @param additionalPackagesToCheck additional packages to check
     * @return true if there any installed cloaking apps
     * */
    fun isRootCloakingAppsDetected(additionalPackagesToCheck: List<String>?): Boolean

    /**
     * Check whether app with specific package name installed.
     *
     * @param packageName package name for detect if any installed package name detected
     * */
    fun isAppInstalledOrDetected(packageName: String): Boolean

}