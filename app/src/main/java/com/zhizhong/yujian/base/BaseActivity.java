package com.zhizhong.yujian.base;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.design.widget.BottomSheetDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.github.androidtools.ClickUtils;
import com.github.androidtools.PhoneUtils;
import com.github.androidtools.SPUtils;
import com.github.androidtools.inter.MyOnClickListener;
import com.github.rxbus.RxBus;
import com.hyphenate.chat.ChatClient;
import com.hyphenate.helpdesk.callback.Callback;
import com.hyphenate.helpdesk.easeui.util.IntentBuilder;
import com.library.base.BaseObj;
import com.library.base.MyBaseActivity;
import com.library.base.view.MyWebViewClient;
import com.sdklibrary.base.ShareParam;
import com.sdklibrary.base.qq.share.MyQQActivityResult;
import com.sdklibrary.base.qq.share.MyQQShare;
import com.sdklibrary.base.qq.share.MyQQShareListener;
import com.sdklibrary.base.qq.share.bean.MyQQWebHelper;
import com.sdklibrary.base.wx.inter.MyWXCallback;
import com.sdklibrary.base.wx.share.MyWXShare;
import com.sdklibrary.base.wx.share.bean.MyWXWebHelper;
import com.tencent.tauth.UiError;
import com.zhizhong.yujian.AppXml;
import com.zhizhong.yujian.Config;
import com.zhizhong.yujian.Constant;
import com.zhizhong.yujian.GetSign;
import com.zhizhong.yujian.R;
import com.zhizhong.yujian.event.JoinShoppingCartEvent;
import com.zhizhong.yujian.event.RefreshLivePeopleEvent;
import com.zhizhong.yujian.network.NetApiRequest;
import com.zhizhong.yujian.network.response.ShareObj;

import org.reactivestreams.Subscription;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/12/18.
 */

public abstract class BaseActivity extends MyBaseActivity {
    protected final String TAG = this.getClass().getSimpleName();
    protected final String noLoginCode = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setBackIcon(R.drawable.back_icon);
        setTitleBackgroud(R.color.title_bg);
        super.onCreate(savedInstanceState);
    }

    protected String getUserId() {
        return SPUtils.getString(mContext, AppXml.userId, noLoginCode);
    }

    protected void clearUserId() {
        SPUtils.removeKey(mContext, AppXml.userId);
        SPUtils.removeKey(mContext, AppXml.hxname);
    }

    public boolean noLogin() {
        if (noLoginCode.equals(getUserId()) || TextUtils.isEmpty(getUserId())) {
            return true;
        } else {
            return false;
        }
    }

    public void Log(String msg) {
        Log.i(TAG + "@@@===", msg);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.app_right_iv:
                if ("share".equals(v.getTag())) {
                    Log("===分享=");
//                    showFenXiangDialog();
                }
                break;
        }
    }
    public void postGetShoppingCartNum(){
        RxBus.getInstance().postReplay(new JoinShoppingCartEvent());
    }
    @Override
    public void setAppRightImg(@DrawableRes int appRightImg) {
        super.setAppRightImg(appRightImg);
      /*  if (appRightImg == R.drawable.share) {
            isShareImg = true;
        }*/
    }



    /*@Override
    protected void setClickListener() {
        super.setClickListener();
        if (BuildConfig.DEBUG && app_title != null) {
            app_title.setOnClickListener(new MyOnClickListener() {
                @Override
                protected void onNoDoubleClick(View v) {
                    Log.i(TAG + "===", "userId===" + getUserId());
                }
            });
        }
    }*/

    protected String getSign(Map map) {
        return GetSign.getSign(map);
    }

    protected String getSign(String key, String value) {
        return GetSign.getSign(key, value);
    }

    protected void initWebViewForContent(WebView webview, String content) {
        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setTextSize(WebSettings.TextSize.NORMAL);
        //自适应屏幕  
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setLoadWithOverviewMode(true);
        webview.getSettings().setDefaultTextEncodingName("UTF -8");//设置默认为utf-8
        //设置Web视图
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
//                initWebTopView();
            }
        });

        webview.loadDataWithBaseURL(null, getNewContent(content), "text/html", "utf-8", null);
//        webview.loadUrl(url);
        // 设置WevView要显示的网页
