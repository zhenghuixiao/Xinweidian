package com.xinkaishi.apple.xinweidian.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xinkaishi.apple.xinweidian.Activity.Order_detailActivity;
import com.xinkaishi.apple.xinweidian.Bean.Order_Bean.OrderDetail;
import com.xinkaishi.apple.xinweidian.Bean.Order_Bean.OrderList;
import com.xinkaishi.apple.xinweidian.R;

import java.util.List;

/**
 * 项目名称：Xinweidian
 * 类描述：
 * 创建人：apple
 * 创建时间：15/6/5 上午11:33
 * 修改人：apple
 * 修改时间：15/6/5 上午11:33
 * 修改备注：
 */
public class Adapter_goods_orders extends BaseExpandableListAdapter{
    private Context context;
    private List<OrderList> grouplist;
    public Adapter_goods_orders(Context context, List<OrderList> orderLists){
        this.context = context;
        this.grouplist = orderLists;
    }

    @Override
    public int getGroupCount() {
        return grouplist.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        //如果有超过两个商品，则显示两行
        List<OrderDetail> childlist = grouplist.get(groupPosition).getTrade();

        return childlist.size() < 2? childlist.size() : 2;
    }

    @Override
    public OrderList getGroup(int groupPosition) {
        return grouplist.get(groupPosition);
    }

    @Override
    public List<OrderDetail> getChild(int groupPosition, int childPosition) {
        return grouplist.get(groupPosition).getTrade();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_goods_ordergroup, null);
            holder.tv_ordergroup_jiaoyihao = (TextView)convertView.findViewById(R.id.tv_ordergroup_jiaoyihao);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        convertView.setClickable(true);

        holder.tv_ordergroup_jiaoyihao.setText(getGroup(groupPosition).getTrade_group_id());
        //todo 数据适配
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_goods_orderchild, null);
            holder.tv_goodscenter_title = (TextView)convertView.findViewById(R.id.tv_goodscenter_title);
            holder.ll_orderchild_state = (LinearLayout)convertView.findViewById(R.id.ll_orderchild_state);
            holder.rl_orderchild_unget = (RelativeLayout)convertView.findViewById(R.id.rl_orderchild_unget);
            holder.rl_orderchild_unpay = (RelativeLayout)convertView.findViewById(R.id.rl_orderchild_unpay);
            holder.rl_orderchild_untake = (RelativeLayout)convertView.findViewById(R.id.rl_orderchild_untake);
            holder.rl_orderchild_close = (RelativeLayout)convertView.findViewById(R.id.rl_orderchild_close);
            holder.rl_orderchild_moneyback = (RelativeLayout)convertView.findViewById(R.id.rl_orderchild_moneyback);
            holder.rl_orderchild_success = (RelativeLayout)convertView.findViewById(R.id.rl_orderchild_success);
            holder.ll_orderchild_item = (LinearLayout)convertView.findViewById(R.id.ll_orderchild_item);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        //子列表数据
        OrderDetail detail = getChild(groupPosition, childPosition).get(childPosition);
        //判断是否有两条数据，两条则移除第一条的状态栏
        if(getChildrenCount(groupPosition) == 2 & childPosition == 0){
            holder.ll_orderchild_state.setVisibility(View.GONE);
        }
        holder.ll_orderchild_item.setOnClickListener(new OrderOnclick(groupPosition));
        switch ((Integer)getGroup(groupPosition).getState()){
            case 0:  // 未支付
                holder.rl_orderchild_unpay.setVisibility(View.VISIBLE);
                //todo 按钮监听
                break;
            case 1:  // 交易成功
                holder.rl_orderchild_success.setVisibility(View.VISIBLE);
                break;
            case 2:  // 已关闭
                holder.rl_orderchild_close.setVisibility(View.VISIBLE);
                break;
            case 3:  // 等待发货
                holder.rl_orderchild_untake.setVisibility(View.VISIBLE);
                break;
            case 4:  // 已发货（部分 全部发货）
                holder.rl_orderchild_unget.setVisibility(View.VISIBLE);
                break;
            case 5:  // 已退款
                holder.rl_orderchild_moneyback.setVisibility(View.VISIBLE);
                break;
            default :
                holder.rl_orderchild_success.setVisibility(View.VISIBLE);
                break;
        }
        holder.tv_goodscenter_title.setText(detail.getTitle());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    class ViewHolder{
        // 交易号  商品名称  地址  状态图片
        TextView tv_ordergroup_jiaoyihao, tv_goodscenter_title, tv_setAddress;
        // 主操作状态栏  item
        LinearLayout ll_orderchild_state, ll_orderchild_item;
        // 子操作状态栏
        RelativeLayout rl_orderchild_unget, rl_orderchild_unpay, rl_orderchild_untake,
                rl_orderchild_close, rl_orderchild_moneyback, rl_orderchild_success;
        ImageView iv_setImg;
    }

    class OrderOnclick implements View.OnClickListener {
        private int groupPosition;
        public OrderOnclick(int groupPosition){
            this.groupPosition = groupPosition;
        }
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setClass(context, Order_detailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("child", grouplist.get(groupPosition));
            intent.putExtras(bundle);
            Log.e("传递", "对象传递成功");
            context.startActivity(intent);
        }
    }
}
