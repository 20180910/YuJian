package com.zhizhong.yujian.module.home.network.response;

public class ZiXunObj {
    /**
     * information_id : 1
     * title : 如何辨别真玉和假玉的区别
     * image_url : http://121.40.186.118:10011/upload/goods.png
     * add_time : 2018-7-26
     */

    private String information_id;
    private String title;
    private String image_url;
    private String add_time;

    public String getInformation_id() {
        return information_id;
    }

    public void setInformation_id(String information_id) {
        this.information_id = information_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }
}
