package com.xinkaishi.apple.xinweidian.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xinkaishi.apple.xinweidian.R;

import java.util.ArrayList;
import java.util.HashMap;

public class Payment_Activity extends ActionBarActivity {
    private TextView tv_payment_detail;
    //分别为 下单时间，下单账号， 商家信息
    private LinearLayout ll_payment_OrdersTime, ll_payment_OrdersAccount, ll_payment_ShopOwner;

    //分别为 交易号 ，商品名称， 商家信息， 下单账号， 下单时间，  付款金额
    private TextView tv_payment_order, tv_payment_name, tv_payment_shopmessage, tv_payment_accounts,
            tv_payment_time, tv_payment_money;
    private int state = 0;//信息展现  0代表简单显示， 1表示详细显示

    private ArrayList<HashMap<String, Object>> list; //订单数据
    private HashMap<String, Object> hm; //传递过来的交易号等返回数据
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_);

        initActionBar();
        initIntent();//拿到数据

        initView();//初始化控件
        initShow();
        setOnclick();
    }

    private boolean initActionBar() {
        // 显示导航按钮
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.mipmap.pay_nav_back);//设置返回键图标
        if (actionBar == null) {
            Log.e("ActionBar", "payment页错误");
            return false;
        }
        //TODO 自定义布局 标题
//        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
//        actionBar.setDisplayShowCustomEnabled(true);
//        actionBar.setCustomView(R.layout.top_back_center_bar);
//        tvTitle = (TextView) actionBar.getCustomView().findViewById(R.id.tv_tbb_title);
//        actionBar.getCustomView().findViewById(R.id.tv_tbb_title);
//        tvTitle.setText(originalTitle);
//        actionBar.getCustomView().findViewById(R.id.iv_tbb_back)
//                .setOnClickListener(new OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        finish();
//                    }
//                });
        return true;
    }

    private void initIntent() {
        Bundle bundle = this.getIntent().getExtras();
        ArrayList bundlelist = bundle.getParcelableArrayList("list");
        bundle.getString("order");
        list = new ArrayList<HashMap<String, Object>>();
        hm = new HashMap<String, Object>();
        list= (ArrayList<HashMap<String, Object>>)bundlelist.get(0);
        hm.put("trade_group_id", bundle.getString("trade_group_id")); //交易号
        hm.put("created_at", bundle.getString("created_at")); //下单时间
        hm.put("consignee", bundle.getString("consignee")); //联系人
        hm.put("cellphone", bundle.getString("cellphone")); //手机号
        hm.put("fee", bundle.getFloat("fee")); //付款金额
        Log.e("数据", list.get(0).get("name") + "" + list.size());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_payment_, menu);
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
        ll_payment_OrdersTime = (LinearLayout)findViewById(R.id.ll_payment_OrdersTime);
        ll_payment_OrdersAccount = (LinearLayout)findViewById(R.id.ll_payment_OrdersAccount);
        tv_payment_detail = (TextView)findViewById(R.id.tv_payment_detail);
        tv_payment_order = (TextView)findViewById(R.id.tv_payment_order);
        tv_payment_name = (TextView)findViewById(R.id.tv_payment_name);
        tv_payment_accounts = (TextView)findViewById(R.id.tv_payment_accounts);
        tv_payment_time = (TextView)findViewById(R.id.tv_payment_time);
        tv_payment_money = (TextView)findViewById(R.id.tv_payment_money);
    }

    private void initShow() {
        StringBuffer stringBuffer = new StringBuffer();
        for(int a = 0; a < list.size(); a ++){
            if(a != list.size() - 1){
                stringBuffer.append(list.get(a).get("name"))
                        .append("_数量X")
                        .append(list.get(a).get("num"))
                        .append(" / ");
            }else{
                stringBuffer.append(list.get(a).get("name"))
                        .append("_数量X")
                        .append(list.get(a).get("num"));
            }

        }
        tv_payment_order.setText(hm.get("trade_group_id").toString());
        tv_payment_name.setText(stringBuffer);
        tv_payment_accounts.setText(hm.get("consignee") + "/" + hm.get("cellphone"));
        tv_payment_time.setText(hm.get("created_at").toString());
        tv_payment_money.setText("￥" + String.format("%.2f", hm.get("fee")));
    }

    private void setOnclick() {
        tv_payment_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (state){
                    case 0:
                        ll_payment_OrdersTime.setVisibility(View.VISIBLE);
                        ll_payment_OrdersAccount.setVisibility(View.VISIBLE);
                        tv_payment_name.setSingleLine(false);
                        tv_payment_detail.setText("收");
                        state = 1;
                        break;
                    case 1:
                        ll_payment_OrdersTime.setVisibility(View.GONE);
                        ll_payment_OrdersAccount.setVisibility(View.GONE);
                        tv_payment_name.setSingleLine(true);
                        tv_payment_detail.setText("详");
                        state = 0;
                        break;
                }
            }
        });
    }
}
