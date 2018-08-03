package com.zhizhong.yujian.module.auction.network.response;

import java.math.BigDecimal;
import java.util.List;

public class PaiMaiOrderDetailObj {
    /**
     * order_no : B180801152857502719
     * order_status : 0
     * courier_list : [{"time":"2018/8/3 17:20:52","context":"暂无物流信息"}]
     * address_recipient : null
     * address_phone : null
     * shipping_address : null
     * goods_id : 2
     * goods_name : 御见翡翠本命佛项链吊坠缅甸天然弥勒佛玉吊坠男女款笑肚玉天然佛公生日礼物
     * goods_img : http://121.40.186.118:10011/upload/goods.png
     * goods_price : 2300
     * goods_number : 1
     * code : 1
     * combined : 2300
     * freight : 0
     * invoice_type : null
     * invoice_name : null
     * invoice_tax_number : null
     * youhui_money : 0
     * payment_add_time :
     * create_add_time : 2018-08-01 15:28:57
     * cancel_order_time :
     * pay_id : 无
     * courier_name : null
     * remark : null
     */

    private String order_no;
    private int order_status;
    private String address_recipient;
    private String address_phone;
    private String shipping_address;
    private String goods_id;
    private String goods_name;
    private String goods_img;
    private BigDecimal goods_price;
    private int goods_number;
    private int code;
    private BigDecimal combined;
    private double freight;
    private String invoice_type;
    private String invoice_name;
    private String invoice_tax_number;
    private double youhui_money;
    private String payment_add_time;
    private String create_add_time;
    private String cancel_order_time;
    private String pay_id;
    private String courier_name;
    private String remark;
    private List<CourierListBean> courier_list;

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public int getOrder_status() {
        return order_status;
    }

    public void setOrder_status(int order_status) {
        this.order_status = order_status;
    }

    public String getAddress_recipient() {
        return address_recipient;
    }

    public void setAddress_recipient(String address_recipient) {
        this.address_recipient = address_recipient;
    }

    public String getAddress_phone() {
        return address_phone;
    }

    public void setAddress_phone(String address_phone) {
        this.address_phone = address_phone;
    }

    public String getShipping_address() {
        return shipping_address;
    }

    public void setShipping_address(String shipping_address) {
        this.shipping_address = shipping_address;
    }

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getGoods_img() {
        return goods_img;
    }

    public void setGoods_img(String goods_img) {
        this.goods_img = goods_img;
    }

    public BigDecimal getGoods_price() {
        return goods_price;
    }

    public void setGoods_price(BigDecimal goods_price) {
        this.goods_price = goods_price;
    }

    public int getGoods_number() {
        return goods_number;
    }

    public void setGoods_number(int goods_number) {
        this.goods_number = goods_number;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public BigDecimal getCombined() {
        return combined;
    }

    public void setCombined(BigDecimal combined) {
        this.combined = combined;
    }

    public double getFreight() {
        return freight;
    }

    public void setFreight(double freight) {
        this.freight = freight;
    }

    public String getInvoice_type() {
        return invoice_type;
    }

    public void setInvoice_type(String invoice_type) {
        this.invoice_type = invoice_type;
    }

    public String getInvoice_name() {
        return invoice_name;
    }

    public void setInvoice_name(String invoice_name) {
        this.invoice_name = invoice_name;
    }

    public String getInvoice_tax_number() {
        return invoice_tax_number;
    }

    public void setInvoice_tax_number(String invoice_tax_number) {
        this.invoice_tax_number = invoice_tax_number;
    }

    public double getYouhui_money() {
        return youhui_money;
    }

    public void setYouhui_money(double youhui_money) {
        this.youhui_money = youhui_money;
    }

    public String getPayment_add_time() {
        return payment_add_time;
    }

    public void setPayment_add_time(String payment_add_time) {
        this.payment_add_time = payment_add_time;
    }

    public String getCreate_add_time() {
        return create_add_time;
    }

    public void setCreate_add_time(String create_add_time) {
        this.create_add_time = create_add_time;
    }

    public String getCancel_order_time() {
        return cancel_order_time;
    }

    public void setCancel_order_time(String cancel_order_time) {
        this.cancel_order_time = cancel_order_time;
    }

    public String getPay_id() {
        return pay_id;
    }

    public void setPay_id(String pay_id) {
        this.pay_id = pay_id;
    }

    public String getCourier_name() {
        return courier_name;
    }

    public void setCourier_name(String courier_name) {
        this.courier_name = courier_name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<CourierListBean> getCourier_list() {
        return courier_list;
    }

    public void setCourier_list(List<CourierListBean> courier_list) {
        this.courier_list = courier_list;
    }

    public static class CourierListBean {
        /**
         * time : 2018/8/3 17:20:52
         * context : 暂无物流信息
         */

        private String time;
        private String context;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getContext() {
            return context;
        }

        public void setContext(String context) {
            this.context = context;
        }
    }
}
