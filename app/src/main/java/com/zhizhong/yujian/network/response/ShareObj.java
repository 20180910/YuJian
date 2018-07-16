package com.zhizhong.yujian.network.response;

import com.library.base.BaseObj;

/**
 * Created by Administrator on 2018/4/23.
 */

public class ShareObj extends BaseObj {
    /**
     * title : 优优点餐
     * content : 优优点餐，志在打造中国最具人气的、经营者与消费者共赢的点餐平台！
     * share_link : http://121.40.186.118:9011/admin/download.aspx?code=82661186
     */

    private String title;
    private String content;
    private String share_link;

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

    public String getShare_link() {
        return share_link;
    }

    public void setShare_link(String share_link) {
        this.share_link = share_link;
    }
}
