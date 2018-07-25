package com.zhizhong.yujian.module.my.activity;

import android.view.View;

import com.zhizhong.yujian.R;
import com.zhizhong.yujian.base.BaseActivity;

import butterknife.OnClick;

public class ZhangHuAnQuanActivity extends BaseActivity {
    @Override
    protected int getContentView() {
        setAppTitle("账户安全");
        return R.layout.zhanghu_anquan_act;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onViewClick(View v) {

    }

    @OnClick({R.id.tv_anquan_update_phone, R.id.tv_anquan_update_pwd, R.id.tv_anquan_update_account})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_anquan_update_phone:
                STActivity(UpdatePhoneActivity.class);
                break;
            case R.id.tv_anquan_update_pwd:
                break;
            case R.id.tv_anquan_update_account:
                break;
        }
    }
}
