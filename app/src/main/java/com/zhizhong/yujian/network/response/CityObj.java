package com.zhizhong.yujian.network.response;

import java.util.List;

/**
 * Created by Administrator on 2017/7/21.
 */

public class CityObj {
    private int id;
    private String title;
    private String parent_id;
    private List<CityObj> pca_list;

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

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public List<CityObj> getPca_list() {
        return pca_list;
    }

    public void setPca_list(List<CityObj> pca_list) {
        this.pca_list = pca_list;
    }
}
