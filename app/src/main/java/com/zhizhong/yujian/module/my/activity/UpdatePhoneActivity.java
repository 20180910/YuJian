package com.zhizhong.yujian.module.my.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.androidtools.SPUtils;
import com.library.base.BaseObj;
import com.library.base.tools.ZhengZeUtils;
import com.zhizhong.yujian.AppXml;
import com.zhizhong.yujian.R;
import com.zhizhong.yujian.base.BaseActivity;
import com.zhizhong.yujian.base.MyCallBack;
import com.zhizhong.yujian.module.my.network.ApiRequest;
import com.zhizhong.yujian.network.NetApiRequest;
import com.zhizhong.yujian.view.MyEditText;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class UpdatePhoneActivity extends BaseActivity {
    @BindView(R.id.tv_update_phone)
    TextView tv_update_phone;
    @BindView(R.id.et_update_phone_pwd)
    MyEditText et_update_phone_pwd;
    @BindView(R.id.et_update_phone_new)
    MyEditText et_update_phone_new;
    @BindView(R.id.et_update_phone_code)
    MyEditText et_update_phone_code;
    @BindView(R.id.tv_update_phone_getcode)
    TextView tv_update_phone_getcode;
    @BindView(R.id.ll_update_phone)
    LinearLayout ll_update_phone;
    private CharSequence smsCode;
    private String oldPhone;

    @Override
    protected int getContentView() {
        setAppTitle("手机号修改");
        return R.layout.update_phone_act;
    }

    @Override
    protected void initView() {
        oldPhone = SPUtils.getString(mContext, AppXml.mobile, null);
        if(TextUtils.isEmpty(oldPhone)){
            ll_update_phone.setVisibility(View.GONE);
        }else{
            ll_update_phone.setVisibility(View.VISIBLE);
            tv_update_phone.setText(oldPhone);
        }
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.tv_update_phone_getcode, R.id.tv_update_phone_complete})
    public void onViewClick(View view) {
        String phone;
        switch (view.getId()) {
            case R.id.tv_update_phone_getcode:
                phone = getSStr(et_update_phone_new);
                if(TextUtils.isEmpty(phone)|| ZhengZeUtils.notMobile(phone)){
                    showMsg("请输入正确手机号");
                    return;
                }
                getMsgCode(phone);
                break;
            case R.id.tv_update_phone_complete:
                String pwd = getSStr(et_update_phone_pwd);
                phone = getSStr(et_update_phone_new);
                String code = getSStr(et_update_phone_code);
                if(TextUtils.isEmpty(pwd)&&oldPhone!=null){
                    showMsg("请输入密码");
                    return;
                }else if(TextUtils.isEmpty(phone)||ZhengZeUtils.notMobile(phone)){
                    showMsg("请输入正确手机号");
                    return;
                }else if(TextUtils.isEmpty(smsCode)||!TextUtils.equals(code, smsCode)){
                    showMsg("请输入正确验证码");
                    return;
                }
                updatePhone(pwd,phone,code);
                break;
        }
    }

    private void updatePhone(String pwd, String phone, String code) {
        showLoading();
        Map<String,String>map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("mobile",phone);
        map.put("sms_code",code);
        map.put("pwd",pwd);
        map.put("sign",getSign(map));
        ApiRequest.updatePhone(map, new MyCallBack<BaseObj>(mContext) {
            @Override
            public void onSuccess(BaseObj obj, int errorCode, String msg) {
                showMsg(msg);
                finish();
            }
        });

    }

    private void getMsgCode(String phone) {
        showLoading();
        Map<String,String> map=new HashMap<String,String>();
        map.put("mobile",phone);
        map.put("type","3");
        map.put("sign",getSign(map));
        NetApiRequest.getMsgCode(map, new MyCallBack<BaseObj>(mContext) {
            @Override
            public void onSuccess(BaseObj obj, int errorCode, String msg) {
                smsCode = obj.getSMSCode();
                countDown(tv_update_phone_getcode);
            }
        });
    }
}
