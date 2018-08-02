package com.zhizhong.yujian.module.my.biz;

import android.content.Context;
import android.content.DialogInterface;

import com.github.androidtools.SPUtils;
import com.github.androidtools.ToastUtils;
import com.github.baseclass.view.Loading;
import com.github.mydialog.MyDialog;
import com.github.rxbus.RxBus;
import com.library.base.BaseObj;
import com.zhizhong.yujian.Config;
import com.zhizhong.yujian.base.MyCallBack;
import com.zhizhong.yujian.event.MyOrderEvent;
import com.zhizhong.yujian.module.my.fragment.MyOrderFragment;
import com.zhizhong.yujian.module.my.network.ApiRequest;

import java.util.HashMap;
import java.util.Map;

import static com.zhizhong.yujian.GetSign.getSign;

public class OrderUtil {
    private static void showLoading(Context mContext) {
        Loading.show(mContext);
    }
    protected static String getUserId(Context mContext) {
        return SPUtils.getString(mContext, Config.user_id, "0");
    }
    public static void evaluationOrder(final Context mContext,final String order_no){

    }
    public static void tuikuanOrder(final Context mContext, final String order_no){


    }
    public static void sureOrder(final Context mContext, final String order_no){
//        sureOrder(orderNo);
    }
    public static void showPay(final Context mContext, final String order_no){
//        showPay();
    }
    public static void deleteOrder(final Context mContext, final String order_no){
        MyDialog.Builder mDialog=new MyDialog.Builder(mContext);
        mDialog.setMessage("是否确认删除订单?");
        mDialog.setNegativeButton(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        mDialog.setPositiveButton(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                showLoading(mContext);
                Map<String,String>map=new HashMap<String,String>();
                map.put("user_id",getUserId(mContext));
                map.put("order_no",order_no);
                map.put("sign",getSign(map));
                ApiRequest.deleteOrder(map, new MyCallBack<BaseObj>(mContext,true) {
                    @Override
                    public void onSuccess(BaseObj obj, int errorCode, String msg) {
                        ToastUtils.showToast(mContext,msg);
                        RxBus.getInstance().post(new MyOrderEvent(MyOrderFragment.type_0));
                    }
                });
            }
        });
        mDialog.create().show();
    }

    public static void cancelOrder(final Context mContext,final String order_no){
        MyDialog.Builder mDialog=new MyDialog.Builder(mContext);
        mDialog.setMessage("是否确认取消订单?");
        mDialog.setNegativeButton(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        mDialog.setPositiveButton(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Loading.show(mContext);
                Map<String,String> map=new HashMap<String,String>();
                map.put("user_id",getUserId(mContext));
                map.put("order_no",order_no);
                map.put("sign",getSign(map));
                ApiRequest.cancelOrder(map, new MyCallBack<BaseObj>(mContext,true) {
                    @Override
                    public void onSuccess(BaseObj obj, int errorCode, String msg) {
                        ToastUtils.showToast(mContext,msg);
                        RxBus.getInstance().post(new MyOrderEvent(MyOrderFragment.type_0));
                    }
                });
            }
        });
        mDialog.create().show();
    }
}
