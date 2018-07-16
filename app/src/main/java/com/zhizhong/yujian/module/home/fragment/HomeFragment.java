package com.zhizhong.yujian.module.home.fragment;

import android.os.Bundle;
import android.view.View;

import com.zhizhong.yujian.R;
import com.zhizhong.yujian.base.BaseFragment;

/**
 * Created by Administrator on 2018/6/20.
 */

public class HomeFragment extends BaseFragment {
    @Override
    protected int getContentView() {
        return R.layout.home_frag;
    }

    public static HomeFragment newInstance() {
        Bundle args = new Bundle();

        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
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
}