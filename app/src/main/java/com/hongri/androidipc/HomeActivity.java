package com.hongri.androidipc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import com.hongri.androidipc.bean.Person;
import com.hongri.androidipc.bean.User;
import com.hongri.androidipc.util.IPCUtil;
import com.hongri.androidipc.util.Logger;

/**
 * @author hongri
 */
public class HomeActivity extends AppCompatActivity {

    /**
     * 当前进程的名字
     */
    public static int appPid = 0;

    public static String processName;
    public static String TAG = HomeActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        /**
         * 获取应用的进程name
         */
        doAPPProcessName();

        /**
         * 序列化操作
         */
        //doSerializable();

        findViewById(R.id.btn).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                doSerializable();

                doParcelable();
                //Intent intent = new Intent(HomeActivity.this, SecondActivity.class);
                //startActivity(intent);
            }
        });

        findViewById(R.id.btnMessager).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, MessagerActivity.class);
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
    }

    private void doParcelable() {
        Person person = new Person();
        person.setWeight(140);
        person.setSchool("beida");

        Intent intent = new Intent(HomeActivity.this, SecondActivity.class);
        intent.putExtra("person", person);
        startActivity(intent);
    }

    private void doSerializable() {
        User user = new User();
        user.setAge(23);
        user.setName("hongri");
        //Intent intent = new Intent(HomeActivity.this, SecondActivity.class);
        //intent.putExtra("user", user);
        //startActivity(intent);

        try {
            /**
             * 序列化过程
             */
            File file = getFilesDir();
            File newFile = new File(file, "cache.txt");
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(newFile));
            out.writeObject(user);
            out.close();

            /**
             * 反序列化过程
             */
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(newFile));
            User newUser = (User)in.readObject();
            String name = newUser.getName();
            int age = newUser.getAge();
            in.close();
            Logger.d("反序列化：" + "name:" + name + " age:" + age);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void doAPPProcessName() {
        appPid = android.os.Process.myPid();

        processName = IPCUtil.getProcessName(this, appPid);

        Logger.d(TAG + "processName:" + processName);
    }

    @Override
    protected void onResume() {
        super.onResume();
        UserManager.UserId = 2;
        Logger.d(UserManager.UserId + "");
        UserManager.UserId = 5;
    }
}
