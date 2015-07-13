package com.xinkaishi.apple.xinweidian.Bean.Order_Bean;

/**
 * 项目名称：Xinweidian
 * 类描述：
 * 创建人：apple
 * 创建时间：15/6/25 下午3:49
 * 修改人：apple
 * 修改时间：15/6/25 下午3:49
 * 修改备注：
 */
public class OrderState {
    private int error;
    private String message;
    private OrderData data; //交易号总数，列表

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public OrderData getData() {
        return data;
    }

    public void setData(OrderData data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ListState [error=" + error + ", message=" + message
                + "data=" + data + "]";
    }
}
