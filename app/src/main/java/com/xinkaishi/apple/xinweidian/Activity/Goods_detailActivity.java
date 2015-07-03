package com.xinkaishi.apple.xinweidian.Activity;

import android.content.Intent;
import android.os.Bundle;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xinkaishi.apple.xinweidian.Adapter.Img_Pageradapter;
import com.xinkaishi.apple.xinweidian.DAO.ShoppingcartDAO;
import com.xinkaishi.apple.xinweidian.R;
import com.xinkaishi.apple.xinweidian.Until.ScreenUtils;

import java.util.ArrayList;
import java.util.HashMap;


public class Goods_detailActivity extends ActionBarActivity {
    private Long id;//商品ID
    private HashMap<String,Object> hm;//该详细页商品信息

    //标题  进价  售价  销量  收藏  库存  利润
     private TextView tv_detailgoods_title, tv_detailgoods_price_in,
            tv_detailgoods_price_out, tv_detailgoods_saleNum, tv_detailgoods_collection,
            tv_detailgoods_inventory, tv_detailgoods_profit;
    private ViewPager detail_viewpager;//轮播图
    private LinearLayout ll_detailgoods_dot; //viewpager dot 位置
    private ArrayList<ImageView> list_img; //viewpager 图片
    private ArrayList<View> list_dot;  // viewpager dot
    private TextView tv_detailgoods_inshopcar;
    //底部 控件
    private LinearLayout ll_detailgoods_down1; //加入购物车 收藏
    private RelativeLayout rl_detailgoods_down2;  // 隐藏确定页
    private LinearLayout ll_detailgoods_back;//返回键
    private TextView tv_min, tv_add, tv_num, tv_allprice, tv_enter; // 加减 数量  总价  确认
    private int shopNum;//购物车中已有的商品数量

