package com.hongri.androidipc;

import android.app.Application;
import com.hongri.androidipc.util.Logger;

/**
 * @author zhongyao
 * @date 2018/8/1
 *
 * 应用启动的流程：
 * 入口ActivityThread.main()，创建UI线程的消息循环， 并最终进入消息循环.
 * 调用ActivityThread.attach()方法
 *
 */

public class IPCApplication extends Application {
    private static final String TAG = IPCApplication.class.getSimpleName() + " ";

    @Override
    public void onCreate() {
        super.onCreate();

        Logger.d(TAG + "IPCApplication onCreate");
    }
}
