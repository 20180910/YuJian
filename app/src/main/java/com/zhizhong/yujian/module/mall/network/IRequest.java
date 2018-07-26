package com.zhizhong.yujian.module.mall.network;

import com.library.base.ResponseObj;
import com.zhizhong.yujian.module.mall.network.response.GoodsDetailObj;
import com.zhizhong.yujian.module.mall.network.response.MallGoodsObj;
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
    //商城轮播图，商品分类
    @GET("api/GoodsClassiFication/GetMallShuffling")
    Call<ResponseObj<MallGoodsObj>> getMallGoodsType(@QueryMap Map<String, String> map);

    //商城推荐商品
    @GET("api/GoodsClassiFication/GetRecommend")
    Call<ResponseObj<List<GoodsObj>>> getMallTuiJian(@QueryMap Map<String, String> map);

    //商城商品详情
    @GET("api/GoodsClassiFication/GetGoodsDetails")
    Call<ResponseObj<GoodsDetailObj>> getGoodsDetail(@QueryMap Map<String, String> map);

}
