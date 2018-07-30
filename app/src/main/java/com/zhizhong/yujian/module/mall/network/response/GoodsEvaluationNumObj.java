package com.zhizhong.yujian.module.mall.network.response;

import com.library.base.BaseObj;

public class GoodsEvaluationNumObj extends BaseObj {
    /**
     * haopin : 1
     * zhongpin : 1
     * chapin : 1
     */

    private int haopin;
    private int zhongpin;
    private int chapin;

    public int getHaopin() {
        return haopin;
    }

    public void setHaopin(int haopin) {
        this.haopin = haopin;
    }

    public int getZhongpin() {
        return zhongpin;
    }

    public void setZhongpin(int zhongpin) {
        this.zhongpin = zhongpin;
    }

    public int getChapin() {
        return chapin;
    }

    public void setChapin(int chapin) {
        this.chapin = chapin;
    }
}
