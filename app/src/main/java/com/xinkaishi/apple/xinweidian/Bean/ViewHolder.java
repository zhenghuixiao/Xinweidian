package com.xinkaishi.apple.xinweidian.Bean;

import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 项目名称：Xinweidian
 * 类描述：
 * 创建人：apple
 * 创建时间：15/6/15 上午11:59
 * 修改人：apple
 * 修改时间：15/6/15 上午11:59
 * 修改备注：
 */
public class ViewHolder {
    // 商品名称  规格  进价  进货数量  单条商品价格  显示的商品数量   减  加
    public TextView tv_shoppingcart_title, tv_shoppingcart_format,
            tv_shoppingcart_inPrice, tv_shoppingcart_picknum,
            tv_shoppingcart_allprice, tv_shoppingcart_shownum,
            tv_shoppingcart_min, tv_shoppingcart_add;
    public ImageView iv_shoppingcart_image;
    public CheckBox cb_shoppingcart_status;
}
