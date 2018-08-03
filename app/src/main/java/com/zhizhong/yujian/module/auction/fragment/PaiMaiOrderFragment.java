package com.zhizhong.yujian.module.auction.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
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
import com.zhizhong.yujian.event.PaiMaiOrderEvent;
import com.zhizhong.yujian.module.auction.activity.AuctionDetailActivity;
import com.zhizhong.yujian.module.auction.activity.PaiMaiOrderDetailActivity;
import com.zhizhong.yujian.module.auction.network.ApiRequest;
import com.zhizhong.yujian.module.auction.network.response.PaiMaiOrderObj;
import com.zhizhong.yujian.network.NetApiRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class PaiMaiOrderFragment extends BaseFragment {
    //(0拍卖中 1待付款 2待发货 3待收货 4已完成 5已结束 6已取消)
    public static final int type0=0;
    public static final int type1=1;
    public static final int type2=2;
    public static final int type3=3;
    public static final int type4=4;
    public static final int type5=5;
    public static final int type6=6;

    @BindView(R.id.rv_paimai_order)
    RecyclerView rv_paimai_order;

    MyAdapter adapter;
    @Override
    protected int getContentView() {
        return R.layout.paimai_order_frag;
    }

    public static PaiMaiOrderFragment newInstance(int type) {
        Bundle args = new Bundle();
        args.putInt(IntentParam.type,type);
        PaiMaiOrderFragment fragment = new PaiMaiOrderFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected void initView() {

        adapter=new MyAdapter<PaiMaiOrderObj>(mContext,R.layout.paimai_order_item,pageSize) {
            @Override
            public void bindData(MyRecyclerViewHolder holder, int position,final PaiMaiOrderObj bean) {
                //状态(0拍卖中 1待付款 2待发货 3待收货 4已完成 5已结束 6已取消)
                holder.setText(R.id.tv_paimai_order_no,"订单号:"+bean.getOrder_no());

                TextView tv_paimai_order_again_chujia = holder.getTextView(R.id.tv_paimai_order_again_chujia);
                TextView tv_paimai_order_pay =holder.getTextView(R.id.tv_paimai_order_pay);
                TextView tv_paimai_order_sure =holder.getTextView(R.id.tv_paimai_order_sure);

                tv_paimai_order_again_chujia.setOnClickListener(new MyOnClickListener() {
                    @Override
                    protected void onNoDoubleClick(View view) {
                        Intent intent = new Intent();
                        intent.putExtra(IntentParam.goodsId, bean.getGoods_id());
                        STActivity(intent, AuctionDetailActivity.class);
                        getActivity().finish();
                    }
                });
                tv_paimai_order_pay.setOnClickListener(new MyOnClickListener() {
                    @Override
                    protected void onNoDoubleClick(View view) {
                        showPay();
                    }
                });
                tv_paimai_order_sure.setOnClickListener(new MyOnClickListener() {
                    @Override
                    protected void onNoDoubleClick(View view) {
                        showSureOrderDialog(bean.getOrder_no());
                    }
                });

                TextView tv_paimai_order_status = holder.getTextView(R.id.tv_paimai_order_status);
                switch (bean.getOrder_status()){
                    case 0:
                        tv_paimai_order_status.setText("拍卖中");
                        tv_paimai_order_again_chujia.setVisibility(View.VISIBLE);
                        tv_paimai_order_pay.setVisibility(View.GONE);
                        tv_paimai_order_sure.setVisibility(View.GONE);
                    break;
                    case 1:
                        tv_paimai_order_status.setText("待付款");
                        tv_paimai_order_again_chujia.setVisibility(View.GONE);
                        tv_paimai_order_pay.setVisibility(View.VISIBLE);
                        tv_paimai_order_sure.setVisibility(View.GONE);
                    break;
                    case 2:
                        tv_paimai_order_status.setText("待发货");
                        tv_paimai_order_again_chujia.setVisibility(View.GONE);
                        tv_paimai_order_pay.setVisibility(View.GONE);
                        tv_paimai_order_sure.setVisibility(View.GONE);
                    break;
                    case 3:
                        tv_paimai_order_status.setText("待收货");
                        tv_paimai_order_again_chujia.setVisibility(View.GONE);
                        tv_paimai_order_pay.setVisibility(View.GONE);
                        tv_paimai_order_sure.setVisibility(View.VISIBLE);
                    break;
                    case 4:
                        tv_paimai_order_status.setText("已完成");
                        tv_paimai_order_again_chujia.setVisibility(View.GONE);
                        tv_paimai_order_pay.setVisibility(View.GONE);
                        tv_paimai_order_sure.setVisibility(View.GONE);
                    break;
                    case 5:
                        tv_paimai_order_status.setText("已出局");
                        tv_paimai_order_again_chujia.setVisibility(View.GONE);
                        tv_paimai_order_pay.setVisibility(View.GONE);
                        tv_paimai_order_sure.setVisibility(View.GONE);
                    break;
                    case 6:
                        tv_paimai_order_status.setText("已取消");
                        tv_paimai_order_again_chujia.setVisibility(View.GONE);
                        tv_paimai_order_pay.setVisibility(View.GONE);
                        tv_paimai_order_sure.setVisibility(View.GONE);
                    break;
                }
                ImageView imageView = holder.getImageView(R.id.iv_paimai_order);
                GlideUtils.into(mContext,bean.getGoods_img(),imageView);

                holder.setText(R.id.tv_paimai_order_name,bean.getGoods_name());
                holder.setText(R.id.tv_paimai_order_price,"当前价: ¥"+bean.getGoods_price().toString());
                holder.setText(R.id.tv_paimai_order_number,"x"+bean.getNumber());
                holder.setText(R.id.tv_paimai_order_time,bean.getAdd_time());
                holder.setText(R.id.tv_paimai_order_goods_num,"共"+bean.getNumber()+"件商品");
                holder.setText(R.id.tv_paimai_order_total_price,"合计:¥"+bean.getCombined().toString());


                holder.itemView.setOnClickListener(new MyOnClickListener() {
                    @Override
                    protected void onNoDoubleClick(View view) {
                        Intent intent=new Intent();
                        intent.putExtra(IntentParam.orderNo,bean.getOrder_no());
                        STActivity(intent,PaiMaiOrderDetailActivity.class);
                    }
                });
            }
        };
        adapter.setOnLoadMoreListener(this);

        rv_paimai_order.setLayoutManager(new LinearLayoutManager(mContext));
        rv_paimai_order.addItemDecoration(getItemDivider(PhoneUtils.dip2px(mContext,5)));
        rv_paimai_order.setAdapter(adapter);


    }

    private void showSureOrderDialog(final String order_no) {
        MyDialog.Builder mDialog=new MyDialog.Builder(mContext);
        mDialog.setMessage("是否确认收货?");
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
                sureOrder(order_no);
            }
        });
        mDialog.create().show();
    }

    private void sureOrder(String order_no) {
        showLoading();
        Map<String,String>map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("order_no",order_no);
        map.put("sign",getSign(map));
        NetApiRequest.sureOrder(map, new MyCallBack<BaseObj>(mContext) {
            @Override
            public void onSuccess(BaseObj obj, int errorCode, String msg) {
                showMsg(msg);
                RxBus.getInstance().post(new PaiMaiOrderEvent(PaiMaiOrderFragment.type3));
                RxBus.getInstance().post(new PaiMaiOrderEvent(PaiMaiOrderFragment.type4));
            }
        });

    }

    @Override
    protected void initRxBus() {
        super.initRxBus();
        getEvent(PaiMaiOrderEvent.class, new MyConsumer<PaiMaiOrderEvent>() {
            @Override
            public void onAccept(PaiMaiOrderEvent event) {
                if(getArguments().getInt(IntentParam.type,-1)==PaiMaiOrderFragment.type3||getArguments().getInt(IntentParam.type,-1)==PaiMaiOrderFragment.type4){
                    getData(1,false);
                }
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
        map.put("user_id",getUserId());
        map.put("sign",getSign(map));
        ApiRequest.getPaiMaiOrder(map, new MyCallBack<List<PaiMaiOrderObj>>(mContext,pl_load,pcfl) {
            @Override
            public void onSuccess(List<PaiMaiOrderObj> list, int errorCode, String msg) {
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
