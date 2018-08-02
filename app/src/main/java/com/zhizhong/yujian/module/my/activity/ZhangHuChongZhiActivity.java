package com.zhizhong.yujian.module.my.activity;

import android.text.TextUtils;
import android.view.View;

import com.github.fastshape.MyTextView;
import com.library.base.BaseObj;
import com.zhizhong.yujian.R;
import com.zhizhong.yujian.base.BaseActivity;
import com.zhizhong.yujian.base.MyCallBack;
import com.zhizhong.yujian.module.my.network.ApiRequest;
import com.zhizhong.yujian.view.MyEditText;

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
                double money=Double.parseDouble(getSStr(et_balance_chongzhi));
                if(TextUtils.isEmpty(getSStr(et_balance_chongzhi))){
                    showMsg("请输入充值金额");
                    return;
                }else if(money<=0){
                    showMsg("充值金额不能小于0");
                    return;
                }
                chongZhi(money);
            break;
        }
    }

    private void chongZhi(double money) {
        showLoading();
        Map<String,String> map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("sign",getSign(map));
        ApiRequest.chongZhiOrder(map, new MyCallBack<BaseObj>(mContext) {
            @Override
            public void onSuccess(BaseObj obj, int errorCode, String msg) {
                showPay();
//                setResult(RESULT_OK);
//                finish();
            }
        });

    }
}
