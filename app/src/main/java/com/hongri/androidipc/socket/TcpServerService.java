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
 * @author hongri
 *
 * 1、server监听本地8688接口
 * 2、server给本地client发送消息"欢迎来到聊天室"
 */
public class TcpServerService extends Service {
    private static boolean isServiceDestoryed = false;
    private String[] mDefinedMessages = new String[] {"哈哈哈哈哈", "呵呵呵呵呵呵", "黑恶黑恶额呵呵呵"};

    public TcpServerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        new Thread(new TcpServer()).start();
    }

    public class TcpServer implements Runnable {

        @Override
        public void run() {
            try {
                //监听本地8688接口
                ServerSocket serverSocket = new ServerSocket(8688);
                while (!isServiceDestoryed) {
                    Socket client = serverSocket.accept();

                    responseClient(client);

                }

            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
    }

    private void responseClient(Socket client) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));

            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())),
                true);

            out.println("欢迎来到聊天室");

            while (!isServiceDestoryed) {
                String str = in.readLine();
                if (str == null) {
                    break;
                }
                Logger.d("来自客户端的消息：" + str);

                int i = new Random().nextInt(mDefinedMessages.length);
                String msg = mDefinedMessages[i];
                out.println(msg);

                out.close();
                in.close();
                client.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
