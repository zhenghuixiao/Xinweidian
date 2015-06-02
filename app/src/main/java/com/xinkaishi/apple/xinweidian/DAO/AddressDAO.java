package com.xinkaishi.apple.xinweidian.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 项目名称：Xinweidian
 * 类描述：
 * 创建人：apple
 * 创建时间：15/5/26 上午11:24
 * 修改人：apple
 * 修改时间：15/5/26 上午11:24
 * 修改备注：
 */
public class AddressDAO {
    private WeidianOpenHelper helper;
    private SQLiteDatabase db;
    public AddressDAO(Context context){
        helper = new WeidianOpenHelper(context);
        //因为getWritableDatabase内部调用了mContext.openOrCreateDatabase(mName, 0, mFactory);
        //所以要确保context已初始化,我们可以把实例化DBManager的步骤放在Activity的onCreate里
        db = helper.getWritableDatabase();
    }

    /**
     * 查找默认收货地址
     *@return HashMap
     */
    public HashMap<String, Object> getDefaultAddress(){
        HashMap<String, Object> hm = new HashMap<String, Object>();
        //查找state状态为1的
        Cursor c = db.rawQuery("select * from address where state = ?", new String[]{"1"});
        while(c.moveToNext()){
            hm.put("name", c.getString(c.getColumnIndex("name")));
            hm.put("tel", c.getString(c.getColumnIndex("tel")));
            hm.put("address", c.getString(c.getColumnIndex("address")));
        }
        return hm;
    }

    /**
     * 查找全部收货地址
     *@return list
     */
    public ArrayList<HashMap<String, Object>> getAllAddress(){
        ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
        //查找state状态为1的
        Cursor c = db.rawQuery("select * from address", null);
        while(c.moveToNext()){
            HashMap<String, Object> hm = new HashMap<String, Object>();
            hm.put("id", c.getString(c.getColumnIndex("_id")));
            hm.put("name", c.getString(c.getColumnIndex("name")));
            hm.put("tel", c.getString(c.getColumnIndex("tel")));
            hm.put("address", c.getString(c.getColumnIndex("address")));
            hm.put("state", c.getInt(c.getColumnIndex("state")));
            list.add(hm);
        }
        Log.e("数据库操作", "返回收货地址列表成功");
        return list;
    }

    /**
     * 添加收货地址
     * @param hm
     */
    public void add(HashMap<String, Object> hm) {

        db.beginTransaction();  //开始事务
        try {
            db.execSQL("insert into address values(null, ?, ?, ?, ?)",
                    new Object[]{hm.get("name"), hm.get("tel"), hm.get("address"), hm.get("state")});
            db.setTransactionSuccessful();  //设置事务成功完成
            Log.e("数据库操作", "添加收货地址成功");
        } finally {
            db.endTransaction();    //结束事务
        }
    }

    /**
     * 修改默认收货地址，将传值state设置为1
     * @param hm
     */
    public void update(HashMap<String, Object> hm) {
        db.beginTransaction();  //开始事务
        try {
            db.execSQL("update address set state=?", new Object[]{0});
            db.execSQL("update address set state=? where name=? and tel=? and address=?",
                    new Object[]{1, hm.get("name"), hm.get("tel"), hm.get("address")});
            db.setTransactionSuccessful();  //设置事务成功完成
            Log.e("数据库操作", "修改默认收货地址成功");
        } finally {
            db.endTransaction();    //结束事务
        }
    }

    /**
     * 删除收货地址
     * @param id
     */
    public void deleteAdd(String id){
        db.delete("address", "_id=?", new String[]{id});
        Log.e("数据库操作", "删除收货地址成功");
    }

    /**
     * close database
     */
    public void closeDB() {
        db.close();
    }
}
