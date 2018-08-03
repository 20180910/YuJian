package com.zhizhong.yujian.module.my.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.github.androidtools.inter.MyOnClickListener;
import com.github.fastshape.MyTextView;
import com.github.mydialog.MySimpleDialog;
import com.library.base.BaseObj;
import com.zhizhong.yujian.IntentParam;
import com.zhizhong.yujian.R;
import com.zhizhong.yujian.base.BaseActivity;
import com.zhizhong.yujian.base.MyCallBack;
import com.zhizhong.yujian.module.my.network.ApiRequest;
import com.zhizhong.yujian.module.my.network.response.MyMoneyObj;
import com.zhizhong.yujian.view.MyEditText;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class TiXianActivity extends BaseActivity {
    @BindView(R.id.tv_tixian_account_balance)
    TextView tv_tixian_account_balance;
    @BindView(R.id.tv_tixian_way)
    TextView tv_tixian_way;
    @BindView(R.id.et_tixian_money)
    MyEditText et_tixian_money;
    @BindView(R.id.tv_tixian_full_money)
    MyTextView tv_tixian_full_money;
    @BindView(R.id.tv_tixian_commit)
    MyTextView tv_tixian_commit;


    private MyMoneyObj moneyObj;
    private String tiXianAccountId;
    private String tiXianType;

    @Override
    protected int getContentView() {
        setAppTitle("提现");
        return R.layout.ti_xian_act;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        showProgress();
        getData(1,false);
    }

    @Override
    protected void onMyReStart() {
        super.onMyReStart();
        getData(1,false);
    }
    @Override
    protected void getData(int page, boolean isLoad) {
        super.getData(page, isLoad);
        Map<String,String> map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("sign",getSign(map));
        ApiRequest.getMyMoney(map, new MyCallBack<MyMoneyObj>(mContext,pl_load,pcfl) {
            @Override
            public void onSuccess(MyMoneyObj obj, int errorCode, String msg) {
                moneyObj = obj;
                tv_tixian_account_balance.setText("¥"+obj.getAccount_balance());
                tv_tixian_full_money.setText("当前最多提现¥"+obj.getAccount_balance());
            }
        });
    }

    @OnClick({R.id.tv_tixian_way, R.id.tv_tixian_commit})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.tv_tixian_way:
                selectPayWay();
                break;
            case R.id.tv_tixian_commit:
                if(TextUtils.isEmpty(getSStr(tv_tixian_way))){
                    showMsg("请选择提现方式");
                    return;
                }else if(TextUtils.isEmpty(getSStr(et_tixian_money))){
                    showMsg("请输入提现金额");
                    return;
                }
                double money = Double.parseDouble(getSStr(et_tixian_money));
                if(money<=0){
                    showMsg("提现金额不能小于0");
                    return;
                }else if(money>(moneyObj.getAccount_balance().doubleValue())){
                    showMsg("提现金额不能超过余额");
                    return;
                }
                tiXian(money);
                break;
        }
    }

    private void tiXian(double money) {
        showLoading();
        Map<String,String>map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("type",tiXianType);
        map.put("account_id",tiXianAccountId);
        map.put("amount",money+"");
        map.put("sign",getSign(map));
        ApiRequest.tiXian(map,new MyCallBack<BaseObj>(mContext) {
            @Override
            public void onSuccess(BaseObj obj, int errorCode, String msg) {
                STActivityForResult(TiXianResultActivity.class,300);
            }
        });
    }

    private void selectPayWay() {
        View selectPayWayView = getLayoutInflater().inflate(R.layout.ti_xian_popu, null);

        final MySimpleDialog dialog=new MySimpleDialog(mContext);
        dialog.setFullWidth();
        dialog.setGravity(Gravity.BOTTOM);
        dialog.setContentView(selectPayWayView);
        dialog.show();

        selectPayWayView.findViewById(R.id.tv_tixian_ali).setOnClickListener(new MyOnClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
                dialog.dismiss();
                STActivityForResult(SelectZhiFuBaoActivity.class,100);
            }
        });
        selectPayWayView.findViewById(R.id.tv_tixian_bank).setOnClickListener(new MyOnClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
                dialog.dismiss();
                STActivityForResult(BankListActivity.class,200);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            switch (requestCode){
                case 100:
                    tiXianType="1";
                    String alipayAccount = data.getStringExtra(IntentParam.alipayAccount);
                    tiXianAccountId = data.getStringExtra(IntentParam.alipayId);
                    tv_tixian_way.setText("支付宝账户："+alipayAccount);
                    break;
                case 200:
                    tiXianType="2";
                    String bankAccount= data.getStringExtra(IntentParam.bankAccount);
                    tiXianAccountId = data.getStringExtra(IntentParam.bankId);
                    tv_tixian_way.setText("银行卡："+bankAccount);
                    break;
                case 300:
                    finish();
                    break;
            }
        }
    }
}
