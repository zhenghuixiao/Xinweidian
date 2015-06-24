package com.xinkaishi.apple.xinweidian.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xinkaishi.apple.xinweidian.R;

import java.util.ArrayList;


public class Digital_Fragment extends Fragment {
    private View view;
    private ViewPager menu_viewpager;
    //页面列表
    private ArrayList<Fragment> fragmentList;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_digital, container, false);


        Child_Menu cm = new Child_Menu();

        return view;
    }

}
