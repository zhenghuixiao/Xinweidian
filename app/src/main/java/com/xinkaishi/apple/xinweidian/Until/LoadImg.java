package com.xinkaishi.apple.xinweidian.Until;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 项目名称：Xinweidian
 * 类描述：
 * 创建人：apple
 * 创建时间：15/5/21 下午5:37
 * 修改人：apple
 * 修改时间：15/5/21 下午5:37
 * 修改备注：
 */
public class LoadImg {
    private static ExecutorService executorService = Executors.newFixedThreadPool(2);
    public static void onLoadImage(final String bitmapUrl,final Cache cache, final OnLoadImageListener onLoadImageListener){
        final Handler handler = new Handler(){
            public void handleMessage(Message msg){
                onLoadImageListener.OnLoadImage((Bitmap) msg.obj, null);
            }
        };

        executorService.submit(new Runnable(){
            Message msg = new Message();
            @Override
            public void run() {
                URL imageUrl ;
                if(cache.getBitmapFromMemCache(bitmapUrl) == null){
                    try {
                        imageUrl = new URL(bitmapUrl);
                        HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
                        InputStream inputStream = conn.getInputStream();
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        cache.addBitmapToMemoryCache(bitmapUrl, bitmap);
                        msg.obj = bitmap;
                        handler.sendMessage(msg);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else{
                    msg.obj = cache.getBitmapFromMemCache(bitmapUrl);
                    handler.sendMessage(msg);
                }
            }

        });

    }
    public interface OnLoadImageListener{
        public void OnLoadImage(Bitmap bitmap,String bitmapPath);
    }
}
