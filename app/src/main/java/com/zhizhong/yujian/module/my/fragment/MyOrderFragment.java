package com.zhizhong.yujian.module.my.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.androidtools.PhoneUtils;
import com.github.androidtools.inter.MyOnClickListener;
import com.github.baseclass.adapter.MyRecyclerViewHolder;
import com.github.mydialog.MyDialog;
import com.github.rxbus.MyConsumer;
import com.github.rxbus.RxBus;
import com.library.base.BaseObj;
import com.zhizhong.yujian.IntentParam;
import com.zhizhong.yujian.R;
import com.zhizhong.yujian.adapter.MyAdapter;
import com.zhizhong.yujian.base.BaseFragment;
import com.zhizhong.yujian.base.GlideUtils;
import com.zhizhong.yujian.base.MyCallBack;
import com.zhizhong.yujian.event.MyOrderEvent;
import com.zhizhong.yujian.event.PayEvent;
import com.zhizhong.yujian.module.mall.event.TuiKuanEvent;
import com.zhizhong.yujian.module.my.activity.FaBiaoEvaluationActivity;
import com.zhizhong.yujian.module.my.activity.OrderDetailActivity;
import com.zhizhong.yujian.module.my.activity.TuiKuanActivity;
import com.zhizhong.yujian.module.my.event.RefreshMyOrderEvent;
import com.zhizhong.yujian.module.my.network.ApiRequest;
import com.zhizhong.yujian.module.my.network.response.OrderObj;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class MyOrderFragment extends BaseFragment {
    //状态(0全部 1待付款 2待发货 3待收货 4待评价) 5已完成 6已取消
    public static final int type_0=0;
    public static final int type_1=1;
    public static final int type_2=2;
    public static final int type_3=3;
    public static final int type_4=4;
    public static final int type_5=5;
    public static final int type_6=6;


    @BindView(R.id.rv_my_order)
    RecyclerView rv_my_order;

    MyAdapter adapter;

    @Override
    protected int getContentView() {
        return R.layout.my_order_frag;
    }

    public static MyOrderFragment newInstance(int type) {

        Bundle args = new Bundle();
        args.putInt(IntentParam.type,type);

        MyOrderFragment fragment = new MyOrderFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected void initView() {
        adapter=new MyAdapter<OrderObj>(mContext,R.layout.my_order_item,pageSize) {
            @Override
            public void bindData(MyRecyclerViewHolder holder, int position, final OrderObj bean) {
                holder.itemView.setOnClickListener(new MyOnClickListener() {
                    @Override
                    protected void onNoDoubleClick(View view) {
                        Intent intent=new Intent();
                        intent.putExtra(IntentParam.orderNo,bean.getOrder_no());
                        STActivity(intent,OrderDetailActivity.class);
                    }
                });

                holder.setText(R.id.tv_order_no,bean.getOrder_no());
                LinearLayout ll_order_goods_list = (LinearLayout) holder.getView(R.id.ll_order_goods_list);
                ll_order_goods_list.removeAllViews();
                for (int i = 0; i < bean.getGoods_list().size(); i++) {
                    OrderObj.GoodsListBean goodsListBean = bean.getGoods_list().get(i);
                    View goodsView = getLayoutInflater().inflate(R.layout.my_order_item_include, null);

                    ImageView iv_order = goodsView.findViewById(R.id.iv_order);

                    GlideUtils.into(mContext,goodsListBean.getGoods_images(),iv_order);
                    TextView tv_order_name = goodsView.findViewById(R.id.tv_order_name);
                    TextView tv_order_price = goodsView.findViewById(R.id.tv_order_price);
                    TextView tv_order_number = goodsView.findViewById(R.id.tv_order_number);

                    tv_order_name.setText(goodsListBean.getGoods_name());
                    tv_order_price.setText("¥"+goodsListBean.getGoods_unitprice().toString());
                    tv_order_number.setText("x"+goodsListBean.getGoods_number());

                    ll_order_goods_list.addView(goodsView);
                }

                holder.setText(R.id.tv_order_time,bean.getCreate_add_time());
                holder.setText(R.id.tv_order_goods_num,"共"+bean.getGoods_list().size()+"件商品");
                holder.setText(R.id.tv_order_total_price,"合计:¥"+bean.getCombined().toString());




                TextView tv_order_cancel = holder.getTextView(R.id.tv_order_cancel);
                TextView tv_order_delete =holder.getTextView(R.id.tv_order_delete);
                TextView tv_order_pay =holder.getTextView(R.id.tv_order_pay);
                TextView tv_order_sure =holder.getTextView(R.id.tv_order_sure);
                TextView tv_order_tuikuan =holder.getTextView(R.id.tv_order_tuikuan);
                TextView tv_order_evaluation =holder.getTextView(R.id.tv_order_evaluation);

                //状态(0全部 1待付款 2待发货 3待收货 4待评价) 5已完成 6已取消
                switch (bean.getOrder_status()){
                    case type_1:
                        holder.setText(R.id.tv_order_status,"待付款");
                        tv_order_cancel.setVisibility(View.VISIBLE);
                        tv_order_delete.setVisibility(View.GONE);
                        tv_order_pay.setVisibility(View.VISIBLE);
                        tv_order_sure.setVisibility(View.GONE);
                        tv_order_tuikuan.setVisibility(View.GONE);
                        tv_order_evaluation.setVisibility(View.GONE);
                    break;
                    case type_2:
                        holder.setText(R.id.tv_order_status,"待发货");
                        tv_order_cancel.setVisibility(View.GONE);
                        tv_order_delete.setVisibility(View.GONE);
                        tv_order_pay.setVisibility(View.GONE);
                        tv_order_sure.setVisibility(View.GONE);
                        tv_order_tuikuan.setVisibility(View.VISIBLE);
                        tv_order_evaluation.setVisibility(View.GONE);
                    break;
                    case type_3:
                        holder.setText(R.id.tv_order_status,"待收货");
                        tv_order_cancel.setVisibility(View.GONE);
                        tv_order_delete.setVisibility(View.GONE);
                        tv_order_pay.setVisibility(View.GONE);
                        tv_order_sure.setVisibility(View.VISIBLE);
                        tv_order_tuikuan.setVisibility(View.VISIBLE);
                        tv_order_evaluation.setVisibility(View.GONE);
                    break;
                    case type_4:
                        holder.setText(R.id.tv_order_status,"待评价");
                        tv_order_cancel.setVisibility(View.GONE);
                        tv_order_delete.setVisibility(View.GONE);
                        tv_order_pay.setVisibility(View.GONE);
                        tv_order_sure.setVisibility(View.GONE);
                        tv_order_tuikuan.setVisibility(View.GONE);
                        tv_order_evaluation.setVisibility(View.VISIBLE);
                    break;
                    case type_5:
                        holder.setText(R.id.tv_order_status,"已完成");
                        tv_order_cancel.setVisibility(View.GONE);
                        tv_order_delete.setVisibility(View.GONE);
                        tv_order_pay.setVisibility(View.GONE);
                        tv_order_sure.setVisibility(View.GONE);
                        tv_order_tuikuan.setVisibility(View.GONE);
                        tv_order_evaluation.setVisibility(View.GONE);
                    break;
                    case type_6:
                        holder.setText(R.id.tv_order_status,"已取消");
                        tv_order_cancel.setVisibility(View.GONE);
                        tv_order_delete.setVisibility(View.VISIBLE);
                        tv_order_pay.setVisibility(View.GONE);
                        tv_order_sure.setVisibility(View.GONE);
                        tv_order_tuikuan.setVisibility(View.GONE);
                        tv_order_evaluation.setVisibility(View.GONE);
                    break;
                }


                tv_order_cancel.setOnClickListener(new MyOnClickListener() {
                    @Override
                    protected void onNoDoubleClick(View view) {
                        cancelOrder(bean.getOrder_no());
                    }
                });
                tv_order_delete.setOnClickListener(new MyOnClickListener() {
                    @Override
                    protected void onNoDoubleClick(View view) {
                        deleteOrder(bean.getOrder_no());
                    }
                });
                tv_order_pay.setOnClickListener(new MyOnClickListener() {
                    @Override
                    protected void onNoDoubleClick(View view) {
                        showPay(bean.getOrder_no(),bean.getCombined().toString());
                    }
                });
                tv_order_sure.setOnClickListener(new MyOnClickListener() {
                    @Override
                    protected void onNoDoubleClick(View view) {
                        sureOrder(bean.getOrder_no());
                    }
                });
                tv_order_tuikuan.setOnClickListener(new MyOnClickListener() {
                    @Override
                    protected void onNoDoubleClick(View view) {
                        tuikuanOrder(bean.getOrder_no());
                    }
                });
                tv_order_evaluation.setOnClickListener(new MyOnClickListener() {
                    @Override
                    protected void onNoDoubleClick(View view) {
                        evaluationOrder(bean.getOrder_no());
                    }
                });



            }
        };
        adapter.setOnLoadMoreListener(this);

        rv_my_order.setLayoutManager(new LinearLayoutManager(mContext));
        rv_my_order.addItemDecoration(getItemDivider(PhoneUtils.dip2px(mContext,5)));
        rv_my_order.setAdapter(adapter);

    }

    private void deleteOrder(final String order_no) {
        MyDialog.Builder mDialog=new MyDialog.Builder(mContext);
        mDialog.setMessage("是否确认删除订单?");
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
                map.put("order_no",order_no);
                map.put("sign",getSign(map));
                ApiRequest.deleteOrder(map, new MyCallBack<BaseObj>(mContext,true) {
                    @Override
                    public void onSuccess(BaseObj obj, int errorCode, String msg) {
                        showMsg(msg);
                        RxBus.getInstance().post(new MyOrderEvent(MyOrderFragment.type_0));
                    }
                });
            }
        });
        mDialog.create().show();
    }

    private void evaluationOrder(String order_no) {
        Intent intent=new Intent();
        intent.putExtra(IntentParam.orderNo,order_no);
        STActivity(intent,FaBiaoEvaluationActivity.class);
    }

    private void tuikuanOrder(String order_no) {
        Intent intent=new Intent();
        intent.putExtra(IntentParam.orderNo,order_no);
        STActivity(intent,TuiKuanActivity.class);
    }

    private void sureOrder(String order_no) {
        showLoading();
        Map<String,String>map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("order_no",order_no);
        map.put("sign",getSign(map));
        ApiRequest.sureOrder(map, new MyCallBack<BaseObj>(mContext) {
            @Override
            public void onSuccess(BaseObj obj, int errorCode, String msg) {
                showMsg(msg);
                RxBus.getInstance().post(new RefreshMyOrderEvent());
            }
        });

    }

    private void cancelOrder(final String order_no) {
        MyDialog.Builder mDialog=new MyDialog.Builder(mContext);
        mDialog.setMessage("是否确认取消订单?");
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
                map.put("order_no",order_no);
                map.put("sign",getSign(map));
                ApiRequest.cancelOrder(map, new MyCallBack<BaseObj>(mContext,true) {
                    @Override
                    public void onSuccess(BaseObj obj, int errorCode, String msg) {
                        showMsg(msg);
                        RxBus.getInstance().post(new MyOrderEvent(MyOrderFragment.type_0));
                    }
                });
            }
        });
        mDialog.create().show();

    }

    @Override
    protected void initRxBus() {
        super.initRxBus();
        getEvent(MyOrderEvent.class, new MyConsumer<MyOrderEvent>() {
            @Override
            public void onAccept(MyOrderEvent event) {
                if(event.type==MyOrderFragment.type_0){
                    getData(1,false);
                }
            }
        });
        getEvent(PayEvent.class, new MyConsumer<PayEvent>() {
            @Override
            public void onAccept(PayEvent event) {
                getData(1,false);
            }
        });
        getEvent(TuiKuanEvent.class, new MyConsumer<TuiKuanEvent>() {
            @Override
            public void onAccept(TuiKuanEvent event) {
                getData(1,false);
            }
        });
        getEvent(RefreshMyOrderEvent.class, new MyConsumer<RefreshMyOrderEvent>() {
            @Override
            public void onAccept(RefreshMyOrderEvent event) {
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
        map.put("type",getArguments().getInt(IntentParam.type)+"");
        map.put("pagesize",pagesize+"");
        map.put("page",page+"");
        map.put("sign",getSign(map));
        ApiRequest.getOrderList(map, new MyCallBack<List<OrderObj>>(mContext,pl_load,pcfl) {
            @Override
            public void onSuccess(List<OrderObj> list, int errorCode, String msg) {
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
