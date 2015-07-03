package com.xinkaishi.apple.xinweidian.Bean.ListBean;

/**
 * 项目名称：Xinweidian
 * 类描述：
 * 创建人：apple
 * 创建时间：15/6/25 下午3:54
 * 修改人：apple
 * 修改时间：15/6/25 下午3:54
 * 修改备注：
 */
public class ListTest {
    private String id;
    private String name;
    private String img; //默认图片
    private float originalPrice;
    private float price;
    private int saleAmount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public float getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(float originalPrice) {
        this.originalPrice = originalPrice;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getSaleAmount() {
        return saleAmount;
    }

    public void setSaleAmount(int saleAmount) {
        this.saleAmount = saleAmount;
    }

    @Override
    public String toString() {
        return "MenuParent [id=" + id + ", name=" + name ;
    }
}
