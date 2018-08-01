package com.zhizhong.yujian.module.auction.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.zhizhong.yujian.R;
import com.zhizhong.yujian.adapter.FragmentAdapter;
import com.zhizhong.yujian.base.BaseActivity;
import com.zhizhong.yujian.module.auction.fragment.PaiMaiOrderFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class PaiMaiOrderActivity extends BaseActivity {
    @BindView(R.id.tb_paimai_order)
    TabLayout tb_paimai_order;

    @BindView(R.id.vp_paimai_order)
    ViewPager vp_paimai_order;

    @Override
    protected int getContentView() {
        setAppTitle("我的拍卖");
        return R.layout.paimai_order_act;
    }

    @Override
    protected void initView() {
        FragmentAdapter fragmentAdapter=new FragmentAdapter(getSupportFragmentManager());

        List<Fragment> list=new ArrayList<>();
        //(0拍卖中 1待付款 2待发货 3待收货 4已完成 5已出局 6已取消)
        list.add(PaiMaiOrderFragment.newInstance(PaiMaiOrderFragment.type0));
        list.add(PaiMaiOrderFragment.newInstance(PaiMaiOrderFragment.type5));
        list.add(PaiMaiOrderFragment.newInstance(PaiMaiOrderFragment.type1));
        list.add(PaiMaiOrderFragment.newInstance(PaiMaiOrderFragment.type2));
        list.add(PaiMaiOrderFragment.newInstance(PaiMaiOrderFragment.type3));
        list.add(PaiMaiOrderFragment.newInstance(PaiMaiOrderFragment.type4));

        List<String>titleList=new ArrayList<>();
        titleList.add("拍卖中");//0
        titleList.add("已出局");//5
        titleList.add("待支付");//1
        titleList.add("待发货");//2
        titleList.add("待收货");//3
        titleList.add("已完成");//4

        fragmentAdapter.setList(list);
        fragmentAdapter.setTitleList(titleList);

        vp_paimai_order.setAdapter(fragmentAdapter);
        vp_paimai_order.setOffscreenPageLimit(list.size()-1);

        tb_paimai_order.setupWithViewPager(vp_paimai_order);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onViewClick(View v) {

    }
}
