package com.zhizhong.yujian.module.home.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.github.androidtools.SPUtils;
import com.github.androidtools.inter.MyOnClickListener;
import com.github.baseclass.permission.PermissionCallback;
import com.github.fastshape.MyRadioButton;
import com.github.mydialog.MyDialog;
import com.github.rxbus.MyConsumer;
import com.github.rxbus.rxjava.MyFlowableSubscriber;
import com.github.rxbus.rxjava.Rx;
import com.hyphenate.chat.ChatClient;
import com.hyphenate.helpdesk.callback.Callback;
import com.hyphenate.helpdesk.util.Log;
import com.library.base.bean.AppVersionObj;
import com.library.base.bean.PayObj;
import com.zhizhong.yujian.AppXml;
import com.zhizhong.yujian.Config;
import com.zhizhong.yujian.Constant;
import com.zhizhong.yujian.GetSign;
import com.zhizhong.yujian.IntentParam;
import com.zhizhong.yujian.R;
import com.zhizhong.yujian.base.BaseActivity;
import com.zhizhong.yujian.base.MyCallBack;
import com.zhizhong.yujian.bean.AppInfo;
import com.zhizhong.yujian.event.LoginSuccessEvent;
import com.zhizhong.yujian.module.auction.fragment.AuctionFragment;
import com.zhizhong.yujian.module.home.fragment.HomeFragment;
import com.zhizhong.yujian.module.home.network.ApiRequest;
import com.zhizhong.yujian.module.home.network.response.SplashObj;
import com.zhizhong.yujian.module.live.fragment.LiveFragment;
import com.zhizhong.yujian.module.mall.fragment.MallFragment;
import com.zhizhong.yujian.module.my.activity.LoginActivity;
import com.zhizhong.yujian.module.my.fragment.MyFragment;
import com.zhizhong.yujian.network.NetApiRequest;
import com.zhizhong.yujian.service.MyAPPDownloadService;
import com.zhizhong.yujian.tools.FileUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import cn.jpush.android.api.JPushInterface;
import io.reactivex.FlowableEmitter;

/**
 * Created by Administrator on 2017/11/4.
 */

public class MainActivity extends BaseActivity {
//    @BindView(R.id.status_bar)
//    View status_bar;

    HomeFragment homeFragment;
    MallFragment foundFragment;
    LiveFragment touGaoFragment;
    AuctionFragment biYouFragment;

    MyFragment myFragment;

    @BindView(R.id.fl_content)
    FrameLayout fl_content;
    @BindView(R.id.rb_home_tab1)
    MyRadioButton rb_home_tab1;

    @BindView(R.id.rb_home_tab2)
    MyRadioButton rb_home_tab2;

    @BindView(R.id.rb_home_tab4)
    MyRadioButton rb_home_tab4;

    @BindView(R.id.rb_home_tab5)
    MyRadioButton rb_home_tab5;

    @BindView(R.id.rb_home_tab3)
    MyRadioButton rb_home_tab3;
    private MyRadioButton selectView;


    private LocalBroadcastManager localBroadcastManager;

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        setLiveRoomPeopleNum();
        getSplashImgUrl();
        if (noLogin()) {
            SPUtils.setPrefString(mContext, Constant.hxname, getDeviceId());
        } else {
            SPUtils.setPrefString(mContext, Constant.hxname, getUserId());
        }
        registerHuanXin();

        String registrationID = JPushInterface.getRegistrationID(mContext);
        android.util.Log.i("registrationID", "registrationID=====" + registrationID);
        if (!TextUtils.isEmpty(registrationID)) {
            SPUtils.setPrefString(mContext, Config.jiguangRegistrationId, registrationID);
        }


        if (SPUtils.getBoolean(mContext, AppXml.updatePWD, false)) {
            clearUserId();
            SPUtils.setPrefBoolean(mContext, AppXml.updatePWD, false);
        }

        homeFragment = new HomeFragment();
        addFragment(R.id.fl_content, homeFragment);

        setTabClickListener();

