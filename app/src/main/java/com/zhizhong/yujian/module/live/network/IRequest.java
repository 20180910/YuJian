package com.zhizhong.yujian.module.live.network;

import com.library.base.ResponseObj;
import com.zhizhong.yujian.module.home.network.response.LiveObj;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by Administrator on 2017/6/28.
 */

public interface IRequest {

    @GET("api/Live/GetLiveList")
    Call<ResponseObj<List<LiveObj>>> getLiveList(@QueryMap Map<String, String> map);

}
