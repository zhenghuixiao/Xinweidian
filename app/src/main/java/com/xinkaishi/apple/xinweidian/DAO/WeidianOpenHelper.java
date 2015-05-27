package com.xinkaishi.apple.xinweidian.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * 项目名称：Xinweidian
 * 类描述：
 * 创建人：apple
 * 创建时间：15/5/26 上午9:31
 * 修改人：apple
 * 修改时间：15/5/26 上午9:31
 * 修改备注：
 */
public class WeidianOpenHelper extends SQLiteOpenHelper {
    private static int version = 1;//数据库版本
    private static String DB_NAME = "weidian.sqlite";//数据库名称

    public WeidianOpenHelper(Context context) {
        super(context, DB_NAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建picture表
        db.execSQL("CREATE TABLE IF NOT EXISTS image" +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT, img BLOB, address TEXT)");
        Log.e("sqlite", "创建成功");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
