package com.xinkaishi.apple.xinweidian.Until;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;

import com.xinkaishi.apple.xinweidian.DAO.ImgDAO;

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
public class LoadSQLImg {
    private static ExecutorService executorService = Executors.newFixedThreadPool(2);
    public static void onLoadImage(final String address, final ImgDAO dao, final OnLoadImageListener onLoadImageListener){
        final Handler handler = new Handler(){
            public void handleMessage(Message msg){
                onLoadImageListener.OnLoadImage((Bitmap) msg.obj);
            }
        };

        executorService.submit(new Runnable(){
            Message msg = new Message();
            @Override
            public void run() {
                Bitmap bitmap = dao.getBmp(address);
                msg.obj = bitmap;
                handler.sendMessage(msg);
            }

        });

    }
    public interface OnLoadImageListener{
        public void OnLoadImage(Bitmap bitmap);
    }
}
