package com.zhizhong.yujian.module.mall.fragment;

import android.os.Bundle;
import android.view.View;

import com.zhizhong.yujian.IntentParam;
import com.zhizhong.yujian.R;
import com.zhizhong.yujian.base.BaseFragment;
import com.zhizhong.yujian.base.GlideUtils;

import butterknife.BindView;
import cn.jzvd.JZVideoPlayerStandard;

public class GoodsVideoFragment extends BaseFragment {
    @BindView(R.id.videoplayer)
    JZVideoPlayerStandard videoplayer;
    @Override
    protected int getContentView() {
        return R.layout.goods_video_frag;
    }

    public static GoodsVideoFragment newInstance(String imgUrl,String videoUrl) {
        Bundle args = new Bundle();
        args.putString(IntentParam.imgUrl,imgUrl);
        args.putString(IntentParam.videoUrl,videoUrl);
        GoodsVideoFragment fragment = new GoodsVideoFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected void initView() {

        GlideUtils.intoD(mContext, getArguments().getString(IntentParam.imgUrl), videoplayer.thumbImageView, R.color.c_press);
        videoplayer.setUp(getArguments().getString(IntentParam.videoUrl), JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL,"");

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onViewClick(View v) {

    }
}
