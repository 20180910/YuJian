package com.zhizhong.yujian.module.my.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.github.androidtools.inter.MyOnClickListener;
import com.github.baseclass.adapter.MyRecyclerViewHolder;
import com.github.mydialog.MyDialog;
import com.zhizhong.yujian.IntentParam;
import com.zhizhong.yujian.R;
import com.zhizhong.yujian.adapter.MyAdapter;
import com.zhizhong.yujian.base.BaseActivity;
import com.zhizhong.yujian.base.GlideUtils;
import com.zhizhong.yujian.base.MyCallBack;
import com.zhizhong.yujian.module.auction.activity.AuctionDetailActivity;
import com.zhizhong.yujian.module.mall.activity.GoodsDetailActivity;
import com.zhizhong.yujian.module.my.network.ApiRequest;
import com.zhizhong.yujian.module.my.network.response.CollectionGoodsObj;
import com.zhizhong.yujian.network.NetApiRequest;
import com.zhizhong.yujian.network.response.CollectObj;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class MyCollectionActivity extends BaseActivity {
    @BindView(R.id.rv_my_collection)
    RecyclerView rv_my_collection;

    MyAdapter adapter;
    @Override
    protected int getContentView() {
        setAppTitle("我的收藏");
        return R.layout.my_collection_act;
    }

    @Override
    protected void initView() {
        adapter=new MyAdapter<CollectionGoodsObj>(mContext,R.layout.my_collection_item,pageSize) {
            @Override
            public void bindData(MyRecyclerViewHolder holder, int position, final CollectionGoodsObj bean) {
                ImageView imageView = holder.getImageView(R.id.iv_collection);
                GlideUtils.into(mContext,bean.getGoods_image(),imageView);

                holder.setText(R.id.tv_collection_title,bean.getGoods_name());
                holder.setText(R.id.tv_collection_price,"¥"+bean.getGoods_price().toString());
                holder.getTextView(R.id.tv_collection_cancel).setOnClickListener(new MyOnClickListener() {
                    @Override
                    protected void onNoDoubleClick(View view) {
                        cancelCollection(bean.getGoods_id());
                    }
                });
                holder.getTextView(R.id.tv_collection_buy).setOnClickListener(new MyOnClickListener() {
                    @Override
                    protected void onNoDoubleClick(View view) {
                        if(bean.getType()==1){//普通商品
                            Intent intent=new Intent();
                            intent.putExtra(IntentParam.goodsId,bean.getGoods_id());
                            STActivity(intent,GoodsDetailActivity.class);
                        }else{
                            Intent intent=new Intent();
                            intent.putExtra(IntentParam.goodsId,bean.getGoods_id());
                            STActivity(intent,AuctionDetailActivity.class);
                        }
                    }
                });
            }
        };
        adapter.setOnLoadMoreListener(this);

        rv_my_collection.setAdapter(adapter);
    }


    private void cancelCollection(final String goodsId) {
        MyDialog.Builder mDialog=new MyDialog.Builder(mContext);
        mDialog.setMessage("确定取消收藏吗?");
        mDialog.setNegativeButton(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        mDialog.setPositiveButton(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                showLoading();
                Map<String,String>map=new HashMap<String,String>();
                map.put("user_id",getUserId());
                map.put("goods_id",goodsId);
                map.put("sign",getSign(map));
                NetApiRequest.collectGoods(map, new MyCallBack<CollectObj>(mContext,true) {
                    @Override
                    public void onSuccess(CollectObj obj, int errorCode, String msg) {
                        showMsg(msg);
                        getData(1,false);
                    }
                });
            }
        });
        mDialog.create().show();
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
        ApiRequest.getMyCollection(map, new MyCallBack<List<CollectionGoodsObj>>(mContext,pl_load,pcfl) {
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
