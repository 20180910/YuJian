package com.zhizhong.yujian.module.auction.network.response;

import com.library.base.BaseObj;

import java.util.List;

public class PaiMaiListObj extends BaseObj {

    private List<PaiMaiGoodsObj> jijiang_list;
    private List<PaiMaiGoodsObj> tuijian_list;

    public List<PaiMaiGoodsObj> getJijiang_list() {
        return jijiang_list;
    }

    public void setJijiang_list(List<PaiMaiGoodsObj> jijiang_list) {
        this.jijiang_list = jijiang_list;
    }

    public List<PaiMaiGoodsObj> getTuijian_list() {
        return tuijian_list;
    }

    public void setTuijian_list(List<PaiMaiGoodsObj> tuijian_list) {
        this.tuijian_list = tuijian_list;
    }
}
