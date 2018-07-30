package com.zhizhong.yujian.module.mall.network;

import com.library.base.BaseObj;
import com.library.base.ResponseObj;
import com.zhizhong.yujian.module.mall.network.response.GoodsDetailObj;
import com.zhizhong.yujian.module.mall.network.response.GoodsEvaluationNumObj;
import com.zhizhong.yujian.module.mall.network.response.GoodsEvaluationObj;
import com.zhizhong.yujian.module.mall.network.response.MallGoodsObj;
import com.zhizhong.yujian.module.mall.network.response.ShoppingCartObj;
import com.zhizhong.yujian.module.mall.network.response.YouHuiQuanObj;
import com.zhizhong.yujian.module.my.network.response.AddressObj;
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

    //购物车列表
    @GET("api/ShoppingCart/GetShoppingCart")
    Call<ResponseObj<ShoppingCartObj>> getShoppingCart(@QueryMap Map<String, String> map);

    //购物车删除
    @GET("api/ShoppingCart/GetDelShoppingCart")
    Call<ResponseObj<BaseObj>> deleteShoppingCart(@QueryMap Map<String, String> map);

    //购物车加减数量
    @GET("api/ShoppingCart/GetEditNum")
    Call<ResponseObj<BaseObj>> updateShoppingCartNum(@QueryMap Map<String, String> map);

    //获取默认地址
    @GET("api/GoodsClassiFication/GetDefault")
    Call<ResponseObj<List<AddressObj>>> getDefaultAddress(@QueryMap Map<String, String> map);

    //领取中心优惠券列表-商品详情
    @GET("api/GoodsClassiFication/GetCouponRedemption")
    Call<ResponseObj<List<YouHuiQuanObj>>> getYouHuiQuan(@QueryMap Map<String, String> map);

    //可用优惠券列表-提交订单
    @GET("api/GoodsClassiFication/GetAvailableCoupon")
    Call<ResponseObj<List<YouHuiQuanObj>>> keYongYouHuiQuan(@QueryMap Map<String, String> map);


    //领取优惠券
    @GET("api/GoodsClassiFication/GetAddCoupon")
    Call<ResponseObj<BaseObj>> lingQuYouHuiQuan(@QueryMap Map<String, String> map);


    //商品评价数量
    @GET("api/GoodsClassiFication/GetAppraiseNum")
    Call<ResponseObj<GoodsEvaluationNumObj>> getGoodsAllEvaluationNum(@QueryMap Map<String, String> map);

    //商品评价
    @GET("api/GoodsClassiFication/GetAppraiseList")
    Call<ResponseObj<List<GoodsEvaluationObj>>> getGoodsAllEvaluation(@QueryMap Map<String, String> map);


}
