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
import android.widget.ListView;

import com.xinkaishi.apple.xinweidian.Adapter.Adapter_set_address;
import com.xinkaishi.apple.xinweidian.DAO.AddressDAO;
import com.xinkaishi.apple.xinweidian.R;

import java.util.ArrayList;
import java.util.HashMap;

public class SetReceiveIfmActivity extends ActionBarActivity {
    private ListView lv_receivelist;
    private ArrayList<HashMap<String, Object>> list;
    private AddressDAO addDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_receive_ifm);

        // 显示导航按钮
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        initView();//初始化控件

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

    @Override
    protected void onDestroy() {
        addDAO.closeDB();
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        getlist();//获取数据

        setAdapter();//列表适配
        super.onResume();
    }

    private void initView() {
        addDAO = new AddressDAO(this);
        lv_receivelist = (ListView)findViewById(R.id.lv_receivelist);
    }

    private void getlist() {
        list = new ArrayList<HashMap<String, Object>>();
        //从数据库取出全部地址信息
        list = addDAO.getAllAddress();

        HashMap<String, Object> hm = new HashMap<String, Object>();
        //这里生成新建收货地址栏
        hm.put("tel", "新建收货地址");
        hm.put("name", "");
        hm.put("address", "");
        hm.put("state", 0);//状态默认为0，选中状态的为1
        list.add(hm);
    }

    private void setAdapter() {
        final Adapter_set_address adapter = new Adapter_set_address(getApplication(), list, R.layout.layout_set_add,
                new Object[]{"name", "tel", "address", "state"},
                new int[]{R.id.tv_setAdd_name, R.id.tv_setAdd_tel, R.id.tv_setAdd_add, R.id.iv_setAdd_img, R.id.ll_setAdd_all});
        lv_receivelist.setAdapter(adapter);

        lv_receivelist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == list.size() - 1) {
                    Intent intent = new Intent(SetReceiveIfmActivity.this, Add_addressActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.pic_left_in, R.anim.pic_left_out);
                    Log.e("页面", "进入收货信息添加页");
                } else {
                    // 重置状态，确保最多只有一项被选中 state
                    for (HashMap<String, Object> hm : list) {
                        hm.put("state", 0);
                    }
                    addDAO.update(list.get(position));
                    list.get(position).put("state", 1);
                    adapter.notifyDataSetChanged();
                    Log.e("页面", "更新收货信息页并返回默认地址页");
                    finish();
                    overridePendingTransition(R.anim.pic_right_in, R.anim.pic_right_out);
                }
            }
        });
    }
}
