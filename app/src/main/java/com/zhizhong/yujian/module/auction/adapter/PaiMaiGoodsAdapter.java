package com.zhizhong.yujian.module.auction.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.androidtools.DateUtils;
import com.github.androidtools.PhoneUtils;
import com.github.androidtools.inter.MyOnClickListener;
import com.github.baseclass.adapter.MyRecyclerViewHolder;
import com.github.rxbus.RxBus;
import com.zhizhong.yujian.IntentParam;
import com.zhizhong.yujian.R;
import com.zhizhong.yujian.adapter.MyAdapter;
import com.zhizhong.yujian.base.GlideUtils;
import com.zhizhong.yujian.module.auction.activity.AuctionDetailActivity;
import com.zhizhong.yujian.module.auction.event.CountdownEvent;
import com.zhizhong.yujian.module.auction.network.response.PaiMaiGoodsObj;
import com.zhizhong.yujian.tools.DateFormatUtils;

import org.reactivestreams.Subscription;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

public class PaiMaiGoodsAdapter extends MyAdapter<PaiMaiGoodsObj> {
    public PaiMaiGoodsAdapter(Context mContext, int layoutId, int pageSize) {
        super(mContext, R.layout.paimai_all_item, pageSize);
    }

    @Override
    public void bindData(MyRecyclerViewHolder holder, int position, final PaiMaiGoodsObj item) {
        super.bindData(holder, position, item);
        ImageView iv_paimai_type = holder.getImageView(R.id.iv_paimai_type);
        if(item.getType()==1){
            iv_paimai_type.setVisibility(View.VISIBLE);
        }else{
            iv_paimai_type.setVisibility(View.GONE);
        }

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
        TextView tv_paimai_goods_end_time = holder.getTextView(R.id.tv_paimai_goods_end_time);

        if(item.getBegin_time()>new Date().getTime()){//开始时间大于当前时间-未开始
            tv_paimai_status.setText("预展中");
            tv_paimai_status.setTextColor(Color.parseColor("#FF065B9D"));

            holder.setText(R.id.tv_paimai_chujia_num,null);
            tv_paimai_goods_end_time.setText("开拍时间:"+ DateUtils.dateToString(new Date(item.getBegin_time()),"yyyy-MM-dd HH:mm"));

            daoJiShi(tv_paimai_goods_end_time,0,item.getBegin_time(),item.getEnd_time());
        }else if(item.getEnd_time()<new Date().getTime()){
            tv_paimai_status.setText("已结束");//结束时间小于当前时间-已开始
            tv_paimai_status.setTextColor(ContextCompat.getColor(mContext,R.color.red));

            holder.setText(R.id.tv_paimai_chujia_num,item.getChujia_num()+"次出价");
            tv_paimai_goods_end_time.setText("结束时间:"+ DateUtils.dateToString(new Date(item.getEnd_time()),"yyyy-MM-dd HH:mm"));
        }else{
            tv_paimai_status.setText("拍卖中");
            tv_paimai_status.setTextColor(ContextCompat.getColor(mContext,R.color.red));

            holder.setText(R.id.tv_paimai_chujia_num,item.getChujia_num()+"次出价");
            tv_paimai_goods_end_time.setText("距结束:"+ DateFormatUtils.getXCTime(new Date().getTime(),item.getEnd_time(),true));

            daoJiShi(tv_paimai_goods_end_time,1,item.getBegin_time(),item.getEnd_time());
        }
        holder.setText(R.id.tv_paimai_goods_name,item.getGoods_name());
        holder.setText(R.id.tv_paimai_goods_now_price,"当前:¥"+item.getDangqian_price().toString());


        holder.itemView.setOnClickListener(new MyOnClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
                Intent intent=new Intent(mContext,AuctionDetailActivity.class);
                intent.putExtra(IntentParam.goodsId,item.getGoods_id());
                mContext.startActivity(intent);
            }
        });
    }

    public void daoJiShi(final TextView textView,final int flag,final long beginTime,final long endTime){
        Flowable.interval(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new FlowableSubscriber<Long>() {
                    @Override
                    public void onSubscribe(@NonNull Subscription s) {
                        s.request(Long.MAX_VALUE);
                    }
                    @Override
                    public void onNext(Long aLong) {
                        if(flag==0){
                            textView.setText("开拍时间:"+ DateUtils.dateToString(new Date(beginTime),"yyyy-MM-dd HH:mm"));
                            if(beginTime<=new Date().getTime()){
                                RxBus.getInstance().post(new CountdownEvent());
                            }
                        }else{
                            textView.setText("距结束:"+ DateFormatUtils.getXCTime(new Date().getTime(),endTime,true));
                            if(endTime<new Date().getTime()){
                                RxBus.getInstance().post(new CountdownEvent());
                            }
                        }
                    }
                    @Override
                    public void onError(Throwable t) {
                    }
                    @Override
                    public void onComplete() {

                    }
                });
    }
}
