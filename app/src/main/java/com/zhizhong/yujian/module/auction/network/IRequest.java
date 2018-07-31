package com.zhizhong.yujian.module.auction.network;

import com.library.base.ResponseObj;
import com.zhizhong.yujian.module.auction.network.response.PaiMaiBannerObj;
import com.zhizhong.yujian.module.auction.network.response.PaiMaiGoodsDetailObj;
import com.zhizhong.yujian.module.auction.network.response.PaiMaiGoodsObj;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by Administrator on 2017/6/28.
 */

public interface IRequest {
    //拍卖轮播图
    @GET("api/Auction/GetMallShuffling")
    Call<ResponseObj<PaiMaiBannerObj>> getPaiMaiBanner(@QueryMap Map<String, String> map);

    //即将结拍+推荐专场
    @GET("api/Auction/GetComingEnd")
    Call<ResponseObj<List<PaiMaiGoodsObj>>> getPaiMaiTuiJian(@QueryMap Map<String, String> map);

    //拍卖-全部
    @GET("api/Auction/GetAll")
    Call<ResponseObj<List<PaiMaiGoodsObj>>> getAllPaiMai(@QueryMap Map<String, String> map);

    //拍卖-商品详情
    @GET("api/Auction/GetAuctionDetails")
    Call<ResponseObj<PaiMaiGoodsDetailObj>> getPaiMaiGoodsDetail(@QueryMap Map<String, String> map);

}
