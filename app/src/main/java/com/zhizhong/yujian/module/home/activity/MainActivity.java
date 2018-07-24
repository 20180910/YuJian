package com.zhizhong.yujian.module.home.activity;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.FrameLayout;

import com.github.androidtools.SPUtils;
import com.github.androidtools.inter.MyOnClickListener;
import com.github.fastshape.MyRadioButton;
import com.github.mydialog.MyDialog;
import com.github.rxbus.MyConsumer;
import com.library.base.bean.AppVersionObj;
import com.library.base.bean.PayObj;
import com.zhizhong.yujian.AppXml;
import com.zhizhong.yujian.Config;
import com.zhizhong.yujian.GetSign;
import com.zhizhong.yujian.R;
import com.zhizhong.yujian.base.BaseActivity;
import com.zhizhong.yujian.base.MyCallBack;
import com.zhizhong.yujian.event.LoginSuccessEvent;
import com.zhizhong.yujian.module.home.fragment.AuctionFragment;
import com.zhizhong.yujian.module.home.fragment.HomeFragment;
import com.zhizhong.yujian.module.home.fragment.LiveFragment;
import com.zhizhong.yujian.module.home.fragment.MallFragment;
import com.zhizhong.yujian.module.home.fragment.MyFragment;
import com.zhizhong.yujian.module.my.activity.LoginActivity;
import com.zhizhong.yujian.module.my.activity.SettingActivity;
import com.zhizhong.yujian.network.NetApiRequest;
import com.zhizhong.yujian.network.response.ImageObj;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/11/4.
 */

public class MainActivity extends BaseActivity {
//    @BindView(R.id.status_bar)
//    View status_bar;

    HomeFragment homeFragment;
    LiveFragment touGaoFragment;
    MallFragment foundFragment;
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
        getYouXueImg();
        getLiuXueImg();

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


    private void getYouXueImg() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("type", "1");
        map.put("sign", getSign(map));
        NetApiRequest.getYouXueObj(map, new MyCallBack<ImageObj>(mContext) {
            @Override
            public void onSuccess(ImageObj obj, int errorCode, String msg) {
                SPUtils.setPrefString(mContext, AppXml.youXueImg, obj.getImg_url());
            }
        });
    }

    private void getLiuXueImg() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("type", "2");
        map.put("sign", getSign(map));
        NetApiRequest.getYouXueObj(map, new MyCallBack<ImageObj>(mContext) {
            @Override
            public void onSuccess(ImageObj obj, int errorCode, String msg) {
                SPUtils.setPrefString(mContext, AppXml.liuXueImg, obj.getImg_url());
            }
        });
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
                            STActivity(LoginActivity.class);
                            selectView.setChecked(true);
                        } else {
                            selectMy();
                        }
                        STActivity(SettingActivity.class);
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
                } else if (event.status == LoginSuccessEvent.status_0) {
                    selectHome();
                }
                selectView.setChecked(true);
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
        if(selectView!=null){
            selectView.setChecked(false);
            selectView=null;
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
//        updateApp();
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

//                          downloadApp(obj);
                        }
                    });
                    mDialog.create().show();
                } else {
                    SPUtils.setPrefBoolean(mContext, Config.appHasNewVersion, false);
                }
            }
        });
    }
  /*  private void downloadApp(AppVersionObj obj) {
        requestPermission(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE}, new PermissionCallback() {
            @Override
            public void onGranted() {
                MyRx.start(new MyFlowableSubscriber<Boolean>() {
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

    }*/

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
