package com.loper7.miit_rule_checker

import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.util.Log
import top.canyie.pine.Pine
import top.canyie.pine.callback.MethodHook
import java.lang.reflect.Member

/**
 *@Author loper7
 *@Date 2023/11/30 9:27
 *@Description
 **/
object MIITMethodCountChecker {

    private val map : HashMap<String , HashMap<String , Int>> = hashMapOf()
    private val handler by lazy { Handler(Looper.getMainLooper()) }

    /**
     * 开始计数
     * @param members 需要计数的方法
     * @param deadline 截止时间
     */
    @JvmStatic
    fun startCount(members : MutableList<Member?> , deadline : Long = 6000) {
        map.clear()
        for (member in members) {
            if (member == null) continue
            try {
                Pine.hook(member , object : MethodHook() {
                    override fun beforeCall(callFrame : Pine.CallFrame?) {
                        super.beforeCall(callFrame)
                        val methodName = LogHelper.getMethodName(callFrame)
                        try {
                            addCount("$methodName - ${(callFrame !!.args[1] as String)}")
                        } catch (_ : Throwable) {
                            addCount(methodName)
                        }
                    }
                })
            } catch (_ : Exception) {

            }
        }
        handler.postDelayed({
            LogHelper.printMethodCount(map)
        } , deadline)
    }

    /**
     * 开始计数
     * @param members 需要计数的方法
     * @param deadline 截止时间
     */
    fun startCount(vararg members : Member , deadline : Long = 6000) {
        startCount(members.toMutableList() , deadline)
    }

    private fun addCount(method : String) {
        val stackClassName = LogHelper.getStackClassName()
        var count = map[method]?.get(stackClassName) ?: - 1
        if (count != - 1) {
            map[method]?.put(stackClassName , ++ count)
        } else {
            var hasMap = map[method]
            if (hasMap == null) hasMap = hashMapOf()
            hasMap[stackClassName] = 1
            map[method] = hasMap
        }
    }
}