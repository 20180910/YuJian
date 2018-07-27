package com.zhizhong.yujian.network.response;

import com.library.base.BaseObj;

import java.math.BigDecimal;

public class GoodsObj extends BaseObj {
    /**
     * goods_id : 1
     * goods_image : http://121.40.186.118:10011/upload/goods.png
     * goods_name : 御见翡翠本命佛项链吊坠缅甸天然弥勒佛玉吊坠男女款笑肚玉天然佛公生日礼物
     * goods_price : 720000
     * sales_volume : 24
     * original_price : 800000
     * is_collect : 0
     */

    private String goods_id;
    private String goods_image;
    private String goods_name;
    private BigDecimal goods_price;
    private int sales_volume;
    private BigDecimal original_price;
    private int is_collect;

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

    public BigDecimal getGoods_price() {
        return goods_price;
    }

    public void setGoods_price(BigDecimal goods_price) {
        this.goods_price = goods_price;
    }

    public int getSales_volume() {
        return sales_volume;
    }

    public void setSales_volume(int sales_volume) {
        this.sales_volume = sales_volume;
    }

    public BigDecimal getOriginal_price() {
        return original_price;
    }

    public void setOriginal_price(BigDecimal original_price) {
        this.original_price = original_price;
    }

    public int getIs_collect() {
        return is_collect;
    }

    public void setIs_collect(int is_collect) {
        this.is_collect = is_collect;
    }
}
