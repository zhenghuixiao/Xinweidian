package com.xinkaishi.apple.xinweidian.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.xinkaishi.apple.xinweidian.Bean.ImageBean;
import com.xinkaishi.apple.xinweidian.Until.ToBlob;

/**
 * 项目名称：Xinweidian
 * 类描述：
 * 创建人：apple
 * 创建时间：15/5/26 上午11:24
 * 修改人：apple
 * 修改时间：15/5/26 上午11:24
 * 修改备注：
 */
public class ImgDAO {
    private WeidianOpenHelper helper;
    private SQLiteDatabase db;
    public ImgDAO(Context context){
        helper = new WeidianOpenHelper(context);
        //因为getWritableDatabase内部调用了mContext.openOrCreateDatabase(mName, 0, mFactory);
        //所以要确保context已初始化,我们可以把实例化DBManager的步骤放在Activity的onCreate里
        db = helper.getWritableDatabase();
    }

    /**
     * 查找数据库是否为已保存图片
     * @param address
     */
    public boolean getImgAddress(String address){
        boolean bl = false;

        Cursor c = db.rawQuery("select * from image where address = ?", new String[]{String.valueOf(address)});
        while(c.moveToNext()){
            bl = true;
        }

        return bl;
    }

    /**
     * 添加图片路径
     * @param image
     */
    public void add(ImageBean image) {

        db.beginTransaction();  //开始事务
        try {
            db.execSQL("insert into image values(null, ?, ?)", new Object[]{ToBlob.toblob(image.bitmap), image.address});
            db.setTransactionSuccessful();  //设置事务成功完成
        } finally {
            db.endTransaction();    //结束事务
        }
    }

    /**
     * 取出图片
     * @param address
     */
    public Bitmap getBmp(String address){
        Bitmap bmpout = null;
        Cursor c = db.rawQuery("select img from image where address = ?", new String[]{String.valueOf(address)});
        while(c.moveToNext()){
            byte[] in = c.getBlob(c.getColumnIndex("img"));
            bmpout = BitmapFactory.decodeByteArray(in, 0, in.length);
        }

        return bmpout;
    }
    /**
     * close database
     */
    public void closeDB() {
        db.close();
    }
}
