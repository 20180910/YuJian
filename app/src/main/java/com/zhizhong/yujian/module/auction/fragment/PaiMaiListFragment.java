package com.zhizhong.yujian.module.auction.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.github.androidtools.PhoneUtils;
import com.github.baseclass.BaseDividerGridItem;
import com.zhizhong.yujian.IntentParam;
import com.zhizhong.yujian.R;
import com.zhizhong.yujian.base.BaseFragment;
import com.zhizhong.yujian.base.MyCallBack;
import com.zhizhong.yujian.module.auction.adapter.PaiMaiGoodsAdapter;
import com.zhizhong.yujian.module.auction.network.ApiRequest;
import com.zhizhong.yujian.module.auction.network.response.PaiMaiGoodsObj;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class PaiMaiListFragment extends BaseFragment {
    @BindView(R.id.rv_paimai_goods)
    RecyclerView rv_paimai_goods;
    PaiMaiGoodsAdapter adapter;
    @Override
    protected int getContentView() {
        return R.layout.pai_mai_frag;
    }

    public static PaiMaiListFragment newInstance(String type) {

        Bundle args = new Bundle();
        args.putString(IntentParam.type,type);

        PaiMaiListFragment fragment = new PaiMaiListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initView() {
        adapter=new PaiMaiGoodsAdapter(mContext,R.layout.paimai_all_item,pageSize);
        adapter.setOnLoadMoreListener(this);

        rv_paimai_goods.setLayoutManager(new GridLayoutManager(mContext, 2));
        rv_paimai_goods.addItemDecoration(new BaseDividerGridItem(mContext, PhoneUtils.dip2px(mContext, 10), R.color.white));
        rv_paimai_goods.setAdapter(adapter);

    }

    @Override
    protected void initData() {
        showProgress();
        getData(1,false);
    }

    @Override
    protected void getData(int page,final boolean isLoad) {
        super.getData(page, isLoad);
        Map<String, String> map = new HashMap<String, String>();
        map.put("type", getArguments().getString(IntentParam.type));
        map.put("pagesize", pagesize + "");
        map.put("page", page + "");
        map.put("sign", getSign(map));
        ApiRequest.getAllPaiMai(map, new MyCallBack<List<PaiMaiGoodsObj>>(mContext, pl_load, pcfl) {
            @Override
            public void onSuccess(List<PaiMaiGoodsObj> list, int errorCode, String msg) {
                if (isLoad) {
                    pageNum++;
                    adapter.addList(list, true);
                } else {
                    pageNum = 2;
                    adapter.setList(list, true);
                }
            }
        });
    }
    @Override
    protected void onViewClick(View v) {

    }
}

