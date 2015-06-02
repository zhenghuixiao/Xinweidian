package com.xinkaishi.apple.xinweidian.Until;

import java.util.Calendar;

/**
 * 项目名称：Xinweidian
 * 类描述：
 * 创建人：apple
 * 创建时间：15/6/2 上午10:35
 * 修改人：apple
 * 修改时间：15/6/2 上午10:35
 * 修改备注：
 */
public class GetDate {
    /**
     * 获取系统时间
     * @return
     */
    public static String getDate(){
        Calendar ca = Calendar.getInstance();
        int year = ca.get(Calendar.YEAR);           // 获取年份
        int month = ca.get(Calendar.MONTH);         // 获取月份
        int day = ca.get(Calendar.DATE);            // 获取日
        int minute = ca.get(Calendar.MINUTE);       // 分
        int hour = ca.get(Calendar.HOUR);           // 小时
        int second = ca.get(Calendar.SECOND);       // 秒
        int millisecond = ca.get(Calendar.MILLISECOND); // 毫秒

        String date = year + "/" + (month + 1 )+ "/" + day + " "+ hour + ":" + minute + ":" + second + ":" + millisecond;

        return date;
    }
}
