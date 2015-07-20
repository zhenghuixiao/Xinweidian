package com.xinkaishi.apple.xinweidian.Bean.ListBean;

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
public class ListGoods {
    private Long id;
    private String name;
    private String sku_desc;//规格
    private String default_img; //默认图片
    private List<String> roll_images; //默认图片
    private float price;//售价
    private float import_price;//进价
    private int sale_amount;//销量
    private int inventory;//库存
    private float profit;//利润
    private int has_add;//是否已加入店铺
    private int user_collect;//收藏
    private int sku_id;//skuID 提交订单时需要

    public int getSku_id() {
        return sku_id;
    }

    public void setSku_id(int sku_id) {
        this.sku_id = sku_id;
    }

    public List<String> getRoll_images() {
        return roll_images;
    }

    public void setRoll_images(List<String> roll_images) {
        this.roll_images = roll_images;
    }

    public String getSku_desc() {
        return sku_desc;
    }

    public void setSku_desc(String sku_desc) {
        this.sku_desc = sku_desc;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getImport_price() {
        return import_price;
    }

    public void setImport_price(float import_price) {
        this.import_price = import_price;
    }

    public int getHas_add() {
        return has_add;
    }

    public void setHas_add(int has_add) {
        this.has_add = has_add;
    }

    public float getProfit() {
        return profit;
    }

    public void setProfit(float profit) {
        this.profit = profit;
    }

    public int getUser_collect() {
        return user_collect;
    }

    public void setUser_collect(int user_collect) {
        this.user_collect = user_collect;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDefault_img() {
        return default_img;
    }

    public void setDefault_img(String default_img) {
        this.default_img = default_img;
    }


    public float getPrice() {
        return price;
    }

    public int getInventory() {
        return inventory;
    }

    public void setInventory(int inventory) {
        this.inventory = inventory;
    }

    public int getSale_amount() {
        return sale_amount;
    }

    public void setSale_amount(int sale_amount) {
        this.sale_amount = sale_amount;
    }

    @Override
    public String toString() {
        return "MenuParent [id=" + id + ", name=" + name ;
    }
}
