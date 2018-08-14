package com.zhizhong.yujian.module.home.activity;

import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.library.base.view.richedit.TheEditor;
import com.zhizhong.yujian.IntentParam;
import com.zhizhong.yujian.R;
import com.zhizhong.yujian.base.BaseActivity;
import com.zhizhong.yujian.base.MyCallBack;
import com.zhizhong.yujian.module.home.network.ApiRequest;
import com.zhizhong.yujian.module.home.network.response.ZiXunDetailObj;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class ZiXunDetailActivity extends BaseActivity {

    @BindView(R.id.tv_zixundetail_detail)
    TextView tv_zixundetail_detail;
    @BindView(R.id.tv_zixundetail_author)
    TextView tv_zixundetail_author;
    @BindView(R.id.tv_zixundetail_time)
    TextView tv_zixundetail_time;
    @BindView(R.id.te_zixundetail_content)
    TheEditor te_zixundetail_content;

    private String id;

    @Override
    protected int getContentView() {
        setAppTitle("咨询详情");
        setAppRightImg(R.drawable.zixun_share);
        return R.layout.zixun_detail_act;
    }

    @Override
    protected void initView() {
        id = getIntent().getStringExtra(IntentParam.id);

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
        map.put("information_id", id);
        map.put("sign", getSign(map));
        ApiRequest.getZiXunDetail(map, new MyCallBack<ZiXunDetailObj>(mContext, pl_load, pcfl) {
            @Override
            public void onSuccess(ZiXunDetailObj obj, int errorCode, String msg) {
                tv_zixundetail_detail.setText(obj.getTitle());
                tv_zixundetail_author.setText("作者:"+obj.getAuthor());
                tv_zixundetail_time.setText("时间:"+obj.getAdd_time());
                te_zixundetail_content.setInputEnabled(false);
                te_zixundetail_content.setEditorFontSize(12);
                te_zixundetail_content.setHtml(obj.getContent());
                te_zixundetail_content.setEditorFontColor(ContextCompat.getColor(mContext,R.color.gray_99));
            }
        });
    }

    @OnClick({R.id.app_right_iv})
    protected void onViewClick(View v) {
        switch (v.getId()){
            case R.id.app_right_iv:
                showFenXiangDialog();
            break;
        }
    }
}
