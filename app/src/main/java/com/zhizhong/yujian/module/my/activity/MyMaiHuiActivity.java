package com.zhizhong.yujian.module.my.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhizhong.yujian.IntentParam;
import com.zhizhong.yujian.R;
import com.zhizhong.yujian.base.BaseActivity;
import com.zhizhong.yujian.module.my.network.response.KeMaiHuiObj;

import butterknife.BindView;
import butterknife.OnClick;

public class MyMaiHuiActivity extends BaseActivity {

    @BindView(R.id.tv_kemaihui_detail_jihui)
    TextView tv_kemaihui_detail_jihui;
    @BindView(R.id.tv_kemaihui_detail_name)
    TextView tv_kemaihui_detail_name;
    @BindView(R.id.tv_kemaihui_detail_phone)
    TextView tv_kemaihui_detail_phone;
    @BindView(R.id.tv_kemaihui_detail_address)
    TextView tv_kemaihui_detail_address;
    @BindView(R.id.ll_kemaihui_detail_address_content)
    LinearLayout ll_kemaihui_detail_address_content;
    @BindView(R.id.iv_kemaihui_detail)
    ImageView iv_kemaihui_detail;
    @BindView(R.id.tv_kemaihui_detail_goods_name)
    TextView tv_kemaihui_detail_goods_name;
    @BindView(R.id.tv_kemaihui_detail_buy_price)
    TextView tv_kemaihui_detail_buy_price;
    @BindView(R.id.tv_kemaihui_detail_sales_price)
    TextView tv_kemaihui_detail_sales_price;

    @BindView(R.id.tv_kemaihui_detail_sales)
    TextView tv_kemaihui_detail_sales;

    private KeMaiHuiObj keMaiHuiObj;

    @Override
    protected int getContentView() {
        return R.layout.my_maihui_act;
    }

    @Override
    protected void initView() {
        keMaiHuiObj = (KeMaiHuiObj) getIntent().getSerializableExtra(IntentParam.keMaiHuiGoods);

        if(TextUtils.isEmpty(keMaiHuiObj.getAddress())){
            tv_kemaihui_detail_sales.setVisibility(View.GONE);
        }else{
            tv_kemaihui_detail_sales.setVisibility(View.VISIBLE);
        }



    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.tv_kemaihui_detail_sales})
    public void onViewClick(View v) {
        switch (v.getId()){
            case R.id.tv_kemaihui_detail_sales:

            break;
        }
    }
}
