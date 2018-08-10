package com.zhizhong.yujian.module.my.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.github.androidtools.SPUtils;
import com.github.baseclass.permission.PermissionCallback;
import com.github.mydialog.MyDialog;
import com.github.rxbus.RxBus;
import com.github.rxbus.rxjava.MyFlowableSubscriber;
import com.library.base.BaseObj;
import com.library.base.tools.CacheUtils;
import com.suke.widget.SwitchButton;
import com.zhizhong.yujian.AppXml;
import com.zhizhong.yujian.R;
import com.zhizhong.yujian.base.BaseActivity;
import com.zhizhong.yujian.base.MyCallBack;
import com.zhizhong.yujian.event.LoginSuccessEvent;
import com.zhizhong.yujian.module.my.network.ApiRequest;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.FlowableEmitter;
import io.reactivex.annotations.NonNull;

public class SettingActivity extends BaseActivity {
    @BindView(R.id.sb_setting)
    SwitchButton sb_setting;
    @BindView(R.id.tv_setting_cache)
    TextView tv_setting_cache;
    @BindView(R.id.tv_setting_version)
    TextView tv_setting_version;

    private int message_sink;
    @Override
    protected int getContentView() {
        setAppTitle("设置");
        return R.layout.setting_act;
    }

    @Override
    protected void initView() {

        message_sink =   SPUtils.getInt(mContext, AppXml.message_sink,0);
        if (message_sink==0) {
            sb_setting.setChecked(false);
        }else {
            sb_setting.setChecked(true);
        }
        sb_setting.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction()== MotionEvent.ACTION_UP){
                    setSwitch();
                }
                return true;
            }
        });

        String versionName = getAppVersionName();
        tv_setting_version.setText("V"+versionName);

        requestPermission(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, new PermissionCallback() {
            @Override
            public void onGranted() {
                RXStart(new MyFlowableSubscriber<String>() {
                    @Override
                    public void subscribe(@NonNull FlowableEmitter<String> emitter) {
                        try {
                            String cacheSize = CacheUtils.getExternalCacheSize(mContext);
                            emitter.onNext(cacheSize);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onNext(String s) {
                        tv_setting_cache.setText(s);
                    }
                });
            }
            @Override
            public void onDenied(String s) {
                showMsg("获取权限失败无法正常读取缓存大小");
            }
        });


    }
    private void setSwitch() {
        final boolean checked = sb_setting.isChecked();
        showLoading();
        Map<String,String> map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("message_sink",!checked?"1":"0");
        map.put("sign", getSign(map));
        ApiRequest.setMessageSink(map, new MyCallBack<BaseObj>(mContext) {
            @Override
            public void onSuccess(BaseObj obj, int errorCode, String msg) {
                showMsg(msg);
                sb_setting.setChecked(!checked);
                SPUtils.setPrefInt(mContext, AppXml.message_sink,obj.getMessage_sink());
            }
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                sb_setting.setChecked(checked);
            }
        });
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.tv_setting_account, R.id.tv_setting_fankui, R.id.tv_setting_clear, R.id.tv_setting_about, R.id.tv_setting_exit})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.tv_setting_account:
                STActivity(ZhangHuAnQuanActivity.class);
                break;
            case R.id.tv_setting_fankui:
                STActivity(YiJianFanKuiActivity.class);
                break;
            case R.id.tv_setting_clear:
                requestPermission(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, new PermissionCallback() {
                    @Override
                    public void onGranted() {
                        deleteCache(tv_setting_cache,false);
                    }
                    @Override
                    public void onDenied(String s) {
                        showMsg("获取权限失败无法正常清理缓存");
                    }
                });
                break;
            case R.id.tv_setting_about:
                STActivity(AboutYuJianActivity.class);
                break;
            case R.id.tv_setting_exit:
                exit();
                break;
        }
    }

    private void exit() {
        MyDialog.Builder mDialog=new MyDialog.Builder(mContext);
        mDialog.setMessage("是否确认退出登录?");
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

                clearUserId();
                RxBus.getInstance().post(new LoginSuccessEvent(LoginSuccessEvent.status_0));

                finish();
            }
        });
        mDialog.create().show();
    }
}
