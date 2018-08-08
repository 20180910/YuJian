package com.zhizhong.yujian.module.my.network.response;

public class VIPObj {
    /**
     * zong : 1000
     * haixu : 1000
     * regular_member : 免费注册，即可成为普通会员
     * vip_member : 自注册起，在御见APP消费满1000元即可成为VIP会员。
     */

    private double zong;
    private double haixu;
    private String regular_member;
    private String vip_member;

    public double getZong() {
        return zong;
    }

    public void setZong(double zong) {
        this.zong = zong;
    }

    public double getHaixu() {
        return haixu;
    }

    public void setHaixu(double haixu) {
        this.haixu = haixu;
    }

    public String getRegular_member() {
        return regular_member;
    }

    public void setRegular_member(String regular_member) {
        this.regular_member = regular_member;
    }

    public String getVip_member() {
        return vip_member;
    }

    public void setVip_member(String vip_member) {
        this.vip_member = vip_member;
    }
}
