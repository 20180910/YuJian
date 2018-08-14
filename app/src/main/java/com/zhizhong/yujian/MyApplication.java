package com.zhizhong.yujian;


import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.aspsine.multithreaddownload.DownloadConfiguration;
import com.aspsine.multithreaddownload.DownloadManager;
import com.github.androidtools.SPUtils;
import com.github.baseclass.view.Loading;
import com.github.retrofitutil.NetWorkManager;
import com.hyphenate.chat.ChatClient;
import com.hyphenate.helpdesk.easeui.UIProvider;
import com.iflytek.cloud.SpeechUtility;
import com.sdklibrary.base.ali.pay.MyAliPay;
import com.sdklibrary.base.qq.share.MyQQShare;
import com.sdklibrary.base.wx.pay.MyWXPay;
import com.sdklibrary.base.wx.share.MyWXShare;
import com.tencent.rtmp.TXLiveBase;


/**
 * Created by administartor on 2017/8/8.
 */

public class MyApplication extends MultiDexApplication {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
    @Override
    public void onCreate() {
        SpeechUtility.createUtility(this, "appid=" + Config.xunfei_app_id);
        super.onCreate();
        if(true&&BuildConfig.DEBUG){
            //测试
            NetWorkManager.getInstance(getApplicationContext(),"http://121.40.186.118:10011/",BuildConfig.DEBUG).complete();
        }else{
            //正式
            NetWorkManager.getInstance(getApplicationContext(),"http://121.40.186.118:10011/",BuildConfig.DEBUG).complete();
        }
        initDownloader();
        Loading.setLoadView(R.layout.app_loading_view);


        huanXin();
        MyWXShare.setAppId(Config.weixing_id,Config.weixing_AppSecret);
        MyQQShare.setAppId(Config.qq_id);
        MyAliPay.setConfig(Config.zhifubao_app_id,Config.zhifubao_pid,Config.zhifubao_rsa2);
        MyWXPay.setConfig(Config.weixing_id,Config.weixing_mch_id,Config.weixing_miyao);


        String sdkver = TXLiveBase.getSDKVersionStr();
        Log.d("liteavsdk", "===liteav sdk version is : " + sdkver);
    }
    private void initDownloader() {
        DownloadConfiguration configuration = new DownloadConfiguration();
        configuration.setMaxThreadNum(8);
        configuration.setThreadNum(2);
        DownloadManager.getInstance().init(getApplicationContext(), configuration);
    }


    private void huanXin() {
        ChatClient.Options options = new ChatClient.Options();
        options.setAppkey(Config.hx_appKey);//必填项，appkey获取地址：kefu.easemob.com，“管理员模式 > 渠道管理 > 手机APP”页面的关联的“AppKey”
        options.setTenantId(Config.hx_tenantId);//必填项，tenantId获取地址：kefu.easemob.com，“管理员模式 > 设置 > 企业信息”页面的“租户ID”

        // Kefu SDK 初始化
        if (!ChatClient.getInstance().init(this, options)){
            return;
        }
        // Kefu EaseUI的初始化
        UIProvider.getInstance().init(this);
        //后面可以设置其他属性
    }


    //经度
   public static double longitude;//=121.432986;
    //纬度
   public static double latitude;//=31.229504;

    /**
     * 经度
     * @param context
     * @return
     */
    public static double getJingDu(Context context){
        if(longitude==0){
            longitude= SPUtils.getFloat(context,Config.longitude,0);
            return longitude;
        }else{
            return longitude;
        }
    }

    /**
     * 纬度
     * @param context
     * @return
     */
    public static double getWeiDu(Context context){
        if(latitude==0){
            latitude= SPUtils.getFloat(context,Config.latitude,0);
            return latitude;
        }else{
            return latitude;
        }
    }
    /**
     * 经度
     * @param context
     * @return
     */
    public static double Lng(Context context){
        if(longitude==0){
            longitude= SPUtils.getFloat(context,Config.longitude,0);
            return longitude;
        }else{
            return longitude;
        }
    }
    /**
     * 纬度
     * @param context
     * @return
     */
    public static double Lat(Context context){
        if(latitude==0){
            latitude= SPUtils.getFloat(context,Config.latitude,0);
            return latitude;
        }else{
            return latitude;
        }
    }
}
