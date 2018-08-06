package com.zhizhong.yujian.module.my.network.response;

import java.util.List;

public class MyBaoZhengJinObj {
    /**
     * cash_deposit : 799.0
     * deduct_cash_deposit : 0.0
     * balance_detail_list : [{"remark":"提现（申请）","value":-1,"add_time":"2018年8月6日"}]
     */

    private double cash_deposit;
    private double deduct_cash_deposit;
    private List<BalanceDetailListBean> balance_detail_list;

    public double getCash_deposit() {
        return cash_deposit;
    }

    public void setCash_deposit(double cash_deposit) {
        this.cash_deposit = cash_deposit;
    }

    public double getDeduct_cash_deposit() {
        return deduct_cash_deposit;
    }

    public void setDeduct_cash_deposit(double deduct_cash_deposit) {
        this.deduct_cash_deposit = deduct_cash_deposit;
    }

    public List<BalanceDetailListBean> getBalance_detail_list() {
        return balance_detail_list;
    }

    public void setBalance_detail_list(List<BalanceDetailListBean> balance_detail_list) {
        this.balance_detail_list = balance_detail_list;
    }

    public static class BalanceDetailListBean {
        /**
         * remark : 提现（申请）
         * value : -1.0
         * add_time : 2018年8月6日
         */

        private String remark;
        private double value;
        private String add_time;

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public double getValue() {
            return value;
        }

        public void setValue(double value) {
            this.value = value;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }
    }
}
