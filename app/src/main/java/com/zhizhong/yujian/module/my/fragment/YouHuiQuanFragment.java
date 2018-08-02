package com.zhizhong.yujian.module.my.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.github.androidtools.PhoneUtils;
import com.github.baseclass.adapter.MyRecyclerViewHolder;
import com.zhizhong.yujian.IntentParam;
import com.zhizhong.yujian.R;
import com.zhizhong.yujian.adapter.MyAdapter;
import com.zhizhong.yujian.base.BaseFragment;
import com.zhizhong.yujian.base.MyCallBack;
import com.zhizhong.yujian.module.mall.network.response.YouHuiQuanObj;
import com.zhizhong.yujian.module.my.network.ApiRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class YouHuiQuanFragment extends BaseFragment {
    @BindView(R.id.rv_youhuiquan)
    RecyclerView rv_youhuiquan;

    MyAdapter adapter;

    @Override
    protected int getContentView() {
        return R.layout.youhuiquan_frag;
    }

    @Override
    protected void initView() {
        adapter=new MyAdapter<YouHuiQuanObj>(mContext,R.layout.youhuiquan_item,pageSize) {
            @Override
            public void bindData(MyRecyclerViewHolder holder, int position, YouHuiQuanObj bean) {
                ImageView iv_youhuiquan_status = holder.getImageView(R.id.iv_youhuiquan_status);
                if("1".equals(getArguments().getString(IntentParam.type))){
                    iv_youhuiquan_status.setVisibility(View.VISIBLE);
                    iv_youhuiquan_status.setImageResource(R.drawable.yishiyong);
                }else if("2".equals(getArguments().getString(IntentParam.type))){
                    iv_youhuiquan_status.setVisibility(View.VISIBLE);
                    iv_youhuiquan_status.setImageResource(R.drawable.yiguoqi);
                }else{
                    iv_youhuiquan_status.setVisibility(View.GONE);
                }

                holder.setText(R.id.tv_youhuiquan_money,"¥"+bean.getFace_value());
                holder.setText(R.id.tv_youhuiquan_full_money,"满"+bean.getAvailable().toString()+"元可用");
                holder.setText(R.id.tv_youhuiquan_title,bean.getTitle());
                holder.setText(R.id.tv_youhuiquan_qx,bean.getDeadline());
            }
        };
        adapter.setOnLoadMoreListener(this);

        rv_youhuiquan.setLayoutManager(new LinearLayoutManager(mContext));
        rv_youhuiquan.addItemDecoration(getItemDivider(PhoneUtils.dip2px(mContext,5)));
        rv_youhuiquan.setAdapter(adapter);


    }

    public static YouHuiQuanFragment newInstance(String type) {

        Bundle args = new Bundle();
        args.putString(IntentParam.type,type);
        YouHuiQuanFragment fragment = new YouHuiQuanFragment();
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
        map.put("type",getArguments().getString(IntentParam.type));
        map.put("pagesize",pagesize+"");
        map.put("page",page+"");
        map.put("sign",getSign(map));
        ApiRequest.getMyYouHuiQuan(map, new MyCallBack<List<YouHuiQuanObj>>(mContext,pl_load,pcfl) {
            @Override
            public void onSuccess(List<YouHuiQuanObj> list, int errorCode, String msg) {
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
