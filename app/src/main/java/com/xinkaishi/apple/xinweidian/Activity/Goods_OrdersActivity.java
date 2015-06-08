package com.xinkaishi.apple.xinweidian.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.xinkaishi.apple.xinweidian.Bean.TESTLIST;
import com.xinkaishi.apple.xinweidian.R;

import java.util.ArrayList;
import java.util.HashMap;

public class Goods_OrdersActivity extends ActionBarActivity {
    private RadioButton rb_goods_orders_all, rb_goods_orders_unpay, rb_goods_orders_untake, rb_goods_orders_unget;
    private RadioGroup rg_goods_orders_head;
    private ExpandableListView lv_goods_orders_exlist;

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
        lv_goods_orders_exlist = (ExpandableListView)findViewById(R.id.lv_goods_orders_exlist);
    }

    private void initSetOnclick() {
        rg_goods_orders_head.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_goods_orders_all:
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
        final ExpandableListAdapter adapter = new BaseExpandableListAdapter() {
            //设置组视图的显示文字
            private String[] generalsTypes = new String[] { "魏", "蜀", "吴" };
            //子视图显示文字
            private String[][] generals = new String[][] {
                    { "夏侯惇", "甄姬", "许褚", "郭嘉", "司马懿", "杨修" },
                    { "马超", "张飞", "刘备", "诸葛亮", "黄月英", "赵云" },
                    { "吕蒙", "陆逊", "孙权", "周瑜", "孙尚香" }

            };
            //子视图图片
            public int[][] generallogos = new int[][] {
                    { R.drawable.abc_ab_share_pack_mtrl_alpha, R.drawable.abc_ab_share_pack_mtrl_alpha,
                            R.drawable.abc_ab_share_pack_mtrl_alpha, R.drawable.abc_ab_share_pack_mtrl_alpha,
                            R.drawable.abc_ab_share_pack_mtrl_alpha, R.drawable.abc_ab_share_pack_mtrl_alpha },
                    { R.drawable.abc_ab_share_pack_mtrl_alpha, R.drawable.abc_ab_share_pack_mtrl_alpha,
                            R.drawable.abc_ab_share_pack_mtrl_alpha, R.drawable.abc_ab_share_pack_mtrl_alpha,
                            R.drawable.abc_ab_share_pack_mtrl_alpha, R.drawable.abc_ab_share_pack_mtrl_alpha },
                    { R.drawable.abc_ab_share_pack_mtrl_alpha, R.drawable.abc_ab_share_pack_mtrl_alpha, R.drawable.abc_ab_share_pack_mtrl_alpha,
                            R.drawable.abc_ab_share_pack_mtrl_alpha, R.drawable.abc_ab_share_pack_mtrl_alpha } };

            //自己定义一个获得文字信息的方法
            TextView getTextView() {
                AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
                        ViewGroup.LayoutParams.FILL_PARENT, 64);
                TextView textView = new TextView(
                        Goods_OrdersActivity.this);
                textView.setLayoutParams(lp);
                textView.setGravity(Gravity.CENTER_VERTICAL);
                textView.setPadding(36, 0, 0, 0);
                textView.setTextSize(20);
                textView.setTextColor(Color.BLACK);
                return textView;
            }


            //重写ExpandableListAdapter中的各个方法
            @Override
            public int getGroupCount() {
                // TODO Auto-generated method stub
                return testlist.size();
            }

            @Override
            public HashMap<String, Object> getGroup(int groupPosition) {
                // TODO Auto-generated method stub
                return testlist.get(groupPosition);
            }

            @Override
            public long getGroupId(int groupPosition) {
                // TODO Auto-generated method stub
                return groupPosition;
            }

            @Override
            public int getChildrenCount(int groupPosition) {
                // TODO Auto-generated method stub
                return generals[groupPosition].length;
            }

            @Override
            public Object getChild(int groupPosition, int childPosition) {
                // TODO Auto-generated method stub
                return generals[groupPosition][childPosition];
            }

            @Override
            public long getChildId(int groupPosition, int childPosition) {
                // TODO Auto-generated method stub
                return childPosition;
            }

            @Override
            public boolean hasStableIds() {
                // TODO Auto-generated method stub
                return true;
            }

            @Override
            public View getGroupView(int groupPosition, boolean isExpanded,
                                     View convertView, ViewGroup parent) {
                final ViewHolder holder;
                if(convertView == null){
                    holder = new ViewHolder();
                    convertView = View.inflate(getApplication(), R.layout.layout_goods_ordergroup, null);
                    holder.tv_ordergroup_jiaoyihao = (TextView)convertView.findViewById(R.id.tv_ordergroup_jiaoyihao);
                    convertView.setTag(holder);
                }else{
                    holder = (ViewHolder) convertView.getTag();
                }
                // TODO Auto-generated method stub

                holder.tv_ordergroup_jiaoyihao.setText(getGroup(groupPosition).get("transaction").toString());
                return convertView;
            }

            @Override
            public View getChildView(int groupPosition, int childPosition,
                                     boolean isLastChild, View convertView, ViewGroup parent) {
                final ViewHolder holder;
                if(convertView == null){
                    holder = new ViewHolder();
                    convertView = View.inflate(getApplication(), R.layout.layout_goods_orderchild, null);
                    convertView.setTag(holder);
                }else{
                    holder = (ViewHolder) convertView.getTag();
                }
                // TODO Auto-generated method stub
                LinearLayout ll = new LinearLayout(
                        Goods_OrdersActivity.this);
//                ll.setOrientation(0);
                ImageView generallogo = new ImageView(
                        Goods_OrdersActivity.this);
                generallogo
                        .setImageResource(generallogos[groupPosition][childPosition]);
                ll.addView(generallogo);
                TextView textView = getTextView();
                textView.setText(getChild(groupPosition, childPosition)
                        .toString());
                ll.addView(textView);
                return ll;
            }

            @Override
            public boolean isChildSelectable(int groupPosition,
                                             int childPosition) {
                // TODO Auto-generated method stub
                return true;
            }

            class ViewHolder{
                // 交易号  手机  地址  状态图片
                TextView tv_ordergroup_jiaoyihao, tv_setTel, tv_setAddress;
                ImageView iv_setImg;
            }

        };

        lv_goods_orders_exlist.setAdapter(adapter);


        //设置item点击的监听器
        lv_goods_orders_exlist.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {

                Toast.makeText(
                        Goods_OrdersActivity.this,
                        "你点击了" + adapter.getChild(groupPosition, childPosition),
                        Toast.LENGTH_SHORT).show();

                return false;
            }
        });
    }
}
