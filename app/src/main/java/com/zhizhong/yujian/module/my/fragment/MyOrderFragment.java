package com.zhizhong.yujian.module.my.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.github.androidtools.PhoneUtils;
import com.github.baseclass.adapter.MyRecyclerViewHolder;
import com.zhizhong.yujian.IntentParam;
import com.zhizhong.yujian.R;
import com.zhizhong.yujian.adapter.MyAdapter;
import com.zhizhong.yujian.base.BaseFragment;
import com.zhizhong.yujian.base.MyCallBack;
import com.zhizhong.yujian.module.my.network.ApiRequest;
import com.zhizhong.yujian.module.my.network.response.CollectionGoodsObj;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class MyOrderFragment extends BaseFragment {
    @BindView(R.id.rv_my_order)
    RecyclerView rv_my_order;

    MyAdapter adapter;

    @Override
    protected int getContentView() {
        return R.layout.my_order_frag;
    }

    @Override
    protected void initView() {
        adapter=new MyAdapter(mContext,R.layout.my_order_item,pageSize) {
            @Override
            public void bindData(MyRecyclerViewHolder holder, int position, Object bean) {

            }
        };
        adapter.setOnLoadMoreListener(this);

        rv_my_order.setLayoutManager(new LinearLayoutManager(mContext));
        rv_my_order.addItemDecoration(getItemDivider(PhoneUtils.dip2px(mContext,5)));
        rv_my_order.setAdapter(adapter);

    }

    public static MyOrderFragment newInstance(int type) {

        Bundle args = new Bundle();
        args.putInt(IntentParam.type,type);

        MyOrderFragment fragment = new MyOrderFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected void initData() {
        showProgress();
        getData(1,false);
    }

    @Override
    protected void getData(int page,final boolean isLoad) {
        super.getData(page, isLoad);
        Map<String,String> map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("type",getArguments().getInt(IntentParam.type)+"");
        map.put("pagesize",pagesize+"");
        map.put("page",page+"");
        map.put("sign",getSign(map));
        ApiRequest.getOrderList(map, new MyCallBack<List<CollectionGoodsObj>>(mContext,pl_load,pcfl) {
            @Override
            public void onSuccess(List<CollectionGoodsObj> list, int errorCode, String msg) {
                if(isLoad){
                    pageNum++;
                    adapter.addList(list,true);
                }else{
                    pageNum=2;
                    adapter.setList(list,true);
                }
            }
        });

    }

    @Override
    protected void onViewClick(View v) {

    }
}
