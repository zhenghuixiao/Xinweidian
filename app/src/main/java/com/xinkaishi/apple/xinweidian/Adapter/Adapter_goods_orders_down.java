package com.xinkaishi.apple.xinweidian.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xinkaishi.apple.xinweidian.DAO.AddressDAO;
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
 * 创建时间：15/6/18 上午10:48
 * 修改人：apple
 * 修改时间：15/6/18 上午10:48
 * 修改备注：
 */
public class Adapter_goods_orders_down extends BaseAdapter{
    private Context context;
    private int layoutID;
    private ArrayList<HashMap<String, Object>> list;
    private ImgDAO imgDAO;
    private AddressDAO addressDAO;
    private Cache cache;

    /**
     * @param context class
     * @param list 数据集合list
     * @param layoutID 样式layout
     * @param imgDAO 数据库
     * @param addressDAO 数据库
     */
    public Adapter_goods_orders_down(Context context, ArrayList<HashMap<String, Object>> list,
                                     int layoutID, ImgDAO imgDAO, AddressDAO addressDAO){
        this.context = context;
        this.layoutID = layoutID;
        this.list = list;
        this.context = context;
        this.imgDAO = imgDAO;
        this.addressDAO = addressDAO;
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
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(layoutID, null);
            holder.tv_orderdown_title = (TextView)convertView.findViewById(R.id.tv_orderdown_title);
            holder.tv_orderdown_format = (TextView)convertView.findViewById(R.id.tv_orderdown_format);
            holder.tv_orderdown_inprice = (TextView)convertView.findViewById(R.id.tv_orderdown_inprice);
            holder.tv_orderdown_num = (TextView)convertView.findViewById(R.id.tv_orderdown_num);
            holder.iv_orderdown_image = (ImageView)convertView.findViewById(R.id.iv_orderdown_image);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }
        holder.tv_orderdown_title.setText(list.get(position).get("name").toString());
//        holder.tv_orderdown_format.setText(list.get(position).get("sku_desc").toString());
        holder.tv_orderdown_inprice.setText("￥" + String.format("%.2f", list.get(position).get("import_price")));
        holder.tv_orderdown_num.setText("×" + list.get(position).get("num"));
        LoadImg.onLoadImage(list.get(position).get("default_img").toString(), cache, imgDAO, new LoadImg.OnLoadImageListener() {
            @Override
            public void OnLoadImage(Bitmap bitmap, String bitmapPath) {
                holder.iv_orderdown_image.setImageBitmap(bitmap);
            }
        });
        return convertView;
    }

    class ViewHolder{
        // 商品名称  规格  进价  数量
        TextView tv_orderdown_title, tv_orderdown_format, tv_orderdown_inprice, tv_orderdown_num;
        ImageView iv_orderdown_image;
    }
}
