package com.xinkaishi.apple.xinweidian.Until;

import android.util.Log;

import com.xinkaishi.apple.xinweidian.Bean.TESTLIST;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * 项目名称：Xinweidian
 * 类描述：
 * 创建人：apple
 * 创建时间：15/6/16 上午11:37
 * 修改人：apple
 * 修改时间：15/6/16 上午11:37
 * 修改备注：
 */
public class Post {
    /**
     * 发送Post请求到服务器
     * params请求体内容
     * @return
     */
    public static String submitPostData(Map<String, String> params, String urlPath) {
        URL url;
        byte[] data = getRequestData(params).toString().getBytes();
        try {
            url = new URL(urlPath);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
//            httpURLConnection.setRequestProperty();

            httpURLConnection.setConnectTimeout(3000);        //设置连接超时时间
            httpURLConnection.setDoInput(true);                  //打开输入流，以便从服务器获取数据
            httpURLConnection.setDoOutput(true);                 //打开输出流，以便向服务器提交数据
            httpURLConnection.setRequestMethod("POST");    //设置以Post方式提交数据
            httpURLConnection.setUseCaches(false);               //使用Post方式不能使用缓存
            //设置请求体的类型是文本类型
            httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            //设置请求体的长度
            httpURLConnection.setRequestProperty("Content-Length", String.valueOf(data.length));

            if(TESTLIST.sessionid != null) {
                httpURLConnection.setRequestProperty("cookie", TESTLIST.sessionid);
                Log.e("这里是set", TESTLIST.sessionid);
            }
//            // 取得sessionid.
//            String cookieval = httpURLConnection.getHeaderField("set-cookie");
//            Log.e("这里是get", cookieval);
//
//
//            if(cookieval != null) {
//                TESTLIST.sessionid = cookieval.substring(0, cookieval.indexOf(";"));
//            }
            //获得输出流，向服务器写入数据
            OutputStream outputStream = httpURLConnection.getOutputStream();
            outputStream.write(data);
            Log.e("写入", "完成");

            int response = httpURLConnection.getResponseCode();            //获得服务器的响应码
            if(response == HttpURLConnection.HTTP_OK) {
                //session的收发
                InputStream inptStream = httpURLConnection.getInputStream();
                Log.e("写出", "完成");
                return dealResponseResult(inptStream);//处理服务器的响应结果
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 封装请求体信息
     *
     */
    public static StringBuffer getRequestData(Map<String, String> params){
        StringBuffer stringBuffer = new StringBuffer();        //存储封装好的请求体信息
        try {
            for(Map.Entry<String, String> entry : params.entrySet()) {
                stringBuffer.append(entry.getKey())
                        .append("=")
                        .append(entry.getValue())
                        .append("&");
            }
            stringBuffer.deleteCharAt(stringBuffer.length() - 1);    //删除最后的一个"&"
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringBuffer;
    }

    /**
     * 处理服务器的响应结果（将输入流转化成字符串）
     *  inputStream服务器的响应输入流
     *
     */
    public static String dealResponseResult(InputStream inputStream) {
        String resultData = null;      //存储处理结果
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int len = 0;
        try {
            while((len = inputStream.read(data)) != -1) {
                byteArrayOutputStream.write(data, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        resultData = new String(byteArrayOutputStream.toByteArray());
        return resultData;
    }
}
