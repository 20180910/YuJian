package com.zhizhong.yujian.module.home.fragment;

import android.os.Bundle;
import android.view.View;

import com.github.baseclass.adapter.MyLoadMoreAdapter;
import com.zhizhong.yujian.R;
import com.zhizhong.yujian.base.BaseFragment;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/6/20.
 */

public class LiveFragment extends BaseFragment {

    private MyLoadMoreAdapter flagAdapter;

    @Override
    protected int getContentView() {
        return R.layout.live_frag;
    }

    public static LiveFragment newInstance() {

        Bundle args = new Bundle();

        LiveFragment fragment = new LiveFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initView() {

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





    @Override
    protected void initData() {

    }

    public void onViewClick(View view) {
        switch (view.getId()) {

        }
    }
}