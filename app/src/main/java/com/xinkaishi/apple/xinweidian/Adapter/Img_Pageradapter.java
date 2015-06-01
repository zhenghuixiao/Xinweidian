package com.xinkaishi.apple.xinweidian.Adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * 项目名称：Xinweidian
 * 类描述：
 * 创建人：apple
 * 创建时间：15/5/22 下午5:35
 * 修改人：apple
 * 修改时间：15/5/22 下午5:35
 * 修改备注：
 */
public class Img_Pageradapter extends PagerAdapter{
    private ArrayList<ImageView> list_img;
    public Img_Pageradapter(ArrayList<ImageView> list_img){
        this.list_img = list_img;
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(list_img.get(position));
        return list_img.get(position);
    }

    @Override
    public int getCount() {
        return list_img.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
