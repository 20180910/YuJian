package com.zhizhong.yujian.module.my.network.response;

import java.io.Serializable;
import java.util.List;

public class KeMaiHuiObj implements Serializable{
    /**
     * recipient :
     * phone :
     * address :
     * list2 : [{"user_id":2,"order_no":"P180808100111017394","goods_id":28,"goods_name":"可卖回原石2","goods_images":"http://121.40.186.118:10011/upload/201808/08/201808080945294736.png","goods_unitprice":1,"kemai_price":0.8,"ransom_status":0,"payment_add_time":"2018-08-08 10:01:16"}]
     */

    private String recipient;
    private String phone;
    private String address;
    private List<List2Bean> list2;

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<List2Bean> getList2() {
        return list2;
    }

    public void setList2(List<List2Bean> list2) {
        this.list2 = list2;
    }

    public static class List2Bean implements Serializable{
        /**
         * user_id : 2
         * order_no : P180808100111017394
         * goods_id : 28
         * goods_name : 可卖回原石2
         * goods_images : http://121.40.186.118:10011/upload/201808/08/201808080945294736.png
         * goods_unitprice : 1.0
         * kemai_price : 0.8
         * ransom_status : 0
         * payment_add_time : 2018-08-08 10:01:16
         */

        private String user_id;
        private String order_no;
        private String goods_id;
        private String goods_name;
        private String goods_images;
        private double goods_unitprice;
        private double kemai_price;
        private int ransom_status;
        private String payment_add_time;

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getOrder_no() {
            return order_no;
        }

        public void setOrder_no(String order_no) {
            this.order_no = order_no;
        }

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

        public String getGoods_images() {
            return goods_images;
        }

        public void setGoods_images(String goods_images) {
            this.goods_images = goods_images;
        }

        public double getGoods_unitprice() {
            return goods_unitprice;
        }

        public void setGoods_unitprice(double goods_unitprice) {
            this.goods_unitprice = goods_unitprice;
        }

        public double getKemai_price() {
            return kemai_price;
        }

        public void setKemai_price(double kemai_price) {
            this.kemai_price = kemai_price;
        }

        public int getRansom_status() {
            return ransom_status;
        }

        public void setRansom_status(int ransom_status) {
            this.ransom_status = ransom_status;
        }

        public String getPayment_add_time() {
            return payment_add_time;
        }

        public void setPayment_add_time(String payment_add_time) {
            this.payment_add_time = payment_add_time;
        }
    }
}
