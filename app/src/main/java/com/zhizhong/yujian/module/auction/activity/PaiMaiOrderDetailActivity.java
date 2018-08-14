package com.zhizhong.yujian.module.auction.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.github.androidtools.SPUtils;
import com.github.androidtools.ToastUtils;
import com.github.androidtools.inter.MyOnClickListener;
import com.github.baseclass.view.Loading;
import com.github.fastshape.MyTextView;
import com.github.mydialog.MyDialog;
import com.github.mydialog.MySimpleDialog;
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
import com.zhizhong.yujian.base.BaseActivity;
import com.zhizhong.yujian.base.GlideUtils;
import com.zhizhong.yujian.base.MyCallBack;
import com.zhizhong.yujian.event.PaiMaiOrderEvent;
import com.zhizhong.yujian.module.auction.fragment.PaiMaiOrderFragment;
import com.zhizhong.yujian.module.auction.network.ApiRequest;
import com.zhizhong.yujian.module.auction.network.response.PaiMaiOrderDetailObj;
import com.zhizhong.yujian.module.mall.activity.PaySuccessActivity;
import com.zhizhong.yujian.module.my.activity.AddressListActivity;
import com.zhizhong.yujian.module.my.activity.WuLiuActivity;
import com.zhizhong.yujian.module.my.network.response.AddressObj;
import com.zhizhong.yujian.network.NetApiRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class PaiMaiOrderDetailActivity extends BaseActivity {
    @BindView(R.id.tv_order_detail_status)
    MyTextView tv_order_detail_status;
    @BindView(R.id.tv_order_detail_wuliu)
    TextView tv_order_detail_wuliu;
    @BindView(R.id.tv_order_detail_wuliu_time)
    TextView tv_order_detail_wuliu_time;
    @BindView(R.id.tv_order_detail_shouhuoren)
    TextView tv_order_detail_shouhuoren;
    @BindView(R.id.tv_order_detail_phone)
    TextView tv_order_detail_phone;
    @BindView(R.id.tv_order_detail_address)
    TextView tv_order_detail_address;
    @BindView(R.id.iv_my_auction)
    ImageView iv_my_auction;
    @BindView(R.id.ll_order_detail_goods)
    LinearLayout ll_order_detail_goods;
    @BindView(R.id.tv_order_detail_goods_price)
    TextView tv_order_detail_goods_price;
    @BindView(R.id.tv_order_detail_goods_kuaidi)
    TextView tv_order_detail_goods_kuaidi;
    @BindView(R.id.tv_order_detail_goods_youhui)
    TextView tv_order_detail_goods_youhui;
    @BindView(R.id.tv_order_detail_goods_num)
    TextView tv_order_detail_goods_num;
    @BindView(R.id.tv_order_detail_total_price)
    TextView tv_order_detail_total_price;
    @BindView(R.id.tv_order_detail_orderno)
    TextView tv_order_detail_orderno;
    @BindView(R.id.tv_order_detail_order_time)
    TextView tv_order_detail_order_time;
    @BindView(R.id.tv_order_detail_order_pay_time)
    TextView tv_order_detail_order_pay_time;
    @BindView(R.id.tv_order_detail_order_pay_way)
    TextView tv_order_detail_order_pay_way;
    @BindView(R.id.tv_order_detail_order_kuaidi_name)
    TextView tv_order_detail_order_kuaidi_name;

    @BindView(R.id.tv_paimai_order_again_chujia)
    MyTextView tv_paimai_order_again_chujia;

    @BindView(R.id.tv_paimai_order_pay)
    MyTextView tv_paimai_order_pay;

    @BindView(R.id.tv_paimai_order_sure)
    MyTextView tv_paimai_order_sure;
    private String orderNo;
    private String addressId;

    @Override
    protected int getContentView() {
        setAppTitle("订单详情");
        return R.layout.paimai_order_detail_act;
    }

    @Override
    protected void initView() {

        orderNo = getIntent().getStringExtra(IntentParam.orderNo);
    }

    @Override
    protected void initData() {
        showProgress();
        getData(1, false);
    }

    @Override
    protected void onMyReStart() {
        super.onMyReStart();
        getData(1,false);
    }

    @Override
    protected void getData(int page, boolean isLoad) {
        super.getData(page, isLoad);
        Map<String, String> map = new HashMap<String, String>();
        map.put("user_id", getUserId());
        map.put("order_no", orderNo);
        map.put("sign", getSign(map));
        ApiRequest.getPaiMaiOrderDetail(map, new MyCallBack<PaiMaiOrderDetailObj>(mContext, pl_load, pcfl) {
            @Override
            public void onSuccess(PaiMaiOrderDetailObj obj, int errorCode, String msg) {
                setData(obj);
            }
        });

    }

    private void setData(final PaiMaiOrderDetailObj bean) {
        if (notEmpty(bean.getCourier_list())) {
            tv_order_detail_wuliu.setText(bean.getCourier_list().get(0).getContext());
            tv_order_detail_wuliu_time.setText(bean.getCourier_list().get(0).getTime());
        }

        tv_order_detail_shouhuoren.setText("收货人:" + bean.getAddress_recipient()==null?"":bean.getAddress_recipient());
        tv_order_detail_phone.setText(bean.getAddress_phone());
        tv_order_detail_address.setText("收货地址:" + bean.getShipping_address()==null?"":bean.getShipping_address());
        tv_order_detail_goods_price.setText("¥" + bean.getGoods_price().toString());
        tv_order_detail_goods_kuaidi.setText("¥" + bean.getFreight());
        tv_order_detail_goods_youhui.setText("-¥" + bean.getYouhui_money());
        tv_order_detail_goods_num.setText("共1件商品 合计:");
        tv_order_detail_total_price.setText("¥" + bean.getCombined().toString());

        tv_order_detail_orderno.setText("订单编号:" + bean.getOrder_no());
        tv_order_detail_order_time.setText("下单时间:" + bean.getCreate_add_time());
        tv_order_detail_order_pay_time.setText("付款时间:" + bean.getPayment_add_time());
        tv_order_detail_order_pay_way.setText("支付方式:" + bean.getPay_id());
        tv_order_detail_order_kuaidi_name.setText("配送方式:" + bean.getCourier_name());


        ll_order_detail_goods.removeAllViews();
            View goodsView = getLayoutInflater().inflate(R.layout.my_order_item_include, null);

            ImageView iv_order = goodsView.findViewById(R.id.iv_order);

            GlideUtils.into(mContext, bean.getGoods_img(), iv_order);
            TextView tv_order_name = goodsView.findViewById(R.id.tv_order_name);
            TextView tv_order_price = goodsView.findViewById(R.id.tv_order_price);
            TextView tv_order_number = goodsView.findViewById(R.id.tv_order_number);

            tv_order_name.setText(bean.getGoods_name());
            tv_order_price.setText("¥" + bean.getGoods_price().toString());
            tv_order_number.setText("x" + bean.getGoods_number());

            ll_order_detail_goods.addView(goodsView);

        //(0拍卖中 1待付款 2待发货 3待收货 4已完成 5已结束 6已取消)
        switch (bean.getOrder_status()) {
            case 0:
                tv_order_detail_status.setText("拍卖中");
                tv_paimai_order_again_chujia.setVisibility(View.VISIBLE);
                tv_paimai_order_pay.setVisibility(View.GONE);
                tv_paimai_order_sure.setVisibility(View.GONE);
                break;
            case 1:
                tv_order_detail_status.setText("待付款");
                tv_paimai_order_again_chujia.setVisibility(View.GONE);
                tv_paimai_order_pay.setVisibility(View.VISIBLE);
                tv_paimai_order_sure.setVisibility(View.GONE);
                break;
            case 2:
                tv_order_detail_status.setText("待发货");
                tv_paimai_order_again_chujia.setVisibility(View.GONE);
                tv_paimai_order_pay.setVisibility(View.GONE);
                tv_paimai_order_sure.setVisibility(View.GONE);
                break;
            case 3:
                tv_order_detail_status.setText("待收货");
                tv_paimai_order_again_chujia.setVisibility(View.GONE);
                tv_paimai_order_pay.setVisibility(View.GONE);
                tv_paimai_order_sure.setVisibility(View.VISIBLE);
                break;
            case 4:
                tv_order_detail_status.setText("已完成");
                tv_paimai_order_again_chujia.setVisibility(View.GONE);
                tv_paimai_order_pay.setVisibility(View.GONE);
                tv_paimai_order_sure.setVisibility(View.GONE);
                break;
            case 5:
                tv_order_detail_status.setText("已出局");
                tv_paimai_order_again_chujia.setVisibility(View.GONE);
                tv_paimai_order_pay.setVisibility(View.GONE);
                tv_paimai_order_sure.setVisibility(View.GONE);
                break;
            case 6:
                tv_order_detail_status.setText("已取消");
                tv_paimai_order_again_chujia.setVisibility(View.GONE);
                tv_paimai_order_pay.setVisibility(View.GONE);
                tv_paimai_order_sure.setVisibility(View.GONE);
                break;
        }

        tv_paimai_order_again_chujia.setOnClickListener(new MyOnClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
                Intent intent = new Intent();
                intent.putExtra(IntentParam.goodsId, bean.getGoods_id());
                STActivity(intent, AuctionDetailActivity.class);
                finish();
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
                    map.put("addres_id", addressId);
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



    @OnClick({R.id.ll_order_detail_wuliu_content})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.ll_order_detail_wuliu_content:
                Intent intent = new Intent();
                intent.putExtra(IntentParam.orderNo, orderNo);
                STActivity(intent, WuLiuActivity.class);
                break;
        }
    }
}
