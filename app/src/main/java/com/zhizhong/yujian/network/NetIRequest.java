package com.zhizhong.yujian.network;

import com.library.base.BaseObj;
import com.library.base.ResponseObj;
import com.library.base.bean.AppVersionObj;
import com.library.base.bean.PayObj;
import com.zhizhong.yujian.network.request.UploadImgBody;
import com.zhizhong.yujian.network.response.CityObj;
import com.zhizhong.yujian.network.response.CollectObj;
import com.zhizhong.yujian.network.response.ImageObj;
import com.zhizhong.yujian.network.response.ShareObj;

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

public interface NetIRequest {
    @GET("api/Lib/GetSMSCode")
    Call<ResponseObj<BaseObj>> getMsgCode(@QueryMap Map<String, String> map);

    //base64文件上传
    @POST("api/Lib/PostUploadFileBase64")
    Call<ResponseObj<BaseObj>> uploadImg(@QueryMap Map<String, String> map, @Body UploadImgBody body);

    //修改用户头像
    @GET("api/UserBase/GetSetUserAvatar")
    Call<ResponseObj<BaseObj>> setUserImg(@QueryMap Map<String, String> map);

    //获取省市区
    @GET("api/Lib/GetProvinceCityArea")
    Call<ResponseObj<List<CityObj>>> getAllArea(@QueryMap Map<String, String> map);


    //获取所有城市
    @GET("api/MQLib/GetAllCity")
    Call<ResponseObj<List<CityObj>>> getAllCity(@QueryMap Map<String, String> map);


    //获取所有城市
    @GET("api/MQTravelStudyAbroad/GetTravelStudyAbroad")
    Call<ResponseObj<ImageObj>> getYouXueObj(@QueryMap Map<String, String> map);

    //获取支付url
    @GET("api/MQLib/GetPayWay")
    Call<ResponseObj<PayObj>> getPayNotifyUrl(@QueryMap Map<String, String> map);

    //app更新
    @GET("api/Lib/GetVersionUpdate")
    Call<ResponseObj<AppVersionObj>> getAppVersion(@QueryMap Map<String, String> map);

    //分享页面
    @GET("api/MQLib/GetShareInformations")
    Call<ResponseObj<ShareObj>> getShareInformation(@QueryMap Map<String, String> map);

    //购物车数量
    @GET("api/ShoppingCart/GetShoppingCartCount")
    Call<ResponseObj<BaseObj>> getShoppingNum(@QueryMap Map<String, String> map);

    //收藏商品
    @GET("api/UserBase/GetCollection")
    Call<ResponseObj<CollectObj>> collectGoods(@QueryMap Map<String, String> map);

 /*   //第三方登录
    @GET("api/MQLib/GetAddWXUser")
    Call<ResponseObj<LoginObj>> appLogin(@QueryMap Map<String, String> map);
*/
}
