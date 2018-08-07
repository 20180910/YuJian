package com.zhizhong.yujian.module.my.network.response;

import java.util.List;

public class FaBiaoEvaluationObj {
    /**
     * goods_id : 27
     * goods_images : http://121.40.186.118:10011/upload/goods.png
     * stars_num : 5
     */

    private String goods_id;
    private String goods_images;
    private int stars_num;

    private List<String> image_list;

    private String content;

    public String getGoods_id() {
        return goods_id;
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
    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getGoods_images() {
        return goods_images;
    }

    public void setGoods_images(String goods_images) {
        this.goods_images = goods_images;
    }

    public int getStars_num() {
        return stars_num;
    }

    public void setStars_num(int stars_num) {
        this.stars_num = stars_num;
    }
}
