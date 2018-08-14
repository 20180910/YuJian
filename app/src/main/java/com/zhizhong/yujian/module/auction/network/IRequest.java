package com.zhizhong.yujian.module.auction.network;

import com.library.base.BaseObj;
import com.library.base.ResponseObj;
import com.zhizhong.yujian.module.auction.network.response.BaoZhengJinObj;
import com.zhizhong.yujian.module.auction.network.response.ChuJiaObj;
import com.zhizhong.yujian.module.auction.network.response.PaiMaiBannerObj;
import com.zhizhong.yujian.module.auction.network.response.PaiMaiGoodsDetailObj;
import com.zhizhong.yujian.module.auction.network.response.PaiMaiGoodsObj;
import com.zhizhong.yujian.module.auction.network.response.PaiMaiOrderDetailObj;
import com.zhizhong.yujian.module.auction.network.response.PaiMaiOrderObj;

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

    //拍卖详情-提醒
    @GET("api/Auction/GetRemind")
    Call<ResponseObj<BaseObj>> paiMaiDetailTiXing(@QueryMap Map<String, String> map);

    //拍卖详情-获取价格
    @GET("api/Auction/GetChuJiaPrice")
    Call<ResponseObj<ChuJiaObj>> getChuJiaPrice(@QueryMap Map<String, String> map);

    //拍卖详情-出价
    @GET("api/Auction/GetOfferPrice")
    Call<ResponseObj<BaseObj>> chuJia(@QueryMap Map<String, String> map);

    //拍卖详情-获取保证金
    @GET("api/Auction/GetCashDeposit")
    Call<ResponseObj<BaoZhengJinObj>> getBaoZhengJin(@QueryMap Map<String, String> map);

    //拍卖详情-获取保证金说明
    @GET("api/UserBase/GetCashDepositInstruction")
    Call<ResponseObj<BaoZhengJinObj>> getBaoZhengJinContent(@QueryMap Map<String, String> map);

    //拍卖详情-所有出价
    @GET("api/Auction/GetChuJiaMore")
    Call<ResponseObj<List<ChuJiaObj>>> getAllChuJia(@QueryMap Map<String, String> map);


    //拍卖订单
    @GET("api/Auction/GetMyAuction")
    Call<ResponseObj<List<PaiMaiOrderObj>>> getPaiMaiOrder(@QueryMap Map<String, String> map);

    //拍卖订单详情
    @GET("api/Auction/GetOrderMore")
    Call<ResponseObj<PaiMaiOrderDetailObj>> getPaiMaiOrderDetail(@QueryMap Map<String, String> map);


    //拍卖订单设置地址
    @GET("api/Auction/GetSetAddress")
    Call<ResponseObj<BaseObj>> paiMaiSetAddress(@QueryMap Map<String, String> map);




}
