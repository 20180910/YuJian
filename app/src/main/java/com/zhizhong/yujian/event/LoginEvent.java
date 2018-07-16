package com.zhizhong.yujian.event;


import com.zhizhong.yujian.module.my.network.response.LoginObj;

public class LoginEvent {
    public LoginObj obj;

    public LoginEvent(LoginObj obj) {
        this.obj = obj;
    }
}
