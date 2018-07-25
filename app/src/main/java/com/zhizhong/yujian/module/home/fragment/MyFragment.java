package com.zhizhong.yujian.module.home.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.androidtools.SPUtils;
import com.github.fastshape.MyImageView;
import com.google.gson.Gson;
import com.zhizhong.yujian.AppXml;
import com.zhizhong.yujian.R;
import com.zhizhong.yujian.base.BaseFragment;
import com.zhizhong.yujian.base.GlideUtils;
import com.zhizhong.yujian.base.MyCallBack;
import com.zhizhong.yujian.module.my.activity.MessageActivity;
import com.zhizhong.yujian.module.my.activity.SettingActivity;
import com.zhizhong.yujian.module.my.network.ApiRequest;
import com.zhizhong.yujian.module.my.network.response.LoginObj;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/6/20.
 */

public class MyFragment extends BaseFragment {


    @BindView(R.id.iv_my_setting)
    ImageView iv_my_setting;
    @BindView(R.id.iv_my_message)
    ImageView iv_my_message;
    @BindView(R.id.iv_my)
    MyImageView iv_my;
    @BindView(R.id.tv_my_nickname)
    TextView tv_my_nickname;
    @BindView(R.id.tv_my_balance)
    TextView tv_my_balance;
    @BindView(R.id.tv_my_coupon)
    TextView tv_my_coupon;

    @Override
    protected int getContentView() {
        return R.layout.my_frag;
    }

    public static MyFragment newInstance() {

        Bundle args = new Bundle();

        MyFragment fragment = new MyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initView() {

    }

    @Override
    public void onResume() {
        super.onResume();
        setUserInfo();
    }

    @Override
    protected void onMyReStart() {
        super.onMyReStart();
        getUserInfo();
    }

    private void getUserInfo() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("user_id", getUserId());
        map.put("sign", getSign(map));
        ApiRequest.getUserInfo(map, new MyCallBack<LoginObj>(mContext) {
            @Override
            public void onSuccess(LoginObj obj, int errorCode, String msg) {
                String json = new Gson().toJson(obj);
                SPUtils.setPrefString(mContext, AppXml.loginJson, json);

            }
        });

    }


    private void setUserInfo() {
        String json = SPUtils.getString(mContext, AppXml.loginJson, null);
        if (!TextUtils.isEmpty(json)) {
            LoginObj loginObj = new Gson().fromJson(json, LoginObj.class);
            GlideUtils.intoD(mContext,loginObj.getAvatar(),iv_my,R.drawable.default_person);

            tv_my_nickname.setText(loginObj.getNick_name());
            tv_my_balance.setText("¥"+loginObj.getAmount());
            tv_my_coupon.setText(loginObj.getCoupons_count()+"");
        }
    }


    @Override
    protected void initData() {

    }

    @OnClick({R.id.iv_my_setting, R.id.iv_my_message, R.id.ll_my_allorder, R.id.ll_my_daifukuan, R.id.ll_my_daifahuo, R.id.ll_my_daipingjia, R.id.tv_my_wodepaimai, R.id.tv_my_wodetuikuan, R.id.tv_my_wodekemaihui, R.id.tv_my_wodeshoucang, R.id.tv_my_wodedizhi, R.id.tv_my_wodebaozhengjin, R.id.tv_my_wodepingjia, R.id.tv_my_help})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.iv_my_setting:
                STActivity(SettingActivity.class);
                break;
            case R.id.iv_my_message:
                STActivity(MessageActivity.class);
                break;
            case R.id.ll_my_allorder:
                break;
            case R.id.ll_my_daifukuan:
                break;
            case R.id.ll_my_daifahuo:
                break;
            case R.id.ll_my_daipingjia:
                break;
            case R.id.tv_my_wodepaimai:
                break;
            case R.id.tv_my_wodetuikuan:
                break;
            case R.id.tv_my_wodekemaihui:
                break;
            case R.id.tv_my_wodeshoucang:
                break;
            case R.id.tv_my_wodedizhi:
                break;
            case R.id.tv_my_wodebaozhengjin:
                break;
            case R.id.tv_my_wodepingjia:
                break;
            case R.id.tv_my_help:
                break;
        }
    }
}