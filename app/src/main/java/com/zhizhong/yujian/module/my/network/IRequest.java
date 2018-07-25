package com.zhizhong.yujian.module.my.network;

import com.library.base.BaseObj;
import com.library.base.ResponseObj;
import com.zhizhong.yujian.module.my.network.response.AboutObj;
import com.zhizhong.yujian.module.my.network.response.LoginObj;
import com.zhizhong.yujian.module.my.network.response.MessageObj;

import java.util.List;
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

    //获取用户资料
    @GET("api/UserBase/GetUserInfo")
    Call<ResponseObj<LoginObj>> getUserInfo(@QueryMap Map<String, String> map);

    //我的消息
    @GET("api/UserBase/GetNewsList")
    Call<ResponseObj<List<MessageObj>>> getMessageList(@QueryMap Map<String, String> map);

    //消息开关
    @GET("api/UserBase/GetMessageSink")
    Call<ResponseObj<BaseObj>> setMessageSink(@QueryMap Map<String, String> map);

    //意见反馈
    @GET("api/UserBase/GetSubmitFeedback")
    Call<ResponseObj<BaseObj>> fanKui(@QueryMap Map<String, String> map);

    //关于
    @GET("api/UserBase/GetAbout")
    Call<ResponseObj<AboutObj>> about(@QueryMap Map<String, String> map);

}
