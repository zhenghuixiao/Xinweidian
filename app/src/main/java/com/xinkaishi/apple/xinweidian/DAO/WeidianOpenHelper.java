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
        //创建图片缓存表  image
        db.execSQL("CREATE TABLE IF NOT EXISTS image" +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT, img BLOB, address TEXT)");
        Log.e("sqlite", "图片缓存表创建成功");

        //创建收货地址缓存表  address
        db.execSQL("CREATE TABLE IF NOT EXISTS address" +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, tel TEXT, address TEXT, state INTEGER)");
        Log.e("sqlite", "收货地址缓存表创建成功");

        //创建购物车缓存表  shopcar
        //主键  商品ID 名称  图片地址  规格  进价  数量  状态（暂时没用）
        db.execSQL("CREATE TABLE IF NOT EXISTS shopcar" +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT, id INTEGER, name TEXT, img TEXT, format TEXT, price_in REAL, num TEXT, state INTEGER)");
        Log.e("sqlite", "购物车缓存表创建成功");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
