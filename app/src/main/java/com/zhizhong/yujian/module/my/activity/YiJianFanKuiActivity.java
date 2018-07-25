package com.zhizhong.yujian.module.my.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.github.fastshape.MyTextView;
import com.library.base.BaseObj;
import com.zhizhong.yujian.R;
import com.zhizhong.yujian.base.BaseActivity;
import com.zhizhong.yujian.base.MyCallBack;
import com.zhizhong.yujian.module.my.network.ApiRequest;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class YiJianFanKuiActivity extends BaseActivity {
    @BindView(R.id.et_yjfk_content)
    EditText et_yjfk_content;

    @BindView(R.id.tv_yjfk_commit)
    MyTextView tv_yjfk_commit;
    @Override
    protected int getContentView() {
        setAppTitle("意见反馈");
        return R.layout.yijian_fankui_act;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.tv_yjfk_commit})
    protected void onViewClick(View v) {
        switch (v.getId()){
            case R.id.tv_yjfk_commit:
                String content = getSStr(et_yjfk_content);
                if(TextUtils.isEmpty(content)){
                    showMsg("请输入内容");
                    return;
                }
                fanKui(content);
            break;
        }
    }

    private void fanKui(String content) {
        showLoading();
        Map<String,String> map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("content",content);
        map.put("sign",getSign(map));
        ApiRequest.fanKui(map, new MyCallBack<BaseObj>(mContext) {
            @Override
            public void onSuccess(BaseObj obj, int errorCode, String msg) {
                showMsg(msg);
                finish();
            }
        });

    }
}
