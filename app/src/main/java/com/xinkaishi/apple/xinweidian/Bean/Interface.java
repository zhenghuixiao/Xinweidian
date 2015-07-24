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

//货源中心
    public static String LOGIN = ADDRESS + "shop/auth/login";//登入
    public static String EXPRESS_FEE = ADDRESS + "shop/trade/post-rule"; //获取运费规则
    public static String GOODS_LIST = ADDRESS + "shop/item/list";//商品列表
    public static String MENU_LIST = ADDRESS + "shop/item/category";//菜单列表
    public static String ORDER_LIST = ADDRESS + "shop/trade/trade-list";//订单列表
    public static String ORDER_POST = ADDRESS + "shop/trade/submit-trade";//提交订单
    public static String ORDER_COLSE = ADDRESS + "shop/trade/cancel-trade";//关闭、取消订单

    //排序的参数
    public static String LIST_SALES = ADDRESS + "&order=sale_amount";
    public static String LIST_PROFIT = ADDRESS + "&order=profit";
    public static String LIST_PRICE = ADDRESS + "&order=price";
//    public static String LIST_SALES = ADDRESS + "&order=price_asc";

    public static String GETIN_MYSHOP = ADDRESS + "shop/shop/add-item";//加入店铺
}
