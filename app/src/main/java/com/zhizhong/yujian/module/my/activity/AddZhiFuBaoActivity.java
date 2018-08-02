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

public class AddZhiFuBaoActivity extends BaseActivity {
    @BindView(R.id.et_add_zhifubao_account)
    MyEditText et_add_zhifubao_account;
    @BindView(R.id.et_add_zhifubao_name)
    MyEditText et_add_zhifubao_name;
    @BindView(R.id.tv_add_zhifubao_commit)
    MyTextView tv_add_zhifubao_commit;

    @Override
    protected int getContentView() {
        setAppTitle("添加支付宝");
        return R.layout.add_zhifubao_act;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
    }


    @OnClick({R.id.tv_add_zhifubao_commit})
    protected void onViewClick(View v) {
        switch (v.getId()) {
            case R.id.tv_add_zhifubao_commit:
                String account = getSStr(et_add_zhifubao_account);
                String name = getSStr(et_add_zhifubao_name);
                if(TextUtils.isEmpty(account)){
                    showMsg("请输入支付宝账号");
                    return;
                }else if(TextUtils.isEmpty(name)){
                    showMsg("请输入支付宝姓名");
                    return;
                }
                addZhiFuBao(account,name);
                break;
        }
    }

    private void addZhiFuBao(String account, String name) {
        showLoading();
        Map<String, String> map = new HashMap<String, String>();
        map.put("user_id", getUserId());
        map.put("alipay_number", account);
        map.put("realname", name);
        map.put("sign", getSign(map));
        ApiRequest.addAliAccount(map, new MyCallBack<BaseObj>(mContext) {
            @Override
            public void onSuccess(BaseObj obj, int errorCode, String msg) {
                showMsg(msg);
                setResult(RESULT_OK);
                finish();
            }
        });

    }


}
