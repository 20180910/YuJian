package com.zhizhong.yujian.module.my.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.zhizhong.yujian.IntentParam;
import com.zhizhong.yujian.R;
import com.zhizhong.yujian.adapter.FragmentAdapter;
import com.zhizhong.yujian.base.BaseActivity;
import com.zhizhong.yujian.module.my.fragment.MyOrderFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MyOrderActivity extends BaseActivity {
    @BindView(R.id.tb_my_order)
    TabLayout tb_my_order;

    @BindView(R.id.vp_my_order)
    ViewPager vp_my_order;
    private int type;

    @Override
    protected int getContentView() {
        setAppTitle("我的订单");
        return R.layout.my_order_act;
    }

    @Override
    protected void initView() {
        type = getIntent().getIntExtra(IntentParam.type, 0);

        FragmentAdapter fragmentAdapter=new FragmentAdapter(getSupportFragmentManager());

        List<Fragment> list=new ArrayList<>();
        list.add(MyOrderFragment.newInstance(MyOrderFragment.type_0));
        list.add(MyOrderFragment.newInstance(MyOrderFragment.type_1));
        list.add(MyOrderFragment.newInstance(MyOrderFragment.type_2));
        list.add(MyOrderFragment.newInstance(MyOrderFragment.type_3));
        list.add(MyOrderFragment.newInstance(MyOrderFragment.type_4));
//        list.add(MyOrderFragment.newInstance(MyOrderFragment.type_5));
//        list.add(MyOrderFragment.newInstance(MyOrderFragment.type_6));

//        状态(0全部 1待付款 2待发货 3待收货 4待评价) 5已完成 6已取消
        List<String>titleList=new ArrayList<>();
        titleList.add("全部");//0
        titleList.add("待付款");//1
        titleList.add("待发货");//2
        titleList.add("待收货");//3
        titleList.add("待评价");//4
//        titleList.add("已完成");//5
//        titleList.add("已取消");//6

        fragmentAdapter.setList(list);
        fragmentAdapter.setTitleList(titleList);

        vp_my_order.setAdapter(fragmentAdapter);
        vp_my_order.setOffscreenPageLimit(list.size()-1);

        tb_my_order.setupWithViewPager(vp_my_order);

        vp_my_order.post(new Runnable() {
            @Override
            public void run() {
                vp_my_order.setCurrentItem(type);
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onViewClick(View v) {

    }
}
