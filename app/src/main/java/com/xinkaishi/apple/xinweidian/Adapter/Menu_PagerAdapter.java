package com.xinkaishi.apple.xinweidian.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * 项目名称：Xinweidian
 * 类描述：
 * 创建人：apple
 * 创建时间：15/6/24 上午11:40
 * 修改人：apple
 * 修改时间：15/6/24 上午11:40
 * 修改备注：
 */
public class Menu_PagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> fragmentList;

    public Menu_PagerAdapter(FragmentManager fm, ArrayList<Fragment> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
