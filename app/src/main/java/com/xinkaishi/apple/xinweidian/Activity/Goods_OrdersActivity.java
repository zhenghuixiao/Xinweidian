package com.xinkaishi.apple.xinweidian.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xinkaishi.apple.xinweidian.Adapter.Adapter_goods_orders;
import com.xinkaishi.apple.xinweidian.Bean.Order_Bean.OrderList;
import com.xinkaishi.apple.xinweidian.Bean.Order_Bean.OrderState;
import com.xinkaishi.apple.xinweidian.R;
import com.xinkaishi.apple.xinweidian.Until.DataAnalysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Goods_OrdersActivity extends ActionBarActivity{
    private RadioButton rb_goods_orders_all, rb_goods_orders_unpay, rb_goods_orders_untake, rb_goods_orders_unget;
    private RadioGroup rg_goods_orders_head;
    private ExpandableListView lv_goods_orders_exlist;
    private Adapter_goods_orders adapter;
    private ArrayList<HashMap<String, Object>> orderlist;
    private Gson gson;
    private OrderState orderState;
    private int maxpage;//列表最大页数

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_orders_);

        initActionBar();
        initView();
        new initData("http://pc.xinkaishi.com/shop/trade/trade-list").execute();//获取数据
        initSetOnclick();
//        initAdapter();
    }

    /**
     *
     * 加载订单数据
     */
    private class initData extends AsyncTask<Void, Void, Integer> {
        private  String url;
        public initData(String url){
            this.url = url;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            String json = null;
            try {

                json = DataAnalysis.readParse(url);
                Log.e("orderlist", json);

                orderState = gson.fromJson(json, new TypeToken<OrderState>() {}.getType());
                Log.e("error", orderState.getError() + "");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return orderState.getError();
        }

        @Override
        protected void onPostExecute(Integer error) {
            if(error == 1){
                Log.e("orderlist", "接口返回错误");
                return;
            }
            List<OrderList> listorder = orderState.getData().getList(); //订单列表数据集
            maxpage = orderState.getData().getCount() / 20 + 1;//最大 页数
            Log.e("读取数据", "一共" + orderState.getData().getCount() + "件商品 " + "共" + maxpage + "页");
            for(int a = 0; a < listorder.size(); a ++){
                HashMap<String, Object> hm = new HashMap<String, Object>();
                hm.put("trade_group_id", listorder.get(a).getTrade_group_id());     //交易号
                hm.put("consignee", listorder.get(a).getConsignee());               //收货人
                hm.put("address", listorder.get(a).getAddress() + "!i");            //收货地址
                hm.put("fee", listorder.get(a).getFee());                           //总价
                hm.put("express_fee", listorder.get(a).getExpress_fee());           //邮费
                hm.put("source", listorder.get(a).getSource());                     //来源编号
                hm.put("source_text", listorder.get(a).getSource_text());           //来源text
                hm.put("state", listorder.get(a).getState());                       //交易单号状态
                hm.put("state_text", listorder.get(a).getState_text());             //状态文字
                hm.put("created_at", listorder.get(a).getCreated_at());             //创建时间
                hm.put("pay_at", listorder.get(a).getPay_at());                     //付款时间
                hm.put("send_at", listorder.get(a).getSend_at());                   //发货时间
                hm.put("recv_at", listorder.get(a).getRecv_at());                   //收货时间
                hm.put("done_at", listorder.get(a).getDone_at());                   //下单时间
                hm.put("trade", listorder.get(a).getTrade());                       //交易号内详情
                orderlist.add(hm);
            }
            Log.e("orderlist", "数据转换成功");
            initAdapter(listorder);
            super.onPostExecute(error);
        }
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

        gson = new Gson();
        orderState = new OrderState();
        orderlist = new ArrayList<HashMap<String, Object>>();
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

    private void initAdapter(List<OrderList> orderLists) {
        adapter = new Adapter_goods_orders(Goods_OrdersActivity.this, orderLists);
        lv_goods_orders_exlist.setAdapter(adapter);
        // 默认展开全部项
        for(int a = 0; a < orderlist.size(); a ++){
            lv_goods_orders_exlist.expandGroup(a);
        }
    }
}
