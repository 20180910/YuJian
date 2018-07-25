package com.zhizhong.yujian.module.my.activity;

import android.text.TextUtils;
import android.view.View;

import com.github.androidtools.SPUtils;
import com.library.base.BaseObj;
import com.library.base.tools.ZhengZeUtils;
import com.zhizhong.yujian.AppXml;
import com.zhizhong.yujian.R;
import com.zhizhong.yujian.base.BaseActivity;
import com.zhizhong.yujian.base.MyCallBack;
import com.zhizhong.yujian.module.my.network.ApiRequest;
import com.zhizhong.yujian.view.MyEditText;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class UpdatePwdActivity extends BaseActivity {
    @BindView(R.id.et_update_pwd_old)
    MyEditText et_update_pwd_old;
    @BindView(R.id.et_update_pwd_new)
    MyEditText et_update_pwd_new;
    @BindView(R.id.et_update_pwd_re)
    MyEditText et_update_pwd_re;

    @Override
    protected int getContentView() {
        setAppTitle("密码修改");
        return R.layout.update_pwd_act;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }



    @OnClick({R.id.tv_update_pwd_complete})
    public void onViewClick(View v) {
        switch (v.getId()){
            case R.id.tv_update_pwd_complete:
                String oldPwd = getSStr(et_update_pwd_old);
                String newPwd =getSStr(et_update_pwd_new);
                String rePwd =getSStr(et_update_pwd_re);
                if(TextUtils.isEmpty(oldPwd)){
                    showMsg("旧密码不能为空");
                    return;
                }else if(!ZhengZeUtils.isShuZiAndZiMu(newPwd)||newPwd.length()>20||newPwd.length()<8){
                    showMsg("请输入8-20位数字加字母的密码");
                    return;
                }else if(!TextUtils.equals(newPwd,rePwd)){
                    showMsg("两次密码不一样");
                    return;
                }
                updatePwd(oldPwd,newPwd);
            break;
        }
    }

    private void updatePwd(String oldPwd, String newPwd) {
        showLoading();
        Map<String,String> map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("oldPassword",oldPwd);
        map.put("newPassword",newPwd);
        map.put("sign",getSign(map));
        ApiRequest.updatePwd(map, new MyCallBack<BaseObj>(mContext) {
            @Override
            public void onSuccess(BaseObj obj, int errorCode, String msg) {
                showMsg(msg);
                SPUtils.setPrefBoolean(mContext, AppXml.updatePWD,true);
                finish();
            }
        });

    }
}
