package com.xinkaishi.apple.xinweidian.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.xinkaishi.apple.xinweidian.Activity.Goods_centerActivity;
import com.xinkaishi.apple.xinweidian.Activity.Shopping_cartActivity;
import com.xinkaishi.apple.xinweidian.DAO.ImgDAO;
import com.xinkaishi.apple.xinweidian.DAO.ShoppingcartDAO;
import com.xinkaishi.apple.xinweidian.R;
import com.xinkaishi.apple.xinweidian.Until.Cache;
import com.xinkaishi.apple.xinweidian.Until.LoadImg;
import com.xinkaishi.apple.xinweidian.Until.ScreenUtils;

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
    private View darkview;
    private Cache cache;
    private ImgDAO imgDAO;
    private ShoppingcartDAO shoppingcartDAO;
    private String add;
    private int num = 1;//进货商品数量
    private int hasadd;
    /**
     * @param context class
     * @param list 数据集合list
     * @param layoutID 样式layout
     * @param flag list中存带标签
     * @param ItemIDs list中各项ID
     * @param imgDAO 数据库
     * @param shoppingcartDAO 数据库
     * */
    public Adapter_goods_center(Context context, ArrayList<HashMap<String, Object>> list,
                                int layoutID, String flag[], int ItemIDs[], ImgDAO imgDAO,
                                ShoppingcartDAO shoppingcartDAO, View darkview){
        this.context = context;
        this.list = list;
        this.layoutID = layoutID;
        this.flag = flag;
        this.ItemIDs = ItemIDs;
        this.darkview = darkview;
        cache = new Cache();
        this.imgDAO = imgDAO;
        this.shoppingcartDAO = shoppingcartDAO;
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
            holder.tv_goodscenter_shoucang = (TextView) convertView.findViewById(ItemIDs[5]);
            holder.tv_goodscenter_goodsIn = (TextView) convertView.findViewById(ItemIDs[6]);
            holder.tv_goodscenter_getInshop = (TextView) convertView.findViewById(ItemIDs[7]);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        hasadd = (Integer)list.get(position).get("has_add");
        holder.iv_goodscenter_image.setBackgroundColor(convertView.getResources().getColor(R.color.white));
        holder.tv_goodscenter_title.setText(list.get(position).get(flag[2]) + "");
        holder.tv_goodscenter_price_out.setText("￥" + list.get(position).get(flag[3]));
        holder.tv_goodscenter_profit.setText("￥" + list.get(position).get(flag[4]));
        holder.tv_goodscenter_price_in.setText("￥" + list.get(position).get(flag[5]));
        if(hasadd == 0){
            holder.tv_goodscenter_getInshop.setText("加入店铺");
        }else{
            holder.tv_goodscenter_getInshop.setText("已加入");
        }

        HashMap<String, Object> hm = list.get(position);
        String goodId = list.get(position).get("id").toString();
        holder.tv_goodscenter_getInshop.setOnClickListener(new MyOnclickListener(hm, holder));
        holder.tv_goodscenter_goodsIn.setOnClickListener(new MyOnclickListener(hm, holder));
        holder.tv_goodscenter_shoucang.setOnClickListener(new MyOnclickListener(hm, holder));


        add = list.get(position).get(flag[1]).toString();
        LoadImg.onLoadImage(add, cache, imgDAO, new LoadImg.OnLoadImageListener() {
            @Override
            public void OnLoadImage(Bitmap bitmap, String bitmapPath) {
                holder.iv_goodscenter_image.setImageBitmap(bitmap);
            }
        });

        return convertView;
    }

    class ViewHolder{
        // 商品名称  售价  利润  进价   收藏按钮， 进货按钮， 加入店铺按钮
        TextView tv_goodscenter_title, tv_goodscenter_price_out, tv_goodscenter_profit, tv_goodscenter_price_in,
                 tv_goodscenter_shoucang, tv_goodscenter_goodsIn, tv_goodscenter_getInshop;
        ImageView iv_goodscenter_image;
    }

    class MyOnclickListener implements View.OnClickListener {
        private HashMap<String, Object> hm;
        private Animation animIn, animout;
        private ViewHolder holder;
        public MyOnclickListener(HashMap<String, Object> hm, ViewHolder holder){
            this.hm = hm;
            this.holder = holder;
            animIn = AnimationUtils.loadAnimation(context, R.anim.fade_in_anim);
            animout = AnimationUtils.loadAnimation(context, R.anim.fade_out_anim);
        }
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                //加入店铺
                case R.id.tv_goodscenter_getInshop:
                    if((Integer)hm.get("has_add") == 0) {
                        //todo post接口
                        hm.put("has_add", 1);
                        holder.tv_goodscenter_getInshop.setText("已加入");
                        Toast t = Toast.makeText(context, "", Toast.LENGTH_SHORT);
                        t.setGravity(Gravity.CENTER, 0, 0);
                        View layout = LayoutInflater.from(context).inflate(R.layout.layout_toast_style_inshop, null);
                        t.setView(layout);
                        t.show();
                    }
                    break;
                //进货
                case R.id.tv_goodscenter_goodsIn:
                    // 把商品加入数据库
                    showPup();
                    // 背景变暗
                    darkview.startAnimation(animIn);
                    darkview.setVisibility(View.VISIBLE);
                    break;
                case R.id.tv_goodscenter_shoucang:
                    break;
            }
        }
        private void showPup() {
            final PopupWindow popWindow_picknum;
            popWindow_picknum = new PopupWindow(context);
            View vPopWindow = LayoutInflater.from(context).inflate(R.layout.layout_popup_goodsin, null);
            popWindow_picknum.setContentView(vPopWindow);
            popWindow_picknum.setWidth(ScreenUtils.getScreenW(Goods_centerActivity.instance)*2/3); //获取activty宽
            popWindow_picknum.setHeight(ScreenUtils.getScreenH(Goods_centerActivity.instance)*1/4); //获取activty宽减60
            popWindow_picknum.setFocusable(true);
            popWindow_picknum.setOutsideTouchable(true);
            popWindow_picknum.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.textview_yuanjiao_white));

            Button bt_min = (Button)vPopWindow.findViewById(R.id.bt_min);
            Button bt_add = (Button)vPopWindow.findViewById(R.id.bt_add);
            final TextView tv_num = (TextView)vPopWindow.findViewById(R.id.tv_num);
            bt_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    num++;
                    tv_num.setText(num + "");
                }
            });
            bt_min.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (num > 1) {
                        num--;
                    }
                    tv_num.setText(num + "");
                }
            });

            TextView tv_continue = (TextView)vPopWindow.findViewById(R.id.tv_continue);
            tv_continue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    hm.put("state", 1);
                    hm.put("num", tv_num.getText());
                    if(shoppingcartDAO.isInShop((Long)hm.get("id"))){

                        shoppingcartDAO.update(hm);
                    }else {
                        shoppingcartDAO.add(hm);
                    }
                    num = 1;
                    popWindow_picknum.dismiss();
                }
            });

            TextView tv_topay = (TextView)vPopWindow.findViewById(R.id.tv_topay);
            tv_topay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    hm.put("state", 1);
                    hm.put("num", tv_num.getText());
                    if(shoppingcartDAO.isInShop((Long)hm.get("id"))){

                        shoppingcartDAO.update(hm);
                    }else {
                        shoppingcartDAO.add(hm);
                    }
                    num = 1;
                    popWindow_picknum.dismiss();
                    Intent intent = new Intent(context, Shopping_cartActivity.class);
                    context.startActivity(intent);
                }
            });

            popWindow_picknum.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    // 背景变回初始
                    if (!popWindow_picknum.isShowing()) {
                        num = 1;
                        darkview.setVisibility(View.GONE);
                        darkview.startAnimation(animout);
                    }
                }
            });
            popWindow_picknum.showAtLocation(vPopWindow, Gravity.CENTER, 0, 0);
        }
    }
}
