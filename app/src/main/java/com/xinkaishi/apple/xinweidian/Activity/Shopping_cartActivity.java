package com.xinkaishi.apple.xinweidian.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.xinkaishi.apple.xinweidian.Adapter.Adapter_shopping_list;
import com.xinkaishi.apple.xinweidian.Bean.Shoppingcart_totalprice;
import com.xinkaishi.apple.xinweidian.Bean.ViewHolder;
import com.xinkaishi.apple.xinweidian.DAO.ImgDAO;
import com.xinkaishi.apple.xinweidian.DAO.ShoppingcartDAO;
import com.xinkaishi.apple.xinweidian.R;

import java.util.ArrayList;
import java.util.HashMap;


public class Shopping_cartActivity extends ActionBarActivity {
    public static Shopping_cartActivity instance = null;
    private String id;//商品ID
    private ListView lv_shopping_cart_list;
    private ArrayList<HashMap<String, Object>> list;
    private ImageView iv_shopping_cart_del; //垃圾桶
    // 数据库操作
    private ImgDAO imgdao;
    private ShoppingcartDAO shoppingcartDAO;

    //list 适配
    private TextView tv_shopping_cart_pay;
    private Adapter_shopping_list adapter;
    private int checkNum; // 记录选中的条目数量
    private TextView tv_shopping_cart_showNum;// 用于显示选中的条目数量
    private TextView tv_shopping_cart_totalPrice;// 需支付总价
    private Button bt_shopping_selectall;
    private float totalPrice;//全部商品价格
    private Shoppingcart_totalprice shoppingcart_totalprice;//全部商品价格对象

