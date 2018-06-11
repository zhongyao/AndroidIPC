package com.hongri.androidipc.contentprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.hongri.androidipc.db.DbOpenHelper;
import com.hongri.androidipc.util.Logger;

/**
 * @author zhongyao
 * @date 2018/6/11
 *
 * onCreate方法运行在主线程(main)中
 *
 * 其他方法运行在Binder线程池中
 */

public class MyContentProvider extends ContentProvider {
    private static final String TAG = MyContentProvider.class.getSimpleName() + " ";
    /**
     * 唯一标识
     */
    public static final String AUTHORITIES = "com.hongri.androidipc.contentprovider.provider";

    public static final Uri BOOK_CONTENT_URI = Uri.parse("content://" + AUTHORITIES + "/book");

    public static final Uri USER_CONTENT_URI = Uri.parse("content://" + AUTHORITIES + "/user");

    public static final int BOOK_URI_CODE = 0;

    public static final int USER_URI_CODE = 1;

    private DbOpenHelper mDbHelper;

    private SQLiteDatabase mDB;

    private Context mContext;

    private static final UriMatcher mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        mUriMatcher.addURI(AUTHORITIES, "book", BOOK_URI_CODE);
        mUriMatcher.addURI(AUTHORITIES, "user", USER_URI_CODE);
    }

    /**
     * ContentProvider的创建：做一些初始化的工作
     *
     * @return
     */
    @Override
    public boolean onCreate() {
        String currentThreadName = Thread.currentThread().getName();
        Logger.d(TAG + "onCreate--" + "currentThreadName:" + currentThreadName);

        mContext = getContext();

        mDbHelper = new DbOpenHelper(getContext(), "", null, 1);
        mDB = mDbHelper.getWritableDatabase();
        mDB.execSQL("delete from " + DbOpenHelper.BOOK_TABLE_NAME);
        mDB.execSQL("delete from " + DbOpenHelper.USER_TABLE_NAME);
        mDB.execSQL("insert into book values('1','Android',234);");
        mDB.execSQL("insert into book values('2','Java',348);");
        mDB.execSQL("insert into user values('1','hongri',1);");
        mDB.execSQL("insert into user values('2','huyin',1);");
        return true;
    }

    private String getTableName(Uri uri) {
        switch (mUriMatcher.match(uri)) {
            case BOOK_URI_CODE:
                return DbOpenHelper.BOOK_TABLE_NAME;
            case USER_URI_CODE:
                return DbOpenHelper.USER_TABLE_NAME;
            default:
                break;
        }
        return "";
    }

    /**
     * 用来返回一个MIME类型（媒体类型）
     *
     * @param uri
     * @return
     */
    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    /**
     * 增
     *
     * @param uri
     * @param values
     * @return
     */
    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        String tableName = getTableName(uri);
        mDB.insert(tableName, null, values);
        mContext.getContentResolver().notifyChange(uri, null);
        return uri;
    }

    /**
     * 删
     *
     * @param uri
     * @param selection
     * @param selectionArgs
     * @return
     */
    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        String tableName = getTableName(uri);
        int count = mDB.delete(tableName, selection, selectionArgs);
        if (count > 0) {
            mContext.getContentResolver().notifyChange(uri, null);
        }
        return count;
    }

    /**
     * 查
     *
     * @param uri
     * @param projection
     * @param selection
     * @param selectionArgs
     * @param sortOrder
     * @return
     */
    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        String currentThreadName = Thread.currentThread().getName();
        Logger.d(TAG + "query: currentThreadName:" + currentThreadName + " uri:" + uri);

        String tableName = getTableName(uri);

        return mDB.query(tableName, projection, selection, selectionArgs, null, null, sortOrder, null);
    }

    /**
     * 改
     *
     * @param uri
     * @param values
     * @param selection
     * @param selectionArgs
     * @return
     */
    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection,
                      @Nullable String[] selectionArgs) {
        String tableName = getTableName(uri);
        if (tableName == null) {
            return 0;
        }
        int row = mDB.update(tableName, values, selection, selectionArgs);
        if (row > 0) {
            mContext.getContentResolver().notifyChange(uri, null);
        }
        return row;
    }
}
