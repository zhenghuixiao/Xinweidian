package com.xinkaishi.apple.xinweidian.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.xinkaishi.apple.xinweidian.Bean.Interface;
import com.xinkaishi.apple.xinweidian.R;
import com.xinkaishi.apple.xinweidian.Until.DataAnalysis;


public class LoadingActivity extends Activity {
    private WebView wv_test;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        TextView tv_loading = (TextView)findViewById(R.id.tv_loading);
        WebView wv_test = (WebView)findViewById(R.id.wv_test);

        String url = "http://test.pc.xinkaishi.com/wx/index/preview-shop?id=1";
        wv_test.loadUrl(url);
        tv_loading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Log.e("返回", DataAnalysis.readParse(Interface.LOGIN).toString());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Intent intent = new Intent(LoadingActivity.this, MainActivity.class);
//                        Intent intent = new Intent(LoadingActivity.this, TestWebActivity.class);
                        startActivity(intent);
                    }
                }).start();

            }
        });
    }

}
