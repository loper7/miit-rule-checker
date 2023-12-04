package com.loper7.miit_rule_checker

import android.text.TextUtils
import android.util.Log
import top.canyie.pine.Pine
import java.lang.reflect.Member

/**
 *@Author loper7
 *@Date 2023/11/28 17:54
 *@Description
 **/
object LogHelper {
    private const val TAG = "MIIT_RULE_CHECKER"


    fun e(message : String?) = Log.e(TAG , message ?: "")


    fun printHookedMethod(name : String) {
        Log.w(TAG , "----------------------${TAG}----------------------")
        Log.w(TAG , "*****Method call detected*****")
        Log.w(TAG , "    ${name}")
        Log.w(TAG , "******************************")
        Log.w(TAG , "Stack information:")
        Log.w(TAG , getMethodStack())
        Log.w(TAG , "----------------------${TAG}----------------------")
    }

    fun printMethodCount(map : HashMap<String , HashMap<String , Int>>?) {

        Log.w(TAG , "----------------------${TAG}----------------------")
        if (map.isNullOrEmpty()) {
            Log.i(TAG , "设定时间内指定方法没有被调用")
        } else {
            map.forEach {
                Log.d(TAG , ">>>>>>>>>>> ${it.key}")
                it.value.forEach { value ->
                    Log.i(TAG , "stackClassName: ${value.key}\tcount: ${value.value}")
                }
                Log.d(TAG , "")
            }
        }
        Log.w(TAG , "----------------------${TAG}----------------------")
    }

    fun getMethodName(callFrame : Pine.CallFrame?) : String {
        if (callFrame == null) return ""
        val constructors = callFrame.method.declaringClass.declaredConstructors
        val name = if (constructors != null && constructors.isNotEmpty()) {
            try {
                "${constructors[0].toString().split(" ")[1].split("(")[0]}.${callFrame.method.name}"
            } catch (e : Exception) {
                "${constructors[0]}.${callFrame.method.name}"
            }
        } else {
            "${callFrame.method.declaringClass.`package`?.name}.${callFrame.method.name}"
        }
        return name
    }

    fun getMethodStack() : String {
        val stackTraceElements = Thread.currentThread().stackTrace
        val builder = StringBuilder()
        for (element in stackTraceElements) {
            val line = element.toString()
            val className = element.className
            val currPackage = this.javaClass.`package`?.name ?: "com.loper7.miit_rule_checker"
            val isChecker =
                TextUtils.isEmpty(className) || className.startsWith("top.canyie.pine") || className.startsWith("dalvik.system.VMStack") || className.startsWith(
                    "java.") || className.startsWith(currPackage)
            if (isChecker) continue
            builder.append(line)
            builder.append("\n")
        }
        return builder.toString()
    }

    fun getStackClassName() : String {
        val stackTraceElements = Thread.currentThread().stackTrace
        for (element in stackTraceElements) {
            val className = element.className
            val currPackage = this.javaClass.`package`?.name ?: "com.loper7.miit_rule_checker"
            val isChecker =
                TextUtils.isEmpty(className) || className.startsWith("top.canyie.pine") || className.startsWith("dalvik.system.VMStack") || className.startsWith(
                    "java.") || className.startsWith(currPackage)
            if (isChecker) continue
            val splits = className.split("\\$".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            return splits[0]
        }
        return ""
    }

}