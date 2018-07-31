package com.zhizhong.yujian.module.auction.network.response;

import com.library.base.BaseObj;

import java.util.List;

public class PaiMaiBannerObj extends BaseObj {
    /**
     * shuffling_list : [{"img_url":"http://121.40.186.118:10011/upload/goods.png","goods_id":1},{"img_url":"http://121.40.186.118:10011/upload/goods.png","goods_id":2},{"img_url":"http://121.40.186.118:10011/upload/goods.png","goods_id":3}]
     * zero_title : 0元拍
     * zero_img : http://121.40.186.118:10011/upload/0yuan.jpg
     * new_title : 新人推荐
     * new_img : http://121.40.186.118:10011/upload/new.jpg
     * item_title : 历史拍品
     * item_img : http://121.40.186.118:10011/upload/item.jpg
     */

    private String zero_title;
    private String zero_img;
    private String new_title;
    private String new_img;
    private String item_title;
    private String item_img;
    private List<ShufflingListBean> shuffling_list;

    public String getZero_title() {
        return zero_title;
    }

    public void setZero_title(String zero_title) {
        this.zero_title = zero_title;
    }

    public String getZero_img() {
        return zero_img;
    }

    public void setZero_img(String zero_img) {
        this.zero_img = zero_img;
    }

    public String getNew_title() {
        return new_title;
    }

    public void setNew_title(String new_title) {
        this.new_title = new_title;
    }

    public String getNew_img() {
        return new_img;
    }

    public void setNew_img(String new_img) {
        this.new_img = new_img;
    }

    public String getItem_title() {
        return item_title;
    }

    public void setItem_title(String item_title) {
        this.item_title = item_title;
    }

    public String getItem_img() {
        return item_img;
    }

    public void setItem_img(String item_img) {
        this.item_img = item_img;
    }

    public List<ShufflingListBean> getShuffling_list() {
        return shuffling_list;
    }

    public void setShuffling_list(List<ShufflingListBean> shuffling_list) {
        this.shuffling_list = shuffling_list;
    }

    public static class ShufflingListBean {
        /**
         * img_url : http://121.40.186.118:10011/upload/goods.png
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
}
