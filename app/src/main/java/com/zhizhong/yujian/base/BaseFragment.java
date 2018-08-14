package com.zhizhong.yujian.base;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.github.androidtools.SPUtils;
import com.library.base.MyBaseFragment;
import com.zhizhong.yujian.AppXml;
import com.zhizhong.yujian.Config;
import com.zhizhong.yujian.GetSign;

import java.util.Map;

/**
 * Created by Administrator on 2017/12/18.
 */

public abstract class BaseFragment extends MyBaseFragment {
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    protected String getUserId() {
        return SPUtils.getString(mContext, Config.user_id, "0");
    }
    protected void clearUserId() {
        SPUtils.removeKey(mContext, AppXml.userId);
    }
    public boolean noLogin(){
        if("0".equals(getUserId())){
            return true;
        }else{
            return false;
        }
    }
    protected String getSign(Map map) {
        return GetSign.getSign(map);
    }
    public void Log(String msg){
        Log.i(TAG+"===",msg);
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



   /* protected void setBannerList(Banner bn_home, List bannerList){
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
        String versionName = "1.0.0";
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
   /* public void showFenXiangDialog(){
        if (fenXiangDialog == null) {
            View sexView= LayoutInflater.from(mContext).inflate(R.layout.popu_fen_xiang,null);
            sexView.findViewById(R.id.iv_yaoqing_wx).setOnClickListener(new MyOnClickListener() {
                @Override
                protected void onNoDoubleClick(View view) {
                    fenXiang(ShareParam.friend);
                    fenXiangDialog.dismiss();
                }
            });
            sexView.findViewById(R.id.iv_yaoqing_friend).setOnClickListener(new MyOnClickListener() {
                @Override
                protected void onNoDoubleClick(View view) {
                    fenXiang(ShareParam.friendCircle);
                    fenXiangDialog.dismiss();

                }
            });
            sexView.findViewById(R.id.iv_yaoqing_qq).setOnClickListener(new MyOnClickListener() {
                @Override
                protected void onNoDoubleClick(View view) {
                    fenXiang(ShareParam.QQ);
                    fenXiangDialog.dismiss();
                }
            });
            sexView.findViewById(R.id.iv_yaoqing_qzone).setOnClickListener(new MyOnClickListener() {
                @Override
                protected void onNoDoubleClick(View view) {
                    fenXiang(ShareParam.QZONE);
                    fenXiangDialog.dismiss();
                }
            });
            *//*sexView.findViewById(R.id.iv_yaoqing_sina).setOnClickListener(new MyOnClickListener() {
                @Override
                protected void onNoDoubleClick(View view) {
                    showMsg("正在开发中");
                    fenXiangDialog.dismiss();
                }
            });*//*
            sexView.findViewById(R.id.tv_fenxiang_cancle).setOnClickListener(new MyOnClickListener() {
                @Override
                protected void onNoDoubleClick(View view) {
                    fenXiangDialog.dismiss();
                }
            });
            fenXiangDialog=new BottomSheetDialog(mContext);
            fenXiangDialog.setCanceledOnTouchOutside(true);
            fenXiangDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            fenXiangDialog.setContentView(sexView);
        }
        fenXiangDialog.show();
    }*/



    /*protected void fenXiang(@ShareParam.MyShareType int platform) {
        showLoading();
        Map<String,String> map=new HashMap<String,String>();
        map.put("rnd",getRnd());
        map.put("sign",GetSign.getSign(map));
        NetApiRequest.getShareInformation(map, new MyCallBack<ShareObj>(mContext,true) {
            @Override
            public void onSuccess(ShareObj obj,int errorCode,String msg) {
                if(platform== ShareParam.QQ||platform== ShareParam.QZONE){
                    MyQQWebHelper helper=new MyQQWebHelper(platform);
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
                }else{
                    MyWXWebHelper helperWX=new MyWXWebHelper(platform);
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
    }*/
    /**************************************************************/
    /*****************************************************************************/
   /* BottomSheetDialog peiXunPayDialog;
    protected void showPeiXunPay(String orderNo,double price,String orderType) {
        peiXunPayDialog = new BottomSheetDialog(mContext);
        peiXunPayDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        View view = LayoutInflater.from(mContext).inflate(R.layout.tijiaoorder_pay_popu, null);
        RadioButton rb_order_pay = view.findViewById(R.id.rb_order_pay);
        view.findViewById(R.id.tv_commit_liuyan).setOnClickListener(new MyOnClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
                showLoading();

                if(rb_order_pay.isChecked()){
                    MyWXOrderBean bean=new MyWXOrderBean();
                    bean.setTotalFee((int) AndroidUtils.chengFa(price,100));
                    bean.setOut_trade_no(orderNo);
                    bean.setBody(Constant.orderBody1);
                    weixinPay(bean,peiXunPayDialog,orderType);
                }else{
                    MyAliOrderBean bean=new MyAliOrderBean();
                    bean.setOut_trade_no(orderNo);
                    bean.setTotal_amount(price);
                    bean.setBody(Constant.orderBody1);
                    aliPay(bean, peiXunPayDialog,orderType);
                }

            }
        });

        peiXunPayDialog.setContentView(view);
        peiXunPayDialog.show();
    }

    private void weixinPay(MyWXOrderBean bean, BottomSheetDialog payDialog, String type) {
        String url = SPUtils.getString(mContext, Config.payType_WX, null);
        bean.setNotifyUrl(url);
//        bean.setIP(mContext);
        MyWXPay.newInstance(mContext).startPay(bean, new MyWXCallback() {
            @Override
            public void onSuccess() {
                BaseFragment.this.paySuccess(payDialog,type);
            }
            @Override
            public void onFail() {
                dismissLoading();
                showMsg("支付失败");
                if(payDialog!=null){
                    payDialog.dismiss();
                }
            }
            @Override
            public void onCancel() {
                dismissLoading();
                showMsg("支付已取消");
                if(payDialog!=null){
                    payDialog.dismiss();
                }
            }
        });
    }
    private void paySuccess(BottomSheetDialog payDialog,String type){
        MyRxBus.getInstance().postReplay(new RefreshOrderEvent(type));
        dismissLoading();
        if(payDialog!=null){
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
                BaseFragment.this.paySuccess(payDialog,type);
            }
            @Override
            public void payFail() {
                dismissLoading();
                showMsg("支付失败");
                if(payDialog!=null){
                    payDialog.dismiss();
                }
            }
            @Override
            public void payCancel() {
                dismissLoading();
                showMsg("支付已取消");
                if(payDialog!=null){
                    payDialog.dismiss();
                }
            }
        });
    }*/
  /* public void showPay(final String orderNo,final String money){
       final MySimpleDialog dialog=new MySimpleDialog(mContext);
       View view = getLayoutInflater().inflate(R.layout.sure_order_popu, null);
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
                    yuePay(orderNo,money);
                }
            }
        });
       dialog.setContentView(view);
       dialog.setFullWidth();
       dialog.setGravity(Gravity.BOTTOM);
       dialog.show();
   }

    private void yuePay(String orderNo,String money) {
        showLoading();
        Map<String,String>map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("order_no",orderNo);
        map.put("money",money);
        map.put("sign",getSign(map));
        NetApiRequest.yuePay(map, new MyCallBack<BaseObj>(mContext) {
            @Override
            public void onSuccess(BaseObj obj, int errorCode, String msg) {
                RxBus.getInstance().post(new PayEvent());
                STActivity(PaySuccessActivity.class);
            }
        });

    }*/
}
