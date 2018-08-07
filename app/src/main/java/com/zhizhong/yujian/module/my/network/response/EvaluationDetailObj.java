package com.zhizhong.yujian.module.my.network.response;

import java.util.List;

public class EvaluationDetailObj {

    /**
     * appraise_id : 21
     * goods_img : http://121.40.186.118:10011/upload/goods.png
     * number_stars : 5
     * type : 好
     * content : hggff
     * image_list : ["http://121.40.186.118:10011/upload/201808/07/180807175157959142.jpg"]
     * after_review : {"appraise_id":21,"content":"gffggh","image_list":["http://121.40.186.118:10011/upload/201808/07/180807175243512070.jpg"]}
     */

    private String appraise_id;
    private String goods_img;
    private int number_stars;
    private String type;
    private String content;
    private AfterReviewBean after_review;
    private List<String> image_list;

    public String getAppraise_id() {
        return appraise_id;
    }

    public void setAppraise_id(String appraise_id) {
        this.appraise_id = appraise_id;
    }

    public String getGoods_img() {
        return goods_img;
    }

    public void setGoods_img(String goods_img) {
        this.goods_img = goods_img;
    }

    public int getNumber_stars() {
        return number_stars;
    }

    public void setNumber_stars(int number_stars) {
        this.number_stars = number_stars;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
         * image_list : ["http://121.40.186.118:10011/upload/201808/07/180807175243512070.jpg"]
         */

        private String appraise_id;
        private String content;
        private List<String> image_list;

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

        public List<String> getImage_list() {
            return image_list;
        }

        public void setImage_list(List<String> image_list) {
            this.image_list = image_list;
        }
    }
}
