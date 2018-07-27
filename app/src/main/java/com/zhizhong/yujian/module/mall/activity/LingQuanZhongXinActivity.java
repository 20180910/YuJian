package com.zhizhong.yujian.module.mall.activity;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.github.baseclass.adapter.MyRecyclerViewHolder;
import com.zhizhong.yujian.R;
import com.zhizhong.yujian.adapter.MyAdapter;
import com.zhizhong.yujian.base.BaseActivity;
import com.zhizhong.yujian.base.MyCallBack;
import com.zhizhong.yujian.module.mall.network.ApiRequest;
import com.zhizhong.yujian.module.mall.network.response.YouHuiQuanObj;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class LingQuanZhongXinActivity extends BaseActivity {
    @BindView(R.id.rv_lingquan)
    RecyclerView rv_lingquan;

    MyAdapter adapter;
    @Override
    protected int getContentView() {
        setAppTitle("领券中心");
        return R.layout.lingquan_zhongxin_act;
    }

    @Override
    protected void initView() {

        adapter=new MyAdapter(mContext,R.layout.lingquan_zhongxin_item,pageSize) {
            @Override
            public void bindData(MyRecyclerViewHolder holder, int position, Object bean) {

            }
        };
        adapter.setOnLoadMoreListener(this);
        rv_lingquan.setAdapter(adapter);

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
        map.put("sign",getSign(map));
        ApiRequest.getYouHuiQuan(map, new MyCallBack<List<YouHuiQuanObj>>(mContext,pl_load,pcfl) {
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
