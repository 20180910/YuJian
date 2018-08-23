package com.zhizhong.yujian;


import android.content.Context;
import android.os.Environment;
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
import com.tencent.imsdk.TIMCallBack;
import com.tencent.imsdk.TIMElem;
import com.tencent.imsdk.TIMElemType;
import com.tencent.imsdk.TIMGroupManager;
import com.tencent.imsdk.TIMLogLevel;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMMessage;
import com.tencent.imsdk.TIMMessageListener;
import com.tencent.imsdk.TIMSdkConfig;
import com.tencent.imsdk.TIMTextElem;
import com.tencent.rtmp.TXLiveBase;

import java.util.List;

import static com.hyphenate.chat.adapter.EMACallRtcImpl.TAG;


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
        //初始化 SDK 基本配置
        TIMSdkConfig config = new TIMSdkConfig(Config.IM_APPID)
                .enableCrashReport(false)
                .enableLogPrint(true)
                .setLogLevel(TIMLogLevel.DEBUG)
                .setLogPath(Environment.getExternalStorageDirectory().getPath() + "/justfortest/");

        //初始化 SDK
        TIMManager.getInstance().init(getApplicationContext(), config);

//        a();
    }

    public void Log(String msg) {
        Log.i(TAG + "@@@===", msg);
    }
    private void a() {
        String name="88885502";
        String uisg="eJxlj0FPgzAAhe-8CsLZSClU0cSTwEI6wjaYZl4Ig0KqW8G2IM3if9exJTbxXb8v7*WdDNM0rXyZ3ZZV1Q1MFlL1xDIfTQtYN3*w72ldlLJwef0PkqmnnBRlIwmfoYMQggDoDq0Jk7ShV8P-DUIAaoaoP4p55lLhAeDAOwiQrtB2hkm4fY7XwSGOErwQ9v0geBAisXnPgtx*Xb2MZLnAbZhWe5xiRg5iHbfZBOTwGewi2EXY3vhcqkxMKmfjTjbHvQpSRZOv7dtKJE-apKRHcv3ker774ENPoyPhgnZsFiBwkANdcI5lfBs-tfReZA__";
        TIMManager.getInstance().login(name,uisg, new TIMCallBack() {
            //                TIMManager.getInstance().login(getHXName(),userSig, new TIMCallBack() {
            @Override
            public void onError(int code, String desc) {
                //错误码 code 和错误描述 desc，可用于定位请求失败原因
                //错误码 code 列表请参见错误码表
                Log("==="+"login failed. code: " + code + " errmsg: " + desc);
            }
            @Override
            public void onSuccess() {
                Log("==="+"login succ");
                TIMGroupManager.getInstance().applyJoinGroup("29689_8423628", "申请加群", new TIMCallBack() {
                    public void onError(int code, String desc) {
                        //接口返回了错误码 code 和错误描述 desc，可用于原因
                        //错误码 code 列表请参见错误码表
                        Log("disconnected");
                    }
                    public void onSuccess() {
                        Log("join group");
                    }
                });
            }
        });

        TIMManager.getInstance().addMessageListener(new TIMMessageListener() {//消息监听器
            @Override
            public boolean onNewMessages(List<TIMMessage> msgs) {//收到新消息
                //消息的内容解析请参考消息收发文档中的消息解析说明
                for (int k = 0; k < msgs.size(); k++) {
                    TIMMessage timMessage = msgs.get(k);
                    for(int i = 0; i < timMessage.getElementCount(); ++i) {
                        TIMElem elem = timMessage.getElement(i);
                        //获取当前元素的类型
                        TIMElemType elemType = elem.getType();

                        TIMElem nextElement = timMessage.getElement(i);
                        if (elemType == TIMElemType.Text) {
                            TIMTextElem textElem = (TIMTextElem) nextElement;
                            String text = textElem.getText();
                            Log("==="+text);
                            //处理文本消息
                        } else if (elemType == TIMElemType.Image) {
                            //处理图片消息
                        }//...处理更多消息
                    }
                }
                return false; //返回true将终止回调链，不再调用下一个新消息监听器
            }
        });

    }

    private static class CommonJson<T> {
        String cmd;
        T      data;
    }

    private static final class UserInfo {
        String nickName;
        String headPic;
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
