package com.zhizhong.yujian.module.mall.network.response;

import com.library.base.BaseObj;
import com.zhizhong.yujian.network.response.GoodsObj;

import java.math.BigDecimal;
import java.util.List;

public class GoodsDetailObj extends BaseObj {
    /**
     * goods_id : 1
     * goods_name : 御见翡翠本命佛项链吊坠缅甸天然弥勒佛玉吊坠男女款笑肚玉天然佛公生日礼物
     * goods_image : http://121.40.186.118:10011/upload/goods.png
     * goods_video : http://121.40.186.118:10011/upload/201807/24/201807241616313464.mp4
     * img_list : ["http://121.40.186.118:10011/upload/goods.png","http://121.40.186.118:10011/upload/goods.png","http://121.40.186.118:10011/upload/goods.png","http://121.40.186.118:10011/upload/goods.png","http://121.40.186.118:10011/upload/goods.png","http://121.40.186.118:10011/upload/goods.png"]
     * goods_price : 720000
     * original_price : 800000
     * courier : 0
     * sales_volume : 24
     * addresss : 上海
     * stock : 98
     * pingjia_num : 0
     * pingjia_list : []
     * product_parameter_list : [{"parameter_name":"品牌:","parameter_value":"御见"},{"parameter_name":"材质:","parameter_value":"翡翠"},{"parameter_name":"尺寸:","parameter_value":"15×11mm"},{"parameter_name":"毛重:","parameter_value":"154g"},{"parameter_name":"产地:","parameter_value":"缅甸"}]
     * goods_details_list : ["http://121.40.186.118:10011/upload/goods.png","http://121.40.186.118:10011/upload/goods.png","http://121.40.186.118:10011/upload/goods.png","http://121.40.186.118:10011/upload/goods.png","http://121.40.186.118:10011/upload/goods.png","http://121.40.186.118:10011/upload/goods.png"]
     * tuijian_list : [{"goods_id":10,"goods_image":"http://121.40.186.118:10011/upload/goods.png","goods_name":"御见翡翠本命佛项链吊坠缅甸天然弥勒佛玉吊坠男女款笑肚玉天然佛公生日礼物","goods_price":720000,"sales_volume":24,"original_price":800000,"is_collect":0},{"goods_id":11,"goods_image":"http://121.40.186.118:10011/upload/goods.png","goods_name":"御见翡翠本命佛项链吊坠缅甸天然弥勒佛玉吊坠男女款笑肚玉天然佛公生日礼物","goods_price":720000,"sales_volume":24,"original_price":800000,"is_collect":0},{"goods_id":12,"goods_image":"http://121.40.186.118:10011/upload/goods.png","goods_name":"御见翡翠本命佛项链吊坠缅甸天然弥勒佛玉吊坠男女款笑肚玉天然佛公生日礼物","goods_price":720000,"sales_volume":24,"original_price":800000,"is_collect":0},{"goods_id":13,"goods_image":"http://121.40.186.118:10011/upload/goods.png","goods_name":"御见翡翠本命佛项链吊坠缅甸天然弥勒佛玉吊坠男女款笑肚玉天然佛公生日礼物","goods_price":720000,"sales_volume":24,"original_price":800000,"is_collect":0}]
     * is_collect : 0
     */

    private String goods_id;
    private String goods_name;
    private String goods_image;
    private String goods_video;
    private BigDecimal goods_price;
    private BigDecimal original_price;
    private int courier;
    private int sales_volume;
    private String addresss;
    private int stock;
    private int pingjia_num;
    private int is_collect;
    private List<String> img_list;
    private List<EvaluationObj> pingjia_list;
    private List<ProductParameterListBean> product_parameter_list;
    private List<String> goods_details_list;
    private List<GoodsObj> tuijian_list;

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

    public String getGoods_video() {
        return goods_video;
    }

    public void setGoods_video(String goods_video) {
        this.goods_video = goods_video;
    }

