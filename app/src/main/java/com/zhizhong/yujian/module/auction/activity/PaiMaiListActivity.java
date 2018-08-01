package com.zhizhong.yujian.module.auction.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.zhizhong.yujian.IntentParam;
import com.zhizhong.yujian.R;
import com.zhizhong.yujian.adapter.FragmentAdapter;
import com.zhizhong.yujian.base.BaseActivity;
import com.zhizhong.yujian.module.auction.fragment.PaiMaiListFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class PaiMaiListActivity extends BaseActivity {
    @BindView(R.id.tb_paimai)
    TabLayout tb_paimai;
    @BindView(R.id.vp_paimai)
    ViewPager vp_paimai;

    @Override
    protected int getContentView() {
        setAppTitle("拍卖");
        return R.layout.pai_mai_act;
    }

    @Override
    protected void initView() {
       final int index = getIntent().getIntExtra(IntentParam.index, 0);
        FragmentAdapter adapter=new FragmentAdapter(getSupportFragmentManager());
        List<Fragment> list=new ArrayList<>();
        List<String>titleList=new ArrayList<>();
        titleList.add("全部");
        titleList.add("0元拍");
        titleList.add("新手推荐");
        titleList.add("历史拍品");
        //类别(0全部 1零元拍 2新人推荐 3历史拍品)
        list.add(PaiMaiListFragment.newInstance("0"));
        list.add(PaiMaiListFragment.newInstance("1"));
        list.add(PaiMaiListFragment.newInstance("2"));
        list.add(PaiMaiListFragment.newInstance("3"));
        adapter.setList(list);
        adapter.setTitleList(titleList);
        vp_paimai.setAdapter(adapter);
        vp_paimai.setOffscreenPageLimit(list.size()-1);
        tb_paimai.setupWithViewPager(vp_paimai);

        vp_paimai.post(new Runnable() {
            @Override
            public void run() {
                vp_paimai.setCurrentItem(index);
            }
        });
    }
   /* @Override
    protected void initRxBus() {
        super.initRxBus();
        getEventReplay(SelectPaiMaiTypeEvent.class, new MyConsumer<SelectPaiMaiTypeEvent>() {
            @Override
            public void onAccept(SelectPaiMaiTypeEvent event) {
                vp_paimai.setCurrentItem(event.index);
            }
        });
    }*/

    @Override
    protected void initData() {

    }

    @Override
    protected void onViewClick(View v) {

    }
}
