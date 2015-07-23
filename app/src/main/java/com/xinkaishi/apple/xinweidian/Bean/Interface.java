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
    private static int state = 0;
    private static String TESTADD = "http://192.168.1.199:4000/";  //0
    private static String ONLINEADD = "http://pc.xinkaishi.com/";  //1
    private static String TESTADD1 = "http://test.pc.xinkaishi.com/";
    public static String ADDRESS = state == 1? ONLINEADD: TESTADD;

    public static String LOGIN = ADDRESS + "shop/auth/login";
    public static String GOODS_LIST = ADDRESS + "shop/item/list";
    public static String MENU_LIST = ADDRESS + "shop/item/category";
    public static String ORDER_LIST = ADDRESS + "shop/trade/trade-list";
    public static String ORDER_POST = ADDRESS + "shop/trade/submit-trade";
    public static String ORDER_COLSE = ADDRESS + "shop/trade/cancel-trade";

    //排序的参数
    public static String LIST_SALES = ADDRESS + "&order=sale_amount";
    public static String LIST_PROFIT = ADDRESS + "&order=profit";
    public static String LIST_PRICE = ADDRESS + "&order=price";
//    public static String LIST_SALES = ADDRESS + "&order=price_asc";
    //加入店铺
    public static String GETIN_MYSHOP = ADDRESS + "shop/shop/add-item";
}
