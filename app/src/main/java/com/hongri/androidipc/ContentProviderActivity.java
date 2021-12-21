package com.hongri.androidipc;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.hongri.androidipc.bean.Book;
import com.hongri.androidipc.bean.User;
import com.hongri.androidipc.contentprovider.MyContentProvider;
import com.hongri.androidipc.util.Logger;

/**
 * @author hongri
 */
public class ContentProviderActivity extends AppCompatActivity implements View.OnClickListener {

    Uri bookUri = MyContentProvider.BOOK_CONTENT_URI;
    Uri userUri = MyContentProvider.USER_CONTENT_URI;
    private Button btnInsert, btnQuery, btnQueryByUser, btnModify, btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_provider);

        btnInsert = (Button) findViewById(R.id.btnInsert);
        btnQuery = (Button) findViewById(R.id.btnQuery);
        btnQueryByUser = (Button) findViewById(R.id.btnQueryByUser);
        btnModify = (Button) findViewById(R.id.btnModify);
        btnDelete = (Button) findViewById(R.id.btnDelete);

        btnInsert.setOnClickListener(this);
        btnQuery.setOnClickListener(this);
        btnQueryByUser.setOnClickListener(this);
        btnModify.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
    }

    private void doInsert() {
        ContentValues values = new ContentValues();
        values.put("name", "Android框架");
        values.put("page", "1213");
        Uri uri = getContentResolver().insert(bookUri, values);
        Logger.d("插入成功 ---> uri:" + uri.toString());
    }

    private void doQuery() {
        Cursor bookCursor = getContentResolver().query(bookUri, new String[]{"_id", "name", "page"}, null, null, null);
        while (bookCursor.moveToNext()) {
            Book book = new Book();
            book.set_id(bookCursor.getInt(0));
            book.setName(bookCursor.getString(1));
            book.setPage(bookCursor.getInt(2));
            Logger.d("book:" + book.toString());
        }
        bookCursor.close();
    }

    private void doQueryUser() {
        Cursor userCursor = getContentResolver().query(userUri, new String[]{"_id", "name", "sex"}, null, null, null);
        while (userCursor.moveToNext()) {
            User user = new User();
            user.set_id(userCursor.getInt(0));
            user.setName(userCursor.getString(1));
            user.setSex(userCursor.getInt(2));
            Logger.d("user:" + user.toString());
        }
        userCursor.close();
    }

    private void doUpdate() {
        ContentValues updateValues = new ContentValues();
        updateValues.put("name", "Android底层");
        updateValues.put("page", 3345);
        int row = getContentResolver().update(bookUri, updateValues, "name=?", new String[]{"Android框架"});
        if (row > 0) {
            Logger.d("book:已修改");
        }
    }

    private void doDelete() {
        int count = getContentResolver().delete(bookUri, "name=?", new String[]{"Java"});
        if (count > 0) {
            Logger.d("book:已进行删除操作");
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btnInsert:
                /**
                 * 增
                 */
                doInsert();
                break;

            case R.id.btnQuery:
                /**
                 * 查
                 */
                doQuery();
                break;

            case R.id.btnQueryByUser:
                /**
                 * 查
                 */
                doQueryUser();
                break;

            case R.id.btnModify:
                /**
                 * 改
                 */
                doUpdate();
                doQuery();

                break;

            case R.id.btnDelete:
                /**
                 * 删
                 */
                doDelete();
                doQuery();
                break;

            default:
                break;
        }
    }
}
