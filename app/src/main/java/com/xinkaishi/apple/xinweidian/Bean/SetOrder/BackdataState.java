package com.xinkaishi.apple.xinweidian.Bean.SetOrder;

/**
 * 项目名称：Xinweidian
 * 类描述：
 * 创建人：apple
 * 创建时间：15/7/17 下午1:33
 * 修改人：apple
 * 修改时间：15/7/17 下午1:33
 * 修改备注：
 */
public class BackdataState {
    private int error;
    private String message;
    private BackData data; //返回的信息 交易号

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

    public BackData getData() {
        return data;
    }

    public void setData(BackData data) {
        this.data = data;
    }
}
