package com.zhizhong.yujian.module.auction.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.androidtools.DateUtils;
import com.github.androidtools.PhoneUtils;
import com.github.baseclass.adapter.MyRecyclerViewHolder;
import com.zhizhong.yujian.R;
import com.zhizhong.yujian.adapter.MyAdapter;
import com.zhizhong.yujian.base.GlideUtils;
import com.zhizhong.yujian.module.auction.network.response.PaiMaiGoodsObj;
import com.zhizhong.yujian.tools.DateFormatUtils;

import java.util.Date;

public class PaiMaiGoodsAdapter extends MyAdapter<PaiMaiGoodsObj> {
    public PaiMaiGoodsAdapter(Context mContext, int layoutId, int pageSize) {
        super(mContext, R.layout.paimai_all_item, pageSize);
    }

    @Override
    public void bindData(MyRecyclerViewHolder holder, int position, PaiMaiGoodsObj item) {
        super.bindData(holder, position, item);
        final ImageView imageView = holder.getImageView(R.id.iv_paimai_img);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if(position%2==0){
            layoutParams.height= (PhoneUtils.getScreenWidth(mContext)-PhoneUtils.dip2px(mContext,10))/2;
            layoutParams.width= (PhoneUtils.getScreenWidth(mContext)-PhoneUtils.dip2px(mContext,10))/2;
        }else{
            layoutParams.height= (PhoneUtils.getScreenWidth(mContext)-PhoneUtils.dip2px(mContext,10))/2;
            layoutParams.width= PhoneUtils.getScreenWidth(mContext)/2;
        }
        imageView.setLayoutParams(layoutParams);

        GlideUtils.into(mContext,item.getGoods_image(),imageView);


        TextView tv_paimai_status = holder.getTextView(R.id.tv_paimai_status);

        if(item.getBegin_time()>new Date().getTime()){//开始时间大于当前时间-未开始
            tv_paimai_status.setText("预展中");
            tv_paimai_status.setTextColor(Color.parseColor("#FF065B9D"));

            holder.setText(R.id.tv_paimai_chujia_num,null);
            holder.setText(R.id.tv_paimai_goods_end_time,"开拍时间:"+ DateUtils.dateToString(new Date(item.getBegin_time()),"yyyy-MM-dd HH:mm"));
        }else if(item.getEnd_time()<new Date().getTime()){
            tv_paimai_status.setText("已结束");//结束时间小于当前时间-已开始
            tv_paimai_status.setTextColor(ContextCompat.getColor(mContext,R.color.red));

            holder.setText(R.id.tv_paimai_chujia_num,item.getChujia_num()+"次出价");
            holder.setText(R.id.tv_paimai_goods_end_time,null);
        }else{
            tv_paimai_status.setText("拍卖中");
            tv_paimai_status.setTextColor(ContextCompat.getColor(mContext,R.color.red));

            holder.setText(R.id.tv_paimai_chujia_num,item.getChujia_num()+"次出价");
            holder.setText(R.id.tv_paimai_goods_end_time,"距结束:"+ DateFormatUtils.getXCTime(item.getBegin_time(),item.getEnd_time(),true));
        }
        holder.setText(R.id.tv_paimai_goods_name,item.getGoods_name());
        holder.setText(R.id.tv_paimai_goods_now_price,"当前:¥"+item.getDangqian_price().toString());
    }
}
