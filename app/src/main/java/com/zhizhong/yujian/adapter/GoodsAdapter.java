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
import com.github.fastshape.MyTextView;
import com.zhizhong.yujian.AppXml;
import com.zhizhong.yujian.R;
import com.zhizhong.yujian.base.GlideUtils;
import com.zhizhong.yujian.module.my.activity.LoginActivity;
import com.zhizhong.yujian.network.response.GoodsObj;
import com.zhizhong.yujian.tools.TextViewUtils;

public class GoodsAdapter extends MyAdapter<GoodsObj> {
    public GoodsAdapter(Context mContext, int layoutId, int pageSize) {
        super(mContext, R.layout.tuijian_goods_item, pageSize);
    }

    @Override
    public void bindData(MyRecyclerViewHolder holder, int position, final GoodsObj item) {
        super.bindData(holder, position, item);
        final ImageView imageView = holder.getImageView(R.id.iv_goods_img);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.height= (PhoneUtils.getScreenWidth(mContext)-PhoneUtils.dip2px(mContext,10))/2;
        layoutParams.width= (PhoneUtils.getScreenWidth(mContext)-PhoneUtils.dip2px(mContext,10))/2;
        imageView.setLayoutParams(layoutParams);

        GlideUtils.into(mContext,item.getGoods_image(),imageView);

        holder.setText(R.id.tv_goods_name,item.getGoods_name());
        holder.setText(R.id.tv_goods_now_price,"¥"+item.getGoods_price());

        TextView tv_goods_old_price = holder.getTextView(R.id.tv_goods_old_price);
        tv_goods_old_price.setText("¥"+item.getOriginal_price());
        TextViewUtils.underline(tv_goods_old_price);

        MyTextView tv_goods_collection = (MyTextView) holder.getView(R.id.tv_goods_collection);
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
                    collection(item.getGoods_id());
                }
            }
        });
    }

    private void collection(String goods_id) {

    }
}
