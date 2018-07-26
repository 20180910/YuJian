package com.zhizhong.yujian.module.mall.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.zhizhong.yujian.IntentParam;
import com.zhizhong.yujian.R;
import com.zhizhong.yujian.base.BaseFragment;
import com.zhizhong.yujian.base.GlideUtils;

import butterknife.BindView;

public class GoodsImageFragment extends BaseFragment {
    @BindView(R.id.iv_goods_img_banner)
    ImageView iv_goods_img_banner;
    @Override
    protected int getContentView() {
        return R.layout.goods_image_frag;
    }

    public static GoodsImageFragment newInstance(String imgUrl) {
        Bundle args = new Bundle();
        args.putString(IntentParam.imgUrl,imgUrl);
        GoodsImageFragment fragment = new GoodsImageFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected void initView() {
        GlideUtils.into(mContext,getArguments().getString(IntentParam.imgUrl),iv_goods_img_banner);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onViewClick(View v) {

    }
}
