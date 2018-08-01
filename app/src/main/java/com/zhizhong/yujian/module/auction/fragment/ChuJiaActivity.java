package com.zhizhong.yujian.module.auction.fragment;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.github.baseclass.adapter.MyRecyclerViewHolder;
import com.github.fastshape.MyTextView;
import com.zhizhong.yujian.IntentParam;
import com.zhizhong.yujian.R;
import com.zhizhong.yujian.adapter.MyAdapter;
import com.zhizhong.yujian.base.BaseActivity;
import com.zhizhong.yujian.base.GlideUtils;
import com.zhizhong.yujian.base.MyCallBack;
import com.zhizhong.yujian.module.auction.network.ApiRequest;
import com.zhizhong.yujian.module.auction.network.response.ChuJiaObj;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class ChuJiaActivity extends BaseActivity {
    @BindView(R.id.rv_chujia)
    RecyclerView rv_chujia;
    MyAdapter adapter;
    private String goodsId;

    @Override
    protected int getContentView() {
        return R.layout.chujia_act;
    }

    @Override
    protected void initView() {
        goodsId = getIntent().getStringExtra(IntentParam.goodsId);

        adapter=new MyAdapter<ChuJiaObj>(mContext,R.layout.paimai_chujia_item,pageSize) {
            @Override
            public void bindData(MyRecyclerViewHolder holder, int position, ChuJiaObj bean) {
                ImageView imageView = holder.getImageView(R.id.iv_chujia_person);
                GlideUtils.into(mContext,bean.getPhoto(),imageView);
                holder.setText(R.id.tv_chujia_name,bean.getNickname());
                holder.setText(R.id.tv_chujia_price,"出价:¥"+bean.getPrice().toString());
                holder.setText(R.id.tv_chujia_time,bean.getAdd_time());


                MyTextView tv_chujia_flag = (MyTextView) holder.getView(R.id.tv_chujia_flag);
                if(position==0){
                    tv_chujia_flag.setText("领先");
                    tv_chujia_flag.getViewHelper().setSolidColor(ContextCompat.getColor(mContext,R.color.red)).complete();
                }else{
                    tv_chujia_flag.setText("出局");
                    tv_chujia_flag.getViewHelper().setSolidColor(ContextCompat.getColor(mContext,R.color.gray_99)).complete();
                }
            }
        };
        adapter.setOnLoadMoreListener(this);

        rv_chujia.setAdapter(adapter);
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
        map.put("goods_id", goodsId);
        map.put("pagesize",pagesize+"");
        map.put("page",page+"");
        map.put("sign",getSign(map));
        ApiRequest.getAllChuJia(map, new MyCallBack<List<ChuJiaObj>>(mContext,pl_load,pcfl) {
            @Override
            public void onSuccess(List<ChuJiaObj> list, int errorCode, String msg) {
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
