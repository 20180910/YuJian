package com.zhizhong.yujian.module.my.network.response;

import com.library.base.BaseObj;

public class LoginObj extends BaseObj {

    /**
     * mobile : 15601772922
     * user_id : 2
     * nick_name :
     * avatar :
     * sex :
     * birthday : 2018-07-25
     * province :
     * area :
     * city :
     * amount : 0
     * coupons_count : 0
     * qq_name :
     * weibo_name :
     * wechat_name :
     * is_set_name : 1
     */

    private String mobile;
    private String user_id;
    private String nick_name;
    private String avatar;
    private String sex;
    private String birthday;
    private String province;
    private String area;
    private String city;
    private double amount;
    private int coupons_count;
    private String qq_name;
    private String weibo_name;
    private String wechat_name;
    private int is_set_name;//是否显示设置昵称页(1显示 0不显示)

    public String getMobile() {
        return mobile;
    }


    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getCoupons_count() {
        return coupons_count;
    }

    public void setCoupons_count(int coupons_count) {
        this.coupons_count = coupons_count;
    }

    public String getQq_name() {
        return qq_name;
    }

    public void setQq_name(String qq_name) {
        this.qq_name = qq_name;
    }

    public String getWeibo_name() {
        return weibo_name;
    }

    public void setWeibo_name(String weibo_name) {
        this.weibo_name = weibo_name;
    }

    public String getWechat_name() {
        return wechat_name;
    }

    public void setWechat_name(String wechat_name) {
        this.wechat_name = wechat_name;
    }

    public int getIs_set_name() {
        return is_set_name;
    }

    public void setIs_set_name(int is_set_name) {
        this.is_set_name = is_set_name;
    }
}
