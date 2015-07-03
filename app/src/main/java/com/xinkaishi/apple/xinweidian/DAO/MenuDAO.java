package com.xinkaishi.apple.xinweidian.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * 项目名称：Xinweidian
 * 类描述：
 * 创建人：apple
 * 创建时间：15/6/26 上午9:36
 * 修改人：apple
 * 修改时间：15/6/26 上午9:36
 * 修改备注：
 */
public class MenuDAO {
    private WeidianOpenHelper helper;
    private SQLiteDatabase db;
    public MenuDAO(Context context){
        helper = new WeidianOpenHelper(context);
        //因为getWritableDatabase内部调用了mContext.openOrCreateDatabase(mName, 0, mFactory);
        //所以要确保context已初始化,我们可以把实例化DBManager的步骤放在Activity的onCreate里
        db = helper.getWritableDatabase();
    }

    /**
     * 查找menu JSON是否存在
     * @param shopid
     *@return boolean
     */
    public boolean inJSON(int shopid){
        boolean bl = false;
        Cursor c = db.rawQuery("select * from menujson where shopid=?", new String[]{shopid + ""});
        while(c.moveToNext()){
            bl = true;
        }
        return bl;
    }

    /**
     * 查找menu JSON
     *@return list
     */
    public String getMenujson(int shopid){
        String json = null;
        Cursor c = db.rawQuery("select * from menujson where shopid = ?", new String[]{String.valueOf(shopid)});
        while(c.moveToNext()){
            json = c.getString(c.getColumnIndex("json"));
        }
        return json;
    }

    /**
     * 添加menu JSON
     * @param json
     */
    public void add(String json, int shopid) {

        db.beginTransaction();  //开始事务
        try {
            db.execSQL("insert into menujson values(null, ?, ?)", new Object[]{json, shopid});
            db.setTransactionSuccessful();  //设置事务成功完成
        } finally {
            db.endTransaction();    //结束事务
        }
    }

    /**
     * 修改menu JSON
     * @param json
     */
    public void update(String json, int shopid) {
        db.beginTransaction();  //开始事务
        try {
            db.execSQL("update menujson set json=? where shopid=?",
                    new Object[]{json, shopid});
            db.setTransactionSuccessful();  //设置事务成功完成
            Log.e("数据库操作", "修改menu JSON成功");
        } finally {
            db.endTransaction();    //结束事务
        }
    }

    public void closeDB() {
        db.close();
    }
}
