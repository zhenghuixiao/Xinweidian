package com.xinkaishi.apple.xinweidian.Activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.xinkaishi.apple.xinweidian.DAO.AddressDAO;
import com.xinkaishi.apple.xinweidian.R;

import java.util.HashMap;

public class Add_addressActivity extends ActionBarActivity {
    private Spinner sp_add_address_sheng, sp_add_address_shi, sp_add_address_qu;
    //灰色显示的提示 用户详细地址，姓名，手机
    private TextView tv_add_address_detail, tv_add_address_name, tv_add_address_phone;
    private EditText et_add_address_detail, et_add_address_name, et_add_address_phone;
    private AddressDAO addDAO;
    private ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);

        // 显示导航按钮
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        initView();//初始化控件
        initShowView();//焦点监听
        setAdapter();//省市区三级联动适配
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_address, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.action_ok: //完成按钮
                // 判断是否信息完整 完整则加入数据库   //TODO 并上传服务器
                if(et_add_address_detail.getText().toString().equals("")){
                    Toast.makeText(Add_addressActivity.this, "详细地址不能为空", Toast.LENGTH_SHORT).show();
                    return false;
                }else
                if(et_add_address_name.getText().toString().equals("")){
                    Toast.makeText(Add_addressActivity.this, "收货人姓名不能为空", Toast.LENGTH_SHORT).show();
                    return false;
                }else
                if(et_add_address_phone.getText().toString().equals("")){
                    Toast.makeText(Add_addressActivity.this, "收货人手机不能为空", Toast.LENGTH_SHORT).show();
                    return false;
                }
                HashMap<String, Object> hm = new HashMap<String, Object>();
                hm.put("name", et_add_address_name.getText().toString());
                hm.put("tel", et_add_address_phone.getText().toString());
                hm.put("address", et_add_address_detail.getText().toString());
                hm.put("state", 0);
                //新增收货信息
                addDAO.add(hm);
                Log.e("界面", "返回设置收货信息列表页");
                finish();
                overridePendingTransition(R.anim.pic_right_in, R.anim.pic_right_out);
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

    private void initView() {
        addDAO = new AddressDAO(this);

        sp_add_address_sheng = (Spinner)findViewById(R.id.sp_add_address_sheng);
        sp_add_address_shi = (Spinner)findViewById(R.id.sp_add_address_shi);
        sp_add_address_qu = (Spinner)findViewById(R.id.sp_add_address_qu);

        tv_add_address_detail = (TextView)findViewById(R.id.tv_add_address_detail);
        tv_add_address_name = (TextView)findViewById(R.id.tv_add_address_name);
        tv_add_address_phone = (TextView)findViewById(R.id.tv_add_address_phone);

        et_add_address_detail = (EditText)findViewById(R.id.et_add_address_detail);
        et_add_address_name = (EditText)findViewById(R.id.et_add_address_name);
        et_add_address_phone = (EditText)findViewById(R.id.et_add_address_phone);
    }

    private void initShowView() {
        //焦点获取监听器
        et_add_address_detail.setOnFocusChangeListener(new MyOnFocusChangeListener());
        et_add_address_name.setOnFocusChangeListener(new MyOnFocusChangeListener());
        et_add_address_phone.setOnFocusChangeListener(new MyOnFocusChangeListener());

    }

    class MyOnFocusChangeListener implements View.OnFocusChangeListener {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if(hasFocus) {// 此处为得到焦点时的处理内容
                switch (v.getId()) {
                    case R.id.et_add_address_detail:
                        tv_add_address_detail.setVisibility(View.GONE);
                        break;
                    case R.id.et_add_address_name:
                        tv_add_address_name.setVisibility(View.GONE);
                        break;
                    case R.id.et_add_address_phone:
                        tv_add_address_phone.setVisibility(View.GONE);
                        break;
                }
            } else {// 此处为失去焦点时的处理内容
                switch (v.getId()) {
                    case R.id.et_add_address_detail:
                        if(et_add_address_detail.getText().toString().equals("")){
                            tv_add_address_detail.setVisibility(View.VISIBLE);
                        }
                        break;
                    case R.id.et_add_address_name:
                        if(et_add_address_name.getText().toString().equals("")){
                            tv_add_address_name.setVisibility(View.VISIBLE);
                        }
                        break;
                    case R.id.et_add_address_phone:
                        if(et_add_address_phone.getText().toString().equals("")){
                            tv_add_address_phone.setVisibility(View.VISIBLE);
                        }
                        break;
                }
            }
        }
    }
    private void setAdapter() {
        //将可选内容与ArrayAdapter连接起来
        adapter = ArrayAdapter.createFromResource(this, R.array.sheng, android.R.layout.simple_spinner_item);

        //设置下拉列表的风格
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //将adapter 添加到spinner中
        sp_add_address_sheng.setAdapter(adapter);

        //添加事件spinner_sheng事件监听
        sp_add_address_sheng.setOnItemSelectedListener(new SpinnerXMLSelectedListener());

        //设置默认值
        sp_add_address_sheng.setVisibility(View.VISIBLE);
    }

    //使用XML形式操作
    class SpinnerXMLSelectedListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            adapter.getItem(position);//选中的
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
}
