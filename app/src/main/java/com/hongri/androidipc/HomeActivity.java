package com.hongri.androidipc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;

import com.hongri.androidipc.contentprovider.ContentProviderActivity;
import com.hongri.androidipc.messenger.MessengerActivity;
import com.hongri.androidipc.socket.TCPClientActivity;

/**
 * @author hongri
 * IPC通信 主入口
 */
public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        findViewById(R.id.btnMessenger).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, MessengerActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btnProvider).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ContentProviderActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btnTCP).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, TCPClientActivity.class);
                startActivity(intent);
            }
        });
    }
}
