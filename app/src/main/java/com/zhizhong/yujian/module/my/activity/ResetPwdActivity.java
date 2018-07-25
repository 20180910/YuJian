package com.zhizhong.yujian.module.my.activity;

import android.text.TextUtils;
import android.view.View;

import com.library.base.BaseObj;
import com.library.base.tools.ZhengZeUtils;
import com.zhizhong.yujian.IntentParam;
import com.zhizhong.yujian.R;
import com.zhizhong.yujian.base.BaseActivity;
import com.zhizhong.yujian.base.MyCallBack;
import com.zhizhong.yujian.module.my.network.ApiRequest;
import com.zhizhong.yujian.view.MyEditText;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class ResetPwdActivity extends BaseActivity {
    @BindView(R.id.et_reset_pwd)
    MyEditText et_reset_pwd;
    @BindView(R.id.et_reset_repwd)
    MyEditText et_reset_repwd;
    private String phone;
    private String smsCode;

    @Override
    protected int getContentView() {
        setAppTitle("重置密码");
        return R.layout.reset_pwd_act;
    }

    @Override
    protected void initView() {
        phone = getIntent().getStringExtra(IntentParam.phone);
        smsCode = getIntent().getStringExtra(IntentParam.smsCode);
    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.tv_reset_complete)
    public void onViewClick(View v) {
        switch (v.getId()){
            case R.id.tv_reset_complete:
                String pwd=getSStr(et_reset_pwd);
                String repwd=getSStr(et_reset_repwd);
                if(!ZhengZeUtils.isShuZiAndZiMu(pwd)||pwd.length()>20||pwd.length()<8){
                    showMsg("请输入8-20位数字加字母的密码");
                    return;
                }else if(!TextUtils.equals(pwd,repwd)){
                    showMsg("两次密码不一样,请重新输入");
                    return;
                }
                resetPwd(pwd);
            break;
        }
    }

    private void resetPwd(String pwd) {
        showLoading();
        Map<String,String> map=new HashMap<String,String>();
        map.put("mobile",phone);
        map.put("newPassword",pwd);
        map.put("sms_code", smsCode);
        map.put("sign",getSign(map));
        ApiRequest.resetPWD(map, new MyCallBack<BaseObj>(mContext) {
            @Override
            public void onSuccess(BaseObj obj, int errorCode, String msg) {
                showMsg(msg);
                setResult(RESULT_OK);
                finish();
            }
        });

    }
}
