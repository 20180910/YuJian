package com.zhizhong.yujian.module.my.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.github.androidtools.SPUtils;
import com.github.rxbus.RxBus;
import com.google.gson.Gson;
import com.library.base.BaseObj;
import com.library.base.tools.ZhengZeUtils;
import com.zhizhong.yujian.AppXml;
import com.zhizhong.yujian.R;
import com.zhizhong.yujian.base.BaseActivity;
import com.zhizhong.yujian.base.MyCallBack;
import com.zhizhong.yujian.event.LoginSuccessEvent;
import com.zhizhong.yujian.module.my.network.ApiRequest;
import com.zhizhong.yujian.module.my.network.response.LoginObj;
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
                STActivityForResult(LoginForPwdActivity.class,100);
                break;
        }
    }

    private void loginForMsg(String phone, String code) {
        showLoading();
        Map<String,String>map=new HashMap<String,String>();
        map.put("mobile",phone);
        map.put("code",code);
        map.put("registrationid", SPUtils.getString(mContext, AppXml.registrationId,"0"));
        map.put("sign",getSign(map));
        ApiRequest.loginForMsg(map, new MyCallBack<LoginObj>(mContext) {
            @Override
            public void onSuccess(LoginObj obj, int errorCode, String msg) {
                setLoginObj(obj);
            }
        });

    }

    private void setLoginObj(LoginObj obj) {
        String json = new Gson().toJson(obj);
        SPUtils.setPrefString(mContext,AppXml.loginJson,json);

        RxBus.getInstance().post(new LoginSuccessEvent(LoginSuccessEvent.status_1));

        finish();
    }

    private void getMsgCode(String phone) {
        showLoading();
        Map<String,String> map=new HashMap<String,String>();
        map.put("mobile",phone);
        map.put("type","1");
        map.put("sign",getSign(map));
        NetApiRequest.getMsgCode(map, new MyCallBack<BaseObj>(mContext) {
            @Override
            public void onSuccess(BaseObj obj, int errorCode, String msg) {
                smsCode = obj.getSMSCode();
                countDown(tv_login_msg_getcode);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            switch (requestCode){
                case 100:
                    finish();
                break;
            }
        }
    }
}
