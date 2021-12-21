package com.hongri.androidipc;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import com.hongri.androidipc.util.Logger;
import com.hongri.androidipc.util.MyConstant;

/**
 * @author hongri
 */
public class MessengerActivity extends AppCompatActivity {

    private Messenger mService;

    private Messenger mGetReplyMessenger = new Messenger(new MessengerHandler());

    private static class MessengerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MyConstant.MESSAGE_FROM_CLIENT:
                    Logger.d("client got the reply:" + msg.getData().getString("reply"));
                    break;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messager);

        findViewById(R.id.btn).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                //intent.setComponent(new ComponentName("com.hongri.server","com.hongri.server.MessagerService"));
                //intent.setClassName(MessagerActivity.this, "com.hongri.androidipc.MessagerService");
                intent.setClass(MessengerActivity.this, MessengerService.class);
                bindService(intent, conn, Context.BIND_AUTO_CREATE);
            }
        });
    }

    ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            try {
                mService = new Messenger(service);

                Message message = Message.obtain(null, 1);
                Bundle bundle = new Bundle();
                bundle.putString("msg", "This is a message from client");
                message.setData(bundle);
                /**
                 * 当客户端发送消息的时候，需要把服务端回复的Messenger通过Message的replyTo方法参数传递给服务端
                 */
                message.replyTo = mGetReplyMessenger;

                mService.send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (conn != null) {
            unbindService(conn);
            conn = null;
        }
    }
}
