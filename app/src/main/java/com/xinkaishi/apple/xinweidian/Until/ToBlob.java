package com.xinkaishi.apple.xinweidian.Until;

import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;

/**
 * 项目名称：Xinweidian
 * 类描述：
 * 创建人：apple
 * 创建时间：15/5/26 下午4:14
 * 修改人：apple
 * 修改时间：15/5/26 下午4:14
 * 修改备注：
 */
public class ToBlob {
    public static byte[] toblob(Bitmap bitmap)
     {
         ByteArrayOutputStream baos = new ByteArrayOutputStream();
         bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
         return baos.toByteArray();
     }
}
