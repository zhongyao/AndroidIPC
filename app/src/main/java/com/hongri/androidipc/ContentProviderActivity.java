package com.hongri.androidipc;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.hongri.androidipc.bean.Book;
import com.hongri.androidipc.bean.User;
import com.hongri.androidipc.contentprovider.MyContentProvider;
import com.hongri.androidipc.util.Logger;

/**
 * @author hongri
 */
public class ContentProviderActivity extends AppCompatActivity {

    Uri bookUri = MyContentProvider.BOOK_CONTENT_URI;
    Uri userUri = MyContentProvider.USER_CONTENT_URI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_provider);

        //Uri bookUri = Uri.parse("content://com.hongri.androidipc.contentprovider.provider");
        //getContentResolver().query(bookUri, null, null, null, null);
        //getContentResolver().query(bookUri, null, null, null, null);
        //getContentResolver().query(bookUri, null, null, null, null);

        //Uri bookUri = Uri.parse(MyContentProvider.BOOK_CONTENT_URI)

        /**
         * 增
         */
        doInsert();

        /**
         * 查
         */
        doQuery();

        /**
         * 查
         */
        doQueryUser();

        /**
         * 改
         */
        doUpdate();
        doQuery();

        /**
         * 删
         */
        doDelete();
        doQuery();

    }

    private void doInsert() {
        ContentValues values = new ContentValues();
        values.put("name", "Android框架");
        values.put("page", "1213");
        getContentResolver().insert(bookUri, values);
    }

    private void doQuery() {
        Cursor bookCursor = getContentResolver().query(bookUri, new String[] {"_id", "name", "page"}, null, null, null);
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
        Cursor userCursor = getContentResolver().query(userUri, new String[] {"_id", "name", "sex"}, null, null, null);
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
        int row = getContentResolver().update(bookUri, updateValues, "name=?", new String[] {"Android框架"});
        if (row > 0) {
            Logger.d("book:已修改");
        }
    }

    private void doDelete() {
        int count = getContentResolver().delete(bookUri, "name=?", new String[] {"Java"});
        if (count > 0) {
            Logger.d("book:已进行删除操作");
        }
    }
}
