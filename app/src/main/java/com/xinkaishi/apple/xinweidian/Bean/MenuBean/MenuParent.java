package com.xinkaishi.apple.xinweidian.Bean.MenuBean;

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
public class MenuParent {
    private int id;
    private String name;
    private int num;
    private int count;
    private List<MenuChild> child;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public List<MenuChild> getChild() {
        return child;
    }

    public void setChild(List<MenuChild> child) {
        this.child = child;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "MenuParent [id=" + id + ", name=" + name + "num=" + num + "child=" + child ;
    }
}
