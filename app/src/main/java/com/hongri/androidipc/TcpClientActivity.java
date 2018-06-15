package com.hongri.androidipc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.hongri.androidipc.socket.TcpServerService;
import com.hongri.androidipc.util.Logger;

/**
 * @author hongri
 */
public class TcpClientActivity extends AppCompatActivity implements OnClickListener {
    private EditText et;
    private TextView tv;
    private Button btnSend;
    private PrintWriter printWriter;
    private static final int MESSAGE_SOCKET_CONNECTED = 1;
    private static final int RECEIVED_NEW_MESSAGE = 2;

    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MESSAGE_SOCKET_CONNECTED:
                    Logger.d("MESSAGE_SOCKET_CONNECTED");
                    break;
                case RECEIVED_NEW_MESSAGE:
                    Logger.d("RECEIVED_NEW_MESSAGE");
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tpcclient);

        et = (EditText)findViewById(R.id.et);
        tv = (TextView)findViewById(R.id.tv);
        btnSend = (Button)findViewById(R.id.btnSend);

        btnSend.setOnClickListener(this);

        Intent intent = new Intent(this, TcpServerService.class);
        startService(intent);

        new Thread(new Runnable() {
            @Override
            public void run() {
                startConnectTCPServer();
            }
        }).start();
    }

    private void startConnectTCPServer() {

        Socket socket = null;
        while (socket == null) {
            try {
                socket = new Socket("30.77.72.81", 8688);
                printWriter = new PrintWriter(
                    new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);

                mHandler.sendEmptyMessage(MESSAGE_SOCKET_CONNECTED);

            } catch (IOException e) {
                SystemClock.sleep(1000);
                Logger.d("connect retry");
                e.printStackTrace();
            }
        }

        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while (!isFinishing()) {
                String msg = bufferedReader.readLine();
                if (msg != null) {
                    Logger.d("client received:" + msg);
                    String time = formatDataTime(System.currentTimeMillis());
                    mHandler.obtainMessage(RECEIVED_NEW_MESSAGE,
                        "client received new message time:" + time + " msg:" + msg)
                        .sendToTarget();
                }
            }

            socket.close();
            printWriter.close();
            bufferedReader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String formatDataTime(long time) {
        return new SimpleDateFormat("HH:mm:ss").format(new Date(time));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSend:
                String etMsg = et.getText().toString();
                if (!TextUtils.isEmpty(etMsg)) {
                    printWriter.print(etMsg);
                    et.setText("");
                    String time = formatDataTime(System.currentTimeMillis());
                    tv.setText(tv.getText() + " self " + time + ":" + etMsg + "\n");
                }
                break;
            default:
                break;
        }
    }
}
