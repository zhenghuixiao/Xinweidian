package com.xinkaishi.apple.xinweidian.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.xinkaishi.apple.xinweidian.R;


public class LoadingActivity extends Activity {
    private WebView wv_test;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        TextView tv_loading = (TextView)findViewById(R.id.tv_loading);
        WebView wv_test = (WebView)findViewById(R.id.wv_test);
        wv_test.loadUrl("http://test.pc.xinkaishi.com/wx/index/preview-shop?id=1");
        tv_loading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoadingActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
