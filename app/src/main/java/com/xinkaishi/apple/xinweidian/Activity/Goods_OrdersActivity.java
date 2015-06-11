package com.xinkaishi.apple.xinweidian.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ExpandableListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.xinkaishi.apple.xinweidian.Adapter.Adapter_goods_orders;
import com.xinkaishi.apple.xinweidian.Bean.TESTLIST;
import com.xinkaishi.apple.xinweidian.CustomView.PinnedHeaderExpandableListView;
import com.xinkaishi.apple.xinweidian.R;

import java.util.ArrayList;
import java.util.HashMap;

public class Goods_OrdersActivity extends ActionBarActivity  implements
        PinnedHeaderExpandableListView.OnHeaderUpdateListener {
    private RadioButton rb_goods_orders_all, rb_goods_orders_unpay, rb_goods_orders_untake, rb_goods_orders_unget;
    private RadioGroup rg_goods_orders_head;
    private PinnedHeaderExpandableListView lv_goods_orders_exlist;
    private Adapter_goods_orders adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_orders_);

        initActionBar();
        initView();
        initSetOnclick();
        initAdapter();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_goods_orders_, menu);
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
    public View getPinnedHeader(int firstVisibleGroupPos) {
        View headerView = getLayoutInflater().inflate(R.layout.layout_goods_ordergroup, null);

        headerView.setLayoutParams(new AbsListView.LayoutParams(
                AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT));
        return headerView;
    }

    @Override
    public void updatePinnedHeader(View headerView, int firstVisibleGroupPos) {
        HashMap<String, Object> firstVisibleGroup = (HashMap<String, Object>) adapter.getGroup(firstVisibleGroupPos);
        if(headerView != null){
            TextView textView = (TextView) headerView.findViewById(R.id.tv_ordergroup_jiaoyihao);
            textView.setText(firstVisibleGroup.get("transaction").toString());
        }
    }

    private boolean initActionBar() {
        // 显示导航按钮
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        if (actionBar == null) {
            Log.e("ActionBar", "订单页错误");
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

    private void initView() {
        rg_goods_orders_head = (RadioGroup)findViewById(R.id.rg_goods_orders_head);
        rb_goods_orders_all = (RadioButton)findViewById(R.id.rb_goods_orders_all);
        rb_goods_orders_unpay = (RadioButton)findViewById(R.id.rb_goods_orders_unpay);
        rb_goods_orders_untake = (RadioButton)findViewById(R.id.rb_goods_orders_untake);
        rb_goods_orders_unget = (RadioButton)findViewById(R.id.rb_goods_orders_unget);
        lv_goods_orders_exlist = (PinnedHeaderExpandableListView)findViewById(R.id.lv_goods_orders_exlist);
    }

    private void initSetOnclick() {
        rg_goods_orders_head.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_goods_orders_all:
                        //todo 刷新列表
                        break;
                    case R.id.rb_goods_orders_unpay:
                        break;
                    case R.id.rb_goods_orders_untake:
                        break;
                    case R.id.rb_goods_orders_unget:
                        break;
                }
            }
        });
    }

    private void initAdapter() {
        final ArrayList<HashMap<String, Object>> testlist = new TESTLIST().getList();
        adapter = new Adapter_goods_orders(Goods_OrdersActivity.this, testlist);
        lv_goods_orders_exlist.setAdapter(adapter);
        lv_goods_orders_exlist.setOnHeaderUpdateListener(this);
        // 默认展开全部项
        for(int a = 0; a < testlist.size(); a ++){
            lv_goods_orders_exlist.expandGroup(a);
        }
        lv_goods_orders_exlist.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;
            }
        }, false);
    }
}
