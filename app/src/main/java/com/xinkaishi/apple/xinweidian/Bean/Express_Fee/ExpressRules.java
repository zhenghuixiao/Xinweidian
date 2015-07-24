package com.xinkaishi.apple.xinweidian.Bean.Express_Fee;

/**
 * 项目名称：Xinweidian
 * 类描述：
 * 创建人：apple
 * 创建时间：15/7/24 下午3:22
 * 修改人：apple
 * 修改时间：15/7/24 下午3:22
 * 修改备注：
 */
public class ExpressRules {
    private int fee_l;
    private int fee_h;
    private double percent;

    public int getFee_l() {
        return fee_l;
    }

    public void setFee_l(int fee_l) {
        this.fee_l = fee_l;
    }

    public int getFee_h() {
        return fee_h;
    }

    public void setFee_h(int fee_h) {
        this.fee_h = fee_h;
    }

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }
}