    public BigDecimal getGoods_price() {
        return goods_price;
    }

    public void setGoods_price(BigDecimal goods_price) {
        this.goods_price = goods_price;
    }

    public BigDecimal getOriginal_price() {
        return original_price;
    }

    public void setOriginal_price(BigDecimal original_price) {
        this.original_price = original_price;
    }

    public int getCourier() {
        return courier;
    }

    public void setCourier(int courier) {
        this.courier = courier;
    }

    public int getSales_volume() {
        return sales_volume;
    }

    public void setSales_volume(int sales_volume) {
        this.sales_volume = sales_volume;
    }

    public String getAddresss() {
        return addresss;
    }

    public void setAddresss(String addresss) {
        this.addresss = addresss;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getPingjia_num() {
        return pingjia_num;
    }

    public void setPingjia_num(int pingjia_num) {
        this.pingjia_num = pingjia_num;
    }

    public int getIs_collect() {
        return is_collect;
    }

    public void setIs_collect(int is_collect) {
        this.is_collect = is_collect;
    }

    public List<String> getImg_list() {
        return img_list;
    }

    public void setImg_list(List<String> img_list) {
        this.img_list = img_list;
    }

    public List<EvaluationObj> getPingjia_list() {
        return pingjia_list;
    }

    public void setPingjia_list(List<EvaluationObj> pingjia_list) {
        this.pingjia_list = pingjia_list;
    }

    public List<ProductParameterListBean> getProduct_parameter_list() {
        return product_parameter_list;
    }

    public void setProduct_parameter_list(List<ProductParameterListBean> product_parameter_list) {
        this.product_parameter_list = product_parameter_list;
    }

    public List<String> getGoods_details_list() {
        return goods_details_list;
    }

    public void setGoods_details_list(List<String> goods_details_list) {
        this.goods_details_list = goods_details_list;
    }

    public List<GoodsObj> getTuijian_list() {
        return tuijian_list;
    }

    public void setTuijian_list(List<GoodsObj> tuijian_list) {
        this.tuijian_list = tuijian_list;
    }

    public static class EvaluationObj {
        private String photo;
        private String nickname;
        private String content;
        private String add_time;

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

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }
    }
    public static class ProductParameterListBean {
        /**
         * parameter_name : 品牌:
         * parameter_value : 御见
         */

        private String parameter_name;
        private String parameter_value;

        public String getParameter_name() {
            return parameter_name;
        }

        public void setParameter_name(String parameter_name) {
            this.parameter_name = parameter_name;
        }

        public String getParameter_value() {
            return parameter_value;
        }

        public void setParameter_value(String parameter_value) {
            this.parameter_value = parameter_value;
        }
    }

    /*public static class TuijianListBean {
        *//**
         * goods_id : 10
         * goods_image : http://121.40.186.118:10011/upload/goods.png
         * goods_name : 御见翡翠本命佛项链吊坠缅甸天然弥勒佛玉吊坠男女款笑肚玉天然佛公生日礼物
         * goods_price : 720000
         * sales_volume : 24
         * original_price : 800000
         * is_collect : 0
         *//*

        private int goods_id;
        private String goods_image;
        private String goods_name;
        private int goods_price;
        private int sales_volume;
        private int original_price;
        private int is_collect;

        public int getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(int goods_id) {
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

        public int getGoods_price() {
            return goods_price;
        }

        public void setGoods_price(int goods_price) {
            this.goods_price = goods_price;
        }

        public int getSales_volume() {
            return sales_volume;
        }

        public void setSales_volume(int sales_volume) {
            this.sales_volume = sales_volume;
        }

        public int getOriginal_price() {
            return original_price;
        }

        public void setOriginal_price(int original_price) {
            this.original_price = original_price;
        }

        public int getIs_collect() {
            return is_collect;
        }

        public void setIs_collect(int is_collect) {
            this.is_collect = is_collect;
        }
    }*/
}
