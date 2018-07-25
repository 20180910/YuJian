package com.zhizhong.yujian.module.my.network.response;

import com.library.base.BaseObj;

public class HelpCenterObj extends BaseObj {
    /**
     * title : 为什么无法登陆?
     * content : 答案答案答案答案答案答案答案答案答案答案
     */

    private String title;
    private String content;
    private boolean isCheck;

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
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
