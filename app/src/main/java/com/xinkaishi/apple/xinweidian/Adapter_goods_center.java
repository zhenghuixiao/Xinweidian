package com.xinkaishi.apple.xinweidian;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xinkaishi.apple.xinweidian.DAO.ImgDAO;
import com.xinkaishi.apple.xinweidian.Until.Cache;
import com.xinkaishi.apple.xinweidian.Until.LoadImg;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 项目名称：Xinweidian
 * 类描述：
 * 创建人：apple
 * 创建时间：15/5/21 下午4:12
 * 修改人：apple
 * 修改时间：15/5/21 下午4:12
 * 修改备注：
 */
public class Adapter_goods_center extends BaseAdapter{
    private Context context;
    private ArrayList<HashMap<String, Object>> list;
    private int layoutID;
    private String flag[];
    private int ItemIDs[];
    private Cache cache;
    private ImgDAO imgdao;

    private String add;
    /**
     * @param context class
     * @param list 数据集合list
     * @param layoutID 样式layout
     * @param flag list中存带标签
     * @param ItemIDs list中各项ID
     * */
    public Adapter_goods_center(Context context, ArrayList<HashMap<String, Object>> list,
                                int layoutID, String flag[], int ItemIDs[], ImgDAO imgdao){
        this.context = context;
        this.list = list;
        this.layoutID = layoutID;
        this.flag = flag;
        this.ItemIDs = ItemIDs;
        cache = new Cache();
        this.imgdao = imgdao;
        add = null;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(layoutID, null);
            holder = new ViewHolder();
            holder.iv_goodscenter_image = (ImageView) convertView.findViewById(ItemIDs[0]);
            holder.tv_goodscenter_title = (TextView) convertView.findViewById(ItemIDs[1]);
            holder.tv_goodscenter_price_out = (TextView) convertView.findViewById(ItemIDs[2]);
            holder.tv_goodscenter_profit = (TextView) convertView.findViewById(ItemIDs[3]);
            holder.tv_goodscenter_price_in = (TextView) convertView.findViewById(ItemIDs[4]);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.iv_goodscenter_image.setBackgroundColor(convertView.getResources().getColor(R.color.white));
        holder.tv_goodscenter_title.setText(list.get(position).get(flag[2]) + "");
        holder.tv_goodscenter_price_out.setText(list.get(position).get(flag[3]) + "");
        holder.tv_goodscenter_profit.setText(list.get(position).get(flag[4]) + "");
        holder.tv_goodscenter_price_in.setText(list.get(position).get(flag[5]) + "");

        add = list.get(position).get(flag[1]).toString();
        LoadImg.onLoadImage(add, cache, imgdao, new LoadImg.OnLoadImageListener() {
            @Override
            public void OnLoadImage(Bitmap bitmap, String bitmapPath) {
                holder.iv_goodscenter_image.setImageBitmap(bitmap);
            }
        });

        return convertView;
    }

    class ViewHolder{
        // 商品名称  售价  利润  进价
        TextView tv_goodscenter_title, tv_goodscenter_price_out, tv_goodscenter_profit, tv_goodscenter_price_in;
        ImageView iv_goodscenter_image;
    }
}
