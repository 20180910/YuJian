package com.zhizhong.yujian.module.my.network.response;

import com.library.base.BaseObj;

public class AboutObj extends BaseObj {
    /**
     * logo : http://121.40.186.118:10011/upload/201712/28/201712281114470140.png
     * title : 带你一起遇见原石之美
     * content : 介绍介绍介绍介绍介绍介绍介绍介绍介绍介绍介绍介绍介绍介绍介绍介绍介绍介绍
     */

    private String logo;
    private String title;
    private String content;

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
