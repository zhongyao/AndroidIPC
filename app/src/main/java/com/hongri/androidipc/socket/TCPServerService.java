package com.hongri.androidipc.socket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.hongri.androidipc.util.Logger;

/**
 * socket跨进程通信
 * 2、服务端
 */
public class TCPServerService extends Service {

    private boolean mIsServiceDestroyed = false;
    private final String[] mDefinedMessages = new String[]{
            "你好啊，哈哈",
            "请问你叫什么名字呀？",
            "今天北京天气不错啊，shy",
            "你知道吗？我可是可以和多个人同时聊天的哦",
            "给你讲个笑话吧：据说爱笑的人运气不会太差，不知道真假。"
    };

    @Override
    public void onCreate() {
        new Thread(new TcpServer()).start();
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        mIsServiceDestroyed = true;
        super.onDestroy();
    }

    private class TcpServer implements Runnable {

        @SuppressWarnings("resource")
        @Override
        public void run() {
            ServerSocket serverSocket;
            try {
                serverSocket = new ServerSocket(8888);
            } catch (IOException e) {
                System.err.println("establish tcp server failed, port:8888");
                e.printStackTrace();
                return;
            }

            while (!mIsServiceDestroyed) {
                try {
                    // 接受客户端请求
                    final Socket client = serverSocket.accept();
                    Logger.d("accept");
                    new Thread() {
                        @Override
                        public void run() {
                            try {
                                responseClient(client);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void responseClient(Socket client) throws IOException {
        // 用于接收客户端消息
        BufferedReader in = new BufferedReader(new InputStreamReader(
                client.getInputStream()));
        // 用于向客户端发送消息
        PrintWriter out = new PrintWriter(new BufferedWriter(
                new OutputStreamWriter(client.getOutputStream())), true);
        out.println("欢迎来到聊天室！");
        while (!mIsServiceDestroyed) {
            String str = in.readLine();
            Logger.d("msg from client:" + str);
            if (str == null) {
                break;
            }
            int i = new Random().nextInt(mDefinedMessages.length);
            String msg = mDefinedMessages[i];
            out.println(msg);
            Logger.d("send :" + msg);
        }
        Logger.d("client quit.");
        // 关闭流
        MyUtils.close(out);
        MyUtils.close(in);
        client.close();
    }

}
