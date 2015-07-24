package com.xinkaishi.apple.xinweidian.Bean.Express_Fee;

/**
 * 项目名称：Xinweidian
 * 类描述：
 * 创建人：apple
 * 创建时间：15/7/24 下午3:20
 * 修改人：apple
 * 修改时间：15/7/24 下午3:20
 * 修改备注：
 */
public class ExpressState {
    private int error;
    private String message;
    private ExpressData data;

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public ExpressData getData() {
        return data;
    }

    public void setData(ExpressData data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
