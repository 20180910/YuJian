package com.zhizhong.yujian.module.my.network.response;

import com.library.base.BaseObj;

public class MessageObj extends BaseObj {
    /**
     * id : 5
     * title : 啦啦啦啦！
     * content : 啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦！
     * add_time : 2018-07-25 14:21:57
     */

    private String id;
    private String title;
    private String content;
    private String add_time;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }
}