//        webview.loadDataWithBaseURL(null, content, "text/html", "utf-8",null);
        webview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webview.getSettings().setJavaScriptEnabled(true); //设置支持Javascript
        webview.requestFocus(); //触摸焦点起作用.如果不设置，则在点击网页文本输入框时，不能弹出软键盘及不响应其他的一些事件。
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }

    protected void initSimpleWebViewForUrl(WebView webview, String url) {
        WebSettings webSetting = webview.getSettings();
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        //此方法不支持4.4以后
        webSetting.setUseWideViewPort(true);
        webSetting.setJavaScriptEnabled(true);
        webview.loadUrl(url);
        webview.setWebViewClient(new MyWebViewClient());
    }
    public  String getDeviceId() {
        String appSign = SPUtils.getString(mContext, Constant.appsign,null);
        if(TextUtils.isEmpty(appSign)||"0".equals(appSign)){
            SPUtils.setPrefString(mContext, Constant.appsign,new Date().getTime()+"");
        }
        return SPUtils.getString(mContext, Constant.appsign,new Date().getTime()+"");
/*
        String id;
        //android.telephony.TelephonyManager
        TelephonyManager mTelephony = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        if (mTelephony.getDeviceId() != null) {
            id = mTelephony.getDeviceId();
        } else {
            //android.provider.Settings;
            id = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        }
        return id;*/
    }
    public void setLiveRoomPeopleNum() {
        Map<String,String>map=new HashMap<String,String>();
        map.put("user_id",getHXName());
        map.put("type","2");
        map.put("channel_id","");
        map.put("sign",getSign(map));
        NetApiRequest.setLiveRoomPeopleNum(map, new MyCallBack<BaseObj>(mContext) {
            @Override
            public void onSuccess(BaseObj obj, int errorCode, String msg) {
                RxBus.getInstance().post(new RefreshLivePeopleEvent());
            }
            @Override
            public void onError(Throwable e, boolean hiddenMsg) {
                super.onError(e,true);
            }
        });
    }
    public void setLiveRoomPeopleNum(String groupId) {
        Map<String,String>map=new HashMap<String,String>();
        map.put("user_id",getHXName());
        map.put("type","1");
        map.put("channel_id",groupId);
        map.put("sign",getSign(map));
        NetApiRequest.setLiveRoomPeopleNum(map, new MyCallBack<BaseObj>(mContext) {
            @Override
            public void onSuccess(BaseObj obj, int errorCode, String msg) {

            }
        });
    }
    public String  getHXName(){
        String hxName=SPUtils.getString(mContext,Constant.hxname,getDeviceId());
        if (noLogin()||"0".equals(hxName)) {
            hxName=SPUtils.getString(mContext,Constant.hxname,getDeviceId());
            Log("22==="+hxName);
        }else{
            hxName=SPUtils.getString(mContext,Constant.hxname,getUserId());
            Log("33==="+hxName);
        }
        return hxName;
    }
    public void goHX(){
        String hxName=SPUtils.getString(mContext,Constant.hxname,null);
        if (noLogin()) {
            SPUtils.setPrefString(mContext,Constant.hxname,getDeviceId());
        }else{
            SPUtils.setPrefString(mContext,Constant.hxname,getUserId());
        }
        if(hxName!=null&& ChatClient.getInstance().isLoggedInBefore()&&!TextUtils.isEmpty(hxName)&&hxName.equalsIgnoreCase(ChatClient.getInstance().getCurrentUserName())){
            //已经登录，可以直接进入会话界面
            OpenHuanXin();
        }else{
            showLoading();
            if(ChatClient.getInstance().isLoggedInBefore()){
                ChatClient.getInstance().logout(true, new Callback() {
                    @Override
                    public void onSuccess() {
                        String name=SPUtils.getString(mContext,Constant.hxname,null);
                        loginHXSuccess(name);
                    }
                    @Override
                    public void onError(int i, String s) {
                        dismissLoading();
                    }
                    @Override
                    public void onProgress(int i, String s) {
                    }
                });
            }else{
                String name=SPUtils.getString(mContext,Constant.hxname,null);
                loginHXSuccess(name);
            }
        }
    }

    private void loginHXSuccess(String hxName) {
        //未登录，需要登录后，再进入会话界面
        ChatClient.getInstance().login(hxName, "123456", new Callback() {
            @Override
            public void onSuccess() {
                dismissLoading();
                OpenHuanXin();
            }
            @Override
            public void onError(int i, String s) {
                dismissLoading();
            }
            @Override
            public void onProgress(int i, String s) {
            }
        });
    }

    private void OpenHuanXin() {
        Intent intent = new IntentBuilder(mContext)
                .setServiceIMNumber(Config.hx_fwh) //获取地址：kefu.easemob.com，“管理员模式 > 渠道管理 > 手机APP”页面的关联的“IM服务号”
                .build();
        startActivity(intent);
    }
    protected void initWebViewForUrl(final WebView webview, String url) {
        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setTextSize(WebSettings.TextSize.NORMAL);
        //自适应屏幕  
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setLoadWithOverviewMode(true);
        webview.getSettings().setDefaultTextEncodingName("UTF -8");//设置默认为utf-8
        //设置Web视图
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
//                initWebTopView();
                imgReset(webview);
            }
        });

