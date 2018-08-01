package com.zhizhong.yujian.module.my.network.response;

import java.math.BigDecimal;
import java.util.List;

public class OrderObj {
    /**
     * order_no : P180801203215738554
     * goods_list : [{"order_no":"P180801203215738554","goods_id":26,"goods_name":"御见翡翠本命佛项链吊坠缅甸天然弥勒佛玉吊坠男女款笑肚玉天然佛公生日礼物","goods_images":"http://121.40.186.118:10011/upload/goods.png","goods_unitprice":720000,"goods_number":1}]
     * goods_list_count : 1
     * combined : 720000
     * order_status : 1
     * return_goods_status : false
     * refund_status : null
     * create_add_time : 2018-08-01 20:32:15
     */

    private String order_no;
    private int goods_list_count;
    private BigDecimal combined;
    private int order_status;
    private boolean return_goods_status;
    private int refund_status;
    private String create_add_time;
    private List<GoodsListBean> goods_list;

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public int getGoods_list_count() {
        return goods_list_count;
    }

    public void setGoods_list_count(int goods_list_count) {
        this.goods_list_count = goods_list_count;
    }

    public BigDecimal getCombined() {
        return combined;
    }

    public void setCombined(BigDecimal combined) {
        this.combined = combined;
    }

    public int getOrder_status() {
        return order_status;
    }

    public void setOrder_status(int order_status) {
        this.order_status = order_status;
    }

    public boolean isReturn_goods_status() {
        return return_goods_status;
    }

    public void setReturn_goods_status(boolean return_goods_status) {
        this.return_goods_status = return_goods_status;
    }

    public int getRefund_status() {
        return refund_status;
    }

    public void setRefund_status(int refund_status) {
        this.refund_status = refund_status;
    }

    public String getCreate_add_time() {
        return create_add_time;
    }

    public void setCreate_add_time(String create_add_time) {
        this.create_add_time = create_add_time;
    }

    public List<GoodsListBean> getGoods_list() {
        return goods_list;
    }

    public void setGoods_list(List<GoodsListBean> goods_list) {
        this.goods_list = goods_list;
    }

    public static class GoodsListBean {
        /**
         * order_no : P180801203215738554
         * goods_id : 26
         * goods_name : 御见翡翠本命佛项链吊坠缅甸天然弥勒佛玉吊坠男女款笑肚玉天然佛公生日礼物
         * goods_images : http://121.40.186.118:10011/upload/goods.png
         * goods_unitprice : 720000
         * goods_number : 1
         */

        private String order_no;
        private String goods_id;
        private String goods_name;
        private String goods_images;
        private BigDecimal goods_unitprice;
        private int goods_number;

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

        public BigDecimal getGoods_unitprice() {
            return goods_unitprice;
        }

        public void setGoods_unitprice(BigDecimal goods_unitprice) {
            this.goods_unitprice = goods_unitprice;
        }

        public int getGoods_number() {
            return goods_number;
        }

        public void setGoods_number(int goods_number) {
            this.goods_number = goods_number;
        }
    }
}
