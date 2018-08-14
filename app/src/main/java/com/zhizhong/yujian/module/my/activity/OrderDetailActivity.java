package com.zhizhong.yujian.module.my.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.androidtools.inter.MyOnClickListener;
import com.github.fastshape.MyTextView;
import com.github.mydialog.MyDialog;
import com.github.rxbus.RxBus;
import com.library.base.BaseObj;
import com.zhizhong.yujian.IntentParam;
import com.zhizhong.yujian.R;
import com.zhizhong.yujian.base.BaseActivity;
import com.zhizhong.yujian.base.GlideUtils;
import com.zhizhong.yujian.base.MyCallBack;
import com.zhizhong.yujian.event.MyOrderEvent;
import com.zhizhong.yujian.module.my.event.RefreshMyOrderEvent;
import com.zhizhong.yujian.module.my.fragment.MyOrderFragment;
import com.zhizhong.yujian.module.my.network.ApiRequest;
import com.zhizhong.yujian.module.my.network.response.OrderDetailObj;
import com.zhizhong.yujian.tools.PayView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class OrderDetailActivity extends BaseActivity {

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
    @BindView(R.id.tv_order_detail_cancel)
    MyTextView tv_order_detail_cancel;
    @BindView(R.id.tv_order_detail_delete)
    MyTextView tv_order_detail_delete;
    @BindView(R.id.tv_order_detail_pay)
    MyTextView tv_order_detail_pay;
    @BindView(R.id.tv_order_detail_sure)
    MyTextView tv_order_detail_sure;
    @BindView(R.id.tv_order_detail_tuikuan)
    MyTextView tv_order_detail_tuikuan;
    @BindView(R.id.tv_order_detail_evaluation)
    MyTextView tv_order_detail_evaluation;
    private String orderNo;

    @Override
    protected int getContentView() {
        setAppTitle("订单详情");
        return R.layout.order_detail_act;
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
        ApiRequest.getOrderDetail(map, new MyCallBack<OrderDetailObj>(mContext, pl_load, pcfl) {
            @Override
            public void onSuccess(OrderDetailObj obj, int errorCode, String msg) {
                setData(obj);
            }
        });

    }

    private void setData(final OrderDetailObj bean) {
        if (notEmpty(bean.getCourier_list())) {
            tv_order_detail_wuliu.setText(bean.getCourier_list().get(0).getContext());
            tv_order_detail_wuliu_time.setText(bean.getCourier_list().get(0).getTime());
        }

        tv_order_detail_shouhuoren.setText("收货人:" + bean.getAddress_recipient());
        tv_order_detail_phone.setText(bean.getAddress_phone());
        tv_order_detail_address.setText("收货地址:" + bean.getShipping_address());
        tv_order_detail_goods_price.setText("¥" + bean.getGoods_total_amount().toString());
        tv_order_detail_goods_kuaidi.setText("¥" + bean.getFreight());
        tv_order_detail_goods_youhui.setText("-¥" + bean.getYouhui_money());
        tv_order_detail_goods_num.setText("共" + bean.getGoods_list().size() + "件商品 合计:");
        tv_order_detail_total_price.setText("¥" + bean.getCombined().toString());

        tv_order_detail_orderno.setText("订单编号:" + bean.getOrder_no());
        tv_order_detail_order_time.setText("下单时间:" + bean.getCreate_add_time());
        tv_order_detail_order_pay_time.setText("付款时间:" + bean.getPayment_add_time());
        tv_order_detail_order_pay_way.setText("支付方式:" + bean.getPay_id());
        tv_order_detail_order_kuaidi_name.setText("配送方式:" + bean.getCourier_name());


        ll_order_detail_goods.removeAllViews();
        for (int i = 0; i < bean.getGoods_list().size(); i++) {
            OrderDetailObj.GoodsListBean goodsListBean = bean.getGoods_list().get(i);
            View goodsView = getLayoutInflater().inflate(R.layout.my_order_item_include, null);

            ImageView iv_order = goodsView.findViewById(R.id.iv_order);

            GlideUtils.into(mContext, goodsListBean.getGoods_images(), iv_order);
            TextView tv_order_name = goodsView.findViewById(R.id.tv_order_name);
            TextView tv_order_price = goodsView.findViewById(R.id.tv_order_price);
            TextView tv_order_number = goodsView.findViewById(R.id.tv_order_number);

            tv_order_name.setText(goodsListBean.getGoods_name());
            tv_order_price.setText("¥" + goodsListBean.getGoods_unitprice().toString());
            tv_order_number.setText("x" + goodsListBean.getGoods_number());

            ll_order_detail_goods.addView(goodsView);
        }


        switch (bean.getOrder_status()) {
            case MyOrderFragment.type_1:
                tv_order_detail_status.setText("待付款");
                tv_order_detail_cancel.setVisibility(View.VISIBLE);
                tv_order_detail_delete.setVisibility(View.GONE);
                tv_order_detail_pay.setVisibility(View.VISIBLE);
                tv_order_detail_sure.setVisibility(View.GONE);
                tv_order_detail_tuikuan.setVisibility(View.GONE);
                tv_order_detail_evaluation.setVisibility(View.GONE);
                break;
            case MyOrderFragment.type_2:
                tv_order_detail_status.setText("待发货");
                tv_order_detail_cancel.setVisibility(View.GONE);
                tv_order_detail_delete.setVisibility(View.GONE);
                tv_order_detail_pay.setVisibility(View.GONE);
                tv_order_detail_sure.setVisibility(View.GONE);
                tv_order_detail_tuikuan.setVisibility(View.VISIBLE);
                tv_order_detail_evaluation.setVisibility(View.GONE);
                break;
            case MyOrderFragment.type_3:
                tv_order_detail_status.setText("待收货");
                tv_order_detail_cancel.setVisibility(View.GONE);
                tv_order_detail_delete.setVisibility(View.GONE);
                tv_order_detail_pay.setVisibility(View.GONE);
                tv_order_detail_sure.setVisibility(View.VISIBLE);
                tv_order_detail_tuikuan.setVisibility(View.VISIBLE);
                tv_order_detail_evaluation.setVisibility(View.GONE);
                break;
            case MyOrderFragment.type_4:
                tv_order_detail_status.setText("待评价");
                tv_order_detail_cancel.setVisibility(View.GONE);
                tv_order_detail_delete.setVisibility(View.GONE);
                tv_order_detail_pay.setVisibility(View.GONE);
                tv_order_detail_sure.setVisibility(View.GONE);
                tv_order_detail_tuikuan.setVisibility(View.GONE);
                tv_order_detail_evaluation.setVisibility(View.VISIBLE);
                break;
            case MyOrderFragment.type_5:
                tv_order_detail_status.setText("已完成");
                tv_order_detail_cancel.setVisibility(View.GONE);
                tv_order_detail_delete.setVisibility(View.GONE);
                tv_order_detail_pay.setVisibility(View.GONE);
                tv_order_detail_sure.setVisibility(View.GONE);
                tv_order_detail_tuikuan.setVisibility(View.GONE);
                tv_order_detail_evaluation.setVisibility(View.GONE);
                break;
            case MyOrderFragment.type_6:
                tv_order_detail_status.setText("已取消");
                tv_order_detail_cancel.setVisibility(View.GONE);
                tv_order_detail_delete.setVisibility(View.VISIBLE);
                tv_order_detail_pay.setVisibility(View.GONE);
                tv_order_detail_sure.setVisibility(View.GONE);
                tv_order_detail_tuikuan.setVisibility(View.GONE);
                tv_order_detail_evaluation.setVisibility(View.GONE);
                break;
        }

        tv_order_detail_cancel.setOnClickListener(new MyOnClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
                cancelOrder(bean.getOrder_no());
            }
        });
        tv_order_detail_delete.setOnClickListener(new MyOnClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
                deleteOrder(bean.getOrder_no());
            }
        });
        tv_order_detail_pay.setOnClickListener(new MyOnClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
//                showPay(bean.getOrder_no());
                PayView.showPay(mContext,bean.getOrder_no(),bean.getCombined().doubleValue());
            }
        });
        tv_order_detail_sure.setOnClickListener(new MyOnClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
                sureOrder(bean.getOrder_no());
            }
        });
        tv_order_detail_tuikuan.setOnClickListener(new MyOnClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
                tuikuanOrder(bean.getOrder_no());
            }
        });
        tv_order_detail_evaluation.setOnClickListener(new MyOnClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
                evaluationOrder(bean.getOrder_no());
            }
        });

    }

    private void deleteOrder(final String order_no) {
        MyDialog.Builder mDialog = new MyDialog.Builder(mContext);
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
                Map<String, String> map = new HashMap<String, String>();
                map.put("user_id", getUserId());
                map.put("order_no", order_no);
                map.put("sign", getSign(map));
                ApiRequest.deleteOrder(map, new MyCallBack<BaseObj>(mContext, true) {
                    @Override
                    public void onSuccess(BaseObj obj, int errorCode, String msg) {
                        showMsg(msg);
                        RxBus.getInstance().post(new MyOrderEvent(MyOrderFragment.type_0));
                        finish();
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
        finish();
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
                getData(1,false);
            }
        });
    }

    private void cancelOrder(final String order_no) {
        MyDialog.Builder mDialog = new MyDialog.Builder(mContext);
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
                Map<String, String> map = new HashMap<String, String>();
                map.put("user_id", getUserId());
                map.put("order_no", order_no);
                map.put("sign", getSign(map));
                ApiRequest.cancelOrder(map, new MyCallBack<BaseObj>(mContext, true) {
                    @Override
                    public void onSuccess(BaseObj obj, int errorCode, String msg) {
                        getData(1, false);
                        showMsg(msg);
                        RxBus.getInstance().post(new MyOrderEvent(MyOrderFragment.type_0));
                    }
                });
            }
        });
        mDialog.create().show();
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
