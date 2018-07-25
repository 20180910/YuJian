package com.zhizhong.yujian.module.my.activity;

import android.text.TextUtils;
import android.view.View;

import com.github.androidtools.SPUtils;
import com.github.rxbus.RxBus;
import com.library.base.tools.ZhengZeUtils;
import com.zhizhong.yujian.AppXml;
import com.zhizhong.yujian.R;
import com.zhizhong.yujian.base.BaseActivity;
import com.zhizhong.yujian.base.MyCallBack;
import com.zhizhong.yujian.event.LoginSuccessEvent;
import com.zhizhong.yujian.module.my.network.ApiRequest;
import com.zhizhong.yujian.module.my.network.response.LoginObj;
import com.zhizhong.yujian.view.MyEditText;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginForPwdActivity extends BaseActivity {
    @BindView(R.id.et_login_phone)
    MyEditText et_login_phone;
    @BindView(R.id.et_login_pwd)
    MyEditText et_login_pwd;

    @Override
    protected int getContentView() {
        setAppRightTitle("手机号登录");
        setTitleBackgroud(R.color.transparent);
        return R.layout.login_act;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.app_right_tv,R.id.tv_login_commit,R.id.tv_login_forget, R.id.iv_login_qq, R.id.iv_login_wx})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.tv_login_commit:
                String phone = getSStr(et_login_phone);
                String pwd = getSStr(et_login_pwd);
                if(TextUtils.isEmpty(phone)|| ZhengZeUtils.notMobile(phone)){
                    showMsg("请输入正确手机号");
                    return;
                }else if(TextUtils.isEmpty(pwd)){
                    showMsg("请输入密码");
                    return;
                }
                loginForPwd(phone,pwd);
                break;
            case R.id.tv_login_forget:
                STActivity(FindPwdActivity.class);
                break;
            case R.id.iv_login_qq:
                break;
            case R.id.iv_login_wx:
                break;
            case R.id.app_right_tv:
                finish();
                break;
        }
    }

    private void loginForPwd(String phone, String pwd) {
        showLoading();
        Map<String,String> map=new HashMap<String,String>();
        map.put("mobile",phone);
        map.put("pwd",pwd);
        map.put("registrationid", SPUtils.getString(mContext, AppXml.registrationId,"0"));
        map.put("sign",getSign(map));
        ApiRequest.loginForPwd(map, new MyCallBack<LoginObj>(mContext) {
            @Override
            public void onSuccess(LoginObj obj, int errorCode, String msg) {
                setLoginObj(obj);
            }
        });
    }
    private void setLoginObj(LoginObj obj) {
        SPUtils.setPrefString(mContext,AppXml.userId,obj.getUser_id());
        SPUtils.setPrefString(mContext,AppXml.mobile,obj.getUser_id());
        SPUtils.setPrefString(mContext,AppXml.avatar,obj.getAvatar());
        SPUtils.setPrefString(mContext,AppXml.nick_name,obj.getUser_id());
        SPUtils.setPrefString(mContext,AppXml.sex,obj.getUser_id());
        SPUtils.setPrefString(mContext,AppXml.birthday,obj.getUser_id());
        SPUtils.setPrefString(mContext,AppXml.amount,obj.getAmount()+"");

        SPUtils.setPrefInt(mContext,AppXml.coupons_count,obj.getCoupons_count());
        SPUtils.setPrefInt(mContext,AppXml.message_sink,obj.getMessage_sink());

        SPUtils.setPrefString(mContext,AppXml.qq_name,obj.getQq_name());
        SPUtils.setPrefString(mContext,AppXml.wechat_name,obj.getWechat_name());


        RxBus.getInstance().post(new LoginSuccessEvent(LoginSuccessEvent.status_1));

        setResult(RESULT_OK);
        finish();
    }
}
