package com.hongri.androidipc.Util;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;

/**
 * @author zhongyao
 * @date 2018/6/1
 */

public class IPCUtil {
    /**
     * 获取进程的名字
     * @param context
     */
    public static String getProcessName(Context context, int appPid) {
        ActivityManager manager = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningAppProcessInfo> infos = manager.getRunningAppProcesses();
        for (RunningAppProcessInfo info : infos) {
            if (info.pid == appPid) {
                return info.processName;
            }
        }
        return "";
    }
}
