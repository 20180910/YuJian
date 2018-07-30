package com.zhizhong.yujian.module.mall.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.androidtools.PhoneUtils;
import com.github.baseclass.adapter.MyRecyclerViewHolder;
import com.library.base.BaseObj;
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

        adapter=new MyAdapter<YouHuiQuanObj>(mContext,R.layout.lingquan_zhongxin_item,pageSize) {
            @Override
            public void bindData(MyRecyclerViewHolder holder, int position, final YouHuiQuanObj bean) {
                ImageView imageView = holder.getImageView(R.id.iv_lingquan_status);
                TextView tv_lingquan_status = holder.getTextView(R.id.tv_lingquan_status);
                if(bean.getStatus()==1){
                    imageView.setVisibility(View.VISIBLE);
                    tv_lingquan_status.setVisibility(View.GONE);
                }else{
                    imageView.setVisibility(View.GONE);
                    tv_lingquan_status.setVisibility(View.VISIBLE);
                    tv_lingquan_status.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            lingQu(bean.getId());
                        }
                    });
                }
                holder.setText(R.id.tv_lingquan_money,"¥"+bean.getFace_value());
                holder.setText(R.id.tv_lingquan_full_money,"满"+bean.getAvailable().toString()+"元可用");
                holder.setText(R.id.tv_lingquan_title,bean.getTitle());
                holder.setText(R.id.tv_lingquan_qx,bean.getDeadline());
            }
        };
        adapter.setOnLoadMoreListener(this);
        rv_lingquan.setLayoutManager(new LinearLayoutManager(mContext));
        rv_lingquan.addItemDecoration(getItemDivider(PhoneUtils.dip2px(mContext,8)));
        rv_lingquan.setAdapter(adapter);


    }

    private void lingQu(String id) {
        showLoading();
        Map<String,String>map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("coupons_id",id);
        map.put("sign",getSign(map));
        ApiRequest.lingQuYouHuiQuan(map, new MyCallBack<BaseObj>(mContext,true) {
            @Override
            public void onSuccess(BaseObj obj, int errorCode, String msg) {
                showMsg(msg);
                getData(1,false);
            }
        });

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
