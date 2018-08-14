package com.zhizhong.yujian.module.home.fragment;

import android.text.TextUtils;
import android.view.View;

import com.tencent.rtmp.TXLivePlayer;
import com.tencent.rtmp.ui.TXCloudVideoView;
import com.zhizhong.yujian.R;
import com.zhizhong.yujian.base.BaseActivity;
import com.zhizhong.yujian.view.MyEditText;

import butterknife.BindView;
import butterknife.OnClick;

public class TestAct extends BaseActivity {
    @BindView(R.id.et_address_url)
    MyEditText et_address_url;

    @BindView(R.id.video_view)
    TXCloudVideoView video_view;
    private TXLivePlayer mLivePlayer;

    @Override
    protected int getContentView() {
        return R.layout.test_act;
    }

    @Override
    protected void initView() {
        mLivePlayer = new TXLivePlayer(this);
        //关键 player 对象与界面 view
        mLivePlayer.setPlayerView(video_view);
    }

    @Override
    protected void initData() {
    }

    @OnClick({R.id.tv_start})
    protected void onViewClick(View v) {
        switch (v.getId()){
            case R.id.tv_start:
                if(TextUtils.isEmpty(getSStr(et_address_url))){
                    showMsg("请输入播放地址");
                    return;
                }
                String flvUrl = getSStr(et_address_url);//"http://2157.liveplay.myqcloud.com/live/2157_xxxx.flv";
                mLivePlayer.startPlay(flvUrl, TXLivePlayer.PLAY_TYPE_LIVE_FLV); //推荐 FLV
            break;
        }
    }
}
