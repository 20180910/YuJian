package com.zhizhong.yujian.module.mall.network.response;

import com.library.base.BaseObj;
import com.zhizhong.yujian.network.response.GoodsObj;

import java.math.BigDecimal;
import java.util.List;

public class ShoppingCartObj extends BaseObj {
    /**
     * status : 1
     * tuijian : [{"goods_id":26,"goods_image":"http://121.40.186.118:10011/upload/goods.png","goods_name":"御见翡翠本命佛项链吊坠缅甸天然弥勒佛玉吊坠男女款笑肚玉天然佛公生日礼物","goods_price":720000,"sales_volume":24,"original_price":800000,"is_collect":0},{"goods_id":24,"goods_image":"http://121.40.186.118:10011/upload/goods.png","goods_name":"御见翡翠本命佛项链吊坠缅甸天然弥勒佛玉吊坠男女款笑肚玉天然佛公生日礼物","goods_price":720000,"sales_volume":24,"original_price":800000,"is_collect":0},{"goods_id":25,"goods_image":"http://121.40.186.118:10011/upload/goods.png","goods_name":"御见翡翠本命佛项链吊坠缅甸天然弥勒佛玉吊坠男女款笑肚玉天然佛公生日礼物","goods_price":720000,"sales_volume":24,"original_price":800000,"is_collect":0},{"goods_id":15,"goods_image":"http://121.40.186.118:10011/upload/goods.png","goods_name":"御见翡翠本命佛项链吊坠缅甸天然弥勒佛玉吊坠男女款笑肚玉天然佛公生日礼物","goods_price":720000,"sales_volume":24,"original_price":800000,"is_collect":0},{"goods_id":10,"goods_image":"http://121.40.186.118:10011/upload/goods.png","goods_name":"御见翡翠本命佛项链吊坠缅甸天然弥勒佛玉吊坠男女款笑肚玉天然佛公生日礼物","goods_price":720000,"sales_volume":24,"original_price":800000,"is_collect":0},{"goods_id":7,"goods_image":"http://121.40.186.118:10011/upload/goods.png","goods_name":"御见翡翠本命佛项链吊坠缅甸天然弥勒佛玉吊坠男女款笑肚玉天然佛公生日礼物","goods_price":500,"sales_volume":2000,"original_price":500,"is_collect":0},{"goods_id":23,"goods_image":"http://121.40.186.118:10011/upload/goods.png","goods_name":"御见翡翠本命佛项链吊坠缅甸天然弥勒佛玉吊坠男女款笑肚玉天然佛公生日礼物","goods_price":720000,"sales_volume":24,"original_price":800000,"is_collect":0},{"goods_id":21,"goods_image":"http://121.40.186.118:10011/upload/goods.png","goods_name":"御见翡翠本命佛项链吊坠缅甸天然弥勒佛玉吊坠男女款笑肚玉天然佛公生日礼物","goods_price":720000,"sales_volume":24,"original_price":800000,"is_collect":0},{"goods_id":5,"goods_image":"http://121.40.186.118:10011/upload/goods.png","goods_name":"御见翡翠本命佛项链吊坠缅甸天然弥勒佛玉吊坠男女款笑肚玉天然佛公生日礼物","goods_price":720000,"sales_volume":24,"original_price":800000,"is_collect":0},{"goods_id":11,"goods_image":"http://121.40.186.118:10011/upload/goods.png","goods_name":"御见翡翠本命佛项链吊坠缅甸天然弥勒佛玉吊坠男女款笑肚玉天然佛公生日礼物","goods_price":720000,"sales_volume":24,"original_price":800000,"is_collect":0}]
     * shopping_cart_list : [{"id":7,"goods_id":20,"goods_name":"御见翡翠本命佛项链吊坠缅甸天然弥勒佛玉吊坠男女款笑肚玉天然佛公生日礼物","goods_image":"http://121.40.186.118:10011/upload/goods.png","stock":50,"number":1,"price":720000},{"id":6,"goods_id":22,"goods_name":"御见翡翠本命佛项链吊坠缅甸天然弥勒佛玉吊坠男女款笑肚玉天然佛公生日礼物","goods_image":"http://121.40.186.118:10011/upload/goods.png","stock":50,"number":1,"price":720000},{"id":5,"goods_id":24,"goods_name":"御见翡翠本命佛项链吊坠缅甸天然弥勒佛玉吊坠男女款笑肚玉天然佛公生日礼物","goods_image":"http://121.40.186.118:10011/upload/goods.png","stock":50,"number":2,"price":720000},{"id":4,"goods_id":26,"goods_name":"御见翡翠本命佛项链吊坠缅甸天然弥勒佛玉吊坠男女款笑肚玉天然佛公生日礼物","goods_image":"http://121.40.186.118:10011/upload/goods.png","stock":50,"number":1,"price":720000},{"id":3,"goods_id":27,"goods_name":"御见翡翠本命佛项链吊坠缅甸天然弥勒佛玉吊坠男女款笑肚玉天然佛公生日礼物","goods_image":"http://121.40.186.118:10011/upload/goods.png","stock":50,"number":8,"price":720000},{"id":2,"goods_id":1,"goods_name":"御见翡翠本命佛项链吊坠缅甸天然弥勒佛玉吊坠男女款笑肚玉天然佛公生日礼物","goods_image":"http://121.40.186.118:10011/upload/goods.png","stock":98,"number":1,"price":720000}]
     */

    private int status;
    private List<GoodsObj> tuijian;
    private List<ShoppingCartListBean> shopping_cart_list;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<GoodsObj> getTuijian() {
        return tuijian;
    }

    public void setTuijian(List<GoodsObj> tuijian) {
        this.tuijian = tuijian;
    }

    public List<ShoppingCartListBean> getShopping_cart_list() {
        return shopping_cart_list;
    }

    public void setShopping_cart_list(List<ShoppingCartListBean> shopping_cart_list) {
        this.shopping_cart_list = shopping_cart_list;
    }

    public static class ShoppingCartListBean {
        /**
         * id : 7
         * goods_id : 20
         * goods_name : 御见翡翠本命佛项链吊坠缅甸天然弥勒佛玉吊坠男女款笑肚玉天然佛公生日礼物
         * goods_image : http://121.40.186.118:10011/upload/goods.png
         * stock : 50
         * number : 1
         * price : 720000
         */

        private String id;
        private String goods_id;
        private String goods_name;
        private String goods_image;
        private int stock;
        private int number;
        private BigDecimal price;
        private boolean isCheck;

        public boolean isCheck() {
            return isCheck;
        }

        public void setCheck(boolean check) {
            isCheck = check;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public String getGoods_image() {
            return goods_image;
        }

        public void setGoods_image(String goods_image) {
            this.goods_image = goods_image;
        }

        public int getStock() {
            return stock;
        }

        public void setStock(int stock) {
            this.stock = stock;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public BigDecimal getPrice() {
            return price;
        }

        public void setPrice(BigDecimal price) {
            this.price = price;
        }
    }
}
