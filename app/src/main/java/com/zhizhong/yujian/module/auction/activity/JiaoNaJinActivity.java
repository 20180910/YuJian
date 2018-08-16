package com.zhizhong.yujian.module.auction.activity;

import android.support.v4.content.ContextCompat;
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
import com.library.base.view.richedit.TheEditor;
import com.sdklibrary.base.ali.pay.MyAliOrderBean;
import com.sdklibrary.base.ali.pay.MyAliPay;
import com.sdklibrary.base.ali.pay.MyAliPayCallback;
import com.sdklibrary.base.ali.pay.PayResult;
import com.sdklibrary.base.wx.inter.MyWXCallback;
import com.sdklibrary.base.wx.pay.MyWXOrderBean;
import com.sdklibrary.base.wx.pay.MyWXPay;
import com.zhizhong.yujian.Config;
import com.zhizhong.yujian.Constant;
import com.zhizhong.yujian.IntentParam;
import com.zhizhong.yujian.R;
import com.zhizhong.yujian.base.BaseActivity;
import com.zhizhong.yujian.base.MyCallBack;
import com.zhizhong.yujian.bean.JiaoNaJinObj;
import com.zhizhong.yujian.module.auction.network.ApiRequest;
import com.zhizhong.yujian.module.auction.network.response.BaoZhengJinObj;
import com.zhizhong.yujian.view.MyEditText;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class JiaoNaJinActivity extends BaseActivity {

    @BindView(R.id.et_baozhengjin_money)
    MyEditText et_baozhengjin_money;
    @BindView(R.id.tv_baozhengjin_commit)
    MyTextView tv_baozhengjin_commit;
    @BindView(R.id.te_baozhengjin_content)
    TheEditor te_baozhengjin_content;
    private BigDecimal baoZhengJin;

    @Override
    protected int getContentView() {
        setAppTitle("缴纳保证金");
        return R.layout.jiaonajin_act;
    }

    @Override
    protected void initView() {
        String money = getIntent().getStringExtra(IntentParam.baoZhengJin);
        if (!TextUtils.isEmpty(money)) {
            baoZhengJin = new BigDecimal(money);
            et_baozhengjin_money.setText(baoZhengJin.toString());
        }
        te_baozhengjin_content.setInputEnabled(false);
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
        map.put("rnd", getRnd());
        map.put("sign", getSign(map));
        ApiRequest.getBaoZhengJinContent(map, new MyCallBack<BaoZhengJinObj>(mContext, pl_load, pcfl) {
            @Override
            public void onSuccess(BaoZhengJinObj obj, int errorCode, String msg) {
                te_baozhengjin_content.setHtml(obj.getContent());
                te_baozhengjin_content.setBackgroundColor(ContextCompat.getColor(mContext, R.color.transparent));
                te_baozhengjin_content.setEditorFontSize(13);
            }
        });

    }


    @OnClick({R.id.tv_baozhengjin_commit})
    public void onViewClick(View v) {
        switch (v.getId()) {
            case R.id.tv_baozhengjin_commit:
                if(TextUtils.isEmpty(getSStr(et_baozhengjin_money))){
                    showMsg("请输入缴纳金");
                    return;
                }else if(Double.parseDouble(getSStr(et_baozhengjin_money))<=0){
                    showMsg("缴纳金必须大于0");
                    return;
                }
                showPay(new BigDecimal(getSStr(et_baozhengjin_money)));
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
                jiaoNa(rg_select_pay,money);
            }
        });
        dialog.setContentView(view);
        dialog.setFullWidth();
        dialog.setGravity(Gravity.BOTTOM);
        dialog.show();
    }

    private void jiaoNa(final RadioGroup rg_select_pay,final BigDecimal money) {
        showLoading();
        Map<String, String> map = new HashMap<String, String>();
        map.put("userid", getUserId());
        map.put("type","2");
        map.put("amount", money.toString());
        map.put("sign", getSign(map));
        ApiRequest.chongZhi(map, new MyCallBack<JiaoNaJinObj>(mContext) {
            @Override
            public void onSuccess(JiaoNaJinObj obj, int errorCode, String msg) {
                switch (rg_select_pay.getCheckedRadioButtonId()){
                    case R.id.rb_pay_weixin:
                        MyWXOrderBean wxBean=new MyWXOrderBean();
                        wxBean.setBody(Constant.orderBody);
                        String wxUrl = SPUtils.getString(mContext, Config.payType_WX, null);
                        wxBean.setNotifyUrl(wxUrl);
                        wxBean.setOut_trade_no(obj.getOrder_no());
                        wxBean.setTotalFee((int)(obj.getAmount()*100));
                        wxPay(wxBean);
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
    private void wxPay(MyWXOrderBean bean) {
        showLoading();
        MyWXPay.newInstance(mContext).startPay(bean, new MyWXCallback() {
            @Override
            public void onSuccess() {
                dismissLoading();
                showMsg("缴纳成功");
                finish();
            }
            @Override
            public void onFail() {
                dismissLoading();
                showMsg("缴纳失败");
            }
            @Override
            public void onCancel() {
                dismissLoading();
                showMsg("缴纳取消");
            }
        });
    }

    private void aliPay(MyAliOrderBean bean) {
        showLoading();
        MyAliPay.newInstance(mContext).startPay(bean, new MyAliPayCallback() {
            @Override
            public void paySuccess(PayResult payResult) {
                dismissLoading();
                showMsg("缴纳成功");
                finish();
            }
            @Override
            public void payFail() {
                dismissLoading();
                showMsg("缴纳失败");
            }
            @Override
            public void payCancel() {
                dismissLoading();
                showMsg("缴纳取消");
            }
        });
    }
}
