package com.loper7.miit_rule_checker

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.pm.VersionedPackage
import android.location.LocationListener
import android.os.Build
import java.lang.reflect.Member

object MIITMethods {

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
        val getMacAddress = clazz.getDeclaredMethod("getMacAddress")
        val getIpAddress = clazz.getDeclaredMethod("getIpAddress")
    }

    object LocationManager {
        private val clazz = android.location.LocationManager::class.java
        val getLastKnownLocation = clazz.getDeclaredMethod("getLastKnownLocation" , String::class.java)
        val requestLocationUpdates = clazz.getDeclaredMethod("requestLocationUpdates" ,
            String::class.java ,
            Long::class.javaPrimitiveType ,
            Float::class.javaPrimitiveType ,
            LocationListener::class.java)
    }

    object NetworkInterface {
        private val clazz = java.net.NetworkInterface::class.java
        val getHardwareAddress = clazz.getDeclaredMethod("getHardwareAddress")
    }

    object ApplicationPackageManager {
        @SuppressLint("PrivateApi")
        private val clazz : Class<*>? = try {
            Class.forName("android.app.ApplicationPackageManager")
        } catch (e : Exception) {
            null
        }

        val getInstalledPackages = clazz?.getDeclaredMethod("getInstalledPackages" , Int::class.javaPrimitiveType)
        val getInstalledApplications =
            clazz?.getDeclaredMethod("getInstalledApplications" , Int::class.javaPrimitiveType)
        val getInstallerPackageName = clazz?.getDeclaredMethod("getInstallerPackageName" , String::class.java)
        val getPackageInfo =
            clazz?.getDeclaredMethod("getPackageInfo" , String::class.java , Int::class.javaPrimitiveType)
    }

    object PackageManager {
        private val clazz = android.content.pm.PackageManager::class.java
        val getInstalledPackages = clazz.getDeclaredMethod("getInstalledPackages" , Int::class.javaPrimitiveType)
        val getInstalledApplications =
            clazz.getDeclaredMethod("getInstalledApplications" , Int::class.javaPrimitiveType)
        val getInstallerPackageName = clazz.getDeclaredMethod("getInstallerPackageName" , String::class.java)
        val getPackageInfo =
            clazz.getDeclaredMethod("getPackageInfo" , String::class.java , Int::class.javaPrimitiveType)
        val getPackageInfo1 = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            clazz.getDeclaredMethod("getPackageInfo" , String::class.java , android.content.pm.PackageManager.PackageInfoFlags::class.java)
        } else {
            null
        }
       val getPackageInfo2 = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
           clazz.getDeclaredMethod("getPackageInfo" , VersionedPackage::class.java ,Int::class.javaPrimitiveType)
       } else {
           null
       }
        val getPackageInfo3 = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            clazz.getDeclaredMethod("getPackageInfo" , VersionedPackage::class.java, android.content.pm.PackageManager.PackageInfoFlags::class.java)
        } else {
            null
        }
    }

    object Secure {
        private val clazz = android.provider.Settings.Secure::class.java
        val getString = clazz.getDeclaredMethod("getString" , ContentResolver::class.java , String::class.java)
    }

    object TelephonyManager {
        private val clazz = android.telephony.TelephonyManager::class.java
        val getDeviceId = clazz.getDeclaredMethod("getDeviceId")
        val getDeviceIdWithInt = clazz.getDeclaredMethod("getDeviceId" , Int::class.javaPrimitiveType)
        val getImei = clazz.getDeclaredMethod("getImei")
        val getImeiWithInt = clazz.getDeclaredMethod("getImei" , Int::class.javaPrimitiveType)
        val getSubscriberId = clazz.getDeclaredMethod("getSubscriberId")
    }


}