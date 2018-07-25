package com.zhizhong.yujian.module.home.fragment;

import android.os.Bundle;
import android.view.View;

import com.zhizhong.yujian.R;
import com.zhizhong.yujian.base.BaseFragment;
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

    }

    private boolean isFirstInto = true;

    private void setUserInfo(LoginObj obj) {
        if (obj == null) {


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