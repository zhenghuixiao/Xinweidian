package com.xinkaishi.apple.xinweidian.Bean.MenuBean;

import java.util.List;

/**
 * 项目名称：Xinweidian
 * 类描述：
 * 创建人：apple
 * 创建时间：15/6/25 下午3:51
 * 修改人：apple
 * 修改时间：15/6/25 下午3:51
 * 修改备注：
 */
public class MenuData {
    private int count;
    private List<MenuParent> list;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<MenuParent> getList() {
        return list;
    }

    public void setList(List<MenuParent> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "MenuData [count=" + count + ", list=" + list;
    }
}
