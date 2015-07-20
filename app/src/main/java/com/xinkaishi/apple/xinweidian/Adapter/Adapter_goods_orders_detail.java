package com.xinkaishi.apple.xinweidian.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xinkaishi.apple.xinweidian.Bean.Order_Bean.OrderDetail;
import com.xinkaishi.apple.xinweidian.Bean.Order_Bean.OrderList;
import com.xinkaishi.apple.xinweidian.CustomView.SwipeItemLayout;
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
public class Adapter_goods_orders_detail extends BaseExpandableListAdapter{
    private Context context;
    private OrderList orderlist;
    private Cache cache;
    private ImgDAO imgDAO;
    public Adapter_goods_orders_detail(Context context, OrderList orderlist, Cache cache, ImgDAO imgDAO){
        this.context = context;
        this.orderlist = orderlist;
        this.cache = cache;
        this.imgDAO = imgDAO;
    }

    @Override
    public int getGroupCount() {
        return 1;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        List<OrderDetail> childlist = orderlist.getTrade();

        return childlist.size();
    }

    @Override
    public OrderList getGroup(int groupPosition) {
        return orderlist;
    }

    @Override
    public OrderDetail getChild(int groupPosition, int childPosition) {
        List<OrderDetail> list = orderlist.getTrade();
        return list.get(childPosition);
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
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_detail_ordergroup, null);
            holder.tv_ordergroup_jiaoyihao = (TextView)convertView.findViewById(R.id.tv_ordergroup_jiaoyihao);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        convertView.setClickable(true);

        holder.tv_ordergroup_jiaoyihao.setText(orderlist.getTrade_group_id());
        //todo 数据适配
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if(true){
            holder = new ViewHolder();
            View itemView = LayoutInflater.from(context).inflate(R.layout.layout_detail_orderchild, null);
            View menuView = LayoutInflater.from(context).inflate(R.layout.layout_view_moneyback, null);
            //itemView  menuView
            convertView = new SwipeItemLayout(itemView, menuView, null, null);
            holder.rl_moneyback  = (RelativeLayout)menuView.findViewById(R.id.rl_moneyback);
            holder.tv_orderchild_title = (TextView)itemView.findViewById(R.id.tv_orderchild_title);
            holder.tv_orderchild_orderid = (TextView)itemView.findViewById(R.id.tv_orderchild_orderid);
            holder.tv_orderchild_format = (TextView)itemView.findViewById(R.id.tv_orderchild_format);
            holder.iv_orderchild_image = (ImageView)itemView.findViewById(R.id.iv_orderchild_image);
            holder.iv_orderchild_image_back = (ImageView)itemView.findViewById(R.id.iv_orderchild_image_back);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_orderchild_title.setText(getChild(groupPosition, childPosition).getTitle());
        holder.tv_orderchild_orderid.setText(getChild(groupPosition, childPosition).getTrade_id());
        holder.tv_orderchild_format.setText(getChild(groupPosition, childPosition).getSpec());
        //todo 这里是判断子订单的状态是否退款 改变图片
        if((Integer)getChild(groupPosition, childPosition).getStatus() != 0){
            holder.iv_orderchild_image_back.setVisibility(View.VISIBLE);
        }else {
            holder.iv_orderchild_image_back.setVisibility(View.GONE);
        }

        //给imageview做标识
        holder.iv_orderchild_image.setTag(getChild(groupPosition, childPosition).getPic());
        LoadImg.onLoadImage(getChild(groupPosition, childPosition).getPic(), cache, imgDAO, new LoadImg.OnLoadImageListener() {
            @Override
            public void OnLoadImage(Bitmap bitmap, String bitmapPath) {
            //只有当当前地址和view标识一致时才加载图片   解决图片重复问题
            if(holder.iv_orderchild_image.getTag().equals(bitmapPath)){
                holder.iv_orderchild_image.setImageBitmap(bitmap);
            }
            }
        });


        holder.rl_moneyback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo 改变状态，刷新列表
                Log.e("退款", "退款");
                getChild(groupPosition, childPosition).setStatus(1);
                Adapter_goods_orders_detail.this.notifyDataSetChanged();
            }
        });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    class ViewHolder{
        // 交易号  商品名称  订单号  规格  进价  数量
        TextView tv_ordergroup_jiaoyihao, tv_orderchild_title, tv_orderchild_orderid, tv_orderchild_format;
        ImageView iv_orderchild_image, iv_orderchild_image_back;
        RelativeLayout rl_moneyback;
    }
}
