package com.zhizhong.yujian.module.mall.network.response;

import com.library.base.BaseObj;

import java.math.BigDecimal;

public class YouHuiQuanObj extends BaseObj {
    /**
     * id : 3
     * title : 全场商品可用
     * face_value : 10.0
     * available : 100.0
     * begin_time : 2018-07-10 00:00:00
     * end_time : 2018-08-01 23:59:59
     * deadline : 2018-07-10~2018-08-01
     * status : 0
     */

    private String id;
    private String title;
    private BigDecimal face_value;
    private BigDecimal available;
    private String begin_time;
    private String end_time;
    private String deadline;
    private int status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getFace_value() {
        return face_value;
    }

    public void setFace_value(BigDecimal face_value) {
        this.face_value = face_value;
    }

    public BigDecimal getAvailable() {
        return available;
    }

    public void setAvailable(BigDecimal available) {
        this.available = available;
    }

    public String getBegin_time() {
        return begin_time;
    }

    public void setBegin_time(String begin_time) {
        this.begin_time = begin_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
