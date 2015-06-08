package com.xinkaishi.apple.xinweidian.Activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.xinkaishi.apple.xinweidian.Adapter.Adapter_goods_center;
import com.xinkaishi.apple.xinweidian.DAO.ImgDAO;
import com.xinkaishi.apple.xinweidian.Fragment.Appliance_Fragment;
import com.xinkaishi.apple.xinweidian.Fragment.Clothing_Fragment;
import com.xinkaishi.apple.xinweidian.Fragment.Digital_Fragment;
import com.xinkaishi.apple.xinweidian.Fragment.Infant_Fragment;
import com.xinkaishi.apple.xinweidian.R;

import java.util.ArrayList;
import java.util.HashMap;


public class Goods_centerActivity extends ActionBarActivity {
    private RadioGroup gr_goods_center_menu; //菜单栏
    private Digital_Fragment digital_fragment;  //菜单frag
    private Appliance_Fragment appliance_fragment;
    private Clothing_Fragment clothing_fragment;
    private Infant_Fragment infant_fragment;
    private ListView lv_goods_center_list;
    private ArrayList<HashMap<String, Object>> list;// 数据
    private ImgDAO imgdao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_center);

        // 显示导航按钮
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        initView(); //加载控件
        defalutFragment(); //默认菜单栏
        setFragmentLlistener(); //监听菜单栏
        setListView();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_goods_center, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.action_shoppingcart:
                Intent intent1 = new Intent(Goods_centerActivity.this, Shopping_cartActivity.class);
                startActivity(intent1);
                Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_order:
                Intent intent2 = new Intent(Goods_centerActivity.this, Goods_OrdersActivity.class);
                startActivity(intent2);
                Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_collection:
                Toast t = Toast.makeText(this, "", Toast.LENGTH_SHORT);
                t.setGravity(Gravity.CENTER, 0, 0);
                View layout = LayoutInflater.from(this).inflate(R.layout.layout_toast_style, null);
                t.setView(layout);
                t.show();
                Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_search:
                Intent intent4 = new Intent(Goods_centerActivity.this, SearchActivity.class);
                startActivity(intent4);
                Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
                return true;
            // 导航返回键
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void initView() {
        gr_goods_center_menu = (RadioGroup)findViewById(R.id.gr_goods_center_menu);
        lv_goods_center_list = (ListView)findViewById(R.id.lv_goods_center_list);
        imgdao = new ImgDAO(getApplication());
        list = new ArrayList<HashMap<String, Object>>();
    }

    private void defalutFragment() {
        digital_fragment = new Digital_Fragment();
        appliance_fragment = new Appliance_Fragment();
        infant_fragment = new Infant_Fragment();
        clothing_fragment = new Clothing_Fragment();
        //设置默认fragment
        FragmentManager fm = getFragmentManager();
        // 开启Fragment事务
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.fl_goods_center_menu, digital_fragment);
        transaction.commit();
    }

    private void setFragmentLlistener() {
        // 数码 家电 母婴 服装
        gr_goods_center_menu.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                FragmentManager fm = getFragmentManager();
                // 开启Fragment事务
                FragmentTransaction transaction = fm.beginTransaction();
                switch (checkedId) {
                    case R.id.rb_goods_center_digital:
                        Toast.makeText(Goods_centerActivity.this, "数码", Toast.LENGTH_SHORT).show();
                        transaction.replace(R.id.fl_goods_center_menu, digital_fragment);
                        transaction.commit();
                        break;
                    case R.id.rb_goods_center_appliance:
                        Toast.makeText(Goods_centerActivity.this, "电器", Toast.LENGTH_SHORT).show();
                        transaction.replace(R.id.fl_goods_center_menu, appliance_fragment);
                        transaction.commit();
                        break;
                    case R.id.rb_goods_center_infant:
                        Toast.makeText(Goods_centerActivity.this, "母婴", Toast.LENGTH_SHORT).show();
                        transaction.replace(R.id.fl_goods_center_menu, infant_fragment);
                        transaction.commit();
                        break;
                    case R.id.rb_goods_center_clothing:
                        Toast.makeText(Goods_centerActivity.this, "服装", Toast.LENGTH_SHORT).show();
                        transaction.replace(R.id.fl_goods_center_menu, clothing_fragment);
                        transaction.commit();
                        break;

                }
            }
        });
    }

    private void setListView() {
        for(int a = 0; a < 30; a ++){
            HashMap<String, Object> hm = new HashMap<String, Object>();
            hm.put("id", a);
            hm.put("image", "http://img2.imgtn.bdimg.com/it/u=2482911974,2403159586&fm=21&gp=0.jpg");
            hm.put("title", "标题---此处商品ID为：" + a);
            hm.put("price_in", 100 + a);
            hm.put("profit", 100 + a);
            hm.put("price_out", 100 + a);
            list.add(hm);
        }
        Adapter_goods_center adapter_goods_center = new Adapter_goods_center(
                Goods_centerActivity.this, list, R.layout.layout_goods_center,
                new String[]{"id", "image", "title", "price_out", "profit", "price_in"},
                new int[]{R.id.iv_goodscenter_image, R.id.tv_goodscenter_title, R.id.tv_goodscenter_price_out,
                        R.id.tv_goodscenter_profit, R.id.tv_goodscenter_price_in, R.id.tv_goodscenter_shoucang,
                        R.id.tv_goodscenter_goodsIn, R.id.tv_goodscenter_getInshop}, imgdao);
        lv_goods_center_list.setAdapter(adapter_goods_center);
        lv_goods_center_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String goodId = list.get(position).get("id").toString();
                Intent intent = new Intent(Goods_centerActivity.this,Goods_detailActivity.class);
                intent.putExtra("id", goodId);
                startActivity(intent);
                overridePendingTransition(R.anim.pic_left_in, R.anim.pic_left_out);
            }
        });
    }

    @Override
    protected void onDestroy() {
        imgdao.closeDB();
        super.onDestroy();
    }
}
