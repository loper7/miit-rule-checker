package com.loper7.miit_rule_checker

import android.text.TextUtils
import android.util.Log
import top.canyie.pine.Pine

/**
 *@Author loper7
 *@Date 2023/11/28 17:54
 *@Description
 **/
object LogHelper {
    private const val TAG = "MIIT_RULE_CHECKER"


    fun e(message : String?) = Log.e(TAG , message ?: "")


    fun printHookedMethod(callFrame : Pine.CallFrame) {
        Log.w(TAG , "----------------------${TAG}----------------------")
        Log.w(TAG , "*****Method call detected*****")
        Log.w(TAG , "    ${callFrame.method.name}")
        Log.w(TAG , "******************************")
        Log.w(TAG , "Stack information:")
        Log.w(TAG , getMethodStack())
        Log.w(TAG , "----------------------${TAG}----------------------")
    }

    private fun getMethodStack() : String {
        val stackTraceElements = Thread.currentThread().stackTrace
        val builder = StringBuilder()
        for (element in stackTraceElements) {
            val line = element.toString()
            val className = element.className
            val currPackage = this.javaClass.`package`?.name ?: "com.loper7.miit_rule_checker"
            val isChecker = TextUtils.isEmpty(className) ||
                    className.startsWith("top.canyie.pine") ||
                    className.startsWith("dalvik.system.VMStack") ||
                    className.startsWith("java.") ||
                    className.startsWith(currPackage)
            if (isChecker) continue
            builder.append(line)
            builder.append("\n")
        }
        return builder.toString()
    }

}