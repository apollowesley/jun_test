package com.dtdream.rdic.saas.utils;

/**
 * Created by Ozz8 on 2015/07/01.
 */
public class ThreadUtils {
    /**
     * 1 表示当前栈
     * 2 表示被调函数
     * @param frame
     * @return
     */
    public static StackTraceElement frame (int frame) {
        StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
        int frm = frame + 1;
        if (stacks.length > frm && null != stacks[frm])
            return stacks[frm];
        else
            return null;
    }
    public static StringBuilder info (int frame) {
        int frm = frame + 1;
        StringBuilder sbret = new StringBuilder();
        StackTraceElement stack = frame(frm);
        if (null == stack)
            return sbret;
        sbret.append(stack.getFileName());
        sbret.append("/");
        sbret.append(stack.getMethodName());
        sbret.append("/");
        sbret.append(stack.getLineNumber());
        return sbret;
    }
}
