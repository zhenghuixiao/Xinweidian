package com.xinkaishi.apple.xinweidian.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.xinkaishi.apple.xinweidian.R;

public class Payment_Activity extends ActionBarActivity {
    private ImageView iv_payment_detail;
    //分别为 下单时间，下单账号， 商家信息， 未展开时商品名称， 展开详细时的商品名称
    private LinearLayout ll_payment_OrdersTime, ll_payment_OrdersAccount, ll_payment_ShopOwner,
                            ll_payment_SimpleName, ll_payment_DetailName;
    private int state = 0;//0代表简单显示， 1表示详细显示
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_);

        initActionBar();
        initView();//初始化控件
        setOnclick();
    }

    private boolean initActionBar() {
        // 显示导航按钮
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
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
        ll_payment_ShopOwner = (LinearLayout)findViewById(R.id.ll_payment_ShopOwner);
        ll_payment_SimpleName = (LinearLayout)findViewById(R.id.ll_payment_SimpleName);
        ll_payment_DetailName = (LinearLayout)findViewById(R.id.ll_payment_DetailName);
        iv_payment_detail = (ImageView)findViewById(R.id.iv_payment_detail);
    }

    private void setOnclick() {
        iv_payment_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (state){
                    case 0:
                        ll_payment_OrdersTime.setVisibility(View.VISIBLE);
                        ll_payment_OrdersAccount.setVisibility(View.VISIBLE);
                        ll_payment_ShopOwner.setVisibility(View.VISIBLE);
                        ll_payment_DetailName.setVisibility(View.VISIBLE);
                        ll_payment_SimpleName.setVisibility(View.GONE);
                        iv_payment_detail.setBackgroundResource((R.drawable.dot_focused));
                        state = 1;
                        break;
                    case 1:
                        ll_payment_OrdersTime.setVisibility(View.GONE);
                        ll_payment_OrdersAccount.setVisibility(View.GONE);
                        ll_payment_ShopOwner.setVisibility(View.GONE);
                        ll_payment_DetailName.setVisibility(View.GONE);
                        ll_payment_SimpleName.setVisibility(View.VISIBLE);
                        iv_payment_detail.setBackgroundResource((R.drawable.dot_normal));
                        state = 0;
                        break;
                }
            }
        });
    }
}
