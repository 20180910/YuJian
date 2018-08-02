package com.zhizhong.yujian.module.my.network.response;

import java.math.BigDecimal;
import java.util.List;

public class MyBalanceObj {
    /**
     * balance : 799
     * balance_detail_list : [{"remark":"提现（申请）","value":-1,"add_time":"2018年8月1日"}]
     */

    private BigDecimal balance;
    private List<BalanceDetailListBean> balance_detail_list;

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
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
         * value : -1
         * add_time : 2018年8月1日
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
