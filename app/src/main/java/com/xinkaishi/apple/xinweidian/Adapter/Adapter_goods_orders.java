package com.xinkaishi.apple.xinweidian.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xinkaishi.apple.xinweidian.Activity.Order_detailActivity;
import com.xinkaishi.apple.xinweidian.Bean.Order_Bean.OrderDetail;
import com.xinkaishi.apple.xinweidian.Bean.Order_Bean.OrderList;
import com.xinkaishi.apple.xinweidian.DAO.ImgDAO;
import com.xinkaishi.apple.xinweidian.R;
import com.xinkaishi.apple.xinweidian.Until.Cache;
import com.xinkaishi.apple.xinweidian.Until.LoadImg;

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
    private Cache cache;
    private ImgDAO imgDAO;
    public Adapter_goods_orders(Context context, List<OrderList> orderLists,
                                Cache cache, ImgDAO imgDAO){
        this.context = context;
        this.grouplist = orderLists;
        this.cache = cache;
        this.imgDAO = imgDAO;
    }

    @Override
    public int getGroupCount() {
        return grouplist.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
//        //如果有超过两个商品，则显示两行
//        List<OrderDetail> childlist = grouplist.get(groupPosition).getTrade();
//
//        return childlist.size() < 2? childlist.size() : 2;
        return 1;
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
            holder.tv_orderchild_orderid = (TextView)convertView.findViewById(R.id.tv_orderchild_orderid);
            holder.iv_orderchild_image = (ImageView)convertView.findViewById(R.id.iv_orderchild_image);
            holder.iv_orderchild_image_back = (ImageView)convertView.findViewById(R.id.iv_orderchild_image_back);
            holder.tv_orderchild_title = (TextView)convertView.findViewById(R.id.tv_orderchild_title);
            holder.tv_orderchild_format = (TextView)convertView.findViewById(R.id.tv_orderchild_format);
            holder.tv_orderchild_count = (TextView)convertView.findViewById(R.id.tv_orderchild_count);
            holder.tv_orderchild_allprice = (TextView)convertView.findViewById(R.id.tv_orderchild_allprice);
            holder.tv_orderchild_express_fee = (TextView)convertView.findViewById(R.id.tv_orderchild_express_fee);
            holder.tv_orderchild_button1 = (TextView)convertView.findViewById(R.id.tv_orderchild_button1);
            holder.tv_orderchild_button2 = (TextView)convertView.findViewById(R.id.tv_orderchild_button2);
            holder.tv_orderchild_statetext = (TextView)convertView.findViewById(R.id.tv_orderchild_statetext);

            holder.ll_orderchild_state = (LinearLayout)convertView.findViewById(R.id.ll_orderchild_state);
            holder.ll_orderchild_item = (LinearLayout)convertView.findViewById(R.id.ll_orderchild_item);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        //子列表数据
        OrderDetail detail = getChild(groupPosition, childPosition).get(childPosition);
        //判断是否有两条数据，两条则移除第一条的状态栏
//        if(getChildrenCount(groupPosition) == 2 & childPosition == 0){
//            holder.ll_orderchild_state.setVisibility(View.GONE);
//        }

        int state = getChild(groupPosition, childPosition).get(childPosition).getStatus();
        //todo 这里是判断子订单的状态是否退款 改变图片
        if(state != 0){
            holder.iv_orderchild_image_back.setVisibility(View.VISIBLE);
        }else {
            holder.iv_orderchild_image_back.setVisibility(View.GONE);
        }
        holder.ll_orderchild_item.setOnClickListener(new OrderOnclick(groupPosition, state));
        switch (getGroup(groupPosition).getState()){
            case 0:  // 未支付
                holder.tv_orderchild_button2.setVisibility(View.VISIBLE);
                holder.tv_orderchild_button1.setBackgroundResource(R.drawable.textview_biankuang_maincolor);
                holder.tv_orderchild_button2.setBackgroundResource(R.drawable.textview_biankuang_red);
                holder.tv_orderchild_button1.setText("继续支付");
                holder.tv_orderchild_button2.setText("关闭订单");
                break;
            case 1:  // 已付款/待备货
                holder.tv_orderchild_button2.setVisibility(View.GONE);
                holder.tv_orderchild_button1.setBackgroundResource(R.drawable.textview_biankuang_maincolor);
                holder.tv_orderchild_button1.setText("提醒发货");
                break;
            case 2:  // 已发货
                holder.tv_orderchild_button2.setVisibility(View.GONE);
                holder.tv_orderchild_button1.setBackgroundResource(R.drawable.textview_biankuang_red);
                holder.tv_orderchild_button1.setText("查看详情");
                break;
            case 3:  // 已收货/确认收货
                holder.tv_orderchild_button2.setVisibility(View.GONE);
                holder.tv_orderchild_button1.setBackgroundResource(R.drawable.textview_biankuang_maincolor);
                holder.tv_orderchild_button1.setText("查看详情");
                break;
            case 4:  // 已完成
                holder.tv_orderchild_button2.setVisibility(View.GONE);
                holder.tv_orderchild_button1.setBackgroundResource(R.drawable.textview_biankuang_maincolor);
                holder.tv_orderchild_button1.setText("交易成功");
                break;
            case 5:  // 已取消/关闭
                holder.tv_orderchild_button2.setVisibility(View.GONE);
                holder.tv_orderchild_button1.setBackgroundResource(R.drawable.textview_biankuang_backdack);
                holder.tv_orderchild_button1.setText("删除订单");
                break;
            case 6:  // 部分发货
                holder.tv_orderchild_button2.setVisibility(View.GONE);
                holder.tv_orderchild_button1.setBackgroundResource(R.drawable.textview_biankuang_backdack);
                holder.tv_orderchild_button1.setText("查看详情");
                break;
            case 7:  // 已退款
                holder.tv_orderchild_button2.setVisibility(View.GONE);
                holder.tv_orderchild_button1.setBackgroundResource(R.drawable.textview_biankuang_backdack);
                holder.tv_orderchild_button1.setText("退款详情");
                break;
            default :
                break;
        }
        holder.iv_orderchild_image.setImageDrawable(context.getResources().getDrawable(R.color.white));
        holder.tv_orderchild_statetext.setText(getGroup(groupPosition).getState_text());
        holder.tv_orderchild_title.setText(detail.getTitle());
        holder.tv_orderchild_format.setText(detail.getSpec());
        holder.tv_orderchild_orderid.setText(detail.getTrade_id());
        holder.tv_orderchild_count.setText("共" + getGroup(groupPosition).getTrade().size() + "件商品,总价（含运费）");
        holder.tv_orderchild_allprice.setText("￥" + String.format("%.2f", getGroup(groupPosition).getFee()));
        holder.tv_orderchild_express_fee.setText("￥" + String.format("%.2f", getGroup(groupPosition).getExpress_fee()));

        holder.tv_orderchild_button1.setOnClickListener(new MyBTonclick(getGroup(groupPosition).getState()));
        //给imageview做标识
        holder.iv_orderchild_image.setTag(detail.getPic());
        LoadImg.onLoadImage(detail.getPic(), cache, imgDAO, new LoadImg.OnLoadImageListener() {
            @Override
            public void OnLoadImage(Bitmap bitmap, String bitmapPath) {
                //只有当当前地址和view标识一致时才加载图片   解决图片重复问题
                if(holder.iv_orderchild_image.getTag().equals(bitmapPath)){
                    holder.iv_orderchild_image.setImageBitmap(bitmap);
                }
            }
        });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    class ViewHolder{
        // 交易号  商品名称  地址  状态图片
        TextView tv_ordergroup_jiaoyihao, tv_orderchild_title, tv_orderchild_orderid, tv_orderchild_format,
                tv_orderchild_express_fee, tv_orderchild_allprice, tv_orderchild_count,
                tv_orderchild_button1, tv_orderchild_button2, tv_orderchild_statetext;
        // 主操作状态栏  item
        LinearLayout ll_orderchild_state, ll_orderchild_item;
        ImageView iv_orderchild_image, iv_orderchild_image_back;
    }

    class OrderOnclick implements View.OnClickListener {
        private int groupPosition;
        private int state;
        public OrderOnclick(int groupPosition, int state){
            this.groupPosition = groupPosition;
            this.state = state;
        }
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            //state=7 为退款订单，跳转退款详情
            if(state == 7){

            }else {
                intent.setClass(context, Order_detailActivity.class);
            }
            Bundle bundle = new Bundle();
            bundle.putSerializable("child", grouplist.get(groupPosition));
            intent.putExtras(bundle);
            Log.e("传递", "对象传递成功");
            context.startActivity(intent);
        }
    }

    class MyBTonclick implements View.OnClickListener {
        private int state;
        public MyBTonclick(int state){
            this.state = state;
        }
        @Override
        public void onClick(View v) {
            switch (state){
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4: //查看详情
                    break;
                case 5: //删除订单
                    break;
                case 6:
                    break;
                case 7:
                    break;
            }

        }
    }
}
