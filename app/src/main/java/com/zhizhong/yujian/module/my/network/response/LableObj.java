package com.zhizhong.yujian.module.my.network.response;

import com.library.base.BaseObj;

public class LableObj extends BaseObj {
    /**
     * id : 1
     * title : 爱情
     */

    private int id;
    private String title;
    private boolean isSelect;

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
