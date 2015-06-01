package com.xinkaishi.apple.xinweidian.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xinkaishi.apple.xinweidian.DAO.ImgDAO;
import com.xinkaishi.apple.xinweidian.R;
import com.xinkaishi.apple.xinweidian.Until.Cache;
import com.xinkaishi.apple.xinweidian.Until.LoadImg;

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
public class Adapter_shopping_list extends BaseAdapter{
    private Context context;
    private ArrayList<HashMap<String, Object>> list;
    private int layoutID;
    private String flag[];
    private int ItemIDs[];
    private Cache cache;
    private ImgDAO imgdao;
    /**
     * @param context class
     * @param list 数据集合list
     * @param layoutID 样式layout
     * @param flag list中存带标签
     * @param ItemIDs list中各项ID
     * */
    public Adapter_shopping_list(Context context, ArrayList<HashMap<String, Object>> list,
                                 int layoutID, String flag[], int ItemIDs[], ImgDAO imgdao){
        this.context = context;
        this.list = list;
        this.layoutID = layoutID;
        this.flag = flag;
        this.ItemIDs = ItemIDs;
        this.imgdao = imgdao;
        cache = new Cache();
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
            holder.tv_shoppingcart_status = (ImageView) convertView.findViewById(ItemIDs[0]);
            holder.iv_shoppingcart_image = (ImageView) convertView.findViewById(ItemIDs[1]);
            holder.tv_shoppingcart_title = (TextView) convertView.findViewById(ItemIDs[2]);
            holder.tv_shoppingcart_format = (TextView) convertView.findViewById(ItemIDs[3]);
            holder.tv_shoppingcart_inPrice = (TextView) convertView.findViewById(ItemIDs[4]);
            holder.tv_shoppingcart_picknum = (TextView) convertView.findViewById(ItemIDs[5]);
            holder.tv_shoppingcart_allprice = (TextView) convertView.findViewById(ItemIDs[6]);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_shoppingcart_status.setBackgroundColor((Integer)list.get(position).get(flag[1]) == 1 ?
                convertView.getResources().getColor(R.color.white) : convertView.getResources().getColor(R.color.black));
        holder.iv_shoppingcart_image.setBackgroundColor(convertView.getResources().getColor(R.color.white));
        holder.tv_shoppingcart_title.setText(list.get(position).get(flag[3]) + "");
        holder.tv_shoppingcart_format.setText(list.get(position).get(flag[4]) + "");
        holder.tv_shoppingcart_inPrice.setText(list.get(position).get(flag[5]) + "");
        holder.tv_shoppingcart_picknum.setText(list.get(position).get(flag[6]) + "");
        holder.tv_shoppingcart_allprice.setText((Integer)list.get(position).get(flag[5]) * (Integer)list.get(position).get(flag[5]) + "");
        LoadImg.onLoadImage(list.get(position).get(flag[2]).toString(), cache, imgdao, new LoadImg.OnLoadImageListener() {
            @Override
            public void OnLoadImage(Bitmap bitmap, String bitmapPath) {
                holder.iv_shoppingcart_image.setImageBitmap(bitmap);
            }
        });
        return convertView;
    }

    class ViewHolder{
        // 商品名称  规格  进价  进货数量
        TextView tv_shoppingcart_title, tv_shoppingcart_format, tv_shoppingcart_inPrice, tv_shoppingcart_picknum, tv_shoppingcart_allprice;
        ImageView iv_shoppingcart_image, tv_shoppingcart_status;
    }
}
