package co.id.fadlurahmanfdev.kotlin_core_platform.data.repository

import android.content.Context
import android.os.Build
import android.provider.Settings
import android.provider.Settings.Secure
import java.io.File

class CorePlatformRepositoryImpl : CorePlatformRepository {
    override fun getDeviceId(context: Context): String {
        return Secure.getString(context.contentResolver, Secure.ANDROID_ID)
    }

    override fun isDevelopmentModeEnabled(context: Context): Boolean {
        return Settings.Secure.getInt(
            context.contentResolver,
            Settings.Global.DEVELOPMENT_SETTINGS_ENABLED,
            0
        ) != 0
    }

    override fun isEmulatorDevice(context: Context): Boolean {
        val androidId = Secure.getString(context.contentResolver, "android_id")
        return "sdk" == Build.PRODUCT || "google_sdk" == Build.PRODUCT || androidId == null
    }

    override fun isRootedDevice(context: Context): Boolean {
        val isEmulator: Boolean = isEmulatorDevice(context)
        val buildTags = Build.TAGS
        if (!isEmulator && buildTags != null && buildTags.contains("test-keys")) {
            return true
        } else {
            var file = File("/system/app/Superuser.apk")
            if (file.exists()) {
                return true
            } else {
                file = File("/system/xbin/su")
                return !isEmulator && file.exists()
            }
        }
    }
}