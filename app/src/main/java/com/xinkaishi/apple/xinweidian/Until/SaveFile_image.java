package com.xinkaishi.apple.xinweidian.Until;

import android.os.Environment;

import com.xinkaishi.apple.xinweidian.Bean.ImgSavefile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 项目名称：Xinweidian
 * 类描述：
 * 创建人：apple
 * 创建时间：15/5/26 下午2:04
 * 修改人：apple
 * 修改时间：15/5/26 下午2:04
 * 修改备注：
 */
public class SaveFile_image {
    /**
     * 保存文件
     * @param inSream
     * @param url  图片的物理地址
     */
    public void save(InputStream inSream, String url){
        String file = Environment.getExternalStorageDirectory().getAbsolutePath()
                + ImgSavefile.MY_FILE + url + ".png"; // 保存路径
        try{
            File saveFile = new File(file);
            if (!saveFile.exists()){
                File dir = new File(saveFile.getParent());
                dir.mkdirs();
                saveFile.createNewFile();
            }

            FileOutputStream outStream = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int len = -1;
            while( (len = inSream.read(buffer)) != -1) {
                outStream.write(buffer, 0, len);
            }
            outStream.close();
            inSream.close();
        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
