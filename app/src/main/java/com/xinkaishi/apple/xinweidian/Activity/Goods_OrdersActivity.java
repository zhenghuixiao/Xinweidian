package com.xinkaishi.apple.xinweidian.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xinkaishi.apple.xinweidian.Adapter.Adapter_goods_orders;
import com.xinkaishi.apple.xinweidian.Bean.Interface;
import com.xinkaishi.apple.xinweidian.Bean.Order_Bean.OrderList;
import com.xinkaishi.apple.xinweidian.Bean.Order_Bean.OrderState;
import com.xinkaishi.apple.xinweidian.DAO.ImgDAO;
import com.xinkaishi.apple.xinweidian.R;
import com.xinkaishi.apple.xinweidian.Until.Cache;
import com.xinkaishi.apple.xinweidian.Until.DataAnalysis;

import java.util.List;

public class Goods_OrdersActivity extends ActionBarActivity{
    private RadioButton rb_goods_orders_all, rb_goods_orders_unpay, rb_goods_orders_untake, rb_goods_orders_unget;
    private RadioGroup rg_goods_orders_head;
    private ExpandableListView lv_goods_orders_exlist;
    private Adapter_goods_orders adapter;
    private List<OrderList> orderList;//列表数据
    private Gson gson;
    private OrderState orderState;
    private int maxpage;//列表最大页数
    private Cache cache;//内存缓存
    private ImgDAO imgDAO;//数据库缓存

    private String finalurl; //最终读取地址
    private View footer; ;//list页脚
    private Handler handler;//翻页
    private int number = 20; // 每次获取多少条数据
    private boolean loadfinish = true; // 指示数据是否加载完成


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_orders_);

        initActionBar();
        initView();
        new initData(Interface.ORDER_LIST).execute();//获取数据
        initSetOnclick();
//        initAdapter();
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
        actionBar.setHomeAsUpIndicator(R.mipmap.pay_nav_back);//设置返回键图标
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

    @Override
    protected void onDestroy() {
        imgDAO.closeDB();
        super.onDestroy();
    }

    private void initView() {
        rg_goods_orders_head = (RadioGroup)findViewById(R.id.rg_goods_orders_head);
        rb_goods_orders_all = (RadioButton)findViewById(R.id.rb_goods_orders_all);
        rb_goods_orders_unpay = (RadioButton)findViewById(R.id.rb_goods_orders_unpay);
        rb_goods_orders_untake = (RadioButton)findViewById(R.id.rb_goods_orders_untake);
        rb_goods_orders_unget = (RadioButton)findViewById(R.id.rb_goods_orders_unget);
        lv_goods_orders_exlist = (ExpandableListView)findViewById(R.id.lv_goods_orders_exlist);

        footer = getLayoutInflater().inflate(R.layout.layout_goods_center_listfoot, null); //list页脚
        gson = new Gson();
        orderState = new OrderState();
        finalurl = Interface.ORDER_LIST + "?state=0";//默认地址为全部订单
        handler = new MyHandler();

        cache = new Cache();
        imgDAO = new ImgDAO(this);
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
            orderList = orderState.getData().getList(); //订单列表数据集
            maxpage = orderState.getData().getCount() / 20 + 1;//最大 页数
            maxpage = orderState.getData().getCount() % number == 0 ?
                    orderState.getData().getCount() / number
                    : orderState.getData().getCount() / number + 1;
            Log.e("读取数据", "一共" + orderState.getData().getCount() + "件商品 " + "共" + maxpage + "页");
//            for(int a = 0; a < listorder.size(); a ++){
//                HashMap<String, Object> hm = new HashMap<String, Object>();
//                hm.put("trade_group_id", listorder.get(a).getTrade_group_id());     //交易号
//                hm.put("consignee", listorder.get(a).getConsignee());               //收货人
//                hm.put("address", listorder.get(a).getAddress() + "!i");            //收货地址
//                hm.put("fee", listorder.get(a).getFee());                           //总价
//                hm.put("express_fee", listorder.get(a).getExpress_fee());           //邮费
//                hm.put("source", listorder.get(a).getSource());                     //来源编号
//                hm.put("source_text", listorder.get(a).getSource_text());           //来源text
//                hm.put("state", listorder.get(a).getState());                       //交易单号状态
//                hm.put("state_text", listorder.get(a).getState_text());             //状态文字
//                hm.put("created_at", listorder.get(a).getCreated_at());             //创建时间
//                hm.put("pay_at", listorder.get(a).getPay_at());                     //付款时间
//                hm.put("send_at", listorder.get(a).getSend_at());                   //发货时间
//                hm.put("recv_at", listorder.get(a).getRecv_at());                   //收货时间
//                hm.put("done_at", listorder.get(a).getDone_at());                   //下单时间
//                hm.put("trade", listorder.get(a).getTrade());                       //交易号内详情
//                orderlist.add(hm);
//            }
            Log.e("orderlist", "数据转换成功");
            initAdapter();
            super.onPostExecute(error);
        }
    }

