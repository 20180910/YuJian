package com.zhizhong.yujian.module.auction.network.response;

import com.library.base.BaseObj;

import java.math.BigDecimal;

public class PaiMaiGoodsObj extends BaseObj {
    /**
     * goods_id : 2
     * goods_image : http://121.40.186.118:10011/upload/goods.png
     * goods_name : 御见翡翠本命佛项链吊坠缅甸天然弥勒佛玉吊坠男女款笑肚玉天然佛公生日礼物
     * chujia_num : 1
     * dangqian_price : 800
     * begin_time : 1532484000
     * end_time : 1532512800
     */

    private String goods_id;
    private String goods_image;
    private String goods_name;
    private int chujia_num;
    private BigDecimal dangqian_price;
    private long begin_time;
    private long end_time;
    private int multiple=1000;
    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getGoods_image() {
        return goods_image;
    }

    public void setGoods_image(String goods_image) {
        this.goods_image = goods_image;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public int getChujia_num() {
        return chujia_num;
    }

    public void setChujia_num(int chujia_num) {
        this.chujia_num = chujia_num;
    }

    public BigDecimal getDangqian_price() {
        return dangqian_price;
    }

    public void setDangqian_price(BigDecimal dangqian_price) {
        this.dangqian_price = dangqian_price;
    }

    public long getBegin_time() {
        return begin_time*multiple;
    }

    public void setBegin_time(long begin_time) {
        this.begin_time = begin_time;
    }

    public long getEnd_time() {
        return end_time*multiple;
    }

    public void setEnd_time(long end_time) {
        this.end_time = end_time;
    }
}
