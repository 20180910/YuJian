package com.zhizhong.yujian.module.my.network.response;

import java.math.BigDecimal;

public class CollectionGoodsObj {

    /**
     * goods_id : 2
     * goods_name : 御见翡翠本命佛项链吊坠缅甸天然弥勒佛玉吊坠男女款笑肚玉天然佛公生日礼物
     * goods_image : http://121.40.186.118:10011/upload/goods.png
     * goods_price : 500
     * add_time : 2018-08-01 10:45:46
     * type : 2
     */

    private String goods_id;
    private String goods_name;
    private String goods_image;
    private BigDecimal goods_price;
    private String add_time;
    private int type;//type 类别(1商城商品 2拍卖商品)

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getGoods_image() {
        return goods_image;
    }

    public void setGoods_image(String goods_image) {
        this.goods_image = goods_image;
    }

    public BigDecimal getGoods_price() {
        return goods_price;
    }

    public void setGoods_price(BigDecimal goods_price) {
        this.goods_price = goods_price;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
