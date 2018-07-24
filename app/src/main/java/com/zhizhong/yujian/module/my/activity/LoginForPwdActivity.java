package com.zhizhong.yujian.module.my.activity;

import android.view.View;

import com.zhizhong.yujian.R;
import com.zhizhong.yujian.base.BaseActivity;
import com.zhizhong.yujian.view.MyEditText;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginForPwdActivity extends BaseActivity {
    @BindView(R.id.et_login_phone)
    MyEditText et_login_phone;
    @BindView(R.id.et_login_pwd)
    MyEditText et_login_pwd;

    @Override
    protected int getContentView() {
        setAppRightTitle("手机号登录");
        setTitleBackgroud(R.color.transparent);
        return R.layout.login_act;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.app_right_tv,R.id.tv_login_commit, R.id.tv_login_register, R.id.tv_login_forget, R.id.iv_login_qq, R.id.iv_login_wx})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.tv_login_commit:
                break;
            case R.id.tv_login_register:
                break;
            case R.id.tv_login_forget:
                break;
            case R.id.iv_login_qq:
                break;
            case R.id.iv_login_wx:
                break;
            case R.id.app_right_tv:
                finish();
                break;
        }
    }
}
