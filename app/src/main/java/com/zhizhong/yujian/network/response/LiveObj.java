package com.zhizhong.yujian.network.response;

public class LiveObj {
    /**
     * channel_id : 29689_7594
     * channel_name : 房间1
     * channel_status : 0
     * channel_address : http://29689.liveplay.myqcloud.com/live/29689_7594.flv
     * pushStr : rtmp://://29689.livepush.myqcloud.com/live/29689_7594?bizid=29689&txSecret=3226fc56b8fbc944de3409a195176175&txTime=5b72781c
     * create_time : 2018-08-13 14:35:38
     */

    private String channel_id;
    private String channel_name;
    private int channel_status;
    private String channel_address;
    private String pushStr;
    private String create_time;

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
