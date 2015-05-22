package com.xinkaishi.apple.xinweidian;

import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class Goods_detailActivity extends ActionBarActivity {
    private String id;//商品ID
    private TextView tv_goodsdetail_title, tv_goodsdetail_price_in,
            tv_goodsdetail_price_out, tv_goodsdetail_saleNum, tv_goodsdetail_collection,
            tv_goodsdetail_inventory, tv_goodsdetail_profit;
    private ViewPager detail_viewpager;
    private LinearLayout ll_detailgoods_dot;
    private ArrayList<ImageView> list_img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_detail);

        // 显示导航按钮
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        initIntent();// 取到上级传的信息
        initView();
        initViewpager();
    }

    private void initIntent() {
        Intent intent = getIntent();
        id = intent.getExtras().getString("id");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_goods_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.action_shoppingcart:
                Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
                return true;
            // 导航返回键
            case android.R.id.home:
//                // 返回应用的主activity
//                Intent intent = new Intent(this, LoadingActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.pic_right_in,R.anim.pic_right_out);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initView() {
        tv_goodsdetail_profit = (TextView)findViewById(R.id.tv_goodsdetail_profit);
        tv_goodsdetail_title = (TextView)findViewById(R.id.tv_goodsdetail_title);
        tv_goodsdetail_price_in = (TextView)findViewById(R.id.tv_goodsdetail_price_in);
        tv_goodsdetail_price_out = (TextView)findViewById(R.id.tv_goodsdetail_price_out);
        tv_goodsdetail_collection = (TextView)findViewById(R.id.tv_goodsdetail_collection);
        tv_goodsdetail_inventory = (TextView)findViewById(R.id.tv_goodsdetail_inventory);
        tv_goodsdetail_saleNum = (TextView)findViewById(R.id.tv_goodsdetail_saleNum);
        detail_viewpager = (ViewPager)findViewById(R.id.detail_viewpager);
        ll_detailgoods_dot = (LinearLayout)findViewById(R.id.ll_detailgoods_dot);
        list_img = new ArrayList<ImageView>();
        tv_goodsdetail_title.setText("商品ID：" + id);
    }

    private void initViewpager() {
        for(int a = 0; a < 5; a ++){
            View dot = new View(getApplicationContext());
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(15,15);
            if(a ==0){
                dot.setBackgroundResource(R.drawable.dot_focused); //第一个默认选中
            }else{
                dot.setBackgroundResource(R.drawable.dot_normal);
                lp.leftMargin = 5; //除了第一个 设置间距
            }
            dot.setLayoutParams(lp);
//            list_dot.add(dot);
            ll_detailgoods_dot.addView(dot);
        }
        detail_viewpager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return 5;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return false;
            }

            @Override
            public Object instantiateItem(View container, int position) {
                return super.instantiateItem(container, position);
            }
        });
    }

}
