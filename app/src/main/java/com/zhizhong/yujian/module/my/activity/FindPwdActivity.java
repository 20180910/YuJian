package com.zhizhong.yujian.module.my.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.library.base.BaseObj;
import com.library.base.tools.ZhengZeUtils;
import com.zhizhong.yujian.IntentParam;
import com.zhizhong.yujian.R;
import com.zhizhong.yujian.base.BaseActivity;
import com.zhizhong.yujian.base.MyCallBack;
import com.zhizhong.yujian.network.NetApiRequest;
import com.zhizhong.yujian.view.MyEditText;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class FindPwdActivity extends BaseActivity {
    @BindView(R.id.et_findpwd_phone)
    MyEditText et_findpwd_phone;
    @BindView(R.id.et_findpwd_code)
    MyEditText et_findpwd_code;
    @BindView(R.id.tv_findpwd_getcode)
    TextView tv_findpwd_getcode;
    private String smsCode;

    @Override
    protected int getContentView() {
        setAppTitle("找回密码");
        return R.layout.find_pwd_act;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.tv_findpwd_getcode, R.id.tv_findpwd_next})
    public void onViewClick(View view) {
        String phone;
        switch (view.getId()) {
            case R.id.tv_findpwd_getcode:
                phone = getSStr(et_findpwd_phone);
                if(TextUtils.isEmpty(phone)|| ZhengZeUtils.notMobile(phone)){
                    showMsg("请输入正确手机号");
                    return;
                }
                getMsgCode(phone);
                break;
            case R.id.tv_findpwd_next:
                phone = getSStr(et_findpwd_phone);
                String code = getSStr(et_findpwd_code);
                if(TextUtils.isEmpty(phone)||ZhengZeUtils.notMobile(phone)){
                    showMsg("请输入正确手机号");
                    return;
                }else if(TextUtils.isEmpty(smsCode)||!TextUtils.equals(code,smsCode)){
                    showMsg("请输入正确验证码");
                    return;
                }
                Intent intent=new Intent();
                intent.putExtra(IntentParam.phone,phone);
                intent.putExtra(IntentParam.smsCode,code);
                STActivityForResult(intent,ResetPwdActivity.class,100);
                break;
        }
    }

    private void getMsgCode(String phone) {
        showLoading();
        Map<String,String> map=new HashMap<String,String>();
        map.put("mobile",phone);
        map.put("type","2");
        map.put("sign",getSign(map));
        NetApiRequest.getMsgCode(map, new MyCallBack<BaseObj>(mContext) {
            @Override
            public void onSuccess(BaseObj obj, int errorCode, String msg) {
                smsCode = obj.getSMSCode();
                countDown(tv_findpwd_getcode);
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
