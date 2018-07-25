package com.zhizhong.yujian;


import android.app.Application;
import android.content.Context;

import com.aspsine.multithreaddownload.DownloadConfiguration;
import com.aspsine.multithreaddownload.DownloadManager;
import com.github.androidtools.SPUtils;
import com.github.baseclass.view.Loading;
import com.github.retrofitutil.NetWorkManager;
import com.sdklibrary.base.ali.pay.MyAliPay;
import com.sdklibrary.base.qq.share.MyQQShare;
import com.sdklibrary.base.wx.pay.MyWXPay;
import com.sdklibrary.base.wx.share.MyWXShare;


/**
 * Created by administartor on 2017/8/8.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
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

        MyWXShare.setAppId(Config.weixing_id,Config.weixing_AppSecret);
        MyQQShare.setAppId(Config.qq_id);
        MyAliPay.setConfig(Config.zhifubao_app_id,Config.zhifubao_pid,Config.zhifubao_rsa2);
        MyWXPay.setConfig(Config.weixing_id,Config.weixing_mch_id,Config.weixing_miyao);
    }
    private void initDownloader() {
        DownloadConfiguration configuration = new DownloadConfiguration();
        configuration.setMaxThreadNum(8);
        configuration.setThreadNum(2);
        DownloadManager.getInstance().init(getApplicationContext(), configuration);
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
