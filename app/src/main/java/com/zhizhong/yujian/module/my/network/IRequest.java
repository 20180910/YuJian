package com.zhizhong.yujian.module.my.network;

import com.library.base.BaseObj;
import com.library.base.ResponseObj;
import com.zhizhong.yujian.module.mall.network.response.YouHuiQuanObj;
import com.zhizhong.yujian.module.my.network.request.FaBiaoEvaluationBody;
import com.zhizhong.yujian.module.my.network.request.UpdateInfoBody;
import com.zhizhong.yujian.module.my.network.response.AboutObj;
import com.zhizhong.yujian.module.my.network.response.AddressObj;
import com.zhizhong.yujian.module.my.network.response.AliAccountListObj;
import com.zhizhong.yujian.module.my.network.response.BankAccountObj;
import com.zhizhong.yujian.module.my.network.response.BankListObj;
import com.zhizhong.yujian.module.my.network.response.ChongZhiObj;
import com.zhizhong.yujian.module.my.network.response.CollectionGoodsObj;
import com.zhizhong.yujian.module.my.network.response.EvaluationDetailObj;
import com.zhizhong.yujian.module.my.network.response.FaBiaoEvaluationObj;
import com.zhizhong.yujian.module.my.network.response.HelpCenterObj;
import com.zhizhong.yujian.module.my.network.response.KaiHuHangObj;
import com.zhizhong.yujian.module.my.network.response.KeMaiHuiObj;
import com.zhizhong.yujian.module.my.network.response.LoginObj;
import com.zhizhong.yujian.module.my.network.response.MessageObj;
import com.zhizhong.yujian.module.my.network.response.MyBalanceObj;
import com.zhizhong.yujian.module.my.network.response.MyBaoZhengJinObj;
import com.zhizhong.yujian.module.my.network.response.MyEvaluationObj;
import com.zhizhong.yujian.module.my.network.response.MyMoneyObj;
import com.zhizhong.yujian.module.my.network.response.OrderDetailObj;
import com.zhizhong.yujian.module.my.network.response.OrderObj;
import com.zhizhong.yujian.module.my.network.response.TuiHuanHuoDetailObj;
import com.zhizhong.yujian.module.my.network.response.TuiHuanHuoObj;
import com.zhizhong.yujian.module.my.network.response.TuiKuanMoneyObj;
import com.zhizhong.yujian.module.my.network.response.TuiKuanReasonObj;
import com.zhizhong.yujian.module.my.network.response.YouHuiQuanNumObj;
import com.zhizhong.yujian.network.response.WuLiuObj;

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

    //我的收藏
    @GET("api/UserBase/GetMyCollectionList")
    Call<ResponseObj<List<CollectionGoodsObj>>> getMyCollection(@QueryMap Map<String, String> map);

    //我的订单
    @GET("api/UserBase/GetOrderList")
    Call<ResponseObj<List<OrderObj>>> getOrderList(@QueryMap Map<String, String> map);

    //我的订单-取消
    @GET("api/UserBase/GetCancelOrder")
    Call<ResponseObj<BaseObj>> cancelOrder(@QueryMap Map<String, String> map);

    //我的订单-取消
    @GET("api/UserBase/GetDelOrder")
    Call<ResponseObj<BaseObj>> deleteOrder(@QueryMap Map<String, String> map);

    //订单详情
    @GET("api/UserBase/GetOrderMore")
    Call<ResponseObj<OrderDetailObj>> getOrderDetail(@QueryMap Map<String, String> map);

    //订单-确认收货
    @GET("api/UserBase/GetConfirmReceiptGoods")
    Call<ResponseObj<BaseObj>> sureOrder(@QueryMap Map<String, String> map);

    //物流详情
    @GET("api/UserBase/GetLogisticsInformation")
    Call<ResponseObj<WuLiuObj>> getWuLiuDetail(@QueryMap Map<String, String> map);

    //我的余额明细
    @GET("api/UserBase/GetMyBalance")
    Call<ResponseObj<MyBalanceObj>> getMyBalance(@QueryMap Map<String, String> map);

    //充值-缴纳保证金
    @GET("api/CashWithdrawal/GetCreateOrder")
    Call<ResponseObj<ChongZhiObj>> chongZhiOrder(@QueryMap Map<String, String> map);

    //优惠券数量
    @GET("api/UserBase/GetMyCouponsNum")
    Call<ResponseObj<YouHuiQuanNumObj>> getYouHuiQuanNum(@QueryMap Map<String, String> map);

    //我的优惠券
    @GET("api/UserBase/GetMyCoupons")
    Call<ResponseObj<List<YouHuiQuanObj>>> getMyYouHuiQuan(@QueryMap Map<String, String> map);

    //我的余额
    @GET("api/UserBase/GetWithdShow")
    Call<ResponseObj<MyMoneyObj>> getMyMoney(@QueryMap Map<String,String> map);

    //支付宝账户列表
    @GET("api/CashWithdrawal/GetAlipayAccount")
    Call<ResponseObj<List<AliAccountListObj>>> getAliAccountList(@QueryMap Map<String,String> map);

    //删除支付宝账户
    @GET("api/CashWithdrawal/GetDelAlipayAccount")
    Call<ResponseObj<BaseObj>> deleteAliAccount(@QueryMap Map<String,String> map);

    //添加支付宝账户
    @GET("api/CashWithdrawal/GetAddAccount")
    Call<ResponseObj<BaseObj>> addAliAccount(@QueryMap Map<String,String> map);

    //提现
    @GET("api/CashWithdrawal/GetWithdrawals")
    Call<ResponseObj<BaseObj>> tiXian(@QueryMap Map<String,String> map);

    //提现-保证金提现
    @GET("api/CashWithdrawal/GetCashDepositWithdrawal")
    Call<ResponseObj<BaseObj>> tiXianForBaoZhengJin(@QueryMap Map<String,String> map);

    //银行列表
    @GET("api/CashWithdrawal/GetBankList")
    Call<ResponseObj<List<BankListObj>>>getBankList(@QueryMap Map<String,String> map);

    //银行卡账户列表
    @GET("api/CashWithdrawal/GetAccount")
    Call<ResponseObj<List<BankAccountObj>>>getBankAccount(@QueryMap Map<String,String> map);

    //添加银行卡账户
    @GET("api/CashWithdrawal/GetAddAccount")
    Call<ResponseObj<BaseObj>>addBankAccount(@QueryMap Map<String,String> map);

    //删除银行卡账户
    @GET("api/CashWithdrawal/GetDelAccount")
    Call<ResponseObj<BaseObj>>deleteBankAccount(@QueryMap Map<String,String> map);

    //开户行查询说明
    @GET("api/CashWithdrawal/GetCheckOpeningBank")
    Call<ResponseObj<KaiHuHangObj>>getKaiHuHangShuoMing(@QueryMap Map<String,String> map);

    //我的保证金
    @GET("api/UserBase/GetMyCashDeposit")
    Call<ResponseObj<MyBaoZhengJinObj>>myBaoZhengJin(@QueryMap Map<String,String> map);

    //申请退款金额
    @GET("api/UserBase/GetApplyForRefundsMoney")
    Call<ResponseObj<TuiKuanMoneyObj>>tuiKuanMoney(@QueryMap Map<String,String> map);

    //退换货详情
    @GET("api/UserBase/GetMyApplyForRefundsDetails")
    Call<ResponseObj<TuiHuanHuoDetailObj>>tuiHuanHuoDetail(@QueryMap Map<String,String> map);

    //申请退款原因
    @GET("api/UserBase/GetRefundReason")
    Call<ResponseObj<List<TuiKuanReasonObj>>>tuiKuanReason(@QueryMap Map<String,String> map);

    //提交申请退款
    @GET("api/UserBase/GetApplyForRefunds")
    Call<ResponseObj<BaseObj>>tuiKuan(@QueryMap Map<String,String> map);

    //我的退换货列表
    @GET("api/UserBase/GetMyApplyForRefunds")
    Call<ResponseObj<List<TuiHuanHuoObj>>>tuiHuanHuoList(@QueryMap Map<String,String> map);

    //取消申请
    @GET("api/UserBase/GetCancelRequest")
    Call<ResponseObj<BaseObj>>cancelShenQing(@QueryMap Map<String,String> map);

    //取消申请
    @GET("api/UserBase/GetPublishAppraiseImg")
    Call<ResponseObj<List<FaBiaoEvaluationObj>>>faBiaoEvaluationShow(@QueryMap Map<String,String> map);

    //发表评价
    @POST("api/UserBase/PostPublishAppraise")
    Call<ResponseObj<BaseObj>>faBiaoEvaluation(@QueryMap Map<String,String> map,@Body FaBiaoEvaluationBody body);

    //我的评价列表
    @GET("api/UserBase/GetMyAppraise")
    Call<ResponseObj<List<MyEvaluationObj>>>myEvaluationList(@QueryMap Map<String,String> map);

    //再次评价数据显示
    @GET("api/UserBase/GetAppraiseDetail")
    Call<ResponseObj<EvaluationDetailObj>>evaluationDetail(@QueryMap Map<String,String> map);

    //追评
    @POST("api/UserBase/PostAdditionalEvaluation")
    Call<ResponseObj<BaseObj>>againEvaluate(@QueryMap Map<String,String> map,@Body FaBiaoEvaluationObj body);

    //我的可卖回
    @GET("api/UserBase/GetRansom")
    Call<ResponseObj<KeMaiHuiObj>>keMaiHui(@QueryMap Map<String,String> map);

}
