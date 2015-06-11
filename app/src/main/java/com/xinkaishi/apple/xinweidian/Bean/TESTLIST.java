package com.xinkaishi.apple.xinweidian.Bean;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 项目名称：Xinweidian
 * 类描述：
 * 创建人：apple
 * 创建时间：15/6/8 上午11:12
 * 修改人：apple
 * 修改时间：15/6/8 上午11:12
 * 修改备注：
 */
public class TESTLIST {
    public static ArrayList<HashMap<String, Object>> list;

    public ArrayList<HashMap<String, Object>> getList(){
        list = new ArrayList<HashMap<String, Object>>();
        for (int b = 0; b < 10; b ++){
            HashMap<String, Object> hm = new  HashMap<String, Object>();
            ArrayList<HashMap<String, Object>> list1 =  new ArrayList<HashMap<String, Object>>();
            if(b == 2){
                HashMap<String, Object> hmm = new HashMap<String, Object>();
                hmm.put("orderNum", "111111111111");
                hmm.put("name", b + "九阳榨汁机");
                hmm.put("format", "A6666");
                hmm.put("num", 3);
                hmm.put("price", "39.90");
                list1.add(hmm);
            }else if(b == 3) {
                for (int a = 0; a < 8; a++) {
                    HashMap<String, Object> hmm = new HashMap<String, Object>();
                    hmm.put("orderNum", "111111111111" + a);
                    hmm.put("name", b + "九阳榨汁机");
                    hmm.put("format", "A6666");
                    hmm.put("num", 3);
                    hmm.put("price", "39.90");
                    list1.add(hmm);
                }
            }else{
                for (int a = 0; a < 3; a++) {
                    HashMap<String, Object> hmm = new HashMap<String, Object>();
                    hmm.put("orderNum", "111111111111" + a);
                    hmm.put("name", b + "九阳榨汁机");
                    hmm.put("format", "A6666");
                    hmm.put("num", 3);
                    hmm.put("price", "39.90");
                    list1.add(hmm);
                }
            }
            hm.put("transaction", "0000000" + b);
            hm.put("child", list1);
            hm.put("state", b);
            list.add(hm);
        }
        return list;
    }
}
