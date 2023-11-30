package com.loper7.miit_rule_checker

import android.provider.Settings
import android.text.TextUtils
import top.canyie.pine.Pine
import top.canyie.pine.callback.MethodHook
import java.lang.reflect.Member

/**
 *@Author loper7
 *@Date 2023/11/28 17:47
 *@Description 合规检查
 **/
object MIITRuleChecker {
    /**
     * 检查单一方法
     */
    @JvmStatic
    fun check(member : Member?) {
        if (member == null) return
        try {
            Pine.hook(member , callback)
        } catch (e : Throwable) {
            e.printStackTrace()
        }
    }

    /**
     * 检查多个方法
     */
    @JvmStatic
    fun check(vararg member : Member?) {
        if (member.isEmpty()) return
        for (data in member) {
            check(data)
        }
    }

    /**
     * 检查多个方法
     */
    fun check(members : MutableList<Member?>?) {
        if (members.isNullOrEmpty()) return
        for (data in members) {
            check(data)
        }
    }

    /**
     * 检查内置的一些方法调用
     */
    @JvmStatic
    fun checkDefaults() {
        check(MIITMethods.getDefaultMethods())
    }


    private val callback = object : MethodHook() {
        @Throws(Throwable::class)
        override fun beforeCall(callFrame : Pine.CallFrame) {
            super.beforeCall(callFrame)
            try {
                //单独判断获取androidId的情况
                if (callFrame.method.name == "getString" && Settings.Secure.ANDROID_ID != (callFrame.args[1] as String)) return
                if (callFrame.method.name == "getString" && Settings.Secure.ANDROID_ID == (callFrame.args[1] as String)) {
                    LogHelper.printHookedMethod("${callFrame.method.name} - ${Settings.Secure.ANDROID_ID}")
                } else {
                    LogHelper.printHookedMethod(callFrame.method.name)
                }
            } catch (_ : Throwable) {
                LogHelper.printHookedMethod(callFrame.method.name)
            }
        }
    }

}