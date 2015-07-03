package com.xinkaishi.apple.xinweidian.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import com.xinkaishi.apple.xinweidian.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 项目名称：Xinweidian
 * 类描述：
 * 创建人：apple
 * 创建时间：15/6/24 上午11:45
 * 修改人：apple
 * 修改时间：15/6/24 上午11:45
 * 修改备注：
 */
public class Child_Menu extends Fragment {
    private View view;
    private GridView gv_menu;
    private ArrayList<HashMap<String, Object>> list;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_childmenu, container, false);

        gv_menu = (GridView)view.findViewById(R.id.gv_menu);
        list = new ArrayList<>();
        for(int a = 0; a < 8; a ++){
            HashMap<String, Object> hm = new HashMap<>();
            hm.put("name", "全部全部");
            list.add(hm);
        }


        SimpleAdapter simpleAdapter = new SimpleAdapter(getActivity(), list,
                R.layout.layout_menu_gridview, new String[] {"name"}, new int[] {
                R.id.tv_menu_button});
        gv_menu.setAdapter(simpleAdapter);
        gv_menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("menu", "选中" + position);
;            }
        });


        return view;
    }
}
