package com.xinkaishi.apple.xinweidian.Bean.Express_Fee;

import java.util.List;

/**
 * 项目名称：Xinweidian
 * 类描述：
 * 创建人：apple
 * 创建时间：15/7/24 下午3:21
 * 修改人：apple
 * 修改时间：15/7/24 下午3:21
 * 修改备注：
 */
public class ExpressData {
    private int min;
    private List<ExpressRules> rules;

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public List<ExpressRules> getRules() {
        return rules;
    }

    public void setRules(List<ExpressRules> rules) {
        this.rules = rules;
    }
}
