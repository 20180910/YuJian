package com.zhizhong.yujian.module.my.activity;

import android.view.View;

import com.zhizhong.yujian.R;
import com.zhizhong.yujian.base.BaseActivity;

import butterknife.OnClick;

public class TiXianResultActivity extends BaseActivity {
    @Override
    protected int getContentView() {
        setAppTitle("账户提现");
        return R.layout.ti_xian_result_act;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.tv_tixian_result_mingxi,R.id.tv_tixian_result})
    protected void onViewClick(View v) {
        switch (v.getId()){
            case R.id.tv_tixian_result_mingxi:
                setResult(RESULT_OK);
                finish();
            break;
            case R.id.tv_tixian_result:
                finish();
            break;
        }
    }
}
