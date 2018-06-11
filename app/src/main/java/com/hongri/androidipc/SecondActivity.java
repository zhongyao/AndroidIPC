package com.hongri.androidipc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import com.hongri.androidipc.util.IPCUtil;
import com.hongri.androidipc.util.Logger;
import com.hongri.androidipc.bean.Person;
import com.hongri.androidipc.bean.User;

/**
 * @author hongri
 */
public class SecondActivity extends AppCompatActivity {
    /**
     * 当前进程的名字
     */
    public static int appPid = 0;

    public static String processName;
    public static String TAG = SecondActivity.class.getSimpleName() + "---";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        /**
         * 获取应用的进程name
         */
        doAPPProcessName();

        /**
         * 获取上界面传递过来的序列化对象
         */
        getSerializable();

        getParcelable();

        findViewById(R.id.btn).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SecondActivity.this, ThirdActivity.class);
                startActivity(intent);
            }
        });
    }

    private void getParcelable() {
        Person person = getIntent().getParcelableExtra("person");
        if (person == null) {
            return;
        }
        int weight = person.getWeight();
        String school = person.getSchool();
        Logger.d("weight:" + weight + " school:" + school);

    }

    private void getSerializable() {
        User user = (User)getIntent().getSerializableExtra("user");
        if (user == null) {
            return;
        }
        int age = user.getAge();
        String name = user.getName();

        Logger.d("age:" + age + " name:" + name);
    }

    /**
     * 获取应用的进程name
     */
    private void doAPPProcessName() {
        appPid = android.os.Process.myPid();

        processName = IPCUtil.getProcessName(this, appPid);

        Logger.d(TAG + "processName:" + processName);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Logger.d(UserManager.UserId + "");
    }
}