        setBroadcast();
//        STActivity(LoginActivity.class);
    }



    private void getSplashImgUrl() {
        requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, new PermissionCallback() {
            @Override
            public void onGranted() {
                Map<String, String> map = new HashMap<String, String>();
                map.put("rnd", getRnd());
                map.put("sign", getSign(map));
                ApiRequest.getSplash(map, new MyCallBack<SplashObj>(mContext) {
                    @Override
                    public void onSuccess(SplashObj obj, int errorCode, String msg) {
                        Glide.with(mContext).load(obj.getImage_url()).downloadOnly(new SimpleTarget<File>() {
                            @Override
                            public void onResourceReady(final File resource, GlideAnimation<? super File> glideAnimation) {
                                Rx.start(new MyFlowableSubscriber<String>() {
                                    @Override
                                    public void subscribe(FlowableEmitter<String> emitter) {
                                        File cacheFile = resource;
                                        String path = cacheFile.getAbsolutePath();
                                        emitter.onNext(path);
                                        emitter.onComplete();
                                    }
                                    @Override
                                    public void onNext(String path) {
                                        SPUtils.setPrefString(mContext,AppXml.splashUrl,path);
                                    }
                                });

                            }
                        });
                    }
                });
            }
            @Override
            public void onDenied(String s) {

            }
        });

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        String action = intent.getAction();
        if (IntentParam.selectHome.equals(action)) {
            selectHome();
            selectView.setChecked(true);
        } else if (IntentParam.mall.equals(action)) {
            selectMall();
            selectView.setChecked(true);
        }
    }


    private void setTabClickListener() {
        selectView = rb_home_tab1;
        rb_home_tab1.setOnClickListener(getTabClickListener(1));
        rb_home_tab2.setOnClickListener(getTabClickListener(2));
        rb_home_tab3.setOnClickListener(getTabClickListener(3));
        rb_home_tab4.setOnClickListener(getTabClickListener(4));
        rb_home_tab5.setOnClickListener(getTabClickListener(5));

    }


    @NonNull
    private MyOnClickListener getTabClickListener(final int index) {
        return new MyOnClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                switch (index) {
                    case 1:
                        selectHome();
//                        selectMall();
                        break;
                    case 2:
//                        if (noLogin()) {
//                            STActivity(LoginActivity.class);
//                            selectView.setChecked(true);
//                        } else {
                        selectMall();
//                        }
                        break;
                    case 3:
                       /* if (noLogin()) {
                            STActivity(LoginActivity.class);
                            selectView.setChecked(true);
                        } else {*/
                        selectLive();
//                        }
                        break;
                    case 4:
                       /* if (noLogin()) {
                            STActivity(LoginActivity.class);
                            selectView.setChecked(true);
                        } else {*/
                        selectAuction();
//                        }
                        break;
                    case 5:
                        if (noLogin()) {
                            Intent intent = new Intent(IntentParam.needSelectMy);
                            STActivity(intent, LoginActivity.class);
                            selectView.setChecked(true);
                        } else {
                            selectMy();
                        }
                        break;
                }
            }
        };
    }


    @Override
    protected void initRxBus() {
        super.initRxBus();
        getEvent(LoginSuccessEvent.class, new MyConsumer<LoginSuccessEvent>() {
            @Override
            public void onAccept(LoginSuccessEvent event) {
                if (event.status == LoginSuccessEvent.status_1) {
                    selectMy();

                    registerHuanXin();
                } else if (event.status == LoginSuccessEvent.status_0) {
                    selectHome();
                }
                selectView.setChecked(true);
            }
        });


    }

    private void registerHuanXin() {
        Rx.start(new MyFlowableSubscriber<String>() {
            @Override
            public void subscribe(FlowableEmitter<String> emitter) {
                if (TextUtils.isEmpty(SPUtils.getString(mContext, Constant.hxname, null))) {
                    SPUtils.setPrefString(mContext, Constant.appsign, getDeviceId());
                }
                ChatClient.getInstance().createAccount(SPUtils.getString(mContext, Constant.hxname, null).toLowerCase(), "123456", new Callback() {
                    @Override
                    public void onSuccess() {
                    }

                    @Override
                    public void onError(int i, String s) {
                        Log.i("===", i + "=onError==" + s);
                    }

                    @Override
                    public void onProgress(int i, String s) {
                        Log.i("===", i + "=onProgress==" + s);
                    }
                });
                emitter.onComplete();
            }

            @Override
            public void onNext(String obj) {

            }
        });
    }

    private void selectHome() {
        if (selectView == rb_home_tab1) {
            return;
        }
        selectView = rb_home_tab1;
        if (homeFragment == null) {
            homeFragment = new HomeFragment();
            addFragment(R.id.fl_content, homeFragment);
        } else {
            showFragment(homeFragment);
        }
//        hideFragment(homeFragment);
        hideFragment(foundFragment);
        hideFragment(touGaoFragment);
        hideFragment(biYouFragment);
        hideFragment(myFragment);
    }

    private void selectMall() {
        if (selectView == rb_home_tab2) {
            return;
        }
        selectView = rb_home_tab2;
        if (foundFragment == null) {
            foundFragment = new MallFragment();
            addFragment(R.id.fl_content, foundFragment);
        } else {
            showFragment(foundFragment);
        }
        hideFragment(homeFragment);
//        hideFragment(foundFragment);
        hideFragment(touGaoFragment);
        hideFragment(biYouFragment);
        hideFragment(myFragment);
    }


    private void selectLive() {
        if (selectView != null) {
            selectView.setChecked(false);
            selectView = null;
        }
        if (touGaoFragment == null) {
            touGaoFragment = new LiveFragment();
            addFragment(R.id.fl_content, touGaoFragment);
        } else {
            showFragment(touGaoFragment);
        }
        hideFragment(homeFragment);
        hideFragment(foundFragment);
//        hideFragment(touGaoFragment);
        hideFragment(biYouFragment);
        hideFragment(myFragment);
    }

    private void selectAuction() {
        if (selectView == rb_home_tab4) {
            return;
        }
        selectView = rb_home_tab4;
        if (biYouFragment == null) {
            biYouFragment = new AuctionFragment();
            addFragment(R.id.fl_content, biYouFragment);
        } else {
            showFragment(biYouFragment);
        }
        hideFragment(homeFragment);
        hideFragment(foundFragment);
        hideFragment(touGaoFragment);
//        hideFragment(biYouFragment);
        hideFragment(myFragment);
    }


    private void selectMy() {
        if (selectView == rb_home_tab5) {
            return;
        }
        selectView = rb_home_tab5;
        if (myFragment == null) {
            myFragment = new MyFragment();
            addFragment(R.id.fl_content, myFragment);
        } else {
            showFragment(myFragment);
        }
        hideFragment(homeFragment);
        hideFragment(foundFragment);
        hideFragment(touGaoFragment);
        hideFragment(biYouFragment);
//        hideFragment(myFragment);
    }

    @Override
    protected void initData() {
        updateApp();
        getPaymentURL(1);//获取支付宝回传地址
        getPaymentURL(2);//获取微信回传地址
    }

    private void updateApp() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("rnd", getRnd());
        map.put("sign", GetSign.getSign(map));
        NetApiRequest.getAppVersion(map, new MyCallBack<AppVersionObj>(mContext) {
            @Override
            public void onSuccess(final AppVersionObj obj, int errorCode, String msg) {
                if (obj.getAndroid_version() > getAppVersionCode()) {
                    SPUtils.setPrefString(mContext, Config.appDownloadUrl, obj.getAndroid_vs_url());
                    SPUtils.setPrefBoolean(mContext, Config.appHasNewVersion, true);
                    MyDialog.Builder mDialog = new MyDialog.Builder(mContext);
                    mDialog.setMessage("检测到app有新版本是否更新?");
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

                            downloadApp(obj);
                        }
                    });
                    mDialog.create().show();
                } else {
                    SPUtils.setPrefBoolean(mContext, Config.appHasNewVersion, false);
                }
            }
        });
    }

    private void downloadApp(final AppVersionObj obj) {
        requestPermission(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, new PermissionCallback() {
            @Override
            public void onGranted() {
                Rx.start(new MyFlowableSubscriber<Boolean>() {
                    @Override
                    public void subscribe(@io.reactivex.annotations.NonNull FlowableEmitter<Boolean> subscriber) {
                        boolean delete = FileUtils.delete(FileUtils.getDownloadDir());
                        subscriber.onNext(delete);
                        subscriber.onComplete();
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        AppInfo info = new AppInfo();
                        info.setUrl(obj.getAndroid_vs_url());
                        info.setHouZhui(".apk");
                        info.setFileName(MyAPPDownloadService.downloadName);
                        info.setId(obj.getAndroid_version() + "");
                        MyAPPDownloadService.intentDownload(mContext, info);
                    }
                });
            }

            @Override
            public void onDenied(String s) {
                showMsg("获取权限失败,无法正常更新,请在设置中打开相关权限");
            }
        });

    }

    private void getPaymentURL(int type) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("payment_type", type + "");
        map.put("sign", GetSign.getSign(map));
        NetApiRequest.getPayNotifyUrl(map, new MyCallBack<PayObj>(mContext) {
            @Override
            public void onSuccess(PayObj obj, int errorCode, String msg) {
                if (obj.getPayment_type() == 1) {
                    SPUtils.setPrefString(mContext, Config.payType_ZFB, obj.getPayment_url());
                } else {
                    SPUtils.setPrefString(mContext, Config.payType_WX, obj.getPayment_url());
                }
            }
        });

    }


    protected void onViewClick(View v) {
        switch (v.getId()) {

        }
    }

    private void setBroadcast() {
        localBroadcastManager = LocalBroadcastManager.getInstance(this);
    }


    private long mExitTime;

    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - mExitTime) > 1500) {
            showToastS("再按一次退出程序");
            mExitTime = System.currentTimeMillis();
        } else {
            super.onBackPressed();
        }
    }
}
