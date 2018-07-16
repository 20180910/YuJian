package com.zhizhong.yujian.module.home.fragment;

import android.os.Bundle;
import android.view.View;

import com.github.androidtools.SPUtils;
import com.zhizhong.yujian.AppXml;
import com.zhizhong.yujian.R;
import com.zhizhong.yujian.base.BaseFragment;
import com.zhizhong.yujian.base.MyCallBack;
import com.zhizhong.yujian.module.my.network.ApiRequest;
import com.zhizhong.yujian.module.my.network.response.LoginObj;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/6/20.
 */

public class MyFragment extends BaseFragment {


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
        setUserInfo(null);

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
                setUserData(obj);
            }

            private void setUserData(LoginObj obj) {

                setUserInfo(obj);

                SPUtils.setPrefString(mContext, AppXml.userId, obj.getUser_id());
                SPUtils.setPrefString(mContext, AppXml.mobile, obj.getMobile());
                SPUtils.setPrefString(mContext, AppXml.bi_name, obj.getBi_name());
                SPUtils.setPrefString(mContext, AppXml.signature, obj.getSignature());
                SPUtils.setPrefString(mContext, AppXml.sex, obj.getSex());
                SPUtils.setPrefString(mContext, AppXml.birthday, obj.getBirthday());
                SPUtils.setPrefString(mContext, AppXml.province, obj.getProvince());
                SPUtils.setPrefString(mContext, AppXml.area, obj.getArea());
                SPUtils.setPrefString(mContext, AppXml.city, obj.getCity());

                SPUtils.setPrefInt(mContext, AppXml.rongshu_leaf, obj.getRongshu_leaf());
                SPUtils.setPrefInt(mContext, AppXml.card_bag, obj.getCard_bag());
                SPUtils.setPrefInt(mContext, AppXml.guanzhu, obj.getGuanzhu());
                SPUtils.setPrefInt(mContext, AppXml.fensi, obj.getFensi());
                SPUtils.setPrefInt(mContext, AppXml.create_value, obj.getCreate_value());

                SPUtils.setPrefString(mContext, AppXml.create_name, obj.getCreate_name());

                SPUtils.setPrefInt(mContext, AppXml.create_fenshu, obj.getCreate_fenshu());
                SPUtils.setPrefInt(mContext, AppXml.create_xing_num, obj.getCreate_xing_num());
                SPUtils.setPrefInt(mContext, AppXml.read_value, obj.getRead_value());

                SPUtils.setPrefString(mContext, AppXml.read_name, obj.getRead_name());

                SPUtils.setPrefInt(mContext, AppXml.read_fenshu, obj.getRead_fenshu());
                SPUtils.setPrefInt(mContext, AppXml.read_xing_num, obj.getRead_xing_num());
//                SPUtils.setPrefInt(mContext, AppXml.is_show_biaoqian, obj.getIs_show_biaoqian());
                SPUtils.setPrefInt(mContext, AppXml.is_show_nicheng, obj.getIs_show_nicheng());
            }
        });
    }

    private boolean isFirstInto = true;

    private void setUserInfo(LoginObj obj) {
        if (obj == null) {
            obj = new LoginObj();
            obj.setBi_name(SPUtils.getString(mContext, AppXml.bi_name, null));
            obj.setSignature(SPUtils.getString(mContext, AppXml.signature, null));
            obj.setRongshu_leaf(SPUtils.getInt(mContext, AppXml.rongshu_leaf, 0));
            obj.setCard_bag(SPUtils.getInt(mContext, AppXml.card_bag, 0));
            obj.setGuanzhu(SPUtils.getInt(mContext, AppXml.guanzhu, 0));
            obj.setFensi(SPUtils.getInt(mContext, AppXml.fensi, 0));
            obj.setCreate_name(SPUtils.getString(mContext, AppXml.create_name, null));
            obj.setCreate_xing_num(SPUtils.getInt(mContext, AppXml.create_xing_num, 0));
            obj.setCreate_value(SPUtils.getInt(mContext, AppXml.create_value, 0));
            obj.setCreate_fenshu(SPUtils.getInt(mContext, AppXml.create_fenshu, 0));
            obj.setRead_name(SPUtils.getString(mContext, AppXml.read_name, null));
            obj.setRead_xing_num(SPUtils.getInt(mContext, AppXml.read_xing_num, 0));
            obj.setRead_fenshu(SPUtils.getInt(mContext, AppXml.read_fenshu, 0));
            obj.setRead_value(SPUtils.getInt(mContext, AppXml.read_value, 0));

        }

    }


    @Override
    protected void initData() {

    }

    public void onViewClick(View view) {
        switch (view.getId()) {

        }
    }
}