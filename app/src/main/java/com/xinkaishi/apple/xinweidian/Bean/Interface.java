package com.xinkaishi.apple.xinweidian.Bean;

/**
 * 项目名称：Xinweidian
 * 类描述：
 * 创建人：apple
 * 创建时间：15/7/6 上午11:02
 * 修改人：apple
 * 修改时间：15/7/6 上午11:02
 * 修改备注：
 */
public class Interface {
    private static int state = 1;
    private static String TESTADD = "http://192.168.0.156:8080/";
    private static String ONLINEADD = "http://pc.xinkaishi.com/";
    public static String ADDRESS = state == 1? ONLINEADD: TESTADD;

    public static String GOODS_LIST = ADDRESS + "shop/item/list";
    public static String MENU_LIST = ADDRESS + "shop/item/category";
}
