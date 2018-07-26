package com.zhizhong.yujian.module.mall.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.github.androidtools.PhoneUtils;
import com.github.baseclass.BaseDividerGridItem;
import com.zhizhong.yujian.R;
import com.zhizhong.yujian.adapter.GoodsAdapter;
import com.zhizhong.yujian.base.BaseFragment;
import com.zhizhong.yujian.base.MyCallBack;
import com.zhizhong.yujian.module.mall.network.ApiRequest;
import com.zhizhong.yujian.network.response.GoodsObj;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class TuiJianGoodsFragment extends BaseFragment {

    @BindView(R.id.rv_mall_tuijian)
    RecyclerView rv_mall_tuijian;


    GoodsAdapter tuiJianAdapter;
    @Override
    protected int getContentView() {
        return R.layout.tuijian_goods_frag;
    }

    @Override
    protected void initView() {
        tuiJianAdapter=new GoodsAdapter(mContext,R.layout.tuijian_goods_item,pageSize);
        tuiJianAdapter.setOnLoadMoreListener(this);

        rv_mall_tuijian.setLayoutManager(new GridLayoutManager(mContext,2));
        rv_mall_tuijian.addItemDecoration(new BaseDividerGridItem(mContext,PhoneUtils.dip2px(mContext,10),R.color.white));
        rv_mall_tuijian.setAdapter(tuiJianAdapter);



    }

    @Override
    protected void initData() {
        getData(1,false);
    }

    @Override
    protected void getData(int page,final boolean isLoad) {
        super.getData(page, isLoad);
        Map<String,String> map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("pagesize",pagesize+"");
        map.put("page",page+"");
        map.put("sign",getSign(map));
        ApiRequest.getMallTuiJian(map, new MyCallBack<List<GoodsObj>>(mContext,pl_load,pcfl) {
            @Override
            public void onSuccess(List<GoodsObj> list, int errorCode, String msg) {
                if(isLoad){
                    pageNum++;
                    tuiJianAdapter.addList(list,true);
                }else{
                    pageNum=2;
                    tuiJianAdapter.setList(list,true);
                }
            }
        });
    }

    @Override
    protected void onViewClick(View v) {

    }
}
