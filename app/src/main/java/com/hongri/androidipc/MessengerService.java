package com.hongri.androidipc;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import com.hongri.androidipc.util.Logger;
import com.hongri.androidipc.util.MyConstant;

/**
 * @author hongri
 */
public class MessengerService extends Service {

    private Messenger messenger;

    private static class MessengerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MyConstant.MESSAGE_FROM_CLIENT:
                    try {
                        Bundle bundle = msg.getData();
                        Logger.d("server got a message:" + bundle.getString("msg"));
                        Messenger client = msg.replyTo;
                        Message replyMessage = Message.obtain(null, MyConstant.MESSAGE_FROM_CLIENT);

                        Bundle bundleReply = new Bundle();
                        bundleReply.putString("reply", "had received your message!!!");

                        replyMessage.setData(bundleReply);

                        client.send(replyMessage);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return messenger.getBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        messenger = new Messenger(new MessengerHandler());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }
}
