package com.zhizhong.yujian.module.auction.fragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.github.androidtools.PhoneUtils;
import com.github.androidtools.SPUtils;
import com.github.androidtools.ToastUtils;
import com.github.androidtools.inter.MyOnClickListener;
import com.github.baseclass.adapter.MyRecyclerViewHolder;
import com.github.baseclass.view.Loading;
import com.github.mydialog.MyDialog;
import com.github.mydialog.MySimpleDialog;
import com.github.rxbus.MyConsumer;
import com.github.rxbus.RxBus;
import com.library.base.BaseObj;
import com.sdklibrary.base.ali.pay.MyAliOrderBean;
import com.sdklibrary.base.ali.pay.MyAliPay;
import com.sdklibrary.base.ali.pay.MyAliPayCallback;
import com.sdklibrary.base.ali.pay.PayResult;
import com.zhizhong.yujian.Config;
import com.zhizhong.yujian.Constant;
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
import com.zhizhong.yujian.module.mall.activity.PaySuccessActivity;
import com.zhizhong.yujian.module.my.activity.AddressListActivity;
import com.zhizhong.yujian.module.my.network.response.AddressObj;
import com.zhizhong.yujian.network.NetApiRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

import static android.app.Activity.RESULT_OK;

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
    private String addressId;

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
                        showLoading();
                        Map<String, String> map = new HashMap<String, String>();
                        map.put("user_id", getUserId());
                        map.put("sign", getSign(map));
                        com.zhizhong.yujian.module.mall.network.ApiRequest.getDefaultAddress(map, new MyCallBack<List<AddressObj>>(mContext) {
                            @Override
                            public void onSuccess(List<AddressObj> list, int errorCode, String msg) {
                                if (notEmpty(list)) {
                                    AddressObj addressObj = list.get(0);
                                    selectAddress(bean.getOrder_no(),bean.getCombined().doubleValue(),addressObj);
                                } else {
                                    selectAddress(bean.getOrder_no(),bean.getCombined().doubleValue(),null);
                                }
                            }
                            @Override
                            public void onError(Throwable e, boolean hiddenMsg) {
                                super.onError(e, true);
                                selectAddress(bean.getOrder_no(),bean.getCombined().doubleValue(),null);
                            }
                        });
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            switch (requestCode){
                case 300:
                    AddressObj addressObj = (AddressObj) data.getSerializableExtra(IntentParam.addressBean);
                    addressId =addressObj.getAddress_id();
                    tv_sure_order_name.setText(addressObj.getRecipient());
                    tv_sure_order_phone.setText(addressObj.getPhone());
                    tv_sure_order_address.setText("收货地址："+addressObj.getShipping_address()+addressObj.getShipping_address_details());
                break;
            }
        }
    }
      TextView tv_sure_order_name;
      TextView tv_sure_order_phone;
      TextView tv_sure_order_address;
    private void selectAddress(final String orderNo, final double money, AddressObj addressObj) {
        final MySimpleDialog dialog=new MySimpleDialog(mContext);
        View view = mContext.getLayoutInflater().inflate(R.layout.sure_order_popu, null);
        view.findViewById(R.id.iv_pay_cancle).setOnClickListener(new MyOnClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
                dialog.dismiss();
            }
        });

        final LinearLayout il_select_address =view.findViewById(R.id.il_select_address);
        il_select_address.setVisibility(View.VISIBLE);

        final LinearLayout ll_sure_order_select_address =view.findViewById(R.id.ll_sure_order_select_address);
        final LinearLayout ll_sure_order_address =view.findViewById(R.id.ll_sure_order_address);
         tv_sure_order_name    =view.findViewById(R.id.tv_sure_order_name);
         tv_sure_order_phone   =view.findViewById(R.id.tv_sure_order_phone);
         tv_sure_order_address =view.findViewById(R.id.tv_sure_order_address);

        ll_sure_order_select_address.setOnClickListener(new MyOnClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
                Intent intent=new Intent(IntentParam.selectAddress);
                STActivityForResult(intent,AddressListActivity.class,300);
            }
        });

        if (addressObj != null) {
            ll_sure_order_select_address.setVisibility(View.GONE);
            ll_sure_order_address.setVisibility(View.VISIBLE);
            addressId =addressObj.getAddress_id();
            tv_sure_order_name.setText(addressObj.getRecipient());
            tv_sure_order_phone.setText(addressObj.getPhone());
            tv_sure_order_address.setText("收货地址："+addressObj.getShipping_address()+addressObj.getShipping_address_details());
        }else{
            ll_sure_order_select_address.setVisibility(View.VISIBLE);
            ll_sure_order_address.setVisibility(View.GONE);
        }


        final RadioButton rb_pay_weixin =view.findViewById(R.id.rb_pay_weixin);
        final RadioButton rb_pay_zhifubao =view.findViewById(R.id.rb_pay_zhifubao);
        final RadioButton rb_pay_online = view.findViewById(R.id.rb_pay_online);

        TextView tv_pay_total = view.findViewById(R.id.tv_pay_total);
        tv_pay_total.setText("¥"+money);
        view.findViewById(R.id.tv_pay_commit).setOnClickListener(new MyOnClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
                if(TextUtils.isEmpty(addressId)){
                    showMsg("请选择收货地址");
                }else{
                    showLoading();
                    Map<String,String>map=new HashMap<String,String>();
                    map.put("user_id",getUserId());
                    map.put("order_no",orderNo);
                    map.put("addres_id",addressId);
                    map.put("sign",getSign(map));
                    ApiRequest.paiMaiSetAddress(map, new MyCallBack<BaseObj>(mContext) {
                        @Override
                        public void onSuccess(BaseObj obj, int errorCode, String msg) {
                            dialog.dismiss();
                            if(rb_pay_online.isChecked()){
                                yuePay(mContext,orderNo,money+"");
                            }else if(rb_pay_zhifubao.isChecked()){
                                aliPay(mContext,orderNo,money);
                            }else if(rb_pay_weixin.isChecked()){
                                ToastUtils.showToast(mContext,"正在开发中");
                            }
                        }
                    });


                }

            }
        });
        dialog.setContentView(view);
        dialog.setFullWidth();
        dialog.setGravity(Gravity.BOTTOM);
        dialog.show();
    }

    private void aliPay(final Activity mContext, String orderNo, double price) {
        MyAliOrderBean bean=new MyAliOrderBean();
        bean.setOut_trade_no(orderNo);
        bean.setTotal_amount(price);
        bean.setSubject(Constant.orderSubject);
        bean.setBody(Constant.orderBody);
        String url = SPUtils.getString(mContext, Config.payType_ZFB, null);
        bean.setNotifyUrl(url);

        Loading.show(mContext);
        MyAliPay.newInstance(mContext).startPay(bean, new MyAliPayCallback() {
            @Override
            public void paySuccess(PayResult payResult) {
                Loading.dismissLoading();
                Intent intent = new Intent(mContext, PaySuccessActivity.class);
                intent.putExtra(IntentParam.isPaiMai,true);
                mContext.startActivity(intent);
                RxBus.getInstance().post(new PaiMaiOrderEvent(PaiMaiOrderFragment.type1));
                RxBus.getInstance().post(new PaiMaiOrderEvent(PaiMaiOrderFragment.type2));
            }
            @Override
            public void payFail() {
                Loading.dismissLoading();
                ToastUtils.showToast(mContext,"支付失败");
            }
            @Override
            public void payCancel() {
                Loading.dismissLoading();
                ToastUtils.showToast(mContext,"支付取消");
            }
        });
    }

    public   void yuePay(final Activity mContext,String orderNo,String money) {
        Loading.show(mContext);
        Map<String,String> map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("order_no",orderNo);
        map.put("money",money);
        map.put("sign",getSign(map));
        NetApiRequest.yuePay(map, new MyCallBack<BaseObj>(mContext) {
            @Override
            public void onSuccess(BaseObj obj, int errorCode, String msg) {
                RxBus.getInstance().post(new PaiMaiOrderEvent(PaiMaiOrderFragment.type1));
                RxBus.getInstance().post(new PaiMaiOrderEvent(PaiMaiOrderFragment.type2));
                Intent intent = new Intent(mContext, PaySuccessActivity.class);
                intent.putExtra(IntentParam.isPaiMai,true);
                mContext.startActivity(intent);
            }
        });
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
                if(getArguments().getInt(IntentParam.type,-1)==PaiMaiOrderFragment.type3
                        ||getArguments().getInt(IntentParam.type,-1)==PaiMaiOrderFragment.type4
                        ||getArguments().getInt(IntentParam.type,-1)==PaiMaiOrderFragment.type1
                        ||getArguments().getInt(IntentParam.type,-1)==PaiMaiOrderFragment.type2
                        ){
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
