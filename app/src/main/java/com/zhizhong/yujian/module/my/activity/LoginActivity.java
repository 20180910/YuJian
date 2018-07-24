package com.zhizhong.yujian.module.my.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.library.base.BaseObj;
import com.library.base.tools.ZhengZeUtils;
import com.zhizhong.yujian.R;
import com.zhizhong.yujian.base.BaseActivity;
import com.zhizhong.yujian.base.MyCallBack;
import com.zhizhong.yujian.network.NetApiRequest;
import com.zhizhong.yujian.view.MyEditText;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {
    @BindView(R.id.et_login_msg_phone)
    MyEditText et_login_msg_phone;
    @BindView(R.id.et_login_msg_pwd)
    MyEditText et_login_msg_pwd;
    @BindView(R.id.tv_login_msg_getcode)
    TextView tv_login_msg_getcode;
    private String smsCode;

    @Override
    protected int getContentView() {
        setAppRightTitle("密码登录");
        setTitleBackgroud(R.color.transparent);
        return R.layout.login_code_act;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.app_right_tv,R.id.tv_login_msg_getcode, R.id.tv_login_msg_commit, R.id.iv_login_msg_qq, R.id.iv_login_msg_wx})
    public void onViewClick(View view) {
        String phone;
        switch (view.getId()) {
            case R.id.tv_login_msg_getcode:
                phone = getSStr(et_login_msg_phone);
                if(TextUtils.isEmpty(phone)|| ZhengZeUtils.notMobile(phone)){
                    showMsg("请输入正确手机号");
                    return;
                }
                getMsgCode(phone);
                break;
            case R.id.tv_login_msg_commit:
                phone = getSStr(et_login_msg_phone);
                String code = getSStr(et_login_msg_pwd);
                if(TextUtils.isEmpty(phone)||ZhengZeUtils.notMobile(phone)){
                    showMsg("请输入正确手机号");
                    return;
                }else if(TextUtils.isEmpty(smsCode)||!TextUtils.equals(code,smsCode)){
                    showMsg("请输入正确验证码");
                    return;
                }
                loginForMsg(phone,code);
                break;
            case R.id.iv_login_msg_qq:
                break;
            case R.id.iv_login_msg_wx:
                break;
            case R.id.app_right_tv:
                STActivity(LoginForPwdActivity.class);
                break;
        }
    }

    private void loginForMsg(String phone, String code) {
        showLoading();

    }

    private void getMsgCode(String phone) {
        showLoading();
        Map<String,String> map=new HashMap<String,String>();
        map.put("mobile",phone);
        map.put("rnd",getRnd());
        map.put("sign",getSign(map));
        NetApiRequest.getMsgCode(map, new MyCallBack<BaseObj>(mContext) {
            @Override
            public void onSuccess(BaseObj obj, int errorCode, String msg) {
                smsCode = obj.getSMSCode();
                countDown(tv_login_msg_getcode);
            }
        });

    }
}