//        webview.loadDataWithBaseURL(null, getNewContent(url), "text/html", "utf-8",null);
        webview.loadUrl(url);
        // 设置WevView要显示的网页
        webview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webview.getSettings().setJavaScriptEnabled(true); //设置支持Javascript
        webview.requestFocus(); //触摸焦点起作用.如果不设置，则在点击网页文本输入框时，不能弹出软键盘及不响应其他的一些事件。
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }

    private void imgReset(WebView webview) {
        webview.loadUrl("javascript:(function(){" +
                "var objs = document.getElementsByTagName('img'); " +
                "for(var i=0;i<objs.length;i++)  " +
                "{"
                + "var img = objs[i];   " +
                " img.style.maxWidth = '100%';img.style.height='auto';" +
                "}" +
                "})()");
    }


    protected static String getNewContent(String htmltext) {
        /*try {
            Document doc = Jsoup.parse(htmltext);
            Elements elements = doc.getElementsByTag("img");
            for (Element element : elements) {
                element.attr("width", "100%").attr("height", "auto");
            }
            return doc.toString();
        } catch (Exception e) {
            return htmltext;
        }*/
        return null;
    }

    Subscription subscription;

    public void countDown(final TextView textView) {
        textView.setEnabled(false);
        final long count = 30;
        Flowable.interval(1, TimeUnit.SECONDS)
                .take(31)
                .map(new Function<Long, Long>() {
                    @Override
                    public Long apply(@NonNull Long integer) throws Exception {
                        return count - integer;
                    }
                })
                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.from(new Executor(){
                    @Override
                    public void execute(@android.support.annotation.NonNull Runnable command) {
                        runOnUiThread(command);
                    }
                }))
                .subscribe(new FlowableSubscriber<Long>() {
                    @Override
                    public void onSubscribe(@NonNull Subscription s) {
                        subscription = s;
                        s.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(Long aLong) {
                        textView.setText("剩下" + aLong + "s");
                    }
                    @Override
                    public void onError(Throwable t) {
                    }
                    @Override
                    public void onComplete() {
                        textView.setEnabled(true);
                        textView.setText("获取验证码");
                    }
                });
        addSubscription(subscription);
    }

   /* protected void setBannerList(Banner bn_home, List bannerList) {
        if (notEmpty(bannerList)) {
            bn_home.setLayoutParams(ImageSizeUtils.getImageSizeLayoutParams(mContext));
            bn_home.setImages(bannerList);
            bn_home.setImageLoader(new GlideLoader());

            bn_home.start();
        }
    }*/

    public int getAppVersionCode() {
        Context context = mContext;
        int versioncode = 1;
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            String versionName = pi.versionName;
            versioncode = pi.versionCode;
            return versioncode;
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return versioncode;
    }

    public String getAppVersionName() {
        Context context = mContext;
        int versioncode = 1;
        String versionName = "V1.0.0";
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            versioncode = pi.versionCode;
            return versionName;
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return versionName;
    }


    BottomSheetDialog fenXiangDialog;

    public void showFenXiangDialog() {
        showFenXiangDialog("0");
    }
    public void showFenXiangDialog(final String goodsId) {
        if (fenXiangDialog == null) {
            View sexView = LayoutInflater.from(mContext).inflate(R.layout.goods_share_popu, null);
            sexView.findViewById(R.id.ll_yaoqing_wx).setOnClickListener(new MyOnClickListener() {
                @Override
                protected void onNoDoubleClick(View view) {
                    fenXiang(goodsId,ShareParam.friend);
                    fenXiangDialog.dismiss();
                }
            });
            sexView.findViewById(R.id.ll_yaoqing_friend).setOnClickListener(new MyOnClickListener() {
                @Override
                protected void onNoDoubleClick(View view) {
                    fenXiang(goodsId,ShareParam.friendCircle);
                    fenXiangDialog.dismiss();

                }
            });
            sexView.findViewById(R.id.ll_yaoqing_qq).setOnClickListener(new MyOnClickListener() {
                @Override
                protected void onNoDoubleClick(View view) {
                    fenXiang(goodsId,ShareParam.QQ);
                    fenXiangDialog.dismiss();
                }
            });
            sexView.findViewById(R.id.ll_yaoqing_qzone).setOnClickListener(new MyOnClickListener() {
                @Override
                protected void onNoDoubleClick(View view) {
                    fenXiang(goodsId,ShareParam.QZONE);
                    fenXiangDialog.dismiss();
                }
            });
            sexView.findViewById(R.id.tv_fenxiang_cancle).setOnClickListener(new MyOnClickListener() {
                @Override
                protected void onNoDoubleClick(View view) {
                    fenXiangDialog.dismiss();
                }
            });
            fenXiangDialog = new BottomSheetDialog(mContext);
            fenXiangDialog.setCanceledOnTouchOutside(true);
            fenXiangDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            fenXiangDialog.setContentView(sexView);
        }
        fenXiangDialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        MyQQActivityResult.onActivityResult(requestCode, resultCode, data);
    }

    protected void fenXiang(String goodId,final @ShareParam.MyShareType int platform) {
        showLoading();
        Map<String, String> map = new HashMap<String, String>();
        map.put("goods_id",goodId);
        map.put("sign", GetSign.getSign(map));
        NetApiRequest.getShareInformation(map, new MyCallBack<ShareObj>(mContext, true) {
            @Override
            public void onSuccess(ShareObj obj, int errorCode, String msg) {
                if (platform == ShareParam.QQ || platform == ShareParam.QZONE) {
                    MyQQWebHelper helper = new MyQQWebHelper(platform);
                    helper.setTitle(obj.getTitle());
                    helper.setDescription(obj.getContent());
                    helper.setImagePath(obj.getImg());
                    helper.setUrl(obj.getShare_link());
                    MyQQShare.newInstance(mContext).shareWeb(helper, new MyQQShareListener() {
                        @Override
                        public void doComplete(Object o) {
                            dismissLoading();
                            showMsg("分享成功");
                        }
                        @Override
                        public void doError(UiError uiError) {
                            dismissLoading();
                            showMsg("分享失败");
                        }
                        @Override
                        public void doCancel() {
                            dismissLoading();
                            showMsg("取消分享");
                        }
                    });
                } else {
                    MyWXWebHelper helperWX = new MyWXWebHelper(platform);
                    helperWX.setUrl(obj.getShare_link());
                    helperWX.setTitle(obj.getTitle());
                    helperWX.setDescription(obj.getContent());
                    helperWX.setBitmapResId(R.drawable.share_img);
                    MyWXShare.newInstance(mContext).shareWeb(helperWX, new MyWXCallback() {
                        @Override
                        public void onSuccess() {
                            dismissLoading();
                            showMsg("分享成功");
                        }
                        @Override
                        public void onFail() {
                            dismissLoading();
                            showMsg("分享失败");
                        }
                        @Override
                        public void onCancel() {
                            dismissLoading();
                            showMsg("取消分享");
                        }
                    });

                }
            }
        });
    }

    public interface UploadImgCallback {
        void result(String imgUrl);
    }

  /*  public void uploadImg(String imgPath, UploadImgCallback callback) {
        showLoading();
        RXStart(new MyFlowableSubscriber<String>() {
            @Override
            public void subscribe(@NonNull FlowableEmitter<String> subscriber) {
                try {
                    List<File> files = Luban.with(mContext).load(takePhotoImgSavePath).get();
                    File file = files.get(0);
                    String imgStr = BitmapUtils.fileToString(file);
                    subscriber.onNext(imgStr);
                    subscriber.onComplete();
                } catch (Exception e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                }
            }

            @Override
            public void onNext(String base64) {
                Map<String, String> map = new HashMap<String, String>();
                map.put("rnd", getRnd());
                map.put("sign", getSign(map));
                UploadImgBody body = new UploadImgBody();
                body.setFile(base64);
                NetApiRequest.uploadImg(map, body, new MyCallBack<BaseObj>(mContext) {
                    @Override
                    public void onSuccess(BaseObj obj, int errorCode, String msg) {
                        callback.result(obj.getImg());
                    }
                });
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                dismissLoading();
                showToastS("图片插入失败");
            }
        });
    }

*/
    private void setDialogFullWidth(Dialog dialog) {
        Window win = dialog.getWindow();
        win.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        win.setGravity(Gravity.CENTER);
        win.getDecorView().setPadding(0, 0, 0, 0);
//        win.setBackgroundDrawableResource(R.color.transparent_half);
        win.setBackgroundDrawableResource(R.color.white_half);
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = PhoneUtils.getScreenHeight(mContext);
        win.setAttributes(lp);
    }





    /*@android.support.annotation.NonNull
    private MyOnClickListener getListenerSaveIMG() {
        return new MyOnClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
                requestPermission(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, new PermissionCallback() {
                    @Override
                    public void onGranted() {
                        FileUtils.downloadImg(mContext, ziXunObj.getWechat_code());
                    }

                    @Override
                    public void onDenied(String s) {
                        showMsg("获取文件管理权限失败,无法保存图片");
                    }
                });
            }
        };
    }*/

    /*protected void showZiXunDialog(String id) {
        if (ziXunObj == null) {
            showLoading();
            getZiXunData(id, new MyCallBack<ZiXunObj>(mContext) {
                @Override
                public void onSuccess(ZiXunObj obj, int errorCode, String msg) {
                    ziXunObj = obj;
                    showZiXun();
                }
            });
        } else {
            showZiXun();
        }
    }*/

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ClickUtils.clearLastClickTime();
    }

    /*****************************************************************************/
    BottomSheetDialog peiXunPayDialog;

   /* protected void showPeiXunPay(String orderNo, double price, String orderType) {
        peiXunPayDialog = new BottomSheetDialog(mContext);
        peiXunPayDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        View view = LayoutInflater.from(mContext).inflate(R.layout.tijiaoorder_pay_popu, null);
        RadioButton rb_order_pay = view.findViewById(R.id.rb_order_pay);
        view.findViewById(R.id.tv_commit_liuyan).setOnClickListener(new MyOnClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
                showLoading();
                if (rb_order_pay.isChecked()) {
                    MyWXOrderBean bean = new MyWXOrderBean();
                    bean.setTotalFee((int) AndroidUtils.chengFa(price, 100));
                    bean.setOut_trade_no(orderNo);
                    bean.setBody(Constant.orderBody1);
                    weixinPay(bean, peiXunPayDialog, orderType);
                } else {
                    MyAliOrderBean bean = new MyAliOrderBean();
                    bean.setOut_trade_no(orderNo);
                    bean.setTotal_amount(price);
                    bean.setBody(Constant.orderBody1);
                    aliPay(bean, peiXunPayDialog, orderType);
                }

            }
        });

        peiXunPayDialog.setContentView(view);
        peiXunPayDialog.show();
    }

    private void weixinPay(MyWXOrderBean bean, BottomSheetDialog payDialog, String type) {
        String url = SPUtils.getString(mContext, Config.payType_WX, null);
        bean.setNotifyUrl(url);
//        bean.setIp("1");
        MyWXPay.newInstance(mContext).startPay(bean, new MyWXCallback() {
            @Override
            public void onSuccess() {
                BaseActivity.this.paySuccess(payDialog, type);
            }

            @Override
            public void onFail() {
                dismissLoading();
                showMsg("支付失败");
                if (payDialog != null) {
                    payDialog.dismiss();
                }
            }

            @Override
            public void onCancel() {
                dismissLoading();
                showMsg("支付已取消");
                if (payDialog != null) {
                    payDialog.dismiss();
                }
            }
        });
    }

    private void paySuccess(BottomSheetDialog payDialog, String type) {
        MyRxBus.getInstance().postReplay(new RefreshOrderEvent(type));

        if (mContext instanceof OrderDetailActivity) {
            OrderDetailActivity mContext = (OrderDetailActivity) BaseActivity.this.mContext;
            mContext.getData(1, false);
        } else {
            dismissLoading();
        }
        if (payDialog != null) {
            payDialog.dismiss();
        }
    }

    protected void aliPay(MyAliOrderBean bean, BottomSheetDialog payDialog, String type) {
        String url = SPUtils.getString(mContext, Config.payType_ZFB, null);
        bean.setAppId(Config.zhifubao_app_id);
        bean.setPid(Config.zhifubao_pid);
        bean.setSiYao(Config.zhifubao_rsa2);
        bean.setNotifyUrl(url);
        bean.setSubject("麦签订单支付");
        MyAliPay.newInstance(mContext).startPay(bean, new MyAliPayCallback() {
            @Override
            public void paySuccess(PayResult payResult) {
                BaseActivity.this.paySuccess(payDialog, type);
            }

            @Override
            public void payFail() {
                dismissLoading();
                showMsg("支付失败");
                if (payDialog != null) {
                    payDialog.dismiss();
                }
            }

            @Override
            public void payCancel() {
                dismissLoading();
                showMsg("支付已取消");
                if (payDialog != null) {
                    payDialog.dismiss();
                }
            }
        });
    }*/

    public boolean fileIsExists(String strFile) {
        try {
            File f = new File(strFile);
            if (!f.exists()) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

}

