package com.xinkaishi.apple.xinweidian.Bean.MenuBean;

/**
 * 项目名称：Xinweidian
 * 类描述：
 * 创建人：apple
 * 创建时间：15/6/25 下午3:55
 * 修改人：apple
 * 修改时间：15/6/25 下午3:55
 * 修改备注：
 */
public class MenuChild {
    private int id;
    private String name;

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

    @Override
    public String toString() {
        return "MenuChild [id=" + id + ", name=" + name;
    }
}
