package com.zhizhong.yujian.module.mall.fragment;

import android.os.Bundle;
import android.view.View;

import com.youth.banner.Banner;
import com.zhizhong.yujian.IntentParam;
import com.zhizhong.yujian.R;
import com.zhizhong.yujian.base.BaseFragment;
import com.zhizhong.yujian.base.GlideLoader;
import com.zhizhong.yujian.base.ImageSizeUtils;

import java.util.ArrayList;

import butterknife.BindView;

public class GoodsImageFragment extends BaseFragment {
    @BindView(R.id.bn_goods_img_banner)
    Banner bn_goods_img_banner;


    @Override
    protected int getContentView() {
        return R.layout.goods_image_frag;
    }

    public static GoodsImageFragment newInstance(ArrayList<String> bannerList) {
        Bundle args = new Bundle();
        args.putStringArrayList(IntentParam.imgUrl,bannerList);
        GoodsImageFragment fragment = new GoodsImageFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected void initView() {

        bn_goods_img_banner.setLayoutParams(ImageSizeUtils.getImageSizeLayoutParams(mContext,1,1));
        bn_goods_img_banner.setImages(getArguments().getStringArrayList(IntentParam.imgUrl));
        bn_goods_img_banner.setImageLoader(new GlideLoader());
        bn_goods_img_banner.start();
    }

    @Override
    protected void initData() {

    }
    @Override
    public void onStop() {
        super.onStop();
        if (bn_goods_img_banner != null && getArguments().getStringArrayList(IntentParam.imgUrl) != null) {
            bn_goods_img_banner.stopAutoPlay();
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        if (bn_goods_img_banner != null && getArguments().getStringArrayList(IntentParam.imgUrl) != null) {
            bn_goods_img_banner.startAutoPlay();
        }
    }

    @Override
    protected void onViewClick(View v) {

    }
}
