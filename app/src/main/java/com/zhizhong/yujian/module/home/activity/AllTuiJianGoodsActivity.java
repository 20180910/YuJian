package com.zhizhong.yujian.module.home.activity;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.github.androidtools.PhoneUtils;
import com.github.baseclass.BaseDividerGridItem;
import com.zhizhong.yujian.R;
import com.zhizhong.yujian.adapter.GoodsAdapter;
import com.zhizhong.yujian.base.BaseActivity;
import com.zhizhong.yujian.base.MyCallBack;
import com.zhizhong.yujian.module.home.network.ApiRequest;
import com.zhizhong.yujian.network.response.GoodsObj;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class AllTuiJianGoodsActivity extends BaseActivity {
    @BindView(R.id.rv_tuijian_goods)
    RecyclerView rv_tuijian_goods;

    GoodsAdapter adapter;

    @Override
    protected int getContentView() {
        setAppTitle("推荐商品");
        return R.layout.all_tuijian_act;
    }

    @Override
    protected void initView() {
        adapter =new GoodsAdapter(mContext,R.layout.tuijian_goods_item,pageSize);
        adapter.setOnLoadMoreListener(this);

        rv_tuijian_goods.setLayoutManager(new GridLayoutManager(mContext,2));
        rv_tuijian_goods.addItemDecoration(new BaseDividerGridItem(mContext, PhoneUtils.dip2px(mContext,10),R.color.white));
        rv_tuijian_goods.setAdapter(adapter);

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
        map.put("pagesize",pagesize+"");
        map.put("page",page+"");
        map.put("sign",getSign(map));
        ApiRequest.getAllTuiJian(map, new MyCallBack<List<GoodsObj>>(mContext,pl_load,pcfl) {
            @Override
            public void onSuccess(List<GoodsObj> list, int errorCode, String msg) {
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
