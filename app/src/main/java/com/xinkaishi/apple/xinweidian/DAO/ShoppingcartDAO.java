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
public class ShoppingcartDAO {
    private WeidianOpenHelper helper;
    private SQLiteDatabase db;
    public ShoppingcartDAO(Context context){
        helper = new WeidianOpenHelper(context);
        //因为getWritableDatabase内部调用了mContext.openOrCreateDatabase(mName, 0, mFactory);
        //所以要确保context已初始化,我们可以把实例化DBManager的步骤放在Activity的onCreate里
        db = helper.getWritableDatabase();
    }

    /**
     * 查找商品是否在购物车
     * @param id
     *@return boolean
     */
    public boolean isInShop(int id){
        boolean bl = false;
        Cursor c = db.rawQuery("select * from shopcar where id=?", new String[]{id + ""});
        while(c.moveToNext()){
            bl = true;
        }
        return bl;
    }

    /**
     * 查找购物车信息
     *@return list
     */
    public ArrayList<HashMap<String, Object>> getShoppingcart(){
        ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
        Cursor c = db.rawQuery("select * from shopcar", null);
        while(c.moveToNext()){
            HashMap<String, Object> hm = new HashMap<String, Object>();
            hm.put("_id", c.getInt(c.getColumnIndex("_id")));
            hm.put("id", c.getInt(c.getColumnIndex("id")));
            hm.put("name", c.getString(c.getColumnIndex("name")));
            hm.put("format", c.getString(c.getColumnIndex("format")));
            hm.put("price_in", c.getFloat(c.getColumnIndex("price_in")));
            hm.put("num", c.getInt(c.getColumnIndex("num")));
            hm.put("state", c.getInt(c.getColumnIndex("state")));
            hm.put("img", c.getString(c.getColumnIndex("img")));
            list.add(hm);
        }
        return list;
    }


    /**
     * 加入购物车
     * @param hm
     */
    public void add(HashMap<String, Object> hm) {

        db.beginTransaction();  //开始事务
        try {
            db.execSQL("insert into shopcar values(null, ?, ?, ?, ?, ?, ?, ?)",
                    new Object[]{hm.get("id"), hm.get("name"), hm.get("img"), hm.get("format"), hm.get("price_in"),
                            hm.get("num"), hm.get("state")});
            db.setTransactionSuccessful();  //设置事务成功完成
            Log.e("数据库操作", "加入购物车成功");
        } finally {
            db.endTransaction();    //结束事务
        }
    }


    /**
     * 修改购物车商品信息
     * @param hm
     */
    public void update(HashMap<String, Object> hm) {
        db.beginTransaction();  //开始事务
        try {
            db.execSQL("update shopcar set num=?,state=? where id=?",
                    new Object[]{hm.get("num"), hm.get("state"), hm.get("id")});
            db.setTransactionSuccessful();  //设置事务成功完成
            Log.e("数据库操作", "修改购物车商品成功");
        } finally {
            db.endTransaction();    //结束事务
        }
    }

    /**
     * 删除购物车商品
     * @param list
     */
    public void delete(ArrayList<HashMap<String, Object>> list){
        for(int i = 0; i < list.size(); i ++){
            HashMap<String, Object> hm = list.get(i);
            if((Integer)hm.get("state") == 1){
                db.delete("shopcar", "id=?", new String[]{hm.get("id").toString()});
            }
        }
        Log.e("数据库操作", "删除购物车商品成功");
    }

    /**
     * close database
     */
    public void closeDB() {
        db.close();
    }
}
