package com.xinkaishi.apple.xinweidian.Bean.ListBean;

/**
 * 项目名称：Xinweidian
 * 类描述：
 * 创建人：apple
 * 创建时间：15/6/25 下午3:49
 * 修改人：apple
 * 修改时间：15/6/25 下午3:49
 * 修改备注：
 */
public class ListState {
    private int error;
    private String message;
    private ListData data; //菜单总数，列表

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

    public ListData getData() {
        return data;
    }

    public void setData(ListData data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ListState [error=" + error + ", message=" + message
                + "data=" + data + "]";
    }
}
