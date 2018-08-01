package com.zhizhong.yujian.module.auction.network.response;

import com.library.base.BaseObj;

import java.math.BigDecimal;

public class ChuJiaObj extends BaseObj {

    /**
     * price : 1200.0
     */

    private BigDecimal price;
    /**
     * photo : http://121.40.186.118:10011/upload/201807/25/180725192523624794.jpg
     * nickname : 哦哈
     * price : 1300
     * add_time : 2018-08-01 15:28:57
     */

    private String photo;
    private String nickname;
    private String add_time;

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }


    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }
}
