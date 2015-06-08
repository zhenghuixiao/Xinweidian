package com.xinkaishi.apple.xinweidian.Activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.xinkaishi.apple.xinweidian.R;

import java.util.ArrayList;
import java.util.HashMap;

public class SearchActivity extends ActionBarActivity {
    private ListView lv_search_list;
    private ArrayList<HashMap<String, Object>> list;
    private GridView gv_search_hot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initActionBar();
        initView();
        setAdapter();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    private void initView() {
        lv_search_list = (ListView)findViewById(R.id.lv_search_list);
        gv_search_hot = (GridView)findViewById(R.id.gv_search_hot);
        list = new ArrayList<HashMap<String, Object>>();
    }

    private boolean initActionBar() {
        // 显示导航按钮
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        if (actionBar == null) {
            Log.e("ActionBar", "payment页错误");
            return false;
        }
        //TODO 自定义布局 标题
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(R.layout.layout_search_style);
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

    private void setAdapter() {
        for(int a = 0; a < 9; a ++){
            HashMap<String, Object> hm = new HashMap<String, Object>();
            hm.put("name", "搜索" + a);
            list.add(hm);
        }

        SimpleAdapter adapter = new SimpleAdapter(this, list, R.layout.layout_search_history,
                new String[]{"name"}, new int[]{R.id.tv_searchhistory_name});
        lv_search_list.setAdapter(adapter);

        SimpleAdapter adapter1 = new SimpleAdapter(this, list, R.layout.layout_search_hot,
                new String[]{"name"}, new int[]{R.id.tv_searchhot_name});
        gv_search_hot.setAdapter(adapter1);
    }
}
