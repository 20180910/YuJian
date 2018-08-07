package com.zhizhong.yujian.module.my.network.response;

import java.util.List;

public class MyEvaluationObj {

    /**
     * appraise_id : 21
     * photo : http://121.40.186.118:10011/upload/201807/25/180725192523624794.jpg
     * nickname : 哦哈
     * goods_id : 27
     * goods_name : 御见翡翠本命佛项链吊坠缅甸天然弥勒佛玉吊坠男女款笑肚玉天然佛公生日礼物
     * goods_img : http://121.40.186.118:10011/upload/goods.png
     * price : 500
     * content : hggff
     * is_after_review : 1
     * image_list : ["http://121.40.186.118:10011/upload/201808/07/180807175157959142.jpg"]
     * add_time : 2018/8/7
     * after_review : {"appraise_id":21,"content":"gffggh","img_list":["http://121.40.186.118:10011/upload/201808/07/180807175243512070.jpg"]}
     */

    private String appraise_id;
    private String photo;
    private String nickname;
    private String goods_id;
    private String goods_name;
    private String goods_img;
    private double price;
    private String content;
    private int is_after_review;
    private String add_time;
    private AfterReviewBean after_review;
    private List<String> image_list;

    public String getAppraise_id() {
        return appraise_id;
    }

    public void setAppraise_id(String appraise_id) {
        this.appraise_id = appraise_id;
    }

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

    public String getGoods_img() {
        return goods_img;
    }

    public void setGoods_img(String goods_img) {
        this.goods_img = goods_img;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getIs_after_review() {
        return is_after_review;
    }

    public void setIs_after_review(int is_after_review) {
        this.is_after_review = is_after_review;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public AfterReviewBean getAfter_review() {
        return after_review;
    }

    public void setAfter_review(AfterReviewBean after_review) {
        this.after_review = after_review;
    }

    public List<String> getImage_list() {
        return image_list;
    }

    public void setImage_list(List<String> image_list) {
        this.image_list = image_list;
    }

    public static class AfterReviewBean {
        /**
         * appraise_id : 21
         * content : gffggh
         * img_list : ["http://121.40.186.118:10011/upload/201808/07/180807175243512070.jpg"]
         */

        private String appraise_id;
        private String content;
        private List<String> img_list;

        public String getAppraise_id() {
            return appraise_id;
        }

        public void setAppraise_id(String appraise_id) {
            this.appraise_id = appraise_id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public List<String> getImg_list() {
            return img_list;
        }

        public void setImg_list(List<String> img_list) {
            this.img_list = img_list;
        }
    }
}
