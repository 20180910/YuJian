package com.zhizhong.yujian.module.home.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.tencent.rtmp.ITXLivePlayListener;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXLivePlayer;
import com.tencent.rtmp.ui.TXCloudVideoView;
import com.zhizhong.yujian.IntentParam;
import com.zhizhong.yujian.R;
import com.zhizhong.yujian.base.BaseActivity;

import butterknife.BindView;

public class LiveRoomActivity extends BaseActivity {
    @BindView(R.id.video_view)
    TXCloudVideoView mView;
    private String liveAddress;
    private TXLivePlayer mLivePlayer;

    @Override
    protected int getContentView() {
        return R.layout.live_room_act;
    }

    @Override
    protected void initView() {
        liveAddress = getIntent().getStringExtra(IntentParam.liveAddress);
        String title = getIntent().getStringExtra(IntentParam.title);
        setAppTitle(title);
        if(TextUtils.isEmpty(liveAddress)){
            showMsg("该地址无法观看直播");
            finish();
        }
    }

    @Override
    protected void initData() {
        showProgress();
        //创建 player 对象
        mLivePlayer = new TXLivePlayer(this);
        //关键 player 对象与界面 view
        mLivePlayer.setPlayerView(mView);
        mLivePlayer.startPlay(liveAddress, TXLivePlayer.PLAY_TYPE_LIVE_FLV); //推荐 FLV
        mLivePlayer.setPlayListener(new ITXLivePlayListener() {
            @Override
            public void onPlayEvent(int event, Bundle bundle) {
                if (event == TXLiveConstants.PLAY_ERR_NET_DISCONNECT) {
                    showMsg("直播断连");
                    finish();
//                    roomListenerCallback.onDebugLog("[AnswerRoom] 拉流失败：网络断开");
//                    roomListenerCallback.onError(-1, "网络断开，拉流失败");
                }else if(event == TXLiveConstants.PLAY_EVT_PLAY_END){
                    showMsg("直播结束");
                    finish();
                }
            }
            @Override
            public void onNetStatus(Bundle bundle) {
            }
        });
        showContent();
    }

    @Override
    protected void onViewClick(View v) {

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mLivePlayer.stopPlay(true); // true 代表清除最后一帧画面
        mView.onDestroy();
    }
}
