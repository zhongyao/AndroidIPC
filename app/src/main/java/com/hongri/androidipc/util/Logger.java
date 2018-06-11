package com.hongri.androidipc.util;

import android.util.Log;

/**
 * Created by zhongyao on 2018/6/1.
 */

public class Logger {
    public static final String TAG = "hongri";

    public static void d(String message) {
        Log.d(TAG, message);
    }

    public static void e(String message){
        Log.e(TAG,message);
    }
}
