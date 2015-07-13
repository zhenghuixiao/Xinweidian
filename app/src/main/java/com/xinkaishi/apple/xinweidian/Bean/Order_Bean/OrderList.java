package com.xinkaishi.apple.xinweidian.Bean.Order_Bean;

import java.io.Serializable;
import java.util.List;

/**
 * 项目名称：Xinweidian
 * 类描述：
 * 创建人：apple
 * 创建时间：15/6/25 下午3:54
 * 修改人：apple
 * 修改时间：15/6/25 下午3:54
 * 修改备注：
 */

public class OrderList implements Serializable { //实现Serializable接口为了传递对象
    private int state;              //交易单号状态
    private String state_text;      //状态文字
    private String trade_group_id;  //交易号
    private String consignee;       //收货人
    private String address;         //收货地址
    private float fee;              //总价
    private float express_fee;      //邮费
    private int source;             //来源编号
    private String source_text;     //来源text
    private String created_at;      //创建时间
    private String pay_at;          //付款时间
    private String send_at;         //发货时间
    private String recv_at;         //收货时间
    private String done_at;         //下单时间
    private List<OrderDetail> trade;      //交易号内详情

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getState_text() {
        return state_text;
    }

    public void setState_text(String state_text) {
        this.state_text = state_text;
    }

    public String getTrade_group_id() {
        return trade_group_id;
    }

    public void setTrade_group_id(String trade_group_id) {
        this.trade_group_id = trade_group_id;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public float getExpress_fee() {
        return express_fee;
    }

    public void setExpress_fee(float express_fee) {
        this.express_fee = express_fee;
    }

    public float getFee() {
        return fee;
    }

    public void setFee(float fee) {
        this.fee = fee;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public String getSource_text() {
        return source_text;
    }

    public void setSource_text(String source_text) {
        this.source_text = source_text;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getPay_at() {
        return pay_at;
    }

    public void setPay_at(String pay_at) {
        this.pay_at = pay_at;
    }

    public String getSend_at() {
        return send_at;
    }

    public void setSend_at(String send_at) {
        this.send_at = send_at;
    }

    public String getRecv_at() {
        return recv_at;
    }

    public void setRecv_at(String recv_at) {
        this.recv_at = recv_at;
    }

    public String getDone_at() {
        return done_at;
    }

    public void setDone_at(String done_at) {
        this.done_at = done_at;
    }

    public List<OrderDetail> getTrade() {
        return trade;
    }

    public void setTrade(List<OrderDetail> trade) {
        this.trade = trade;
    }

    @Override
    public String toString() {
        return "MenuParent [id=" + trade_group_id + ", state_text=" + state_text ;
    }
}
