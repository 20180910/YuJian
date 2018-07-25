package com.zhizhong.yujian.module.my.network.response;

import com.library.base.BaseObj;

public class AddressObj extends BaseObj {
    /**
     * address_id : 1
     * recipient : 啦啦啦
     * phone : 13872228899
     * shipping_address : 上海市上海市长宁区
     * shipping_address_details : 啦啦啦啦啦
     * is_default : 0
     * province : 上海市
     * city : 上海市
     * area : 长宁区
     */

    private String address_id;
    private String recipient;
    private String phone;
    private String shipping_address;
    private String shipping_address_details;
    private int is_default;
    private String province;
    private String city;
    private String area;

    public String getAddress_id() {
        return address_id;
    }

    public void setAddress_id(String address_id) {
        this.address_id = address_id;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getShipping_address() {
        return shipping_address;
    }

    public void setShipping_address(String shipping_address) {
        this.shipping_address = shipping_address;
    }

    public String getShipping_address_details() {
        return shipping_address_details;
    }

    public void setShipping_address_details(String shipping_address_details) {
        this.shipping_address_details = shipping_address_details;
    }

    public int getIs_default() {
        return is_default;
    }

    public void setIs_default(int is_default) {
        this.is_default = is_default;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
}
