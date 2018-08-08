package com.zhizhong.yujian.module.my.activity;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.github.androidtools.inter.MyOnClickListener;
import com.github.baseclass.adapter.MyRecyclerViewHolder;
import com.zhizhong.yujian.IntentParam;
import com.zhizhong.yujian.R;
import com.zhizhong.yujian.adapter.MyAdapter;
import com.zhizhong.yujian.base.BaseActivity;
import com.zhizhong.yujian.base.GlideUtils;
import com.zhizhong.yujian.base.MyCallBack;
import com.zhizhong.yujian.module.my.network.ApiRequest;
import com.zhizhong.yujian.module.my.network.response.KeMaiHuiObj;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

public class KeMaiHuiActivity extends BaseActivity {
    @BindView(R.id.rv_kemaihui)
    RecyclerView rv_kemaihui;

    MyAdapter adapter;
    private KeMaiHuiObj keMaiHuiObj;

    @Override
    protected int getContentView() {
        setAppTitle("我的可卖回");
        return R.layout.kemaihui_act;
    }

    @Override
    protected void initView() {
        adapter=new MyAdapter<KeMaiHuiObj.List2Bean>(mContext,R.layout.kemaihui_item,pageSize) {
            @Override
            public void bindData(MyRecyclerViewHolder holder, int position, final KeMaiHuiObj.List2Bean bean) {
                ImageView imageView = holder.getImageView(R.id.iv_kemaihui);
                GlideUtils.into(mContext,bean.getGoods_images(),imageView);

                holder.setText(R.id.tv_kemaihui_name,bean.getGoods_name());
                holder.setText(R.id.tv_kemaihui_buy_price,"买入价¥"+bean.getGoods_unitprice());
                holder.setText(R.id.tv_kemaihui_sales_price,"¥"+bean.getKemai_price());



                holder.getView(R.id.tv_kemaihui_sales).setOnClickListener(new MyOnClickListener() {
                    @Override
                    protected void onNoDoubleClick(View view) {

                    }
                });
                holder.itemView.setOnClickListener(new MyOnClickListener() {
                    @Override
                    protected void onNoDoubleClick(View view) {
                        Intent intent=new Intent();
                        intent.putExtra(IntentParam.keMaiHuiGoods,keMaiHuiObj);
                        STActivityForResult(intent,MyMaiHuiActivity.class,100);
                    }
                });
            }
        };
        adapter.setOnLoadMoreListener(this);

        rv_kemaihui.setAdapter(adapter);
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
        ApiRequest.keMaiHui(map, new MyCallBack<KeMaiHuiObj>(mContext,pl_load,pcfl) {
            private KeMaiHuiObj keMaiHuiObj;

            @Override
            public void onSuccess(KeMaiHuiObj obj, int errorCode, String msg) {
                keMaiHuiObj = obj;
                if(isLoad){
                    pageNum++;
                    adapter.addList(obj.getList2(),true);
                }else{
                    pageNum=2;
                    adapter.setList(obj.getList2(),true);
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
                case 100:
                    showLoading();
                    getData(1,false);
                break;
            }
        }
    }
}
