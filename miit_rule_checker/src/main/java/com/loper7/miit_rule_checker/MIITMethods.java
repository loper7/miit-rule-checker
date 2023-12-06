package com.loper7.miit_rule_checker;

import android.content.ContentResolver;
import android.location.LocationListener;
import android.provider.Settings;

import java.lang.reflect.Member;
import java.util.ArrayList;

/**
 * @Author loper7
 * @Date 2023/12/6 10:17
 * @Description
 **/
public class MIITMethods {

    public static ArrayList<Member> getDefaultMethods() {
        ArrayList<Member> members = new ArrayList<>();
        members.add(WifiInfo.getMacAddress);
        members.add(WifiInfo.getIpAddress);
        members.add(LocationManager.getLastKnownLocation);
        members.add(LocationManager.requestLocationUpdates);
        members.add(NetworkInterface.getHardwareAddress);
        members.add(ApplicationPackageManager.getInstalledPackages);
        members.add(ApplicationPackageManager.getInstalledApplications);
        members.add(ApplicationPackageManager.getPackageInfo);
        members.add(ApplicationPackageManager.getInstallerPackageName);
        members.add(Secure.getString);
        members.add(TelephonyManager.getDeviceId);
        members.add(TelephonyManager.getDeviceIdWithInt);
        members.add(TelephonyManager.getImei);
        members.add(TelephonyManager.getImeiWithInt);
        members.add(TelephonyManager.getSubscriberId);
        return members;
    }

    public static ArrayList<Member> getPackageManagerAll() {
        ArrayList<Member> members = new ArrayList<>();
        members.add(ApplicationPackageManager.getInstalledPackages);
        members.add(ApplicationPackageManager.getInstalledApplications);
        members.add(ApplicationPackageManager.getPackageInfo);
        members.add(ApplicationPackageManager.getInstallerPackageName);
        return members;
    }

    public static ArrayList<Member> getStringAll() {
        ArrayList<Member> members = new ArrayList<>();
        members.add(Secure.getString);
        members.add(System.getString);
        return members;
    }

    public static class WifiInfo {
        public static final Class<?> clazz = android.net.wifi.WifiInfo.class;

        public static Member getMacAddress = null;

        static {
            try {
                getMacAddress = clazz.getDeclaredMethod("getMacAddress");
            } catch (NoSuchMethodException ignored) {

            }
        }

        public static Member getIpAddress;

        static {
            getIpAddress = null;
            try {
                getIpAddress = clazz.getDeclaredMethod("getIpAddress");
            } catch (NoSuchMethodException ignored) {
            }
        }

    }

    public static class LocationManager {
        public static final Class<?> clazz = android.location.LocationManager.class;

        public static Member getLastKnownLocation = null;
        public static Member requestLocationUpdates = null;

        static {
            try {
                getLastKnownLocation = clazz.getDeclaredMethod("getLastKnownLocation", String.class);
                requestLocationUpdates = clazz.getDeclaredMethod("requestLocationUpdates", String.class,
                        long.class,
                        float.class,
                        LocationListener.class);
            } catch (NoSuchMethodException e) {

            }
        }
    }

    public static class NetworkInterface {
        public static final Class<?> clazz = java.net.NetworkInterface.class;

        public static Member getHardwareAddress = null;

        static {
            try {
                getHardwareAddress = clazz.getDeclaredMethod("getHardwareAddress");
            } catch (NoSuchMethodException e) {
            }
        }
    }

    public static class ApplicationPackageManager {
        public static Class<?> clazz = null;
        public static Member getInstallerPackageName = null;
        public static Member getInstalledApplications = null;
        public static Member getInstalledPackages = null;
        public static Member getPackageInfo = null;

        static {
            try {
                clazz = Class.forName("android.app.ApplicationPackageManager");
                getInstalledPackages = clazz.getDeclaredMethod("getInstalledPackages", int.class);
                getInstalledApplications = clazz.getDeclaredMethod("getInstalledApplications", int.class);
                getInstallerPackageName = clazz.getDeclaredMethod("getInstallerPackageName", String.class);
                getPackageInfo = clazz.getDeclaredMethod("getPackageInfo", String.class, int.class);
            } catch (Exception e) {
            }
        }
    }

    public static class Secure {
        public static final Class<?> clazz = android.provider.Settings.Secure.class;

        public static Member getString = null;

        static {
            try {
                getString = clazz.getDeclaredMethod("getString", ContentResolver.class, String.class);
            } catch (NoSuchMethodException e) {
            }
        }
    }

    public static class System {
        public static final Class<?> clazz = Settings.System.class;

        public static Member getString = null;

        static {
            try {
                getString = clazz.getDeclaredMethod("getString", ContentResolver.class, String.class);
            } catch (NoSuchMethodException e) {
            }
        }
    }

    public static class TelephonyManager {
        public static final Class<?> clazz = android.telephony.TelephonyManager.class;

        public static Member getDeviceId = null;
        public static Member getDeviceIdWithInt = null;
        public static Member getImei = null;
        public static Member getImeiWithInt = null;
        public static Member getSubscriberId = null;

        static {
            try {
                getDeviceId = clazz.getDeclaredMethod("getDeviceId");
                getDeviceIdWithInt = clazz.getDeclaredMethod("getDeviceId", int.class);
                getImei = clazz.getDeclaredMethod("getImei");
                getImeiWithInt = clazz.getDeclaredMethod("getImei", int.class);
                getSubscriberId = clazz.getDeclaredMethod("getSubscriberId");
            } catch (NoSuchMethodException e) {
            }
        }
    }
}
