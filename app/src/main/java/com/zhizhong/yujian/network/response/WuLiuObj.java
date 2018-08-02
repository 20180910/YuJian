package com.zhizhong.yujian.network.response;

import java.util.List;

public class WuLiuObj {
    /**
     * courier_number :
     * courier_name :
     * courier_phone :
     * courier_list : [{"time":"2018/8/2 15:18:38","context":"暂无物流信息"}]
     */

    private String courier_number;
    private String courier_name;
    private String courier_phone;
    private List<CourierListBean> courier_list;

    public String getCourier_number() {
        return courier_number;
    }

    public void setCourier_number(String courier_number) {
        this.courier_number = courier_number;
    }

    public String getCourier_name() {
        return courier_name;
    }

    public void setCourier_name(String courier_name) {
        this.courier_name = courier_name;
    }

    public String getCourier_phone() {
        return courier_phone;
    }

    public void setCourier_phone(String courier_phone) {
        this.courier_phone = courier_phone;
    }

    public List<CourierListBean> getCourier_list() {
        return courier_list;
    }

    public void setCourier_list(List<CourierListBean> courier_list) {
        this.courier_list = courier_list;
    }

    public static class CourierListBean {
        /**
         * time : 2018/8/2 15:18:38
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