    private boolean buttonAll;//全选按钮状态，true为全选
    private float buttonAll_totalPrice;//全部商品价格

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);

        instance = this;// activity 便于提交订单后关闭
        // 显示导航按钮
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        initView();

        //从数据库中查找购物车商品信息
        list = shoppingcartDAO.getShoppingcart();

        initNum();
        setListView();
        setonclick();//去结算
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_shopping_cart, menu);
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
        imgdao.closeDB();
        shoppingcartDAO.closeDB();
        super.onDestroy();
    }

    private void initView() {
        lv_shopping_cart_list = (ListView)findViewById(R.id.lv_shopping_cart_list);
        tv_shopping_cart_pay = (TextView)findViewById(R.id.tv_shopping_cart_pay);
        iv_shopping_cart_del = (ImageView)findViewById(R.id.iv_shopping_cart_del);
        tv_shopping_cart_showNum = (TextView)findViewById(R.id.tv_shopping_cart_showNum);
        tv_shopping_cart_totalPrice = (TextView)findViewById(R.id.tv_shopping_cart_totalPrice);
        bt_shopping_selectall = (Button)findViewById(R.id.bt_shopping_selectall);
        shoppingcart_totalprice = new Shoppingcart_totalprice();
        shoppingcartDAO = new ShoppingcartDAO(this);
        list = new ArrayList<HashMap<String, Object>>();
        imgdao = new ImgDAO(getApplication());
        buttonAll = true;

    }

    private void setListView() {
        adapter = new Adapter_shopping_list(Shopping_cartActivity.this, list, R.layout.layout_shopping_cart,
                new int[]{R.id.cb_shoppingcart_status, R.id.iv_shoppingcart_image, R.id.tv_goodscenter_title,
                        R.id.tv_shoppingcart_format, R.id.tv_shoppingcart_inprice, R.id.tv_shoppingcart_picknum,
                        R.id.tv_shoppingcart_shownum, R.id.tv_shoppingcart_allprice,
                        R.id.tv_shoppingcart_min, R.id.tv_shoppingcart_add}, tv_shopping_cart_totalPrice,
                         shoppingcart_totalprice, imgdao, shoppingcartDAO);
        lv_shopping_cart_list.setAdapter(adapter);

        lv_shopping_cart_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 取得ViewHolder对象，省去了通过层层的findViewById去实例化cb实例的步骤
                ViewHolder holder = (ViewHolder) view.getTag();
                // 改变CheckBox的状态
                holder.cb_shoppingcart_status.toggle();
                // 将CheckBox的选中状况记录下来
                Adapter_shopping_list.getIsSelected().put(position, holder.cb_shoppingcart_status.isChecked());
                // 调整选定条目
                if (holder.cb_shoppingcart_status.isChecked() == true) {
                    checkNum++;
                    // 选中的state状态改为1 作为数据库删除依据
                    list.get(position).put("state", 1);
                    shoppingcartDAO.update(list.get(position));
                    totalPrice = shoppingcart_totalprice.getTotalprice() +
                            (float) list.get(position).get("import_price") * (Integer) list.get(position).get("num");
                    shoppingcart_totalprice.setTotalprice(totalPrice);
                } else {
                    checkNum--;
                    // 未选中的state状态改为0
                    list.get(position).put("state", 0);
                    shoppingcartDAO.update(list.get(position));
                    totalPrice = shoppingcart_totalprice.getTotalprice() -
                            (float) list.get(position).get("import_price") * (Integer) list.get(position).get("num");
                    shoppingcart_totalprice.setTotalprice(totalPrice);

                }
                if(checkNum == list.size()){
                    buttonAll = true;
                    bt_shopping_selectall.setBackgroundResource(R.drawable.col_btn_sel);
                }else{
                    buttonAll = false;
                    bt_shopping_selectall.setBackgroundResource(R.drawable.col_btn_rec);
                }
                // 用TextView显示
                tv_shopping_cart_showNum.setText("当前结算商品" + checkNum + "件");
                tv_shopping_cart_totalPrice.setText("￥" + String.format("%.2f", totalPrice));

            }
        });
    }

    private void initNum() {
        checkNum = list.size();
        totalPrice = 0;
        for(int a = 0; a < list.size(); a ++){
            list.get(a).put("state", 1);
            totalPrice = totalPrice + (float)list.get(a).get("import_price")*(Integer) list.get(a).get("num");
            buttonAll_totalPrice = totalPrice;
        }

        tv_shopping_cart_showNum.setText("当前结算商品"+ checkNum +"件");
        //放入对象
        shoppingcart_totalprice.setTotalprice(totalPrice);
        //显示全部商品总价
        tv_shopping_cart_totalPrice.setText("￥" + String.format("%.2f", totalPrice));
    }

    private void setonclick() {
        tv_shopping_cart_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<HashMap<String, Object>> listchecked = new ArrayList<HashMap<String, Object>>();
                listchecked.clear();
                //传递list 外加一层
                ArrayList bundlelist = new ArrayList();
                for(int a = 0; a < list.size(); a ++){
                    HashMap<String, Object> hm = list.get(a);
                    if((Integer)hm.get("state") == 1){
                        listchecked.add(hm);
                    }
                }
                if(listchecked.size() == 0){
                    //todo 提示未选中商品
                    return;
                }
                bundlelist.add(listchecked);
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("list", bundlelist);
                Intent intent = new Intent();
                intent.putExtras(bundle);
                intent.setClass(Shopping_cartActivity.this, Confirm_ordersActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.pic_left_in, R.anim.pic_left_out);
            }
        });

        // 全选按钮的回调接口
        bt_shopping_selectall.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(buttonAll){
                    buttonAll = false;
                    // 遍历list的长度，将Adapter中的state值全部设为true
                    for (int i = 0; i < list.size(); i++) {
                        list.get(i).put("state", 0);//改变全部商品的状态
                        Adapter_shopping_list.getIsSelected().put(i, false);
                    }
                    checkNum = 0;
                    totalPrice = 0;
                    shoppingcart_totalprice.setTotalprice(totalPrice);
                    // 通知listView刷新
                    adapter.notifyDataSetChanged();
                    // TextView显示最新的选中数目
                    tv_shopping_cart_showNum.setText("当前结算商品"+checkNum+"件");
                    tv_shopping_cart_totalPrice.setText("￥" + String.format("%.2f", totalPrice));
                    bt_shopping_selectall.setBackgroundResource(R.drawable.col_btn_rec);
                }else{
                    buttonAll = true;
                    totalPrice = 0;
                    // 遍历list的长度，将Adapter中的state值全部设为true
                    for (int i = 0; i < list.size(); i++) {
                        list.get(i).put("state", 1);//改变全部商品的状态
                        Adapter_shopping_list.getIsSelected().put(i, true);
                        totalPrice = totalPrice + (float)list.get(i).get("import_price")*(Integer) list.get(i).get("num");
                    }
                    //全选设置总价
                    shoppingcart_totalprice.setTotalprice(totalPrice);
                    // 数量设为list的长度
                    checkNum = list.size();
                    // 通知listView刷新
                    adapter.notifyDataSetChanged();
                    // TextView显示最新的选中数目
                    tv_shopping_cart_showNum.setText("当前结算商品"+checkNum+"件");
                    tv_shopping_cart_totalPrice.setText("￥" + String.format("%.2f", totalPrice));
                    bt_shopping_selectall.setBackgroundResource(R.drawable.col_btn_sel);
                }
            }
        });

        //删除购物车商品按钮
        iv_shopping_cart_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean bl = false;//标记是否有选中商品
                //删除操作
                if(list.size() == 0){
                    return;
                }
                for(int a = 0; a < list.size(); a ++){
                    if((Integer)list.get(a).get("state") == 1){
                        bl = true;
                        checkNum --;
                        totalPrice = totalPrice - (float) list.get(a).get("import_price") * (Integer) list.get(a).get("num");
                    }
                    // 将CheckBox的选中状况全部设为未选中
                    Adapter_shopping_list.getIsSelected().put(a, false);
                }
                if(!bl){
                    //todo 请选中商品
                    return;
                }
                shoppingcartDAO.delete(list);
                list.clear();//清空数据 重新获取 否则无法刷新
                list.addAll(shoppingcartDAO.getShoppingcart());
                adapter.notifyDataSetChanged();
                //更新显示  剩下的都为未选中，则都为0
                checkNum = 0;
                totalPrice = 0;
                shoppingcart_totalprice.setTotalprice(totalPrice);
                tv_shopping_cart_showNum.setText("当前结算商品" + checkNum + "件");
                tv_shopping_cart_totalPrice.setText("￥" + String.format("%.2f", totalPrice));
                Log.e("购物车删除", "成功删除商品");
            }
        });
    }
}
