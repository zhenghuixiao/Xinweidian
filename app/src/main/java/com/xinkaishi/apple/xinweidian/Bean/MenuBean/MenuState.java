package com.xinkaishi.apple.xinweidian.Bean.MenuBean;

/**
 * 项目名称：Xinweidian
 * 类描述：
 * 创建人：apple
 * 创建时间：15/6/25 下午3:49
 * 修改人：apple
 * 修改时间：15/6/25 下午3:49
 * 修改备注：
 */
public class MenuState {
    private int error;
    private String message;
    private MenuData data; //菜单总数，列表

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

    public MenuData getData() {
        return data;
    }

    public void setData(MenuData data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "MenuState [error=" + error + ", message=" + message
                + "data=" + data + "]";
    }
}
