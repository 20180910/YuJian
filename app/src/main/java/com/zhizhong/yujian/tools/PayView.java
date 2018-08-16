package com.zhizhong.yujian.tools;

import android.app.Activity;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.github.androidtools.SPUtils;
import com.github.androidtools.ToastUtils;
import com.github.androidtools.inter.MyOnClickListener;
import com.github.baseclass.view.Loading;
import com.github.mydialog.MySimpleDialog;
import com.github.rxbus.RxBus;
import com.library.base.BaseObj;
import com.sdklibrary.base.ali.pay.MyAliOrderBean;
import com.sdklibrary.base.ali.pay.MyAliPay;
import com.sdklibrary.base.ali.pay.MyAliPayCallback;
import com.sdklibrary.base.ali.pay.PayResult;
import com.sdklibrary.base.wx.inter.MyWXCallback;
import com.sdklibrary.base.wx.pay.MyWXOrderBean;
import com.sdklibrary.base.wx.pay.MyWXPay;
import com.zhizhong.yujian.Config;
import com.zhizhong.yujian.Constant;
import com.zhizhong.yujian.R;
import com.zhizhong.yujian.base.MyCallBack;
import com.zhizhong.yujian.event.PayEvent;
import com.zhizhong.yujian.module.mall.activity.PaySuccessActivity;
import com.zhizhong.yujian.module.my.event.RefreshMyOrderEvent;
import com.zhizhong.yujian.network.NetApiRequest;

import java.util.HashMap;
import java.util.Map;

import static com.zhizhong.yujian.GetSign.getSign;

public class PayView {
    public static void showPay(final Activity mContext, final String orderNo,final double money){
        final MySimpleDialog dialog=new MySimpleDialog(mContext);
        View view = mContext.getLayoutInflater().inflate(R.layout.sure_order_popu, null);
        view.findViewById(R.id.iv_pay_cancle).setOnClickListener(new MyOnClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
                dialog.dismiss();
            }
        });;
        final RadioButton rb_pay_weixin =view.findViewById(R.id.rb_pay_weixin);
        final RadioButton rb_pay_zhifubao =view.findViewById(R.id.rb_pay_zhifubao);
        final RadioButton rb_pay_online = view.findViewById(R.id.rb_pay_online);

        TextView tv_pay_total = view.findViewById(R.id.tv_pay_total);
        tv_pay_total.setText("¥"+money);
        view.findViewById(R.id.tv_pay_commit).setOnClickListener(new MyOnClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
                dialog.dismiss();
                if(rb_pay_online.isChecked()){
                    yuePay(mContext,orderNo,money+"");
                }else if(rb_pay_zhifubao.isChecked()){
                    aliPay(mContext,orderNo,money);
                }else if(rb_pay_weixin.isChecked()){
                    wxPay(mContext,orderNo,money);
                }
            }
        });
        dialog.setContentView(view);
        dialog.setFullWidth();
        dialog.setGravity(Gravity.BOTTOM);
        dialog.show();
    }

    private static void wxPay(final Activity mContext, String orderNo, double money) {
        Loading.show(mContext);
        MyWXOrderBean wxBean=new MyWXOrderBean();
        wxBean.setBody(Constant.orderBody);
        String wxUrl = SPUtils.getString(mContext, Config.payType_WX, null);
        wxBean.setNotifyUrl(wxUrl);
        wxBean.setOut_trade_no(orderNo);
        wxBean.setTotalFee((int)(money*100));
        MyWXPay.newInstance(mContext).startPay(wxBean, new MyWXCallback() {
            @Override
            public void onSuccess() {
                Loading.dismissLoading();
                mContext.startActivity(new Intent(mContext,PaySuccessActivity.class));
                RxBus.getInstance().post(new RefreshMyOrderEvent());
            }
            @Override
            public void onFail() {
                Loading.dismissLoading();
                ToastUtils.showToast(mContext,"支付失败");
            }
            @Override
            public void onCancel() {
                Loading.dismissLoading();
                ToastUtils.showToast(mContext,"支付取消");
            }
        });
    }

    private static void aliPay(final Activity mContext, String orderNo, double price) {
        MyAliOrderBean bean=new MyAliOrderBean();
        bean.setOut_trade_no(orderNo);
        bean.setTotal_amount(price);
        bean.setSubject(Constant.orderSubject);
        bean.setBody(Constant.orderBody);
        String url = SPUtils.getString(mContext, Config.payType_ZFB, null);
        bean.setNotifyUrl(url);

        Loading.show(mContext);
        MyAliPay.newInstance(mContext).startPay(bean, new MyAliPayCallback() {
            @Override
            public void paySuccess(PayResult payResult) {
                Loading.dismissLoading();
                mContext.startActivity(new Intent(mContext,PaySuccessActivity.class));
                RxBus.getInstance().post(new RefreshMyOrderEvent());
            }
            @Override
            public void payFail() {
                Loading.dismissLoading();
                ToastUtils.showToast(mContext,"支付失败");
            }
            @Override
            public void payCancel() {
                Loading.dismissLoading();
                ToastUtils.showToast(mContext,"支付取消");
            }
        });
    }

    public static void yuePay(final Activity mContext,String orderNo,String money) {
        Loading.show(mContext);
        Map<String,String> map=new HashMap<String,String>();
        map.put("user_id",getUserId(mContext));
        map.put("order_no",orderNo);
        map.put("money",money);
        map.put("sign",getSign(map));
        NetApiRequest.yuePay(map, new MyCallBack<BaseObj>(mContext) {
            @Override
            public void onSuccess(BaseObj obj, int errorCode, String msg) {
                RxBus.getInstance().post(new PayEvent());
                Intent intent=new Intent(mContext,PaySuccessActivity.class);
                mContext.startActivity(intent);
            }
        });
    }
    public static String getUserId(Activity mContext) {
        return SPUtils.getString(mContext, Config.user_id, "0");
    }
}
