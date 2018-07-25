package com.zhizhong.yujian.module.my.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhizhong.yujian.R;
import com.zhizhong.yujian.base.BaseActivity;
import com.zhizhong.yujian.base.GlideUtils;
import com.zhizhong.yujian.base.MyCallBack;
import com.zhizhong.yujian.module.my.network.ApiRequest;
import com.zhizhong.yujian.module.my.network.response.AboutObj;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

public class AboutYuJianActivity extends BaseActivity {
    @BindView(R.id.iv_about_icon)
    ImageView iv_about_icon;
    @BindView(R.id.tv_about_title)
    TextView tv_about_title;
    @BindView(R.id.tv_about_content)
    TextView tv_about_content;
    @BindView(R.id.iv_about_img)
    ImageView iv_about_img;

    @Override
    protected int getContentView() {
        setAppTitle("关于御见");
        return R.layout.about_yujian_act;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        showProgress();
        getData(1,false);
    }

    @Override
    protected void getData(int page,final boolean isLoad) {
        super.getData(page, isLoad);
        Map<String,String> map=new HashMap<String,String>();
        map.put("rnd",getRnd());
        map.put("sign",getSign(map));
        ApiRequest.about(map, new MyCallBack<AboutObj>(mContext,pl_load,pcfl) {
            @Override
            public void onSuccess(AboutObj obj, int errorCode, String msg) {
                GlideUtils.into(mContext,obj.getLogo(),iv_about_icon);
                GlideUtils.into(mContext,obj.getImg(),iv_about_img);
                tv_about_title.setText(obj.getTitle());
                tv_about_content.setText(obj.getContent());
            }
        });

    }

    @Override
    protected void onViewClick(View v) {

    }
}
