package com.zhizhong.yujian.module.my.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.androidtools.PhoneUtils;
import com.github.androidtools.inter.MyOnClickListener;
import com.github.baseclass.adapter.MyRecyclerViewHolder;
import com.github.rxbus.MyConsumer;
import com.github.rxbus.RxBus;
import com.library.base.BaseObj;
import com.zhizhong.yujian.IntentParam;
import com.zhizhong.yujian.R;
import com.zhizhong.yujian.adapter.MyAdapter;
import com.zhizhong.yujian.base.BaseActivity;
import com.zhizhong.yujian.base.GlideUtils;
import com.zhizhong.yujian.base.MyCallBack;
import com.zhizhong.yujian.module.my.event.RefreshTuiHuoEvent;
import com.zhizhong.yujian.module.my.network.ApiRequest;
import com.zhizhong.yujian.module.my.network.response.TuiHuanHuoObj;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class MyTuiKuanListActivity extends BaseActivity {
    @BindView(R.id.rv_my_tuikuan)
    RecyclerView rv_my_tuikuan;

    MyAdapter adapter;

    @Override
    protected int getContentView() {
        setAppTitle("我的退款/售后");
        return R.layout.my_tuikuan_act;
    }

    @Override
    protected void initView() {
        adapter=new MyAdapter<TuiHuanHuoObj>(mContext,R.layout.my_tuikuan_item,pageSize) {
            @Override
            public void bindData(MyRecyclerViewHolder holder, int position,final TuiHuanHuoObj bean) {
                holder.itemView.setOnClickListener(new MyOnClickListener() {
                    @Override
                    protected void onNoDoubleClick(View view) {
                        Intent intent=new Intent();
                        intent.putExtra(IntentParam.orderNo,bean.getOrder_no());
                        intent.putExtra(IntentParam.tuiHuanHuo,true);
                        STActivity(intent,TuiKuanActivity.class);
                    }
                });

                holder.setText(R.id.tv_order_no,bean.getOrder_no());
                LinearLayout ll_order_goods_list = (LinearLayout) holder.getView(R.id.ll_order_goods_list);
                ll_order_goods_list.removeAllViews();
                for (int i = 0; i < bean.getGoods_list().size(); i++) {
                    TuiHuanHuoObj.GoodsListBean goodsListBean = bean.getGoods_list().get(i);
                    View goodsView = getLayoutInflater().inflate(R.layout.my_order_item_include, null);

                    ImageView iv_order = goodsView.findViewById(R.id.iv_order);

                    GlideUtils.into(mContext,goodsListBean.getGoods_images(),iv_order);
                    TextView tv_order_name = goodsView.findViewById(R.id.tv_order_name);
                    TextView tv_order_price = goodsView.findViewById(R.id.tv_order_price);
                    TextView tv_order_number = goodsView.findViewById(R.id.tv_order_number);

                    tv_order_name.setText(goodsListBean.getGoods_name());
                    tv_order_price.setText("¥"+goodsListBean.getGoods_unitprice());
                    tv_order_number.setText("x"+goodsListBean.getGoods_number());

                    ll_order_goods_list.addView(goodsView);
                }

                holder.setText(R.id.tv_order_time,bean.getCreate_add_time());
                holder.setText(R.id.tv_order_goods_num,"共"+bean.getGoods_list().size()+"件商品");
                holder.setText(R.id.tv_order_total_price,"合计:¥"+bean.getCombined());


                //return_goods_status 处于退换货状态(0否 1是),refund_status 退货状态(1退货中 2退货成功 3退货失败

                TextView tv_order_status = holder.getTextView(R.id.tv_order_status);

                TextView tv_order_cancel = holder.getTextView(R.id.tv_order_cancel);
                TextView tv_order_again =holder.getTextView(R.id.tv_order_again);
                if(bean.getRefund_status()==1){
                    tv_order_status.setText("退货中");
                    tv_order_cancel.setVisibility(View.VISIBLE);
                    tv_order_again.setVisibility(View.GONE);
                }else if(bean.getRefund_status()==2){
                    tv_order_status.setText("退货成功");
                    tv_order_cancel.setVisibility(View.GONE);
                    tv_order_again.setVisibility(View.GONE);
                }else if(bean.getRefund_status()==3){
                    tv_order_status.setText("退货失败");
                    tv_order_cancel.setVisibility(View.GONE);
                    tv_order_again.setVisibility(View.VISIBLE);
                }

                tv_order_cancel.setOnClickListener(new MyOnClickListener() {
                    @Override
                    protected void onNoDoubleClick(View view) {
                        cancelShenQing(bean.getOrder_no());
                    }
                });
                tv_order_again.setOnClickListener(new MyOnClickListener() {
                    @Override
                    protected void onNoDoubleClick(View view) {
                        Intent intent=new Intent();
                        intent.putExtra(IntentParam.orderNo,bean.getOrder_no());
                        intent.putExtra(IntentParam.tuiHuanHuo,true);
                        STActivity(intent,TuiKuanActivity.class);
                    }
                });

            }
        };
        adapter.setOnLoadMoreListener(this);

        rv_my_tuikuan.setLayoutManager(new LinearLayoutManager(mContext));
        rv_my_tuikuan.addItemDecoration(getItemDivider(PhoneUtils.dip2px(mContext,5)));
        rv_my_tuikuan.setAdapter(adapter);

    }

    private void cancelShenQing(String order_no) {
        showLoading();
        Map<String,String>map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("order_no",order_no);
        map.put("sign",getSign(map));
        ApiRequest.cancelShenQing(map, new MyCallBack<BaseObj>(mContext,true) {
            @Override
            public void onSuccess(BaseObj obj, int errorCode, String msg) {
                RxBus.getInstance().post(new RefreshTuiHuoEvent());
            }
        });
    }

    @Override
    protected void initData() {
        showProgress();
        getData(1,false);
    }

    @Override
    protected void initRxBus() {
        super.initRxBus();
        getEvent(RefreshTuiHuoEvent.class, new MyConsumer<RefreshTuiHuoEvent>() {
            @Override
            public void onAccept(RefreshTuiHuoEvent event) {
                getData(1,false);
            }
        });
    }

    @Override
    protected void getData(int page,final boolean isLoad) {
        super.getData(page, isLoad);
        Map<String,String> map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("pagesize",pagesize+"");
        map.put("page",page+"");
        map.put("sign",getSign(map));
        ApiRequest.tuiHuanHuoList(map, new MyCallBack<List<TuiHuanHuoObj>>(mContext,pl_load,pcfl) {
            @Override
            public void onSuccess(List<TuiHuanHuoObj> list, int errorCode, String msg) {
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
