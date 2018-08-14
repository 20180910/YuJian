package com.zhizhong.yujian.module.my.activity;

import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.github.androidtools.SPUtils;
import com.github.androidtools.inter.MyOnClickListener;
import com.github.fastshape.MyTextView;
import com.github.mydialog.MySimpleDialog;
import com.sdklibrary.base.ali.pay.MyAliOrderBean;
import com.sdklibrary.base.ali.pay.MyAliPay;
import com.sdklibrary.base.ali.pay.MyAliPayCallback;
import com.sdklibrary.base.ali.pay.PayResult;
import com.zhizhong.yujian.Config;
import com.zhizhong.yujian.Constant;
import com.zhizhong.yujian.R;
import com.zhizhong.yujian.base.BaseActivity;
import com.zhizhong.yujian.base.MyCallBack;
import com.zhizhong.yujian.bean.JiaoNaJinObj;
import com.zhizhong.yujian.view.MyEditText;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class ZhangHuChongZhiActivity extends BaseActivity {
    @BindView(R.id.et_balance_chongzhi)
    MyEditText et_balance_chongzhi;

    @BindView(R.id.tv_balance_chongzhi_commit)
    MyTextView tv_balance_chongzhi_commit;

    @Override
    protected int getContentView() {
        setAppTitle("账户充值");
        return R.layout.zhanghu_chongzhi_act;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.tv_balance_chongzhi_commit})
    protected void onViewClick(View v) {
        switch (v.getId()){
            case R.id.tv_balance_chongzhi_commit:
                if(TextUtils.isEmpty(getSStr(et_balance_chongzhi))){
                    showMsg("请输入充值金额");
                    return;
                }
                double money=Double.parseDouble(getSStr(et_balance_chongzhi));
                if(money<=0){
                    showMsg("充值金额不能小于0");
                    return;
                }
                showPay(new BigDecimal(getSStr(et_balance_chongzhi)));
            break;
        }
    }
    public void showPay(final BigDecimal money) {
        final MySimpleDialog dialog = new MySimpleDialog(mContext);
        View view = mContext.getLayoutInflater().inflate(R.layout.sure_order_popu, null);
        view.findViewById(R.id.iv_pay_cancle).setOnClickListener(new MyOnClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
                dialog.dismiss();
            }
        });
        ;
        final RadioGroup rg_select_pay = view.findViewById(R.id.rg_select_pay);
        final RadioButton rb_pay_weixin = view.findViewById(R.id.rb_pay_weixin);
        final RadioButton rb_pay_zhifubao = view.findViewById(R.id.rb_pay_zhifubao);
        final RadioButton rb_pay_online = view.findViewById(R.id.rb_pay_online);
        rb_pay_online.setVisibility(View.GONE);

        TextView tv_pay_total = view.findViewById(R.id.tv_pay_total);
        tv_pay_total.setText("¥" + money.toString());
        view.findViewById(R.id.tv_pay_commit).setOnClickListener(new MyOnClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
                dialog.dismiss();
                chongZhi(rg_select_pay,money);
            }
        });
        dialog.setContentView(view);
        dialog.setFullWidth();
        dialog.setGravity(Gravity.BOTTOM);
        dialog.show();
    }

    private void chongZhi(final RadioGroup rg_select_pay,final BigDecimal money) {
        showLoading();
        Map<String, String> map = new HashMap<String, String>();
        map.put("userid", getUserId());
        map.put("type","1");
        map.put("amount", money.toString());
        map.put("sign", getSign(map));
        com.zhizhong.yujian.module.auction.network.ApiRequest.chongZhi(map, new MyCallBack<JiaoNaJinObj>(mContext) {
            @Override
            public void onSuccess(JiaoNaJinObj obj, int errorCode, String msg) {
                switch (rg_select_pay.getCheckedRadioButtonId()){
                    case R.id.rb_pay_weixin:

                        break;
                    case R.id.rb_pay_zhifubao:
                        MyAliOrderBean bean=new MyAliOrderBean();
                        bean.setOut_trade_no(obj.getOrder_no());
                        bean.setTotal_amount(obj.getAmount());
                        bean.setSubject(Constant.orderSubject);
                        bean.setBody(Constant.orderBody);
                        String url = SPUtils.getString(mContext, Config.payType_ZFB, null);
                        bean.setNotifyUrl(url);
                        aliPay(bean);
                        break;
                }
            }
        });
    }


    private void aliPay(MyAliOrderBean bean) {
        showLoading();
        MyAliPay.newInstance(mContext).startPay(bean, new MyAliPayCallback() {
            @Override
            public void paySuccess(PayResult payResult) {
                dismissLoading();
                showMsg("充值成功");
                finish();
            }
            @Override
            public void payFail() {
                dismissLoading();
                showMsg("充值失败");
            }
            @Override
            public void payCancel() {
                dismissLoading();
                showMsg("充值取消");
            }
        });
    }
}
