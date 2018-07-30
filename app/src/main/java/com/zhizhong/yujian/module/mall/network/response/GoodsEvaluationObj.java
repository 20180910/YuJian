package com.zhizhong.yujian.module.mall.network.response;

import com.library.base.BaseObj;

import java.util.List;

public class GoodsEvaluationObj extends BaseObj {

    /**
     * photo : http://121.40.186.118:10011/upload/201807/25/180725192523624794.jpg
     * nickname : 哦哈
     * img_list : ["http://121.40.186.118:10011/upload/goods.png","http://121.40.186.118:10011/upload/goods.png"]
     * content : 商品很好 很不错
     * add_time : 2018/7/26
     * after_review : [{"appraise_id":3,"content":"是的范德萨范德萨","img_list":["http://121.40.186.118:10011/upload/goods.png"]}]
     */

    private String photo;
    private String nickname;
    private String content;
    private String add_time;
    private List<String> img_list;
    private List<AfterReviewBean> after_review;

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

    public List<String> getImg_list() {
        return img_list;
    }

    public void setImg_list(List<String> img_list) {
        this.img_list = img_list;
    }

    public List<AfterReviewBean> getAfter_review() {
        return after_review;
    }

    public void setAfter_review(List<AfterReviewBean> after_review) {
        this.after_review = after_review;
    }

    public static class AfterReviewBean {
        /**
         * appraise_id : 3
         * content : 是的范德萨范德萨
         * img_list : ["http://121.40.186.118:10011/upload/goods.png"]
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
