package com.xinkaishi.apple.xinweidian.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.xinkaishi.apple.xinweidian.Bean.Interface;
import com.xinkaishi.apple.xinweidian.Bean.MenuBean.MenuState;
import com.xinkaishi.apple.xinweidian.DAO.MenuDAO;
import com.xinkaishi.apple.xinweidian.R;
import com.xinkaishi.apple.xinweidian.Until.DataAnalysis;
import com.xinkaishi.apple.xinweidian.Until.Until;


public class MainActivity extends ActionBarActivity {
    private TextView tv_toGoodscenter, tv_main_setweb;
    private MenuState menu;
    private MenuDAO menuDAO;
    private WebView wb_main;
    private String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initView();
        new initMenu(Interface.MENU_LIST).execute(); //预读商品页菜单

        Log.e("网络类型", Until.NetType(this));
        tv_toGoodscenter = (TextView)findViewById(R.id.tv_toGoodscenter);
        tv_toGoodscenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Goods_centerActivity.class);
                startActivity(intent);
            }
        });

        tv_main_setweb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url = "http://test.pc.xinkaishi.com/shop/user/index";
                DataAnalysis.synCookies(MainActivity.this, url);
                wb_main.loadUrl(url);
            }
        });

    }

    class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            wb_main.loadUrl(url);
            return super.shouldOverrideUrlLoading(view, url);
        }
    }

    private void initView() {
        menuDAO = new MenuDAO(this);
        tv_main_setweb = (TextView)findViewById(R.id.tv_main_setweb);
        wb_main = (WebView)findViewById(R.id.wb_main);
        wb_main.getSettings().setJavaScriptEnabled(true);
        wb_main.setWebViewClient(new MyWebViewClient());
    }

    private class initMenu extends AsyncTask<Void, Void, String>{
        private String url;
        public initMenu(String url){
            this.url = url;
        }

        @Override
        protected String doInBackground(Void... params) {

            String json = null;
            try {

                json = DataAnalysis.readParse(url);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return json;
        }

        @Override
        protected void onPostExecute(String json) {
            //todo shopid,暂用1
            if(menuDAO.inJSON(1)){
                menuDAO.update(json, 1);
                Log.e("菜单JSON", "已存在，修改成功");
            }else{
                menuDAO.add(json, 1);
                Log.e("菜单JSON", "不存在，新增成功");
            }
            super.onPostExecute(json);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        menuDAO.closeDB();
        super.onDestroy();
    }
}
