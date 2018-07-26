package com.zhizhong.yujian.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.androidtools.PhoneUtils;
import com.github.androidtools.SPUtils;
import com.github.androidtools.inter.MyOnClickListener;
import com.github.baseclass.adapter.MyRecyclerViewHolder;
import com.github.baseclass.view.Loading;
import com.github.fastshape.MyTextView;
import com.zhizhong.yujian.AppXml;
import com.zhizhong.yujian.GetSign;
import com.zhizhong.yujian.IntentParam;
import com.zhizhong.yujian.R;
import com.zhizhong.yujian.base.GlideUtils;
import com.zhizhong.yujian.base.MyCallBack;
import com.zhizhong.yujian.module.mall.activity.GoodsDetailActivity;
import com.zhizhong.yujian.module.my.activity.LoginActivity;
import com.zhizhong.yujian.network.NetApiRequest;
import com.zhizhong.yujian.network.response.CollectObj;
import com.zhizhong.yujian.network.response.GoodsObj;
import com.zhizhong.yujian.tools.TextViewUtils;

import java.util.HashMap;
import java.util.Map;

public class GoodsAdapter extends MyAdapter<GoodsObj> {
    public GoodsAdapter(Context mContext, int layoutId, int pageSize) {
        super(mContext, R.layout.tuijian_goods_item, pageSize);
    }

    @Override
    public void bindData(MyRecyclerViewHolder holder, int position, final GoodsObj item) {
        super.bindData(holder, position, item);
        final ImageView imageView = holder.getImageView(R.id.iv_goods_img);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if(position%2==0){
            layoutParams.height= (PhoneUtils.getScreenWidth(mContext)-PhoneUtils.dip2px(mContext,10))/2;
            layoutParams.width= (PhoneUtils.getScreenWidth(mContext)-PhoneUtils.dip2px(mContext,10))/2;
        }else{
            layoutParams.height= (PhoneUtils.getScreenWidth(mContext)-PhoneUtils.dip2px(mContext,10))/2;
            layoutParams.width= PhoneUtils.getScreenWidth(mContext)/2;
        }
        imageView.setLayoutParams(layoutParams);

        GlideUtils.into(mContext,item.getGoods_image(),imageView);

        holder.setText(R.id.tv_goods_name,item.getGoods_name());
        holder.setText(R.id.tv_goods_now_price,"¥"+item.getGoods_price());

        TextView tv_goods_old_price = holder.getTextView(R.id.tv_goods_old_price);
        tv_goods_old_price.setText("¥"+item.getOriginal_price());
        TextViewUtils.underline(tv_goods_old_price);

        final MyTextView tv_goods_collection = (MyTextView) holder.getView(R.id.tv_goods_collection);
        if(item.getIs_collect()==1){
            tv_goods_collection.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.collection,0,0,0);
        }else{
            tv_goods_collection.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.collection_mall,0,0,0);
        }
        tv_goods_collection.setOnClickListener(new MyOnClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
                if(SPUtils.getString(mContext, AppXml.userId,"0").equals("0")){
                    mContext.startActivity(new Intent(mContext, LoginActivity.class));
                }else{
                    collection(tv_goods_collection,item.getGoods_id());
                }
            }
        });

        holder.itemView.setOnClickListener(new MyOnClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
                Intent intent=new Intent(mContext, GoodsDetailActivity.class);
                intent.putExtra(IntentParam.goodsId,item.getGoods_id());
                mContext.startActivity(intent);
            }
        });
    }

    private void collection(final MyTextView tv_goods_collection,String goodsId) {
        Loading.show(mContext);
        Map<String,String> map=new HashMap<String,String>();
        map.put("user_id",SPUtils.getString(mContext,AppXml.userId,"0"));
        map.put("goods_id",goodsId);
        map.put("sign", GetSign.getSign(map));
        NetApiRequest.collectGoods(map, new MyCallBack<CollectObj>(mContext) {
            @Override
            public void onSuccess(CollectObj obj, int errorCode, String msg) {
                if(obj.getIs_collect()==1){
                    tv_goods_collection.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.collection,0,0,0);
                }else{
                    tv_goods_collection.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.collection_mall,0,0,0);
                }
            }
        });
    }
}
