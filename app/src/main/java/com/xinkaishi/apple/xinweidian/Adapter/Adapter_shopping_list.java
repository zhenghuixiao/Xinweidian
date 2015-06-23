package com.xinkaishi.apple.xinweidian.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.xinkaishi.apple.xinweidian.Bean.Shoppingcart_totalprice;
import com.xinkaishi.apple.xinweidian.Bean.ViewHolder;
import com.xinkaishi.apple.xinweidian.DAO.ImgDAO;
import com.xinkaishi.apple.xinweidian.DAO.ShoppingcartDAO;
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
    // 用来控制CheckBox的选中状况
    private static HashMap<Integer,Boolean> isSelected;
    private int layoutID;
    private int ItemIDs[];
    private Cache cache; //图片缓存类
    private ImgDAO imgdao; // 数据库
    private ShoppingcartDAO shoppingcartDAO; // 数据库
    private float allprice;//单条商品总价
    private float totalPrice;;//全部商品总价
    private Shoppingcart_totalprice shoppingcart_totalprice;;//全部商品总价
    private TextView tv_shopping_cart_totalPrice;//外部的全部商品总价tv
    /**
     * @param context class
     * @param list 数据集合list
     * @param layoutID 样式layout
     * @param ItemIDs list中各项ID
     * */
    public Adapter_shopping_list(Context context, ArrayList<HashMap<String, Object>> list,
                                 int layoutID, int ItemIDs[], TextView tv_shopping_cart_totalPrice,
                                 Shoppingcart_totalprice shoppingcart_totalprice, ImgDAO imgdao,
                                 ShoppingcartDAO shoppingcartDAO){
        this.context = context;
        this.list = list;
        this.layoutID = layoutID;
        this.ItemIDs = ItemIDs;
        this.imgdao = imgdao;
        this.shoppingcartDAO = shoppingcartDAO;
        this.shoppingcart_totalprice = shoppingcart_totalprice;
        this.tv_shopping_cart_totalPrice = tv_shopping_cart_totalPrice;
        isSelected = new HashMap<Integer, Boolean>();
        cache = new Cache();
        initDate();
    }
    // 初始化isSelected的数据
    private void initDate(){
        totalPrice = 0;
        for(int i=0; i<list.size();i++) {
            getIsSelected().put(i,true);
        }
    }

    public static HashMap<Integer,Boolean> getIsSelected() {
        return isSelected;
    }

    public static void setIsSelected(HashMap<Integer,Boolean> isSelected) {
        Adapter_shopping_list.isSelected = isSelected;
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
            holder.cb_shoppingcart_status = (CheckBox) convertView.findViewById(ItemIDs[0]);
            holder.iv_shoppingcart_image = (ImageView) convertView.findViewById(ItemIDs[1]);
            holder.tv_shoppingcart_title = (TextView) convertView.findViewById(ItemIDs[2]);
            holder.tv_shoppingcart_format = (TextView) convertView.findViewById(ItemIDs[3]);
            holder.tv_shoppingcart_inPrice = (TextView) convertView.findViewById(ItemIDs[4]);
            holder.tv_shoppingcart_picknum = (TextView) convertView.findViewById(ItemIDs[5]);
            holder.tv_shoppingcart_shownum = (TextView) convertView.findViewById(ItemIDs[6]);
            holder.tv_shoppingcart_allprice = (TextView) convertView.findViewById(ItemIDs[7]);
            holder.tv_shoppingcart_min = (TextView) convertView.findViewById(ItemIDs[8]);
            holder.tv_shoppingcart_add = (TextView) convertView.findViewById(ItemIDs[9]);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        //一个item的总价
        allprice = (float)list.get(position).get("price_in") * (Integer) list.get(position).get("num");
        list.get(position).put("allprice", allprice);
        holder.iv_shoppingcart_image.setBackgroundColor(convertView.getResources().getColor(R.color.white));
        holder.tv_shoppingcart_title.setText(list.get(position).get("name") + "");
        holder.tv_shoppingcart_format.setText(list.get(position).get("format") + "");
        holder.tv_shoppingcart_inPrice.setText(String.format("%.2f",list.get(position).get("price_in")));
        holder.tv_shoppingcart_picknum.setText(list.get(position).get("num") + "");
        holder.tv_shoppingcart_shownum.setText("数量" + list.get(position).get("num") + "件，小计（不含运费）");
        holder.tv_shoppingcart_allprice.setText("￥" + String.format("%.2f", allprice));
        holder.tv_shoppingcart_min.setOnClickListener(new Myonclick(0, position));
        holder.tv_shoppingcart_add.setOnClickListener(new Myonclick(1, position));
        LoadImg.onLoadImage(list.get(position).get("img").toString(), cache, imgdao, new LoadImg.OnLoadImageListener() {
            @Override
            public void OnLoadImage(Bitmap bitmap, String bitmapPath) {
                holder.iv_shoppingcart_image.setImageBitmap(bitmap);
            }
        });
        //设置选中情况
        holder.cb_shoppingcart_status.setChecked(getIsSelected().get(position));
        return convertView;
    }
    class Myonclick implements View.OnClickListener {
        private int a;
        private int position;
        private int num;
        public Myonclick(int a, int position){
            this.a = a;
            this.position = position;
            num = (Integer)list.get(position).get("num");
        }
        @Override
        public void onClick(View v) {
            if(a == 0 & num > 1){
                num --;
                list.get(position).put("num", num);
                shoppingcartDAO.update(list.get(position));//数据库跟新
                Adapter_shopping_list.this.notifyDataSetChanged();
                //如果是选中状态的，则对总价进行跟新
                if(getIsSelected().get(position)){
                    totalPrice = shoppingcart_totalprice.getTotalprice() - (float)list.get(position).get("price_in");
                    shoppingcart_totalprice.setTotalprice(totalPrice);
                    tv_shopping_cart_totalPrice.setText("￥" + String.format("%.2f", totalPrice));
                }
            }else if(a == 1){
                num ++;
                list.get(position).put("num", num);
                shoppingcartDAO.update(list.get(position));//数据库跟新
                Adapter_shopping_list.this.notifyDataSetChanged();
                //如果是选中状态的，则对总价进行跟新
                if(getIsSelected().get(position)){
                    totalPrice = shoppingcart_totalprice.getTotalprice() + (float)list.get(position).get("price_in");
                    shoppingcart_totalprice.setTotalprice(totalPrice);
                    tv_shopping_cart_totalPrice.setText("￥" + String.format("%.2f", totalPrice));
                }
            }
        }
    }
}
