package com.zhizhong.yujian.module.my.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.zhizhong.yujian.R;
import com.zhizhong.yujian.adapter.FragmentAdapter;
import com.zhizhong.yujian.base.BaseActivity;
import com.zhizhong.yujian.base.MyCallBack;
import com.zhizhong.yujian.module.my.fragment.YouHuiQuanFragment;
import com.zhizhong.yujian.module.my.network.ApiRequest;
import com.zhizhong.yujian.module.my.network.response.YouHuiQuanNumObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class YouHuiQuanActivity extends BaseActivity {
    @BindView(R.id.tv_youhuiquan)
    TabLayout tv_youhuiquan;

    @BindView(R.id.vp_youhuiquan)
    ViewPager vp_youhuiquan;

    @Override
    protected int getContentView() {
        setAppTitle("优惠券");
        return R.layout.youhuiquan_act;
    }

    @Override
    protected void initView() {
        FragmentAdapter adapter=new FragmentAdapter(getSupportFragmentManager());
        List<Fragment> list=new ArrayList<>();
        list.add(YouHuiQuanFragment.newInstance("0"));
        list.add(YouHuiQuanFragment.newInstance("1"));
        list.add(YouHuiQuanFragment.newInstance("2"));


        List<String>titleList=new ArrayList<>();
        titleList.add("未使用(-)");
        titleList.add("已使用(-)");
        titleList.add("已过期(-)");

        adapter.setList(list);
        adapter.setTitleList(titleList);
        vp_youhuiquan.setAdapter(adapter);

        tv_youhuiquan.setupWithViewPager(vp_youhuiquan);

    }

    @Override
    protected void initData() {
        getData(1,false);
    }

    @Override
    protected void getData(int page, boolean isLoad) {
        super.getData(page, isLoad);
        Map<String,String> map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("sign",getSign(map));
        ApiRequest.getYouHuiQuanNum(map, new MyCallBack<YouHuiQuanNumObj>(mContext) {
            @Override
            public void onSuccess(YouHuiQuanNumObj obj, int errorCode, String msg) {
                tv_youhuiquan.getTabAt(0).setText("未使用("+obj.getCount_wsy()+")");
                tv_youhuiquan.getTabAt(1).setText("已使用("+obj.getCount_ysy()+")");
                tv_youhuiquan.getTabAt(2).setText("已过期("+obj.getCount_ygq()+")");
            }
        });

    }

    @Override
    protected void onViewClick(View v) {

    }
}
