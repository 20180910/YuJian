package com.zhizhong.yujian.module.auction.network.response;

import com.library.base.BaseObj;

import java.math.BigDecimal;

public class ChuJiaObj extends BaseObj {

    /**
     * price : 1200.0
     */

    private BigDecimal price;

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
