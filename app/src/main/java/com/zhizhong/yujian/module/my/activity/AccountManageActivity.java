package com.zhizhong.yujian.module.my.activity;

import android.view.View;

import com.github.fastshape.MyTextView;
import com.zhizhong.yujian.R;
import com.zhizhong.yujian.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class AccountManageActivity extends BaseActivity {
    @BindView(R.id.tv_account_manage_wx)
    MyTextView tv_account_manage_wx;
    @BindView(R.id.tv_account_manage_wx_status)
    MyTextView tv_account_manage_wx_status;
    @BindView(R.id.tv_account_manage_qq)
    MyTextView tv_account_manage_qq;
    @BindView(R.id.tv_account_manage_qq_status)
    MyTextView tv_account_manage_qq_status;

    @Override
    protected int getContentView() {
        setAppTitle("第三方登录账户管理");
        return R.layout.account_manage_act;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.tv_account_manage_wx, R.id.tv_account_manage_qq})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.tv_account_manage_wx:
                break;
            case R.id.tv_account_manage_qq:
                break;
        }
    }
}
