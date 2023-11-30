package com.loper7.miit_rule_checker

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.pm.VersionedPackage
import android.location.LocationListener
import android.os.Build
import java.lang.reflect.Member

object MIITMethods {

    @JvmStatic
    fun getDefaultMethods() : MutableList<Member?> {
        return mutableListOf(WifiInfo.getMacAddress ,
            WifiInfo.getIpAddress ,
            LocationManager.getLastKnownLocation ,
            LocationManager.requestLocationUpdates ,
            NetworkInterface.getHardwareAddress ,
            ApplicationPackageManager.getInstalledPackages ,
            ApplicationPackageManager.getInstalledApplications ,
            ApplicationPackageManager.getPackageInfo ,
            ApplicationPackageManager.getInstallerPackageName ,
            PackageManager.getInstalledPackages ,
            PackageManager.getInstalledApplications ,
            PackageManager.getPackageInfo ,
            PackageManager.getPackageInfo1 ,
            PackageManager.getPackageInfo2 ,
            PackageManager.getPackageInfo3 ,
            PackageManager.getInstallerPackageName ,
            Secure.getString ,
            TelephonyManager.getDeviceId ,
            TelephonyManager.getDeviceIdWithInt ,
            TelephonyManager.getImei ,
            TelephonyManager.getImeiWithInt ,
            TelephonyManager.getSubscriberId)
    }
    object WifiInfo {
        private val clazz = android.net.wifi.WifiInfo::class.java
        @JvmStatic
        val getMacAddress = clazz.getDeclaredMethod("getMacAddress")
        @JvmStatic
        val getIpAddress = clazz.getDeclaredMethod("getIpAddress")
    }

    object LocationManager {
        private val clazz = android.location.LocationManager::class.java
        @JvmStatic
        val getLastKnownLocation = clazz.getDeclaredMethod("getLastKnownLocation" , String::class.java)
        @JvmStatic
        val requestLocationUpdates = clazz.getDeclaredMethod("requestLocationUpdates" ,
            String::class.java ,
            Long::class.javaPrimitiveType ,
            Float::class.javaPrimitiveType ,
            LocationListener::class.java)
    }

    object NetworkInterface {
        private val clazz = java.net.NetworkInterface::class.java
        @JvmStatic
        val getHardwareAddress = clazz.getDeclaredMethod("getHardwareAddress")
    }

    object ApplicationPackageManager {
        @SuppressLint("PrivateApi")
        private val clazz : Class<*>? = try {
            Class.forName("android.app.ApplicationPackageManager")
        } catch (e : Exception) {
            null
        }
        @JvmStatic
        val getInstalledPackages = clazz?.getDeclaredMethod("getInstalledPackages" , Int::class.javaPrimitiveType)
        @JvmStatic
        val getInstalledApplications =
            clazz?.getDeclaredMethod("getInstalledApplications" , Int::class.javaPrimitiveType)
        @JvmStatic
        val getInstallerPackageName = clazz?.getDeclaredMethod("getInstallerPackageName" , String::class.java)
        @JvmStatic
        val getPackageInfo =
            clazz?.getDeclaredMethod("getPackageInfo" , String::class.java , Int::class.javaPrimitiveType)
    }

    object PackageManager {
        private val clazz = android.content.pm.PackageManager::class.java
        @JvmStatic
        val getInstalledPackages = clazz.getDeclaredMethod("getInstalledPackages" , Int::class.javaPrimitiveType)
        @JvmStatic
        val getInstalledApplications =
            clazz.getDeclaredMethod("getInstalledApplications" , Int::class.javaPrimitiveType)
        @JvmStatic
        val getInstallerPackageName = clazz.getDeclaredMethod("getInstallerPackageName" , String::class.java)
        @JvmStatic
        val getPackageInfo =
            clazz.getDeclaredMethod("getPackageInfo" , String::class.java , Int::class.javaPrimitiveType)
        @JvmStatic
        val getPackageInfo1 = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            clazz.getDeclaredMethod("getPackageInfo" , String::class.java , android.content.pm.PackageManager.PackageInfoFlags::class.java)
        } else {
            null
        }
        @JvmStatic
       val getPackageInfo2 = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
           clazz.getDeclaredMethod("getPackageInfo" , VersionedPackage::class.java ,Int::class.javaPrimitiveType)
       } else {
           null
       }
        @JvmStatic
        val getPackageInfo3 = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            clazz.getDeclaredMethod("getPackageInfo" , VersionedPackage::class.java, android.content.pm.PackageManager.PackageInfoFlags::class.java)
        } else {
            null
        }
    }

    object Secure {
        private val clazz = android.provider.Settings.Secure::class.java
        @JvmStatic
        val getString = clazz.getDeclaredMethod("getString" , ContentResolver::class.java , String::class.java)
    }

    object TelephonyManager {
        private val clazz = android.telephony.TelephonyManager::class.java
        @JvmStatic
        val getDeviceId = clazz.getDeclaredMethod("getDeviceId")
        @JvmStatic
        val getDeviceIdWithInt = clazz.getDeclaredMethod("getDeviceId" , Int::class.javaPrimitiveType)
        @JvmStatic
        val getImei = clazz.getDeclaredMethod("getImei")
        @JvmStatic
        val getImeiWithInt = clazz.getDeclaredMethod("getImei" , Int::class.javaPrimitiveType)
        @JvmStatic
        val getSubscriberId = clazz.getDeclaredMethod("getSubscriberId")
    }


}