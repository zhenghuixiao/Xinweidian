package com.xinkaishi.apple.xinweidian.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xinkaishi.apple.xinweidian.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 项目名称：Xinweidian
 * 类描述：
 * 创建人：apple
 * 创建时间：15/5/29 下午5:33
 * 修改人：apple
 * 修改时间：15/5/29 下午5:33
 * 修改备注：
 */
public class Adapter_set_address extends BaseAdapter{
    private Context context;
    private ArrayList<HashMap<String, Object>> list;
    private int layoutID;
    private Object flag[];
    private int ItemIDs[];
    /**
     * @param context class
     * @param list 数据集合list
     * @param layoutID 样式layout
     * @param flag list中存带标签
     * @param ItemIDs list中各项ID
     * */
    public Adapter_set_address(Context context, ArrayList<HashMap<String, Object>> list,
                               int layoutID, Object flag[], int ItemIDs[]){
        this.context = context;
        this.list = list;
        this.layoutID = layoutID;
        this.flag = flag;
        this.ItemIDs = ItemIDs;
    }
    @Override
    public int getCount() {
        return list.size();
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(layoutID, null);
            holder = new ViewHolder();
            holder.tv_setName = (TextView) convertView.findViewById(ItemIDs[0]);
            holder.tv_setTel = (TextView) convertView.findViewById(ItemIDs[1]);
            holder.tv_setAddress = (TextView) convertView.findViewById(ItemIDs[2]);
            holder.iv_setImg = (ImageView) convertView.findViewById(ItemIDs[3]);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tv_setName.setText(list.get(position).get(flag[0]) + "");
        holder.tv_setTel.setText(list.get(position).get(flag[1]) + "");
        holder.tv_setAddress.setText(list.get(position).get(flag[2]) + "");
        if(position == list.size() - 1){
            holder.iv_setImg.setBackgroundColor(convertView.getResources().getColor(R.color.black));
        }else{

            if ((Integer)list.get(position).get(flag[3]) == 1) {
                holder.iv_setImg.setVisibility(View.VISIBLE);
                holder.iv_setImg.setBackgroundColor(convertView.getResources().getColor(R.color.black_light));
            } else {
                holder.iv_setImg.setVisibility(View.GONE);
            }
        }
        return convertView;
    }

    class ViewHolder{
        // 姓名  手机  地址  状态图片
        TextView tv_setName, tv_setTel, tv_setAddress;
        ImageView iv_setImg;
    }
}
