package com.loper7.miit_rule_checker;

import android.os.Handler;
import android.os.Looper;

import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import top.canyie.pine.Pine;
import top.canyie.pine.callback.MethodHook;

/**
 * @Author loper7
 * @Date 2023/12/6 10:51
 * @Description
 **/
public class MIITMethodCountChecker {
    private static HashMap<String, HashMap<String, Integer>> map = new HashMap<>();
    private static Handler handler = new Handler(Looper.getMainLooper());


    /**
     * 开始计数
     *
     * @param members  需要计数的方法
     * @param deadline 截止时间
     */
    public static void startCount(Long deadline, ArrayList<Member> members) {
        map.clear();
        for (Member member : members) {
            if (member == null) continue;
            try {
                Pine.hook(member, new MethodHook() {
                    @Override
                    public void beforeCall(Pine.CallFrame callFrame) throws Throwable {
                        super.beforeCall(callFrame);
                        String methodName = LogHelper.getMethodName(callFrame);
                        try {
                            if (callFrame.args[1] instanceof String)
                                addCount(methodName + " - " + callFrame.args[1]);
                            else
                                addCount(methodName);
                        } catch (Throwable throwable) {
                            addCount(methodName);
                        }
                    }
                });
            } catch (Exception e) {

            }
        }
        handler.postDelayed(() -> LogHelper.printMethodCount(map), deadline);
    }

    /**
     * 开始计数
     *
     * @param members  需要计数的方法
     * @param deadline 截止时间
     */
    public static void startCount(Long deadline, Member... members) {
        ArrayList list = new ArrayList();
        Collections.addAll(list, members);
        startCount(deadline, list);
    }

    private static void addCount(String method) {
        String stackClassName = LogHelper.getStackClassName();
        int count = -1;
        try {
            count = map.get(method).get(stackClassName);
        } catch (Exception e) {

        }
        if (count != -1) {
            map.get(method).put(stackClassName, ++count);
        } else {
            HashMap<String, Integer> hasMap = map.get(method);
            if (hasMap == null) hasMap = new HashMap<>();
            hasMap.put(stackClassName, 1);
            map.put(method, hasMap);
        }
    }
}
