package com.zhizhong.yujian.module.auction.network.response;

import java.math.BigDecimal;

public class PaiMaiOrderObj {
    /**
     * order_no : B180801152857502719
     * userid : 2
     * order_status : 0
     * goods_id : 2
     * goods_img : http://121.40.186.118:10011/upload/goods.png
     * goods_name : 御见翡翠本命佛项链吊坠缅甸天然弥勒佛玉吊坠男女款笑肚玉天然佛公生日礼物
     * goods_price : 2300
     * combined : 2300
     * number : 1
     * add_time : 2018-08-01 15:28:57
     */

    private String order_no;
    private String userid;
    private int order_status;
    private String goods_id;
    private String goods_img;
    private String goods_name;
    private BigDecimal goods_price;
    private BigDecimal combined;
    private int number;
    private String add_time;

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public int getOrder_status() {
        return order_status;
    }

    public void setOrder_status(int order_status) {
        this.order_status = order_status;
    }

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getGoods_img() {
        return goods_img;
    }

    public void setGoods_img(String goods_img) {
        this.goods_img = goods_img;
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

    public BigDecimal getCombined() {
        return combined;
    }

    public void setCombined(BigDecimal combined) {
        this.combined = combined;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }
}
