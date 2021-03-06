package com.xinkaishi.apple.xinweidian.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xinkaishi.apple.xinweidian.Adapter.Adapter_goods_orders_detail;
import com.xinkaishi.apple.xinweidian.Bean.Interface;
import com.xinkaishi.apple.xinweidian.Bean.Order_Bean.OrderList;
import com.xinkaishi.apple.xinweidian.CustomView.PinnedHeaderExpandableListView;
import com.xinkaishi.apple.xinweidian.CustomView.StickyLayout;
import com.xinkaishi.apple.xinweidian.DAO.ImgDAO;
import com.xinkaishi.apple.xinweidian.R;
import com.xinkaishi.apple.xinweidian.Until.Cache;
import com.xinkaishi.apple.xinweidian.Until.DataAnalysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Order_detailActivity extends ActionBarActivity implements
        PinnedHeaderExpandableListView.OnHeaderUpdateListener,
        StickyLayout.OnGiveUpTouchEventListener {

    private PinnedHeaderExpandableListView lv_order_detail_exlist;
    private StickyLayout stickyLayout;
    private Adapter_goods_orders_detail adapter;
    private OrderList orderList;//详细数据对象
    private int state;//状态
    private ImageView iv_order_detail_state;//状态图片
    private TextView tv_orderchild_continue, tv_orderchild_close, tv_orderchild_other;  // 继续支付  关闭订单  其他单按钮
    private TextView tv_order_detail_state, tv_order_detail_fee, tv_order_detail_express_fee,
                        tv_order_detail_consignee, tv_order_detail_address,
                        tv_order_detail_doneat, tv_order_detail_payat, tv_order_detail_sendat;
    private TextView tv_express_fee, tv_num, tv_fee;
    private LinearLayout rl_orderchild_unpay, rl_orderchild_other;  // 未支付状态ll  其他ll
    private Cache cache;
    private ImgDAO imgDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        // 显示导航按钮
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.mipmap.pay_nav_back);//设置返回键图标

        Intent intent = getIntent();
        orderList = (OrderList)intent.getSerializableExtra("child");
        state = orderList.getState();

        initView(); //加载控件
        initDetail();
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
        OrderList firstVisibleGroup = adapter.getGroup(firstVisibleGroupPos);
        if (headerView != null) {
            TextView textView = (TextView) headerView.findViewById(R.id.tv_ordergroup_jiaoyihao);
            textView.setText(firstVisibleGroup.getTrade_group_id());
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
        tv_order_detail_state = (TextView)findViewById(R.id.tv_order_detail_state);
        tv_order_detail_fee = (TextView)findViewById(R.id.tv_order_detail_fee);
        tv_order_detail_express_fee = (TextView)findViewById(R.id.tv_order_detail_express_fee);
        tv_order_detail_consignee = (TextView)findViewById(R.id.tv_order_detail_consignee);
        tv_order_detail_address = (TextView)findViewById(R.id.tv_order_detail_address);
        tv_order_detail_doneat = (TextView)findViewById(R.id.tv_order_detail_doneat);
        tv_order_detail_payat = (TextView)findViewById(R.id.tv_order_detail_payat);
        tv_order_detail_sendat = (TextView)findViewById(R.id.tv_order_detail_sendat);
        iv_order_detail_state = (ImageView)findViewById(R.id.iv_order_detail_state);
        tv_express_fee = (TextView)findViewById(R.id.tv_express_fee);
        tv_num = (TextView)findViewById(R.id.tv_num);
        tv_fee = (TextView)findViewById(R.id.tv_fee);

        cache = new Cache();
        imgDAO = new ImgDAO(this);
    }

    private void initDetail() {
        if(state == 0){
            rl_orderchild_unpay.setVisibility(View.VISIBLE); //未支付ll  其他都为other
            tv_orderchild_close.setOnClickListener(new MyOnclick(3)); //关闭订单
            tv_orderchild_continue.setOnClickListener(new MyOnclick(4)); //继续支付
            iv_order_detail_state.setBackgroundResource(R.mipmap.ord_war);
        }else {
            rl_orderchild_other.setVisibility(View.VISIBLE);
            switch (state) {
                //    0 => 订单创建/未付款   1 => 已付款/待备货   2 => 已发货   3 => 已收货/确认收货
                //    4 => 已完成   5 => 订单已取消    6 => 部分发货    7 => 订单已退
                case 1:
                    tv_orderchild_other.setText("提醒发货");
                    tv_orderchild_other.setBackgroundResource(R.drawable.textview_biankuang_maincolor);
                    tv_orderchild_other.setOnClickListener(new MyOnclick(1));
                    iv_order_detail_state.setBackgroundResource(R.mipmap.ord_mon);
                    break;
                case 2:
                    tv_orderchild_other.setText("返回");
                    tv_orderchild_other.setBackgroundResource(R.drawable.textview_biankuang_maincolor);
                    tv_orderchild_other.setOnClickListener(new MyOnclick(0));
                    iv_order_detail_state.setBackgroundResource(R.mipmap.ord_wai);
                    break;
                case 3:
                    tv_orderchild_other.setText("返回");
                    tv_orderchild_other.setBackgroundResource(R.drawable.textview_biankuang_maincolor);
                    tv_orderchild_other.setOnClickListener(new MyOnclick(0));
                    iv_order_detail_state.setBackgroundResource(R.mipmap.ord_wai);
                    break;
                case 4:
                    tv_orderchild_other.setText("返回");
                    tv_orderchild_other.setBackgroundResource(R.drawable.textview_biankuang_maincolor);
                    tv_orderchild_other.setOnClickListener(new MyOnclick(0));
                    iv_order_detail_state.setBackgroundResource(R.mipmap.ord_com);
                    break;
                case 5:
                    tv_orderchild_other.setText("返回");
                    tv_orderchild_other.setBackgroundResource(R.drawable.textview_biankuang_backdack);
                    tv_orderchild_other.setOnClickListener(new MyOnclick(0));
                    iv_order_detail_state.setBackgroundResource(R.mipmap.ord_war);
                    break;
                case 6:
                    tv_orderchild_other.setText("返回");
                    tv_orderchild_other.setBackgroundResource(R.drawable.textview_biankuang_maincolor);
                    tv_orderchild_other.setOnClickListener(new MyOnclick(0));
                    iv_order_detail_state.setBackgroundResource(R.mipmap.ord_wai);
                    break;
                case 7:
                    tv_orderchild_other.setText("返回");
                    tv_orderchild_other.setBackgroundResource(R.drawable.textview_biankuang_backdack);
                    tv_orderchild_other.setOnClickListener(new MyOnclick(0));

                    break;
            }
        }

        tv_order_detail_state.setText(orderList.getState_text());
        tv_order_detail_fee.setText("￥" + String.format("%.2f", orderList.getFee()));
        tv_order_detail_express_fee.setText("(含运费:￥" + String.format("%.2f", orderList.getExpress_fee()) + ")");
        tv_order_detail_consignee.setText(orderList.getConsignee());
        tv_order_detail_address.setText(orderList.getAddress());
        tv_order_detail_doneat.setText(orderList.getDone_at());
        tv_order_detail_payat.setText(orderList.getPay_at());
        tv_order_detail_sendat.setText(orderList.getSend_at());
        tv_express_fee.setText("￥" + String.format("%.2f", orderList.getExpress_fee()));
        tv_fee.setText("￥" + String.format("%.2f", orderList.getFee()));
        tv_num.setText("共" + orderList.getTrade().size() + "件商品，总价(含运费)");
    }
    class MyOnclick implements View.OnClickListener{
        private int a;
        public MyOnclick(int a){
            this.a = a;
        }

        @Override
        public void onClick(View v) {
            switch (a){
                case 0:
                    finish();
                    overridePendingTransition(R.anim.fade_out_anim, R.anim.fade_in_anim);
                    break;
                case 1:
                    Toast t = Toast.makeText(Order_detailActivity.this, "", Toast.LENGTH_SHORT);
                    t.setGravity(Gravity.CENTER, 0, 0);
                    View layout = LayoutInflater.from(Order_detailActivity.this).inflate(R.layout.layout_toast_style_remind, null);
                    t.setView(layout);
                    t.show();
                    break;
                case 2:

                    break;
                case 3:
                    String url = Interface.ORDER_COLSE + "?trade_group_id=" + orderList.getTrade_group_id() + "&reason=" + "测试，不要了";
                    new statePost(url, orderList).execute();
                    break;
                case 4:
                    ArrayList bundlelist = new ArrayList();
                    List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
                    for(int a = 0; a < orderList.getTrade().size(); a ++){
                        HashMap<String, Object> hm = new HashMap<String, Object>();
                        hm.put("name", orderList.getTrade().get(a).getTitle());
                        hm.put("num", orderList.getTrade().get(a).getNum());
                        list.add(hm);
                    }
                    bundlelist.add(list);
                    Bundle bundle = new Bundle();

                    bundle.putParcelableArrayList("list", bundlelist);
                    bundle.putString("created_at", orderList.getCreated_at());//时间
                    bundle.putString("trade_group_id", orderList.getTrade_group_id());//返回的交易号
                    bundle.putString("consignee", orderList.getConsignee());//返回的联系人
                    bundle.putString("cellphone", orderList.getCellphone());//返回的手机号
                    bundle.putFloat("fee", orderList.getFee());//传递的全部价格
                    Intent intent = new Intent(Order_detailActivity.this, Payment_Activity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    break;
            }
        }
    }
    private void initAdapter() {
        adapter = new Adapter_goods_orders_detail(Order_detailActivity.this, orderList, cache, imgDAO);
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

    /**
     * 关闭订单接口
     */

    class statePost extends AsyncTask<Void, Void, String> {
        private String url;
        private OrderList orderList;

        public statePost(String url, OrderList orderList){
            this.url = url;
            this.orderList = orderList;
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                DataAnalysis.readParse(url);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "";
        }

        @Override
        protected void onPostExecute(String s) {
            rl_orderchild_unpay.setVisibility(View.GONE);
            tv_orderchild_other.setVisibility(View.VISIBLE);
            tv_orderchild_other.setBackgroundResource(R.drawable.textview_biankuang_backdack);
            tv_orderchild_other.setText("查看详情");
//            holder.tv_orderchild_statetext.setText("已取消");
            orderList.setState(5);
            Log.e("改变状态", "成功");
            super.onPostExecute(s);
        }
    }
}
