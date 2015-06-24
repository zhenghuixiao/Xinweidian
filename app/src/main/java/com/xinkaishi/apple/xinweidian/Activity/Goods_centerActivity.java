package com.xinkaishi.apple.xinweidian.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.xinkaishi.apple.xinweidian.Adapter.Adapter_goods_center;
import com.xinkaishi.apple.xinweidian.Adapter.Menu_PagerAdapter;
import com.xinkaishi.apple.xinweidian.DAO.ImgDAO;
import com.xinkaishi.apple.xinweidian.DAO.ShoppingcartDAO;
import com.xinkaishi.apple.xinweidian.Fragment.Child_Menu;
import com.xinkaishi.apple.xinweidian.R;

import java.util.ArrayList;
import java.util.HashMap;


public class Goods_centerActivity extends ActionBarActivity {
    private LinearLayout ll_goods_center_down; //菜单栏 layout
    private RelativeLayout rl_goods_center_menu;//隐藏子菜单栏
    private ViewPager menu_viewpager;
    private RadioGroup gr_goods_center_menu; //菜单栏
    private RadioButton rb_goods_center_digital, rb_goods_center_appliance, rb_goods_center_infant, rb_goods_center_clothing; //菜单栏

    private ListView lv_goods_center_list;
    private ArrayList<HashMap<String, Object>> list;// 数据
    private View darkview;//暗色背景
    private ImgDAO imgdao;
    private ShoppingcartDAO shoppingcartDAO;

    private boolean isopen;
    private int checkedID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_center);

        // 显示导航按钮
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        initView(); //加载控件
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
                View layout = LayoutInflater.from(this).inflate(R.layout.layout_toast_style_inshop, null);
                t.setView(layout);
                t.show();
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
        rb_goods_center_digital = (RadioButton)findViewById(R.id.rb_goods_center_digital);
        rb_goods_center_appliance = (RadioButton)findViewById(R.id.rb_goods_center_appliance);
        rb_goods_center_infant = (RadioButton)findViewById(R.id.rb_goods_center_infant);
        rb_goods_center_clothing = (RadioButton)findViewById(R.id.rb_goods_center_clothing);
        ll_goods_center_down = (LinearLayout)findViewById(R.id.ll_goods_center_down);
        rl_goods_center_menu = (RelativeLayout)findViewById(R.id.rl_goods_center_menu);
        lv_goods_center_list = (ListView)findViewById(R.id.lv_goods_center_list);
        menu_viewpager = (ViewPager)findViewById(R.id.menu_viewpager);
        darkview = findViewById(R.id.darkview);
        imgdao = new ImgDAO(getApplication());
        shoppingcartDAO = new ShoppingcartDAO(getApplication());
        list = new ArrayList<HashMap<String, Object>>();

        isopen = false;//默认关闭
        checkedID = 0;//默认0 无选中
    }

    private void setFragmentLlistener() {
        rb_goods_center_digital.setOnClickListener(new Myonclick_menu(1));
        rb_goods_center_appliance.setOnClickListener(new Myonclick_menu(2));
        rb_goods_center_infant.setOnClickListener(new Myonclick_menu(3));
        rb_goods_center_clothing.setOnClickListener(new Myonclick_menu(4));
        // 数码 家电 母婴 服装
        gr_goods_center_menu.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_goods_center_digital:
                        Toast.makeText(Goods_centerActivity.this, "数码", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.rb_goods_center_appliance:
                        Toast.makeText(Goods_centerActivity.this, "电器", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.rb_goods_center_infant:
                        Toast.makeText(Goods_centerActivity.this, "母婴", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.rb_goods_center_clothing:
                        Toast.makeText(Goods_centerActivity.this, "服装", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

    }
    class Myonclick_menu implements View.OnClickListener {
        private int id;
        public Myonclick_menu(int id){
            this.id = id;

        }

        @Override
        public void onClick(View v) {
            Log.e("点击","checkid=" + checkedID + " id=" + id + " isopen=" + isopen);
            if (!isopen) {
                Animation sunshine = AnimationUtils.loadAnimation(Goods_centerActivity.this, R.anim.translate_menu_on);
                ll_goods_center_down.startAnimation(sunshine);
                rl_goods_center_menu.setVisibility(View.VISIBLE);
                isopen = true;
                Log.e("打开","1111111");
            }else if(checkedID == id) {
                Log.e("关闭","checkedID == id");
                Animation sunshine1 = AnimationUtils.loadAnimation(Goods_centerActivity.this, R.anim.translate_menu_off);
                ll_goods_center_down.startAnimation(sunshine1);
                isopen = false; //菜单关闭标记

                sunshine1.setFillEnabled(true);   //解决移动后的闪烁问题
                sunshine1.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        rl_goods_center_menu.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }else{
                Log.e("不变","checkedID != id" + " isopen=" + isopen);
            }
            checkedID = id;
        }
    }
    private void setListView() {
        for(int a = 0; a < 30; a ++){
            HashMap<String, Object> hm = new HashMap<String, Object>();
            hm.put("id", a);
            hm.put("img", "http://img2.imgtn.bdimg.com/it/u=2482911974,2403159586&fm=21&gp=0.jpg");
            hm.put("name", "商品---此处商品ID为：" + a);
            hm.put("format", "A6660" + a);
            hm.put("price_in", 45.50f);
            hm.put("num", 1);
            hm.put("profit", 100 + a);//利润
            hm.put("price_out", 100 + a);
            list.add(hm);
        }
        Adapter_goods_center adapter_goods_center = new Adapter_goods_center(
                Goods_centerActivity.this, list, R.layout.layout_goods_center,
                new String[]{"id", "img", "name", "price_out", "profit", "price_in"},
                new int[]{R.id.iv_goodscenter_image, R.id.tv_goodscenter_title, R.id.tv_goodscenter_price_out,
                        R.id.tv_goodscenter_profit, R.id.tv_goodscenter_price_in, R.id.tv_goodscenter_shoucang,
                        R.id.tv_goodscenter_goodsIn, R.id.tv_goodscenter_getInshop}, imgdao, shoppingcartDAO, darkview);
        lv_goods_center_list.setAdapter(adapter_goods_center);
        lv_goods_center_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("goods", list.get(position));
                Intent intent = new Intent();
                intent.putExtras(bundle);
                intent.setClass(Goods_centerActivity.this, Goods_detailActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.pic_left_in, R.anim.pic_left_out);
            }
        });

        setViewPager();//子菜单的viewpager适配
    }

    private void setViewPager(){
        ArrayList<Fragment> fragmentList = new ArrayList<Fragment>();
        Child_Menu cm = new Child_Menu();
        fragmentList.add(new Child_Menu());
        fragmentList.add(new Child_Menu());
        fragmentList.add(new Child_Menu());
        Log.e("ViewPager", "添加页成功");
        menu_viewpager.setAdapter(new Menu_PagerAdapter(getSupportFragmentManager(), fragmentList));
    }
    @Override
    protected void onDestroy() {
        imgdao.closeDB();
        shoppingcartDAO.closeDB();
        super.onDestroy();
    }

}
