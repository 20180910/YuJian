package com.zhizhong.yujian.module.mall.network.request;

import java.util.List;

public class CommitOrderBody {

    private List<BodyBean> body;

    public List<BodyBean> getBody() {
        return body;
    }

    public void setBody(List<BodyBean> body) {
        this.body = body;
    }

    public static class BodyBean {
        /**
         * shopping_cart_id : 1
         * goods_id : 2
         * number : 3
         */

        private String shopping_cart_id;
        private String goods_id;
        private int number;

        public String getShopping_cart_id() {
            return shopping_cart_id;
        }

        public void setShopping_cart_id(String shopping_cart_id) {
            this.shopping_cart_id = shopping_cart_id;
        }

        public String getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(String goods_id) {
            this.goods_id = goods_id;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }
    }
}

