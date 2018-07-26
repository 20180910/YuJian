package com.zhizhong.yujian.module.mall.network.response;

import com.library.base.BaseObj;

import java.util.List;

public class MallGoodsObj extends BaseObj {
    private List<MallShufflingListBean> mall_shuffling_list;
    private List<GoodsTypeBean> goods_type;

    public List<MallShufflingListBean> getMall_shuffling_list() {
        return mall_shuffling_list;
    }

    public void setMall_shuffling_list(List<MallShufflingListBean> mall_shuffling_list) {
        this.mall_shuffling_list = mall_shuffling_list;
    }

    public List<GoodsTypeBean> getGoods_type() {
        return goods_type;
    }

    public void setGoods_type(List<GoodsTypeBean> goods_type) {
        this.goods_type = goods_type;
    }

    public static class MallShufflingListBean {
        /**
         * img_url : http://121.40.186.118:10011/upload/mall.jpg
         * goods_id : 1
         */

        private String img_url;
        private String goods_id;

        public String getImg_url() {
            return img_url;
        }

        public void setImg_url(String img_url) {
            this.img_url = img_url;
        }

        public String getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(String goods_id) {
            this.goods_id = goods_id;
        }
    }

    public static class GoodsTypeBean {
        /**
         * goods_type_id : 1
         * goods_type_name : 新品推荐
         * goods_type_img : http://121.40.186.118:10011/upload/xptj.png
         */

        private String goods_type_id;
        private String goods_type_name;
        private String goods_type_img;

        public String getGoods_type_id() {
            return goods_type_id;
        }

        public void setGoods_type_id(String goods_type_id) {
            this.goods_type_id = goods_type_id;
        }

        public String getGoods_type_name() {
            return goods_type_name;
        }

        public void setGoods_type_name(String goods_type_name) {
            this.goods_type_name = goods_type_name;
        }

        public String getGoods_type_img() {
            return goods_type_img;
        }

        public void setGoods_type_img(String goods_type_img) {
            this.goods_type_img = goods_type_img;
        }
    }
}
