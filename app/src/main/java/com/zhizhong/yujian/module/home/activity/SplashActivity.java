package com.zhizhong.yujian.module.home.activity;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.BitmapEncoder;
import com.github.androidtools.SPUtils;
import com.github.fastshape.MyTextView;
import com.zhizhong.yujian.AppXml;
import com.zhizhong.yujian.R;
import com.zhizhong.yujian.base.BaseActivity;

import org.reactivestreams.Subscription;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class SplashActivity extends BaseActivity {
    @BindView(R.id.iv_splash)
    ImageView iv_splash;
    @BindView(R.id.tv_splash)
    MyTextView tv_splash;
    private Subscription subscription;
    boolean isClick=false;
    @Override
    protected int getContentView() {
        return R.layout.splash_act;
    }

    synchronized public void setClick(boolean click) {
        isClick = click;
    }

    @Override
    protected void initView() {
        String splashUrl = SPUtils.getString(mContext, AppXml.splashUrl, null);
        if(!TextUtils.isEmpty(splashUrl)){
            Glide.with(mContext).load(splashUrl).asBitmap().encoder(new BitmapEncoder(Bitmap.CompressFormat.PNG,90)).into(iv_splash);
        }
        final long count = 1;
        Flowable.interval(1, TimeUnit.SECONDS)
                .take(2)
                .map(new Function<Long, Long>() {
                    @Override
                    public Long apply(@NonNull Long integer) throws Exception {
                        return count - integer;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new FlowableSubscriber<Long>() {
                    @Override
                    public void onSubscribe(@NonNull Subscription s) {
                        subscription = s;
                        s.request(Long.MAX_VALUE);
                    }
                    @Override
                    public void onNext(Long aLong) {
                        tv_splash.setText("跳过 " + aLong + "s");
                        if(aLong==0&&isClick==false){
                            setClick(true);
                            STActivity(MainActivity.class);
                            finish();
                        }
                    }
                    @Override
                    public void onError(Throwable t) {
                    }
                    @Override
                    public void onComplete() {
                    }
                });
        addSubscription(subscription);
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.tv_splash})
    public void onViewClick(View v) {
        switch (v.getId()){
            case R.id.tv_splash:
                setClick(true);
                STActivity(MainActivity.class);
                finish();
            break;
        }
    }
}