    private ShoppingcartDAO shoppingcartDAO;

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
        initDetail();
        initListener();
    }

    private void initIntent() {
        Bundle bundle = this.getIntent().getExtras();
        //hm为商品信息
        hm = (HashMap<String,Object>)bundle.getSerializable("goods");
        id = (Long)hm.get("id");
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

    @Override
    protected void onDestroy() {
        shoppingcartDAO.closeDB();
        super.onDestroy();
    }

    private void initView() {
        tv_detailgoods_profit = (TextView)findViewById(R.id.tv_detailgoods_profit);
        tv_detailgoods_title = (TextView)findViewById(R.id.tv_detailgoods_title);
        tv_detailgoods_price_in = (TextView)findViewById(R.id.tv_detailgoods_price_in);
        tv_detailgoods_price_out = (TextView)findViewById(R.id.tv_detailgoods_price_out);
        tv_detailgoods_collection = (TextView)findViewById(R.id.tv_detailgoods_collection);
        tv_detailgoods_inventory = (TextView)findViewById(R.id.tv_detailgoods_inventory);
        tv_detailgoods_saleNum = (TextView)findViewById(R.id.tv_detailgoods_saleNum);

        tv_detailgoods_inshopcar = (TextView)findViewById(R.id.tv_detailgoods_inshopcar);
        rl_detailgoods_down2 = (RelativeLayout)findViewById(R.id.rl_detailgoods_down2);
        ll_detailgoods_down1 = (LinearLayout)findViewById(R.id.ll_detailgoods_down1);
        ll_detailgoods_back = (LinearLayout)findViewById(R.id.ll_detailgoods_back);
        tv_min = (TextView)findViewById(R.id.tv_min);
        tv_add = (TextView)findViewById(R.id.tv_add);
        tv_num = (TextView)findViewById(R.id.tv_num);
        tv_allprice = (TextView)findViewById(R.id.tv_allprice);
        tv_enter = (TextView)findViewById(R.id.tv_enter);

        detail_viewpager = (ViewPager)findViewById(R.id.detail_viewpager);
        ll_detailgoods_dot = (LinearLayout)findViewById(R.id.ll_detailgoods_dot);

        list_dot = new ArrayList<View>();
        list_img = new ArrayList<ImageView>();

        shoppingcartDAO = new ShoppingcartDAO(this);
    }

    private void initViewpager() {
        ImageView iv1 = new ImageView(getApplication());
        iv1.setImageDrawable(getResources().getDrawable(R.drawable.che1));
        ImageView iv2 = new ImageView(getApplication());
        iv2.setImageDrawable(getResources().getDrawable(R.drawable.che2));
        ImageView iv3 = new ImageView(getApplication());
        iv3.setImageDrawable(getResources().getDrawable(R.drawable.che3));
        ImageView iv4 = new ImageView(getApplication());
        iv4.setImageDrawable(getResources().getDrawable(R.drawable.che4));
        ImageView iv5 = new ImageView(getApplication());
        iv5.setImageDrawable(getResources().getDrawable(R.drawable.che5));

        list_img.add(iv1);
        list_img.add(iv2);
        list_img.add(iv3);
        list_img.add(iv4);
        list_img.add(iv5);

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
            list_dot.add(dot);
            ll_detailgoods_dot.addView(dot);
        }
        detail_viewpager.setAdapter(new Img_Pageradapter(list_img)); //适配viewpager
        detail_viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            int oldposition = 0;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                list_dot.get(oldposition).setBackgroundResource(R.drawable.dot_normal);
                list_dot.get(position).setBackgroundResource(R.drawable.dot_focused);
                oldposition = position;
                Log.e("1111", "11111");
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    private void initDetail() {
        tv_detailgoods_title.setText(hm.get("name").toString());

        tv_detailgoods_price_in.setText("￥" + String.format("%.2f", (float) hm.get("import_price"))); //进价
        tv_detailgoods_price_out.setText("￥" + String.format("%.2f", (float) hm.get("price")));//售价
        tv_detailgoods_profit.setText("￥" + String.format("%.2f", (float) hm.get("profit")));//利润

        tv_detailgoods_saleNum.setText(hm.get("sale_amount").toString());
        tv_detailgoods_collection.setText(hm.get("user_collect").toString());
        tv_detailgoods_inventory.setText(hm.get("inventory").toString());

        shopNum = 1;
        HashMap<String,Object> data_hm;
        if(shoppingcartDAO.isInShop(id)){
            data_hm = shoppingcartDAO.getGoodsById(id);
            shopNum = (Integer)data_hm.get("num");
            Log.e("数据库", "详细页-商品存在数据库中，数量是" + shopNum);
        }
        tv_num.setText(shopNum + "");
        tv_allprice.setText("￥" + String.format("%.2f", shopNum * (float)hm.get("import_price")));
    }


    private void initListener() {
        final Animation anim_in = AnimationUtils.loadAnimation(getApplication(), R.anim.translate_detail_on);
        final Animation anim_out = AnimationUtils.loadAnimation(getApplication(), R.anim.translate_detail_off);
        final Animation anim_in1 = AnimationUtils.loadAnimation(getApplication(), R.anim.fade_in_anim);
        tv_detailgoods_inshopcar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl_detailgoods_down2.setVisibility(View.VISIBLE);
                rl_detailgoods_down2.startAnimation(anim_in);
                anim_in.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        ll_detailgoods_down1.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }
        });
        ll_detailgoods_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_detailgoods_down1.setVisibility(View.VISIBLE);
                rl_detailgoods_down2.startAnimation(anim_out);
                anim_out.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        rl_detailgoods_down2.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }
        });
        tv_add.setOnClickListener(new Myonclick(1));
        tv_min.setOnClickListener(new Myonclick(0));
        tv_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hm.put("state", 1);//进入购物车的选择状态
                hm.put("num", shopNum);
                if (shoppingcartDAO.isInShop(id)) {
                    shoppingcartDAO.update(hm);
                } else {
                    shoppingcartDAO.add(hm);
                }
                ll_detailgoods_back.performClick();//点击返回
                //选择框 继续逛 跳转购物车
                setPopup();
            }
        });
    }

    public void setPopup(){

        final PopupWindow popupWindow = new PopupWindow(this);
        View vPopWindow = LayoutInflater.from(this).inflate(R.layout.layout_popup_goodsin_detail, null);
        popupWindow.setContentView(vPopWindow);
        popupWindow.setOutsideTouchable(false);
        popupWindow.setHeight(ScreenUtils.getScreenH(this));
//        popupWindow.setHeight(ScreenUtils.getScreenH(this) * 3 / 10);
//        popupWindow.setWidth(ScreenUtils.getScreenW(this) * 4 / 5);
        popupWindow.setWidth(ScreenUtils.getScreenW(this));
        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.textview_biankuang_black));

        TextView tv_continue = (TextView)vPopWindow.findViewById(R.id.tv_continue);
        TextView tv_topay = (TextView)vPopWindow.findViewById(R.id.tv_topay);

        tv_topay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                finish();
                Intent intent = new Intent(getApplication(), Shopping_cartActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.pic_left_in, R.anim.pic_left_out);
            }
        });
        tv_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
                popupWindow.dismiss();
            }
        });

        popupWindow.showAtLocation(vPopWindow, Gravity.CENTER, 0, 0);
    }

    class Myonclick implements View.OnClickListener {
    private int a;
    public Myonclick(int a){
        this.a = a;
    }
    @Override
    public void onClick(View v) {
        switch (a){
            //加号
            case 1:
                if(shopNum >= (Integer)hm.get("inventory")){ //判断是否超过库存
                    return;
                }else{
                    shopNum ++;
                    tv_num.setText(shopNum + "");
                    tv_allprice.setText("￥" + String.format("%.2f", shopNum * (float)hm.get("price_in")));
                }
                break;
            //减号
            case 0:
                if(shopNum <= 1){
                    return;
                }else{
                    shopNum --;
                    tv_num.setText(shopNum + "");
                    tv_allprice.setText("￥" + String.format("%.2f", shopNum * (float)hm.get("price_in")));
                }
                break;
            }
        }
    }

}
