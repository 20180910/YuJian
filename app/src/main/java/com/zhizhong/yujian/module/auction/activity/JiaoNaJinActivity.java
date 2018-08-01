package com.zhizhong.yujian.module.auction.activity;

import android.support.v4.content.ContextCompat;
import android.view.View;

import com.github.fastshape.MyTextView;
import com.library.base.view.richedit.TheEditor;
import com.zhizhong.yujian.IntentParam;
import com.zhizhong.yujian.R;
import com.zhizhong.yujian.base.BaseActivity;
import com.zhizhong.yujian.base.MyCallBack;
import com.zhizhong.yujian.module.auction.network.ApiRequest;
import com.zhizhong.yujian.module.auction.network.response.BaoZhengJinObj;
import com.zhizhong.yujian.view.MyEditText;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class JiaoNaJinActivity extends BaseActivity {

    @BindView(R.id.et_baozhengjin_money)
    MyEditText et_baozhengjin_money;
    @BindView(R.id.tv_baozhengjin_commit)
    MyTextView tv_baozhengjin_commit;
    @BindView(R.id.te_baozhengjin_content)
    TheEditor te_baozhengjin_content;
    private BigDecimal baoZhengJin;

    @Override
    protected int getContentView() {
        setAppTitle("缴纳保证金");
        return R.layout.jiaonajin_act;
    }

    @Override
    protected void initView() {
        baoZhengJin = (BigDecimal) getIntent().getSerializableExtra(IntentParam.baoZhengJin);
        et_baozhengjin_money.setText(baoZhengJin.toString());

        te_baozhengjin_content.setInputEnabled(false);
    }

    @Override
    protected void initData() {
        showProgress();
        getData(1, false);
    }

    @Override
    protected void getData(int page, boolean isLoad) {
        super.getData(page, isLoad);
        Map<String, String> map = new HashMap<String, String>();
        map.put("rnd", getRnd());
        map.put("sign", getSign(map));
        ApiRequest.getBaoZhengJinContent(map, new MyCallBack<BaoZhengJinObj>(mContext, pl_load, pcfl) {
            @Override
            public void onSuccess(BaoZhengJinObj obj, int errorCode, String msg) {
                te_baozhengjin_content.setHtml(obj.getContent());
                te_baozhengjin_content.setBackgroundColor(ContextCompat.getColor(mContext,R.color.transparent));
                te_baozhengjin_content.setEditorFontSize(13);
            }
        });

    }



    @OnClick({R.id.tv_baozhengjin_commit})
    public void onViewClick(View v) {
        switch (v.getId()){
            case R.id.tv_baozhengjin_commit:
                jiaoNa();
            break;
        }
    }

    private void jiaoNa() {

    }
}
