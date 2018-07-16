package com.zhizhong.yujian.module.my.network.response;

import com.library.base.BaseObj;

public class LoginQuestionObj extends BaseObj {
    /**
     * id : 1
     * title : 为什么无法登陆?
     * content : 我怎么知道你为什么无法登陆
     * create_datetime : 2018-07-11 13:15:30
     * update_datetime : 2018-07-11 13:15:30
     * isvalid : true
     */

    private String id;
    private String title;
    private String content;
    private String create_datetime;
    private String update_datetime;
    private boolean isvalid;

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

    public String getCreate_datetime() {
        return create_datetime;
    }

    public void setCreate_datetime(String create_datetime) {
        this.create_datetime = create_datetime;
    }

    public String getUpdate_datetime() {
        return update_datetime;
    }

    public void setUpdate_datetime(String update_datetime) {
        this.update_datetime = update_datetime;
    }

    public boolean isIsvalid() {
        return isvalid;
    }

    public void setIsvalid(boolean isvalid) {
        this.isvalid = isvalid;
    }
}
