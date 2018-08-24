package com.zhizhong.yujian.network.response;

public class LiveStatusObj{
    /**
     * rate_type : 0
     * recv_type : 1
     * status : 0
     */

    private int rate_type;
    private int recv_type;
    private int status;

    public int getRate_type() {
        return rate_type;
    }

    public void setRate_type(int rate_type) {
        this.rate_type = rate_type;
    }

    public int getRecv_type() {
        return recv_type;
    }

    public void setRecv_type(int recv_type) {
        this.recv_type = recv_type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
