package com.zhizhong.yujian.module.home.network;

import com.library.base.ResponseObj;
import com.zhizhong.yujian.module.home.network.response.LiveObj;
import com.zhizhong.yujian.module.home.network.response.ZiXunDetailObj;
import com.zhizhong.yujian.module.home.network.response.ZiXunObj;
import com.zhizhong.yujian.network.response.GoodsObj;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by Administrator on 2017/6/28.
 */

public interface IRequest {
    @GET("api/HomePage/GetInformation")
    Call<ResponseObj<List<ZiXunObj>>> getHomeZiXun(@QueryMap Map<String, String> map);

    @GET("api/HomePage/GetInformation")
    Call<ResponseObj<ZiXunDetailObj>> getZiXunDetail(@QueryMap Map<String, String> map);

    @GET("api/HomePage/GetHomeRecommend")
    Call<ResponseObj<List<GoodsObj>>> getHomeTuiJian(@QueryMap Map<String, String> map);

    @GET("api/HomePage/GetHomeRecommendMore")
    Call<ResponseObj<List<GoodsObj>>> getAllTuiJian(@QueryMap Map<String, String> map);

    @GET("api/Live/GetLiveList")
    Call<ResponseObj<List<LiveObj>>> getLiveList(@QueryMap Map<String, String> map);


}
