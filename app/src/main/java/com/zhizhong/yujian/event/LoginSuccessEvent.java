package com.zhizhong.yujian.event;

/**
 * Created by Administrator on 2018/4/3.
 */

public class LoginSuccessEvent {
    /*0:退出登录，1：登录成功*/
    public static final int status_0=0;
    public static final int status_1=1;
    public int status;

    public LoginSuccessEvent(int status) {
        this.status = status;
    }
}
