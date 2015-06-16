package com.xinkaishi.apple.xinweidian.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.xinkaishi.apple.xinweidian.Adapter.Adapter_shopping_list;
import com.xinkaishi.apple.xinweidian.Bean.ViewHolder;
import com.xinkaishi.apple.xinweidian.DAO.ImgDAO;
import com.xinkaishi.apple.xinweidian.DAO.ShoppingcartDAO;
import com.xinkaishi.apple.xinweidian.R;

import java.util.ArrayList;
import java.util.HashMap;


public class Shopping_cartActivity extends ActionBarActivity {
    private String id;//商品ID
    private ListView lv_shopping_cart_list;
    private ArrayList<HashMap<String, Object>> list;
    // 数据库操作
    private ImgDAO imgdao;
    private ShoppingcartDAO shoppingcartDAO;

    private TextView tv_shopping_cart_pay;
    private int checkNum; // 记录选中的条目数量
    private TextView tv_shopping_cart_showNum;// 用于显示选中的条目数量
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);

        // 显示导航按钮
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        initView();

        //从数据库中查找购物车商品信息
        list = shoppingcartDAO.getShoppingcart();

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
        shoppingcartDAO.closeDB();
        super.onDestroy();
    }

    private void initView() {
        lv_shopping_cart_list = (ListView)findViewById(R.id.lv_shopping_cart_list);
        tv_shopping_cart_pay = (TextView)findViewById(R.id.tv_shopping_cart_pay);
        tv_shopping_cart_showNum = (TextView)findViewById(R.id.tv_shopping_cart_showNum);
        shoppingcartDAO = new ShoppingcartDAO(this);
        list = new ArrayList<HashMap<String, Object>>();
        imgdao = new ImgDAO(getApplication());
    }

    private void setListView() {
        Adapter_shopping_list adapter = new Adapter_shopping_list(Shopping_cartActivity.this, list, R.layout.layout_shopping_cart,
                new int[]{R.id.cb_shoppingcart_status, R.id.iv_shoppingcart_image, R.id.tv_goodscenter_title,
                        R.id.tv_shoppingcart_format, R.id.tv_shoppingcart_inprice, R.id.tv_shoppingcart_picknum,
                        R.id.tv_shoppingcart_shownum, R.id.tv_shoppingcart_allprice}, imgdao);
        lv_shopping_cart_list.setAdapter(adapter);

        lv_shopping_cart_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 取得ViewHolder对象，这样就省去了通过层层的findViewById去实例化我们需要的cb实例的步骤
                ViewHolder holder = (ViewHolder) view.getTag();
                // 改变CheckBox的状态
                holder.cb_shoppingcart_status.toggle();
                // 将CheckBox的选中状况记录下来
                Adapter_shopping_list.getIsSelected().put(position, holder.cb_shoppingcart_status.isChecked());
                // 调整选定条目
                if (holder.cb_shoppingcart_status.isChecked() == true) {
                    checkNum++;
                } else {
                    checkNum--;
                }
                // 用TextView显示
                tv_shopping_cart_showNum.setText("当前结算商品"+checkNum+"件");

            }
        });
    }
    private void setonclick() {
        tv_shopping_cart_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Shopping_cartActivity.this, Confirm_ordersActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.pic_left_in, R.anim.pic_left_out);
            }
        });
    }
}
