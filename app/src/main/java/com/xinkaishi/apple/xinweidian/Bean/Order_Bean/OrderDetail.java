package com.xinkaishi.apple.xinweidian.Bean.Order_Bean;

import java.io.Serializable;

/**
 * 项目名称：Xinweidian
 * 类描述：
 * 创建人：apple
 * 创建时间：15/7/9 下午7:37
 * 修改人：apple
 * 修改时间：15/7/9 下午7:37
 * 修改备注：
 */
public class OrderDetail implements Serializable {
    private String trade_id;       //订单号
    private int status;            //商品退款状态
    private String status_text;    //文字
    private int refund_id;         //是否取消退款？
    private int num;               //数量
    private float price;           //价格
    private String title;          //商品名称
    private String pic;            //商品图片
    private String spec;           //规格

    public String getTrade_id() {
        return trade_id;
    }

    public void setTrade_id(String trade_id) {
        this.trade_id = trade_id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStatus_text() {
        return status_text;
    }

    public void setStatus_text(String status_text) {
        this.status_text = status_text;
    }

    public int getRefund_id() {
        return refund_id;
    }

    public void setRefund_id(int refund_id) {
        this.refund_id = refund_id;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }
}
