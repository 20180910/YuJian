package com.zhizhong.yujian.module.my.network;

import com.library.base.BaseObj;
import com.library.base.ResponseObj;
import com.zhizhong.yujian.module.my.network.response.LoginObj;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by Administrator on 2017/6/28.
 */

public interface IRequest {
    //短信登录
    @GET("api/UserBase/GetUserSMSCodeLogin")
    Call<ResponseObj<LoginObj>> loginForMsg(@QueryMap Map<String, String> map);

    //密码登录
    @GET("api/UserBase/GetUserLogin")
    Call<ResponseObj<LoginObj>> loginForPwd(@QueryMap Map<String, String> map);

    //找回密码
    @GET("api/UserBase/GetResetPassword")
    Call<ResponseObj<BaseObj>> resetPWD(@QueryMap Map<String, String> map);

}
