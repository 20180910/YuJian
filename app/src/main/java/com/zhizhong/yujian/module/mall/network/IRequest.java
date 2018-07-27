package com.zhizhong.yujian.module.mall.network;

import com.library.base.BaseObj;
import com.library.base.ResponseObj;
import com.zhizhong.yujian.module.mall.network.response.GoodsDetailObj;
import com.zhizhong.yujian.module.mall.network.response.MallGoodsObj;
import com.zhizhong.yujian.module.mall.network.response.ShoppingCartObj;
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


}
