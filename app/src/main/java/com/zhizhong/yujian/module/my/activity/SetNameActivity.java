package com.zhizhong.yujian.module.my.activity;

import android.text.TextUtils;
import android.view.View;

import com.github.androidtools.SPUtils;
import com.library.base.BaseObj;
import com.zhizhong.yujian.AppXml;
import com.zhizhong.yujian.R;
import com.zhizhong.yujian.base.BaseActivity;
import com.zhizhong.yujian.base.MyCallBack;
import com.zhizhong.yujian.module.my.network.ApiRequest;
import com.zhizhong.yujian.module.my.network.request.UpdateInfoBody;
import com.zhizhong.yujian.view.MyEditText;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class SetNameActivity extends BaseActivity {
    @BindView(R.id.et_set_name)
    MyEditText et_set_name;

    @Override
    protected int getContentView() {
        setAppTitle("设置昵称");
        return R.layout.set_name_act;
    }

    @Override
    protected void initView() {

        String name = SPUtils.getString(mContext, AppXml.nick_name, null);
        et_set_name.setText(name);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onViewClick(View v) {

    }

    @OnClick({ R.id.tv_set_name_complete})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_set_name_complete:
                String name=getSStr(et_set_name);
                if(TextUtils.isEmpty(name)){
                    showMsg("请输入昵称");
                    return;
                }
                setName(name);
                break;
        }
    }

    private void setName(final String name) {
        showLoading();
        Map<String,String> map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("sign",getSign(map));
        UpdateInfoBody body=new UpdateInfoBody();
        body.setNickname(name);
        ApiRequest.updateUserInfo(map,body, new MyCallBack<BaseObj>(mContext) {
            @Override
            public void onSuccess(BaseObj obj, int errorCode, String msg) {
                showMsg(msg);
                SPUtils.setPrefString(mContext, AppXml.nick_name,name);
                finish();
            }
        });

    }
}
