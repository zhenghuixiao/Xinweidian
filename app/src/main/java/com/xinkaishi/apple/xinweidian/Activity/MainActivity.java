package com.xinkaishi.apple.xinweidian.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.xinkaishi.apple.xinweidian.Bean.Interface;
import com.xinkaishi.apple.xinweidian.Bean.MenuBean.MenuState;
import com.xinkaishi.apple.xinweidian.DAO.MenuDAO;
import com.xinkaishi.apple.xinweidian.R;
import com.xinkaishi.apple.xinweidian.Until.DataAnalysis;


public class MainActivity extends ActionBarActivity {
    private TextView tv_toGoodscenter, tv_main_login, tv_main_get;
    private MenuState menu;
    private String  a = null;
    private MenuDAO menuDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initView();
        tv_toGoodscenter = (TextView)findViewById(R.id.tv_toGoodscenter);
        tv_toGoodscenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Goods_centerActivity.class);
                startActivity(intent);
            }
        });




        tv_main_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Log.e("返回", DataAnalysis.readParse("http://192.168.1.102:4000/shop/auth/login").toString());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });

        tv_main_get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            new initMenu(Interface.MENU_LIST).execute(); //预读商品页菜单
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });

    }

    private void initView() {
        menuDAO = new MenuDAO(this);
        tv_main_login = (TextView)findViewById(R.id.tv_main_login);
        tv_main_get = (TextView)findViewById(R.id.tv_main_get);
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
