package com.fadlurahmanfdev.feature_platform

import android.content.Context
import android.content.pm.PackageManager
import android.provider.Settings.Secure
import com.fadlurahmanfdev.feature_platform.repository.FeaturePlatformRepository
import com.scottyab.rootbeer.RootBeer

class FeaturePlatform(private val context: Context) : FeaturePlatformRepository {
    private val rootBeer = RootBeer(context)

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
     *
     * @return true - if device is rooted & busy box detected (if withBusyBox is checked)
     * */
    override fun isRootedApps(
        withBusyBox: Boolean,
        checkSu: Boolean,
        checkRwPath: Boolean,
        checkSuBinary: Boolean,
        checkBusyBoxBinary: Boolean,
        checkMagiskBinary: Boolean
    ): Boolean {
        val isRooted = if (withBusyBox) {
            rootBeer.isRootedWithBusyBoxCheck || rootBeer.checkForRootNative()
        } else {
            rootBeer.isRooted || rootBeer.checkForRootNative()
        }

        if (isRooted) {
            return true
        }

        var suExist = false
        if (checkSu) {
            suExist = rootBeer.checkSuExists()
        }

        if (suExist) {
            return true
        }

        var rwPathWritable = false
        if (checkRwPath) {
            rwPathWritable = rootBeer.checkForRWPaths()
        }

        if (rwPathWritable) {
            return true
        }

        var suBinaryExist = false
        if (checkSuBinary) {
            suBinaryExist = rootBeer.checkForSuBinary()
        }

        if (suBinaryExist) {
            return true
        }

        var busyBoxBinaryDetected = false
        if (checkBusyBoxBinary) {
            busyBoxBinaryDetected = rootBeer.checkForBusyBoxBinary()
        }

        if (busyBoxBinaryDetected) {
            return true
        }

        var magiskBinaryDetected = false
        if (checkMagiskBinary) {
            magiskBinaryDetected = rootBeer.checkForMagiskBinary()
        }

        if (magiskBinaryDetected) {
            return true
        }

        return false
    }

    /**
     * Check is there any installed apps that have access to root management
     *
     * This function only check list of packages that known as a root management apps.
     * If there any new apps that known as a dangerous apps, you must be add as a [additionalPackagesToCheck]
     *
     * @param additionalPackagesToCheck additional packages to check
     * @return true if there any installed of root management apps
     * * */
    override fun isRootManagementAppsDetected(additionalPackagesToCheck: List<String>?): Boolean {
        var additionalPackages: Array<String>? = null

        if (additionalPackagesToCheck != null) {
            additionalPackages = arrayOf()
            for (i in 0..additionalPackagesToCheck.size) {
                additionalPackages[i] = additionalPackagesToCheck[i]
            }
        }

        return rootBeer.detectRootManagementApps(additionalPackages)
    }

    /**
     * Check is there any installed apps that required root access.
     *
     * This function only check list of packages that known as a dangerous apps.
     * If there any new apps that known as a dangerous apps, you must be add as a [additionalPackagesToCheck]
     *
     * @param additionalPackagesToCheck additional packages to check
     * @return true if there any installed app required root detected
     * */
    override fun isThereAnyAppsRequiredRootDetected(additionalPackagesToCheck: List<String>?): Boolean {
        var additionalPackages: Array<String>? = null

        if (additionalPackagesToCheck != null) {
            additionalPackages = arrayOf()
            for (i in 0..additionalPackagesToCheck.size) {
                additionalPackages[i] = additionalPackagesToCheck[i]
            }
        }
        return rootBeer.detectPotentiallyDangerousApps(additionalPackages)
    }

    /**
     * Check is there any installed apps that detect as a cloaking apps
     *
     * This function only check list of packages that known as a dangerous apps.
     * If there any new apps that known as a dangerous apps, you must be add as a [additionalPackagesToCheck]
     *
     * @param additionalPackagesToCheck additional packages to check
     * @return true if there any installed cloaking apps
     * */
    override fun isRootCloakingAppsDetected(additionalPackagesToCheck: List<String>?): Boolean {
        var additionalPackages: Array<String>? = null

        if (additionalPackagesToCheck != null) {
            additionalPackages = arrayOf()
            for (i in 0..additionalPackagesToCheck.size) {
                additionalPackages[i] = additionalPackagesToCheck[i]
            }
        }
        return rootBeer.detectRootCloakingApps(additionalPackages)
    }

    /**
     * Check whether app with specific package name installed.
     *
     * @param packageName package name for detect if any installed package name detected
     * */
    override fun isAppInstalledOrDetected(packageName: String): Boolean {
        val pm = context.packageManager
        try {
            pm.getPackageInfo(packageName, 0)
            return true
        } catch (e: PackageManager.NameNotFoundException) {
            // Exception thrown, package is not installed into the system
            return false
        }
    }
}