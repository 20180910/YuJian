package com.zhizhong.yujian.module.my.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.androidtools.inter.MyOnClickListener;
import com.github.baseclass.adapter.MyRecyclerViewHolder;
import com.zhizhong.yujian.IntentParam;
import com.zhizhong.yujian.R;
import com.zhizhong.yujian.adapter.MyAdapter;
import com.zhizhong.yujian.base.BaseActivity;
import com.zhizhong.yujian.base.GlideUtils;
import com.zhizhong.yujian.base.MyCallBack;
import com.zhizhong.yujian.module.my.network.ApiRequest;
import com.zhizhong.yujian.module.my.network.response.MyEvaluationObj;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class MyEvaluationActivity extends BaseActivity {
    @BindView(R.id.rv_my_evaluation)
    RecyclerView rv_my_evaluation;

    MyAdapter adapter;

    @Override
    protected int getContentView() {
        setAppTitle("我的评价");
        return R.layout.my_evaluation_act;
    }

    @Override
    protected void initView() {
        adapter=new MyAdapter<MyEvaluationObj>(mContext,R.layout.my_evaluation_item,pageSize) {
            @Override
            public void bindData(MyRecyclerViewHolder holder, int position, MyEvaluationObj bean) {
                ImageView iv_my_evaluation = holder.getImageView(R.id.iv_my_evaluation);
                GlideUtils.into(mContext,bean.getPhoto(),iv_my_evaluation);

                ImageView iv_my_evaluation_goods = holder.getImageView(R.id.iv_my_evaluation_goods);
                GlideUtils.into(mContext,bean.getGoods_img(),iv_my_evaluation_goods);

                holder.setText(R.id.tv_my_evaluation_name,bean.getNickname());
                holder.setText(R.id.tv_my_evaluation_time,bean.getAdd_time());
                holder.setText(R.id.tv_my_evaluation_content,bean.getContent());
                holder.setText(R.id.tv_my_evaluation_goods_name,bean.getGoods_name());
                holder.setText(R.id.tv_my_evaluation_goods_price,"¥"+bean.getPrice());


                TextView tv_my_evaluation_again = holder.getTextView(R.id.tv_my_evaluation_again);
                final Intent intent=new Intent();
                intent.putExtra(IntentParam.evaluateId,bean.getAppraise_id());
                //is_after_review是否已追评(0没有 1有),
                if(bean.getIs_after_review()==0){
                    tv_my_evaluation_again.setVisibility(View.VISIBLE);
                    intent.putExtra(IntentParam.zhuiPing,true);
                }else{
                    tv_my_evaluation_again.setVisibility(View.GONE);
                }
                holder.itemView.setOnClickListener(new MyOnClickListener() {
                    @Override
                    protected void onNoDoubleClick(View view) {
                        STActivityForResult(intent,AgainEvaluationActivity.class,200);
                    }
                });
            }
        };
        adapter.setOnLoadMoreListener(this);

        rv_my_evaluation.setLayoutManager(new LinearLayoutManager(mContext));
        rv_my_evaluation.addItemDecoration(getItemDivider());
        rv_my_evaluation.setAdapter(adapter);


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
        ApiRequest.myEvaluationList(map,new MyCallBack<List<MyEvaluationObj>>(mContext,pl_load,pcfl) {
            @Override
            public void onSuccess(List<MyEvaluationObj> list, int errorCode, String msg) {
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            switch (requestCode){
                case 200:
                    showLoading();
                    getData(1,false);
                break;
            }
        }
    }
}
