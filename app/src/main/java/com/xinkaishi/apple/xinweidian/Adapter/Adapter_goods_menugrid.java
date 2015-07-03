package com.xinkaishi.apple.xinweidian.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xinkaishi.apple.xinweidian.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 项目名称：Xinweidian
 * 类描述：
 * 创建人：apple
 * 创建时间：15/5/25 下午5:33
 * 修改人：apple
 * 修改时间：15/5/25 下午5:33
 * 修改备注：
 */
public class Adapter_goods_menugrid extends BaseAdapter {
    private Context context;
    private ArrayList<HashMap<String, Object>> list;
    private int layoutID;
    private int selected; //是否初始化全部按钮

    /**
     * @param context  class
     * @param list     数据集合list
     * @param layoutID 样式layout
     */
    public Adapter_goods_menugrid(Context context, ArrayList<HashMap<String, Object>> list,
                                  int layoutID) {
        this.context = context;
        this.list = list;
        this.layoutID = layoutID;
        selected = 0;
    }

    public void setSelected(int a){
        selected = a;
    }
    public int getSelected(){
        return selected;
    }

    @Override
    public int getCount() {
        return list.size() + 1;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder_menu holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(layoutID, null);
            holder = new ViewHolder_menu();
            holder.name = (TextView)convertView.findViewById(R.id.tv_menu_button);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder_menu) convertView.getTag();
        }
        if(position == 0){
            holder.name.setText("全部");
        }else {
            holder.name.setText(list.get(position - 1).get("name").toString());
        }
        holder.name.setBackgroundResource(R.color.white);
        holder.name.setTextColor(context.getResources().getColor(R.color.black));

        if(selected == position){
            holder.name.setBackgroundResource(R.drawable.textview_biankuang_maincolor);
            holder.name.setTextColor(context.getResources().getColor(R.color.white));
        }
        return convertView;
    }

    class ViewHolder_menu {
        private TextView name;

    }
}