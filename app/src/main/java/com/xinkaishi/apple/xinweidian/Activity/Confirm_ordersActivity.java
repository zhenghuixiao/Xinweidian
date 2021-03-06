package com.xinkaishi.apple.xinweidian.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xinkaishi.apple.xinweidian.Adapter.Adapter_goods_orders_down;
import com.xinkaishi.apple.xinweidian.Bean.Express_Fee.ExpressRules;
import com.xinkaishi.apple.xinweidian.Bean.Express_Fee.ExpressState;
import com.xinkaishi.apple.xinweidian.Bean.Interface;
import com.xinkaishi.apple.xinweidian.Bean.SetOrder.BackdataState;
import com.xinkaishi.apple.xinweidian.DAO.AddressDAO;
import com.xinkaishi.apple.xinweidian.DAO.ImgDAO;
import com.xinkaishi.apple.xinweidian.DAO.ShoppingcartDAO;
import com.xinkaishi.apple.xinweidian.R;
import com.xinkaishi.apple.xinweidian.Until.DataAnalysis;
import com.xinkaishi.apple.xinweidian.Until.Post;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Confirm_ordersActivity extends ActionBarActivity {
    private RelativeLayout rl_confirm_orders_receiving; //收货信息设置
    private LinearLayout ll_confirm_orders_defaultIfm; //无收货信息时的默认页面
    private HashMap<String, Object> receiceIFM;//数据库中的默认收货地址
    private TextView tv_confirm_orders_yunfei, tv_confirm_orders_allprice;//运费  应付总价
    private TextView tv_confirm_orders_submit;//提交订单
    private ListView lv_confirm_orders_list;
    private double yunfei;//运费
    private double allprice;
    private ProgressBar progressBar_express_fee;
    private ProgressBar progressBar_price;
    private ShoppingcartDAO shoppingcartDAO;
    private String sku;

    //姓名，手机，收货地址
    private TextView tv_confirm_orders_name, tv_confirm_orders_tel, tv_confirm_orders_address;

    private BackdataState backdata;
    private ExpressState expressState;
    private AddressDAO addDAO;
    private ImgDAO imgDAO;
    private Gson gson;

    private ArrayList<HashMap<String, Object>> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_orders);

        // 显示导航按钮
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.mipmap.pay_nav_back);//设置返回键图标

        initIntent();
        initView();//初始化控件
        initAdapter();//list适配
        new initFee().execute();
        setonclick();//监听
    }

    private void initIntent() {
        Bundle bundle = this.getIntent().getExtras();
        ArrayList bundlelist = bundle.getParcelableArrayList("list");
        list = new ArrayList<HashMap<String, Object>>();
        list= (ArrayList<HashMap<String, Object>>)bundlelist.get(0);
        Log.e("数据", list.get(0).get("name") + "" + list.size());
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
        imgDAO.closeDB();
        shoppingcartDAO.closeDB();
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        initMain();//收货信息初始化 刷新

        super.onResume();
    }

    private void initView() {
        rl_confirm_orders_receiving = (RelativeLayout)findViewById(R.id.rl_confirm_orders_receiving);
        ll_confirm_orders_defaultIfm = (LinearLayout)findViewById(R.id.ll_confirm_orders_defaultIfm);
        tv_confirm_orders_name = (TextView)findViewById(R.id.tv_confirm_orders_name);
        tv_confirm_orders_tel = (TextView)findViewById(R.id.tv_confirm_orders_tel);
        tv_confirm_orders_address = (TextView)findViewById(R.id.tv_confirm_orders_address);
        tv_confirm_orders_submit = (TextView)findViewById(R.id.tv_confirm_orders_submit);
        tv_confirm_orders_yunfei = (TextView)findViewById(R.id.tv_confirm_orders_yunfei);
        tv_confirm_orders_allprice = (TextView)findViewById(R.id.tv_confirm_orders_allprice);
        lv_confirm_orders_list = (ListView)findViewById(R.id.lv_confirm_orders_list);
        progressBar_express_fee = (ProgressBar)findViewById(R.id.progressBar_express_fee);
        progressBar_price = (ProgressBar)findViewById(R.id.progressBar_price);
        receiceIFM = new HashMap<String, Object>();
        shoppingcartDAO = new ShoppingcartDAO(this);
        addDAO = new AddressDAO(this);
        imgDAO = new ImgDAO(this);
        gson = new Gson();
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

    private void initAdapter() {
        StringBuffer stringBuffer = new StringBuffer();
        allprice = 0;
        Adapter_goods_orders_down adapter = new Adapter_goods_orders_down(Confirm_ordersActivity.this, list,
                R.layout.layout_goods_order_down, imgDAO, addDAO);
        lv_confirm_orders_list.setAdapter(adapter);

        //拼接sku  计算总价
        for(int a = 0; a < list.size(); a ++){
            allprice = allprice + (float)list.get(a).get("import_price") * (Integer)list.get(a).get("num");
            stringBuffer.append(list.get(a).get("sku_id"))
                    .append(":")
                    .append(list.get(a).get("num"))
                    .append(",");

            Log.e("sku_id", list.get(a).get("sku_id") + "");
        }
        stringBuffer.deleteCharAt(stringBuffer.length() - 1);    //删除最后的一个","
        sku = stringBuffer.toString();
        Log.e("sku", sku);

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
                if(receiceIFM.get("name") == null) {
                    //todo 请先设置收货地址
                    return;
                }
                //todo 加个加载标识
                new POST().execute();
            }
        });
    }

    private class POST extends AsyncTask<Void, Void, Integer>{

        @Override
        protected Integer doInBackground(Void... params) {
            String json = null;
            Map<String, String> hm = new HashMap<String, String>();
            //todo  数据获取
            hm.put("consignee", tv_confirm_orders_name.getText().toString());
            hm.put("cellphone", tv_confirm_orders_tel.getText().toString());
            hm.put("address", tv_confirm_orders_address.getText().toString());
            hm.put("area_id", "90909");//地区ID
            hm.put("sku", sku);//
            json = Post.submitPostData(hm, Interface.ORDER_POST);
            Log.e("json", json);

            backdata = gson.fromJson(json, new TypeToken<BackdataState>() {
            }.getType());

            Log.e("error", backdata.getError() + "");
            return backdata.getError();
        }

        @Override
        protected void onPostExecute(Integer error) {
            if(error == 1){
                Log.e("提交订单", "接口返回错误");
                Toast.makeText(Confirm_ordersActivity.this, backdata.getMessage(), Toast.LENGTH_LONG).show();
                return;
            }
//            SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");//获取当前时间 设置日期格式

            if(allprice != backdata.getData().getFee()){
                //todo
                Log.e("总价校对", "服务器返回总价与计算总价---服务器：" + backdata.getData().getFee() + "本地：" + allprice);
            }
                ArrayList bundlelist = new ArrayList();
                bundlelist.add(list);
                Bundle bundle = new Bundle();

                bundle.putParcelableArrayList("list", bundlelist);
                bundle.putString("created_at", backdata.getData().getCreated_at());//时间
                bundle.putString("trade_group_id", backdata.getData().getTrade_group_id());//返回的交易号
                bundle.putString("consignee", tv_confirm_orders_name.getText().toString());//返回的联系人
                bundle.putString("cellphone", tv_confirm_orders_tel.getText().toString());//返回的手机号
                bundle.putFloat("fee", backdata.getData().getFee());//传递的全部价格
                Intent intent = new Intent(Confirm_ordersActivity.this, Payment_Activity.class);
                intent.putExtras(bundle);

                shoppingcartDAO.delete(list);//数据库删除选中的商品
                // 提交成功后两个页面
                Confirm_ordersActivity.this.finish();
                Shopping_cartActivity.instance.finish();
                startActivity(intent);
                overridePendingTransition(R.anim.pic_left_in, R.anim.pic_left_out);
            super.onPostExecute(error);
        }
    }


    /**
     * 计算运费
     *
     */

    class initFee extends AsyncTask<Void, Void, Integer>{

        @Override
        protected Integer doInBackground(Void... params) {
            String json = null;
            try {
                Log.i("运费", "获取运费计算规则");
                json = DataAnalysis.readParse(Interface.EXPRESS_FEE);
                expressState = gson.fromJson(json, new TypeToken<ExpressState>(){}.getType());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return expressState.getError();
        }

        @Override
        protected void onPostExecute(Integer error) {
            if(error == 1){
                Log.e("提交订单", "接口返回错误");
                Toast.makeText(Confirm_ordersActivity.this, expressState.getMessage(), Toast.LENGTH_LONG).show();
                return;
            }
            int minfee = expressState.getData().getMin();//最小运费
            List<ExpressRules> rules = expressState.getData().getRules();
            if(allprice > rules.get(0).getFee_l() & allprice <= rules.get(0).getFee_h()){  //在第一额度之间
                yunfei = allprice * rules.get(0).getPercent() > minfee? allprice * rules.get(0).getPercent(): minfee;
            }else if(allprice <= rules.get(1).getFee_h()){  //在第二额度之间
                yunfei = allprice * rules.get(1).getPercent();
            }else{  //免运费
                yunfei = 0;
            }
            progressBar_express_fee.setVisibility(View.GONE);
            progressBar_price.setVisibility(View.GONE);
            tv_confirm_orders_yunfei.setText("￥" + String.format("%.2f", yunfei));
            allprice = allprice + yunfei;
            tv_confirm_orders_allprice.setText("￥" + String.format("%.2f", allprice));
            super.onPostExecute(error);
        }
    }
}
