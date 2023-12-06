package com.loper7.miit_rule_checker;

import android.nfc.Tag;
import android.text.TextUtils;
import android.util.Log;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import top.canyie.pine.Pine;

/**
 * @Author loper7
 * @Date 2023/12/6 9:54
 * @Description 日志打印帮助
 **/
public class LogHelper {

    private static final String TAG = "MIIT_RULE_CHECKER";

    public static void e(String message) {
        Log.e(TAG, message);
    }

    public static void printHookedMethod(String name) {
        Log.w(TAG, "----------------------" + TAG + "----------------------");
        Log.w(TAG, "*****Method call detected*****");
        Log.w(TAG, "    " + name);
        Log.w(TAG, "******************************");
        Log.w(TAG, "Stack information:");
        Log.w(TAG, getMethodStack());
        Log.w(TAG, "----------------------" + TAG + "----------------------");
    }

    public static void printMethodCount(HashMap<String, HashMap<String, Integer>> map) {
        Log.w(TAG, "----------------------" + TAG + "----------------------");
        if (map == null || map.size() == 0) {
            Log.i(TAG, "设定时间内指定方法没有被调用");
        } else {
            for (Map.Entry<String, HashMap<String, Integer>> entry : map.entrySet()) {
                Log.d(TAG, ">>>>>>>>>>> " + entry.getKey());
                for (Map.Entry<String, Integer> entry1 : entry.getValue().entrySet()) {
                    Log.i(TAG, "stackClassName: " + entry1.getKey() + "\tcount:" + entry1.getValue());
                }
                Log.d(TAG, "");
            }
        }
        Log.w(TAG, "----------------------" + TAG + "----------------------");
    }


    public static String getMethodName(Pine.CallFrame callFrame) {
        if (callFrame == null) return "";
        Constructor<?>[] constructors = callFrame.method.getDeclaringClass().getDeclaredConstructors();
        String name = "";
        if (constructors.length > 0) {
            try {
                name = String.format("%s.%s", constructors[0].toString().split(" ")[1].split("\\(")[0], callFrame.method.getName());
            } catch (Exception e) {
                name = String.format("%s.%s", constructors[0], callFrame.method.getName());
            }
        }
        return name;
    }

    public static String getMethodStack() {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        StringBuilder builder = new StringBuilder();
        for (StackTraceElement element : stackTraceElements) {
            String className = element.getClassName();
            String currPackage = Objects.requireNonNull(LogHelper.class.getPackage()).getName();
            boolean isChecker = TextUtils.isEmpty(className) ||
                    className.startsWith("top.canyie.pine") ||
                    className.startsWith("dalvik.system.VMStack") ||
                    className.startsWith("java.") ||
                    className.startsWith(currPackage);
            if (isChecker) continue;
            builder.append(element.toString());
            builder.append("\n");
        }
        return builder.toString();
    }

    public static String getStackClassName() {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        for (StackTraceElement element : stackTraceElements) {
            String className = element.getClassName();
            String currPackage = Objects.requireNonNull(LogHelper.class.getPackage()).getName();
            boolean isChecker = TextUtils.isEmpty(className) ||
                    className.startsWith("top.canyie.pine") ||
                    className.startsWith("dalvik.system.VMStack") ||
                    className.startsWith("java.") ||
                    className.startsWith(currPackage);
            if (isChecker) continue;
            String[] splits = className.split("\\$");
            return splits[0];
        }
        return "";
    }

}
