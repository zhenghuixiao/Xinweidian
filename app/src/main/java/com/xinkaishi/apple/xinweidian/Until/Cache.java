package com.xinkaishi.apple.xinweidian.Until;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

/**
 * 项目名称：Xinweidian
 * 类描述：
 * 创建人：apple
 * 创建时间：15/5/21 下午5:39
 * 修改人：apple
 * 修改时间：15/5/21 下午5:39
 * 修改备注：
 */
public class Cache {
    int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

    int cacheSize = maxMemory / 8;

    LruCache<String,Bitmap> mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
        @Override
        protected int sizeOf(String key, Bitmap bitmap) {
            return bitmap.getByteCount() / 1024;
        }
    };

    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            mMemoryCache.put(key, bitmap);
        }
    }

    //当要显示图片时，要调用以下方法判断是否存在缓存中，如果不存在再去下载。
    public Bitmap getBitmapFromMemCache(String key) {
        return mMemoryCache.get(key);
    }
}
