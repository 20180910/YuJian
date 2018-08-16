package com.zhizhong.yujian.module.mall.activity;

import android.content.Intent;
import android.view.View;

import com.zhizhong.yujian.IntentParam;
import com.zhizhong.yujian.R;
import com.zhizhong.yujian.base.BaseActivity;
import com.zhizhong.yujian.module.auction.activity.PaiMaiOrderActivity;
import com.zhizhong.yujian.module.home.activity.MainActivity;
import com.zhizhong.yujian.module.my.activity.MyOrderActivity;

import butterknife.OnClick;

public class PaySuccessActivity extends BaseActivity {

    private Intent intent;
    private boolean isPaiMai;

    @Override
    protected int getContentView() {
        setAppTitle("支付成功");
        return R.layout.pay_success_act;
    }

    @Override
    protected void initView() {
        isPaiMai = getIntent().getBooleanExtra(IntentParam.isPaiMai, false);
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.tv_pay_lookorder,R.id.tv_pay_back})
    protected void onViewClick(View v) {
        switch (v.getId()){
            case R.id.tv_pay_lookorder:
                intent= new Intent();
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                if(isPaiMai){
                    STActivity(intent,PaiMaiOrderActivity.class);
                }else{
                    STActivity(intent,MyOrderActivity.class);
                }
                finish();
            break;
            case R.id.tv_pay_back:
                intent = new Intent(IntentParam.selectHome);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                STActivity(intent,MainActivity.class);
                finish();
            break;
        }
    }
}
