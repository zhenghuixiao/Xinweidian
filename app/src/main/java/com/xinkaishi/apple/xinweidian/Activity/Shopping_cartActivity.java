package com.xinkaishi.apple.xinweidian.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.xinkaishi.apple.xinweidian.Adapter.Adapter_shopping_list;
import com.xinkaishi.apple.xinweidian.DAO.ImgDAO;
import com.xinkaishi.apple.xinweidian.R;

import java.util.ArrayList;
import java.util.HashMap;


public class Shopping_cartActivity extends ActionBarActivity {
    private ListView lv_shopping_cart_list;
    private ArrayList<HashMap<String, Object>> list;
    private ImgDAO imgdao;
    private TextView tv_shopping_cart_pay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);

        // 显示导航按钮
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        initView();
        setListView();
        setonclick();//去结算
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_shopping_cart, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.action_mainback:
                // 返回应用的主activity
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            // 导航返回键
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initView() {
        lv_shopping_cart_list = (ListView)findViewById(R.id.lv_shopping_cart_list);
        tv_shopping_cart_pay = (TextView)findViewById(R.id.tv_shopping_cart_pay);
        list = new ArrayList<HashMap<String, Object>>();
        imgdao = new ImgDAO(getApplication());
    }

    private void setListView() {
        for(int a = 0; a < 30; a ++){
            HashMap<String, Object> hm = new HashMap<String, Object>();
            hm.put("id", a);
            hm.put("status", 1);
            hm.put("image", "http://h.hiphotos.baidu.com/image/w%3D310/sign=5359580831fa828bd1239be2cd1e41cd/dcc451da81cb39db599abb5bd2160924ab183061.jpg");
            hm.put("title", "标题---此处商品ID为：" + a);
            hm.put("format", "规格" + 100 + a);
            hm.put("inprice", 100 + a);
            hm.put("picknum", 10 + a);
            list.add(hm);
        }
        Adapter_shopping_list adapter = new Adapter_shopping_list(Shopping_cartActivity.this, list, R.layout.layout_shopping_cart,
                new String[]{"id", "status", "image", "title", "format", "inprice", "picknum"},
                new int[]{R.id.tv_shoppingcart_status, R.id.iv_shoppingcart_image, R.id.tv_goodscenter_title,
                        R.id.tv_shoppingcart_format, R.id.tv_shoppingcart_inprice, R.id.tv_shoppingcart_picknum, R.id.tv_shoppingcart_allprice}, imgdao);
        lv_shopping_cart_list.setAdapter(adapter);
    }
    private void setonclick() {
        tv_shopping_cart_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Shopping_cartActivity.this, Confirm_ordersActivity.class);
                startActivity(intent);
            }
        });
    }
}
