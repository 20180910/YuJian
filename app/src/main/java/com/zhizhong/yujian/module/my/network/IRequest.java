package com.zhizhong.yujian.module.my.network;

import com.library.base.BaseObj;
import com.library.base.ResponseObj;
import com.zhizhong.yujian.module.my.network.request.UpdateInfoBody;
import com.zhizhong.yujian.module.my.network.response.AboutObj;
import com.zhizhong.yujian.module.my.network.response.AddressObj;
import com.zhizhong.yujian.module.my.network.response.HelpCenterObj;
import com.zhizhong.yujian.module.my.network.response.LoginObj;
import com.zhizhong.yujian.module.my.network.response.MessageObj;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
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

    //修改手机号
    @GET("api/UserBase/GetBindingMobile")
    Call<ResponseObj<BaseObj>> updatePhone(@QueryMap Map<String, String> map);

    //修改密码
    @GET("api/UserBase/GetSetNewPassword")
    Call<ResponseObj<BaseObj>> updatePwd(@QueryMap Map<String, String> map);

    //修改用户信息
    @POST("api/UserBase/PostEditUserInfo")
    Call<ResponseObj<BaseObj>> updateUserInfo(@QueryMap Map<String, String> map, @Body UpdateInfoBody body);

    //帮助中心
    @GET("api/UserBase/GetSupposeYouWantAsk")
    Call<ResponseObj<List<HelpCenterObj>>> helpCenter(@QueryMap Map<String, String> map);

    //地址列表
    @GET("api/ShippingAddress/GetUserAddress")
    Call<ResponseObj<List<AddressObj>>> getAddressList(@QueryMap Map<String, String> map);

    //添加地址
    @GET("api/ShippingAddress/GetSaveUserAddress")
    Call<ResponseObj<BaseObj>> addAddress(@QueryMap Map<String, String> map);

    //删除地址
    @GET("api/ShippingAddress/GetUserAddressDel")
    Call<ResponseObj<BaseObj>> deleteAddress(@QueryMap Map<String, String> map);

    //设置默认地址
    @GET("api/ShippingAddress/GetEditDefalut")
    Call<ResponseObj<BaseObj>> setDefulatAddress(@QueryMap Map<String, String> map);

}
