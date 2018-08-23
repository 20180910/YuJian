package com.zhizhong.yujian.module.home.network.response;

public class LiveObj {
    /**
     * channel_id : 29689_7594
     * channel_name :
     * channel_status : 0
     * channel_address :
     * pushStr :
     * create_time : 2018-08-13 14:35:38
     */

    private String channel_id;
    private String channel_name;
    private int channel_status;
    private String channel_address;
    private String pushStr;
    private String create_time;
    private String live_user_id;

    public String getLive_user_id() {
        return live_user_id;
    }

    public void setLive_user_id(String live_user_id) {
        this.live_user_id = live_user_id;
    }

    public String getChannel_id() {
        return channel_id;
    }

    public void setChannel_id(String channel_id) {
        this.channel_id = channel_id;
    }

    public String getChannel_name() {
        return channel_name;
    }

    public void setChannel_name(String channel_name) {
        this.channel_name = channel_name;
    }

    public int getChannel_status() {
        return channel_status;
    }

    public void setChannel_status(int channel_status) {
        this.channel_status = channel_status;
    }

    public String getChannel_address() {
        return channel_address;
    }

    public void setChannel_address(String channel_address) {
        this.channel_address = channel_address;
    }

    public String getPushStr() {
        return pushStr;
    }

    public void setPushStr(String pushStr) {
        this.pushStr = pushStr;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }
}
