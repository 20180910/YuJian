package com.zhizhong.yujian.module.auction.network.response;

import java.math.BigDecimal;

public class BaoZhengJinObj {
    private BigDecimal cash_deposit;
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public BigDecimal getCash_deposit() {
        return cash_deposit;
    }

    public void setCash_deposit(BigDecimal cash_deposit) {
        this.cash_deposit = cash_deposit;
    }
}
