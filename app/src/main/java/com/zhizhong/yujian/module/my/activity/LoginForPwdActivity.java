package com.zhizhong.yujian.module.my.activity;

import android.text.TextUtils;
import android.view.View;

import com.github.androidtools.SPUtils;
import com.github.rxbus.RxBus;
import com.library.base.tools.ZhengZeUtils;
import com.sdklibrary.base.qq.share.MyQQLoginCallback;
import com.sdklibrary.base.qq.share.MyQQShare;
import com.sdklibrary.base.qq.share.bean.MyQQUserInfo;
import com.sdklibrary.base.wx.share.MyWXLoginCallback;
import com.sdklibrary.base.wx.share.MyWXShare;
import com.sdklibrary.base.wx.share.MyWXUserInfo;
import com.zhizhong.yujian.AppXml;
import com.zhizhong.yujian.Constant;
import com.zhizhong.yujian.IntentParam;
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

public class LoginForPwdActivity extends BaseActivity {
    @BindView(R.id.et_login_phone)
    MyEditText et_login_phone;
    @BindView(R.id.et_login_pwd)
    MyEditText et_login_pwd;
    private boolean needSelectMy;

    @Override
    protected int getContentView() {
        setAppRightTitle("手机号登录");
        setTitleBackgroud(R.color.transparent);
        return R.layout.login_act;
    }

    @Override
    protected void initView() {

        String action = getIntent().getAction();
        needSelectMy =TextUtils.equals(IntentParam.needSelectMy,action);
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
                showLoading();
                MyQQShare.newInstance(mContext).login(new MyQQLoginCallback() {
                    @Override
                    public void loginSuccess(MyQQUserInfo myQQUserInfo) {
                        loginForApp("1",myQQUserInfo.getOpenid(),myQQUserInfo.getNickname(),myQQUserInfo.getUserImageUrl());
                        showMsg("登录成功");
                    }
                    @Override
                    public void loginFail() {
                        dismissLoading();
                        showMsg("登录失败");
                    }
                    @Override
                    public void loginCancel() {
                        dismissLoading();
                        showMsg("取消登录");
                    }
                });
                break;
            case R.id.iv_login_wx:
                showLoading();
                MyWXShare.newInstance(mContext).login(new MyWXLoginCallback() {
                    @Override
                    public void loginSuccess(MyWXUserInfo myWXUserInfo) {
                        loginForApp("2",myWXUserInfo.getUnionid(),myWXUserInfo.getNickname(),myWXUserInfo.getHeadimgurl());
                        showMsg("登录成功");
                    }
                    @Override
                    public void loginFail() {
                        dismissLoading();
                        showMsg("登录失败");
                    }
                    @Override
                    public void loginCancel() {
                        dismissLoading();
                        showMsg("取消登录");
                    }
                });
                break;
            case R.id.app_right_tv:
                finish();
                break;
        }
    }


    private void loginForApp(String platform,String openId,String nickname,String avatar) {
        Map<String,String>map=new HashMap<String,String>();
        map.put("platform",platform);
        map.put("open",openId);
        map.put("nickname",nickname);
        map.put("avatar",avatar);
        map.put("RegistrationID", SPUtils.getString(mContext, AppXml.registrationId,"0"));
        map.put("sign",getSign(map));
        NetApiRequest.appLogin(map, new MyCallBack<LoginObj>(mContext) {
            @Override
            public void onSuccess(LoginObj obj, int errorCode, String msg) {
                setLoginObj(obj);
            }
        });

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
        SPUtils.setPrefString(mContext, Constant.hxname,obj.getUser_id());
        SPUtils.setPrefString(mContext,AppXml.userId,obj.getUser_id());
        SPUtils.setPrefString(mContext,AppXml.mobile,obj.getMobile());
        SPUtils.setPrefString(mContext,AppXml.avatar,obj.getAvatar());
        SPUtils.setPrefString(mContext,AppXml.nick_name,obj.getNick_name());
        SPUtils.setPrefString(mContext,AppXml.sex,obj.getSex());
        SPUtils.setPrefString(mContext,AppXml.birthday,obj.getBirthday());
        SPUtils.setPrefString(mContext,AppXml.amount,obj.getAmount()+"");

        SPUtils.setPrefInt(mContext,AppXml.coupons_count,obj.getCoupons_count());
        SPUtils.setPrefInt(mContext,AppXml.message_sink,obj.getMessage_sink());

        SPUtils.setPrefString(mContext,AppXml.qq_name,obj.getQq_name());
        SPUtils.setPrefString(mContext,AppXml.wechat_name,obj.getWechat_name());

        if(needSelectMy){
            RxBus.getInstance().post(new LoginSuccessEvent(LoginSuccessEvent.status_1));
        }
        setResult(RESULT_OK);
        finish();
    }
}
