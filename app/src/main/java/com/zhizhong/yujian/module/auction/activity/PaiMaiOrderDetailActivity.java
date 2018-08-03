package com.zhizhong.yujian.module.auction.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.androidtools.inter.MyOnClickListener;
import com.github.fastshape.MyTextView;
import com.github.mydialog.MyDialog;
import com.zhizhong.yujian.IntentParam;
import com.zhizhong.yujian.R;
import com.zhizhong.yujian.base.BaseActivity;
import com.zhizhong.yujian.base.GlideUtils;
import com.zhizhong.yujian.base.MyCallBack;
import com.zhizhong.yujian.module.auction.network.ApiRequest;
import com.zhizhong.yujian.module.auction.network.response.PaiMaiOrderDetailObj;
import com.zhizhong.yujian.module.my.activity.WuLiuActivity;

import java.util.HashMap;
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

        tv_order_detail_shouhuoren.setText("收货人:" + bean.getAddress_recipient());
        tv_order_detail_phone.setText(bean.getAddress_phone());
        tv_order_detail_address.setText("收货地址:" + bean.getShipping_address());
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
                showPay();
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


    private void evaluationOrder(String order_no) {

    }

    private void tuikuanOrder(String order_no) {

    }

    private void sureOrder(String order_no) {

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
