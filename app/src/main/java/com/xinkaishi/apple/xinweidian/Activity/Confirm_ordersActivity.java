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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xinkaishi.apple.xinweidian.DAO.AddressDAO;
import com.xinkaishi.apple.xinweidian.R;

import java.util.HashMap;

public class Confirm_ordersActivity extends ActionBarActivity {
    private RelativeLayout rl_confirm_orders_receiving; //收货信息设置
    private LinearLayout ll_confirm_orders_defaultIfm; //无收货信息时的默认页面
    private HashMap<String, Object> receiceIFM;//数据库中的默认收货地址
    private TextView tv_confirm_orders_submit;//提交订单

    //姓名，手机，收货地址
    private TextView tv_confirm_orders_name, tv_confirm_orders_tel, tv_confirm_orders_address;
    private AddressDAO addDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_orders);

        // 显示导航按钮
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        initView();//初始化控件

        setonclick();//监听
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_confirm_orders, menu);
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

    @Override
    protected void onDestroy() {
        addDAO.closeDB();
        super.onDestroy();
    }

    private void initView() {
        rl_confirm_orders_receiving = (RelativeLayout)findViewById(R.id.rl_confirm_orders_receiving);
        ll_confirm_orders_defaultIfm = (LinearLayout)findViewById(R.id.ll_confirm_orders_defaultIfm);
        tv_confirm_orders_name = (TextView)findViewById(R.id.tv_confirm_orders_name);
        tv_confirm_orders_tel = (TextView)findViewById(R.id.tv_confirm_orders_tel);
        tv_confirm_orders_address = (TextView)findViewById(R.id.tv_confirm_orders_address);
        tv_confirm_orders_submit = (TextView)findViewById(R.id.tv_confirm_orders_submit);
        receiceIFM = new HashMap<String, Object>();
        addDAO = new AddressDAO(this);
    }

    @Override
    protected void onResume() {
        initMain();//收货信息初始化 刷新

        super.onResume();
    }

    private void initMain() {
        receiceIFM = addDAO.getDefaultAddress();

        if(receiceIFM.get("name") == null){
            Log.e("eee", "默认收货地址为空");
            ll_confirm_orders_defaultIfm.setVisibility(View.VISIBLE);
        }else{
            ll_confirm_orders_defaultIfm.setVisibility(View.GONE);
            tv_confirm_orders_name.setText(receiceIFM.get("name").toString());
            tv_confirm_orders_tel.setText(receiceIFM.get("tel").toString());
            tv_confirm_orders_address.setText(receiceIFM.get("address").toString());

        }
    }

    private void setonclick() {
        rl_confirm_orders_receiving.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Confirm_ordersActivity.this, SetReceiveIfmActivity.class);
                startActivity(intent);

                overridePendingTransition(R.anim.pic_left_in, R.anim.pic_left_out);
            }
        });

        tv_confirm_orders_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 判断收货地址是否设置
                Intent intent = new Intent(Confirm_ordersActivity.this, Payment_Activity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.pic_left_in, R.anim.pic_left_out);
            }
        });
    }

}
