package com.xinkaishi.apple.xinweidian.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.xinkaishi.apple.xinweidian.Adapter.Adapter_set_address;
import com.xinkaishi.apple.xinweidian.R;

import java.util.ArrayList;
import java.util.HashMap;

public class SetReceiveIfmActivity extends ActionBarActivity {
    private ListView lv_receivelist;
    private ArrayList<HashMap<String, Object>> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_receive_ifm);

        // 显示导航按钮
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        lv_receivelist = (ListView)findViewById(R.id.lv_receivelist);
        getlist();

        Adapter_set_address adapter = new Adapter_set_address(getApplication(), list, R.layout.layout_setadd,
                new Object[]{"name", "tel", "address", "state"},
                new int[]{R.id.tv_setAdd_name, R.id.tv_setAdd_tel, R.id.tv_setAdd_add, R.id.iv_setAdd_img});
        lv_receivelist.setAdapter(adapter);
    }

    private void getlist() {
        list = new ArrayList<HashMap<String, Object>>();
        for(int a = 0; a < 6; a ++){
            HashMap<String, Object> hm = new HashMap<String, Object>();
            if(a == 5){
                //这里生成新建收货地址栏
                hm.put("tel", "新建收货地址");
                hm.put("name", "");
                hm.put("address", "");
                hm.put("state", 0);//状态默认为0，选中状态的为1
                list.add(hm);
            }else {
                hm.put("name", "老司机");
                hm.put("tel", "13333333333");
                hm.put("address", "浙江省杭州市西湖区天堂软件园");
                hm.put("state", 0);//状态默认为0，选中状态的为1
                list.add(hm);
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_set_receive_ifm, menu);
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


}
