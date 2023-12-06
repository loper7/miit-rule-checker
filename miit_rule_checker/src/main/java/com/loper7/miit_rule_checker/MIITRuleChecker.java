package com.loper7.miit_rule_checker;

import android.provider.Settings;
import android.text.TextUtils;

import java.lang.reflect.Member;
import java.util.ArrayList;

import top.canyie.pine.Pine;
import top.canyie.pine.callback.MethodHook;

/**
 * @Author loper7
 * @Date 2023/12/6 10:39
 * @Description
 **/
public class MIITRuleChecker {

    /**
     * 检查单一方法
     */
    public static void check(Member member) {
        if (member == null) return;
        try {
            Pine.hook(member, callback);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }


    /**
     * 检查多个方法
     */
    public static void check(Member... members) {
        if (members == null) return;
        for (Member data : members) {
            check(data);
        }
    }

    /**
     * 检查多个方法
     */
    public static void check(ArrayList<Member> members) {
        if (members == null || members.size() == 0) return;
        for (Member data : members) {
            check(data);
        }
    }

    /**
     * 检查内置的一些方法调用
     */
    public static void checkDefaults() {
        check(MIITMethods.getDefaultMethods());
    }

    private static final MethodHook callback = new MethodHook() {
        @Override
        public void beforeCall(Pine.CallFrame callFrame) throws Throwable {
            super.beforeCall(callFrame);
            String name = LogHelper.getMethodName(callFrame);
            try {
                //单独判断获取androidId的情况
                if (TextUtils.equals(callFrame.method.getName(), "getString") && Settings.Secure.ANDROID_ID != (callFrame.args[1].toString())) return;
                if (TextUtils.equals(callFrame.method.getName(), "getString") && Settings.Secure.ANDROID_ID == (callFrame.args[1].toString())) {
                    LogHelper.printHookedMethod(name + " - " + Settings.Secure.ANDROID_ID);
                } else {
                    LogHelper.printHookedMethod(name);
                }
            } catch (Throwable throwable) {
                LogHelper.printHookedMethod(name);
            }
        }
    };

}
