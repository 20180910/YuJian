package com.zhizhong.yujian.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/21.
 */

public class FragmentAdapter extends FragmentStatePagerAdapter{
    private List<Fragment> list=new ArrayList<>();
    private List<String>titleList=new ArrayList<>();
    public FragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    public List<Fragment> getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }

    public List<String> getTitleList() {
        return titleList;
    }

    public void setTitleList(List<String> titleList) {
        this.titleList = titleList;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }
    @Override
    public int getCount() {
        return list==null?0:list.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titleList.get(position);
    }
}
