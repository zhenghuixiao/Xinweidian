package com.xinkaishi.apple.xinweidian.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xinkaishi.apple.xinweidian.Adapter.Adapter_goods_orders_detail;
import com.xinkaishi.apple.xinweidian.CustomView.PinnedHeaderExpandableListView;
import com.xinkaishi.apple.xinweidian.CustomView.StickyLayout;
import com.xinkaishi.apple.xinweidian.R;

import java.util.HashMap;

public class Order_detailActivity extends ActionBarActivity implements
        PinnedHeaderExpandableListView.OnHeaderUpdateListener,
        StickyLayout.OnGiveUpTouchEventListener {

    private PinnedHeaderExpandableListView lv_order_detail_exlist;
    private StickyLayout stickyLayout;
    private Adapter_goods_orders_detail adapter;
    private HashMap<String, Object> hm;//详情信息
    private int state;//状态
    private TextView tv_orderchild_continue, tv_orderchild_close, tv_orderchild_other;  // 继续支付  关闭订单  其他单按钮
    private LinearLayout rl_orderchild_unpay, rl_orderchild_other;  // 未支付状态ll  其他ll

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        // 显示导航按钮
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        hm = (HashMap<String, Object>)intent.getExtras().get("child");
        state = (Integer)hm.get("state");

        initView(); //加载控件
        //todo 获取数据
        initAdapter();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_order_detail, menu);
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

    // 实现两个接口 生成group布局   跟换对应group的信息  自己加了firstVisibleGroupPos 为了隐藏第一个grouplayout
    @Override
    public View getPinnedHeader(int firstVisibleGroupPos) {
        View headerView = getLayoutInflater().inflate(R.layout.layout_detail_ordergroup, null);

        headerView.setLayoutParams(new AbsListView.LayoutParams(
                AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT));
        return headerView;
    }

    @Override
    public void updatePinnedHeader(View headerView, int firstVisibleGroupPos) {
        HashMap<String, Object> firstVisibleGroup = (HashMap<String, Object>) adapter.getGroup(firstVisibleGroupPos);
        if (headerView != null) {
            TextView textView = (TextView) headerView.findViewById(R.id.tv_ordergroup_jiaoyihao);
            textView.setText(firstVisibleGroup.get("transaction").toString());
        }
    }


    @Override
    public boolean giveUpTouchEvent(MotionEvent event) {
        if (lv_order_detail_exlist.getFirstVisiblePosition() == 0) {
            View view = lv_order_detail_exlist.getChildAt(0);
            if (view != null && view.getTop() >= 0) {
                return true;
            }
        }
        return false;
    }

    private void initView() {
        stickyLayout = (StickyLayout)findViewById(R.id.sticky_layout);
        lv_order_detail_exlist = (PinnedHeaderExpandableListView)findViewById(R.id.lv_order_detail_exlist);
        tv_orderchild_continue = (TextView)findViewById(R.id.tv_orderchild_continue); //继续支付
        tv_orderchild_close = (TextView)findViewById(R.id.tv_orderchild_close); //关闭订单
        tv_orderchild_other = (TextView)findViewById(R.id.tv_orderchild_other); //其他操作
        rl_orderchild_unpay = (LinearLayout)findViewById(R.id.rl_orderchild_unpay);
        rl_orderchild_other = (LinearLayout)findViewById(R.id.rl_orderchild_other);
        if(state == 0){
            rl_orderchild_unpay.setVisibility(View.VISIBLE); //未支付ll  其他都为other
            tv_orderchild_close.setOnClickListener(new MyOnclick(5));
            tv_orderchild_continue.setOnClickListener(new MyOnclick(6));
        }else {
            rl_orderchild_other.setVisibility(View.VISIBLE);
            switch (state) {
            // 1 返回（交易成功）  2 删除订单（已关闭）  3 提醒发货（已支付等待发货）  4 查看物流（已发货）
                case 1:
                    tv_orderchild_other.setText("返回");
                    tv_orderchild_other.setOnClickListener(new MyOnclick(1));
                    break;
                case 2:
                    tv_orderchild_other.setText("删除订单");
                    tv_orderchild_other.setOnClickListener(new MyOnclick(2));
                    break;
                case 3:
                    tv_orderchild_other.setText("提醒发货");
                    tv_orderchild_other.setOnClickListener(new MyOnclick(3));
                    break;
                case 4:
                    tv_orderchild_other.setText("查看物流");
                    tv_orderchild_other.setOnClickListener(new MyOnclick(4));
                    break;
            }
        }
    }

    class MyOnclick implements View.OnClickListener{
        private int a;
        public MyOnclick(int a){
            this.a = a;
        }

        @Override
        public void onClick(View v) {
            switch (a){
                case 1:
                    finish();
                    break;
                case 2:
                    break;
                case 3:
                    Toast t = Toast.makeText(Order_detailActivity.this, "", Toast.LENGTH_SHORT);
                    t.setGravity(Gravity.CENTER, 0, 0);
                    View layout = LayoutInflater.from(Order_detailActivity.this).inflate(R.layout.layout_toast_style_remind, null);
                    t.setView(layout);
                    t.show();
                    break;
                case 4:
                    break;
                case 5:
                    //弹出pop

                    break;
            }
        }
    }
    private void initAdapter() {
        adapter = new Adapter_goods_orders_detail(Order_detailActivity.this, hm);
        lv_order_detail_exlist.setAdapter(adapter);
        lv_order_detail_exlist.setOnHeaderUpdateListener(Order_detailActivity.this);
        stickyLayout.setOnGiveUpTouchEventListener(this);
        // 默认展开全部项 详情页只有1个group
        for(int a = 0; a < 1; a ++){
            lv_order_detail_exlist.expandGroup(a);
        }
        lv_order_detail_exlist.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;
            }
        }, false);
    }
}
