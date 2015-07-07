package com.xinkaishi.apple.xinweidian.Until;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.xinkaishi.apple.xinweidian.Bean.ImageBean;
import com.xinkaishi.apple.xinweidian.DAO.ImgDAO;

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
    private static ExecutorService executorService = Executors.newFixedThreadPool(3);
    public static void onLoadImage(final String bitmapUrl,final Cache cache, final ImgDAO imgdao, final OnLoadImageListener onLoadImageListener){
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
                if(cache.getBitmapFromMemCache(bitmapUrl) != null){
                    msg.obj = cache.getBitmapFromMemCache(bitmapUrl);
                    handler.sendMessage(msg);
                    Log.e("图片", "从缓存中取出图片");
                }else if(imgdao != null & imgdao.getImgAddress(bitmapUrl)){
                    Bitmap bitmap = imgdao.getBmp(bitmapUrl);
                    cache.addBitmapToMemoryCache(bitmapUrl, bitmap);
                    Log.e("图片", "从数据库中取出图片");
                    msg.obj = bitmap;
                    handler.sendMessage(msg);
                }else{
                    try {
                        ImageBean img = new ImageBean();
                        imageUrl = new URL(bitmapUrl);
                        HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
                        InputStream inputStream = conn.getInputStream();
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        cache.addBitmapToMemoryCache(bitmapUrl, bitmap);
                        Log.e("图片", "从网上下载图片");
                        img.bitmap = bitmap;
                        img.address = bitmapUrl;
                        imgdao.add(img);
                        msg.obj = bitmap;
                        handler.sendMessage(msg);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

        });

    }
    public interface OnLoadImageListener{
        public void OnLoadImage(Bitmap bitmap,String bitmapPath);
    }
}