//    0 => 订单创建/未付款
//    1 => 已付款/待备货
//    2 => 已发货
//    3 => 已收货/确认收货
//    4 => 已完成
//    5 => 订单已取消
//    6 => 部分发货
//    7 => 订单已退
    private void initSetOnclick() {
        rg_goods_orders_head.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_goods_orders_all:
                        //todo 刷新列表
                        finalurl = Interface.ORDER_LIST + "?state=0";
                        new initData(finalurl).execute();
                        break;
                    case R.id.rb_goods_orders_unpay:
                        finalurl = Interface.ORDER_LIST + "?state=1";
                        new initData(finalurl).execute();
                        break;
                    case R.id.rb_goods_orders_untake:
                        finalurl = Interface.ORDER_LIST + "?state=2";
                        new initData(finalurl).execute();
                        break;
                    case R.id.rb_goods_orders_unget:
                        finalurl = Interface.ORDER_LIST + "?state=3";
                        new initData(finalurl).execute();
                        break;
                }
            }
        });
    }

    private void initAdapter() {
        adapter = new Adapter_goods_orders(Goods_OrdersActivity.this, orderList, cache, imgDAO);
        lv_goods_orders_exlist.addFooterView(footer);// 添加页脚（放在ListView最后）
        lv_goods_orders_exlist.setAdapter(adapter);
        lv_goods_orders_exlist.removeFooterView(footer);
        lv_goods_orders_exlist.setOnScrollListener(new MyScrollListener());
        // 默认展开全部项
        for(int a = 0; a < orderList.size(); a ++){
            lv_goods_orders_exlist.expandGroup(a);
        }
    }

    /**
     * 底部自动加载监听
     *
     */

    private final class MyScrollListener implements ExpandableListView.OnScrollListener {

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem,
                             int visibleItemCount, int totalItemCount) {
            Log.i("list", "onScroll(firstVisibleItem="
                    + firstVisibleItem + ",visibleItemCount="
                    + visibleItemCount + ",totalItemCount=" + totalItemCount
                    + ")");

            final int loadtotal = totalItemCount;
            int lastItemid = lv_goods_orders_exlist.getLastVisiblePosition(); // 获取当前屏幕最后Item的ID
            Log.e("lastItemid", lastItemid + "");
            if ((lastItemid + 1) == totalItemCount) { // 达到数据的最后一条记录
                if (totalItemCount > 0) {
                    // 当前页
                    int currentpage = totalItemCount % number == 0 ?
                            (totalItemCount/2)/ number
                            : (totalItemCount/2) / number + 1;
                    final int nextpage = currentpage + 1; // 下一页
                    if (nextpage <= maxpage & loadfinish) {
                        Log.e("FooterView", "新增页脚");
                        loadfinish = false;
                        lv_goods_orders_exlist.addFooterView(footer);

                        // 开一个线程加载数据
                        new Thread(new Runnable() {

                            @Override
                            public void run() {
                                List<OrderList> orderList1 = null;
                                try {
                                    Log.e("加载", "开始加载...............................");

                                    //地址
                                    String json = DataAnalysis.readParse(finalurl + "&page=" + nextpage);//
                                    Log.e("page", "这里是第" + nextpage + "页");
                                    Log.e("json", json);
                                    OrderState orderState1 = gson.fromJson(json, new TypeToken<OrderState>() {
                                    }.getType());
                                    orderList1 = orderState1.getData().getList();
                                    Log.e("加载", "加载完成...............................");
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                // 发送消息
                                Message msg = Message.obtain();
                                msg.obj = orderList1;
                                handler.sendMessage(msg);
                            }
                        }).start();
                    }
                }
            }

        }

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            Log.i("OrderActivity", "onScrollStateChanged(scrollState="
                    + scrollState + ")");
            switch (scrollState) {

                case AbsListView.OnScrollListener.SCROLL_STATE_FLING://处于滚动状态

                    break;

                case AbsListView.OnScrollListener.SCROLL_STATE_IDLE://静止

                    break;

                case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL://手指拖动
                    break;

                default:

                    break;

            }
        }

    }

    class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            orderList.addAll((List<OrderList>) msg.obj);
            // 告诉ListView数据已经发生改变，要求ListView更新界面显示
            adapter.notifyDataSetChanged();
            // 默认展开全部项
            for(int a = 0; a < orderList.size(); a ++){
                lv_goods_orders_exlist.expandGroup(a);
            }
            if (lv_goods_orders_exlist.getFooterViewsCount() > 0) { // 如果有底部视图
                lv_goods_orders_exlist.removeFooterView(footer);
            }
            loadfinish = true; // 加载完成
        }
    }
}
