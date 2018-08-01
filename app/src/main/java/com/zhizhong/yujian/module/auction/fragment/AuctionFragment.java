package com.zhizhong.yujian.module.auction.fragment;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.androidtools.PhoneUtils;
import com.github.androidtools.inter.MyOnClickListener;
import com.github.baseclass.BaseDividerGridItem;
import com.github.rxbus.MyConsumer;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;
import com.zhizhong.yujian.IntentParam;
import com.zhizhong.yujian.R;
import com.zhizhong.yujian.base.BaseFragment;
import com.zhizhong.yujian.base.GlideLoader;
import com.zhizhong.yujian.base.GlideUtils;
import com.zhizhong.yujian.base.ImageSizeUtils;
import com.zhizhong.yujian.base.MyCallBack;
import com.zhizhong.yujian.module.auction.activity.AuctionDetailActivity;
import com.zhizhong.yujian.module.auction.adapter.PaiMaiGoodsAdapter;
import com.zhizhong.yujian.module.auction.event.CountdownEvent;
import com.zhizhong.yujian.module.auction.network.ApiRequest;
import com.zhizhong.yujian.module.auction.network.response.PaiMaiBannerObj;
import com.zhizhong.yujian.module.auction.network.response.PaiMaiGoodsObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class AuctionFragment extends BaseFragment {
    @BindView(R.id.app_right_iv)
    ImageView app_right_iv;

    @BindView(R.id.app_title)
    TextView app_title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.rv_tuijian_all)
    RecyclerView rv_tuijian_all;

    PaiMaiGoodsAdapter adapter;


    Banner bn_paimai_img_banner;
    TextView tv_paimai_lingyuanpai;
    ImageView iv_paimai_lingyuan;
    TextView tv_paimai_xingrentuijian;
    ImageView iv_paimai_xinren;
    TextView tv_paimai_lishipaipin;
    ImageView iv_paimai_lishi;
    LinearLayout ll_paimai_jijianjiepai_content;
    LinearLayout ll_paimai_jijianjiepai;

    @Override
    protected int getContentView() {
        return R.layout.auction_frag;
    }

    @Override
    protected void initRxBus() {
        super.initRxBus();
        getEvent(CountdownEvent.class, new MyConsumer<CountdownEvent>() {
            @Override
            public void onAccept(CountdownEvent event) {
                Map<String, String> map = new HashMap<String, String>();
                map.put("type", "0");
                map.put("pagesize", pagesize + "");
                map.put("page", "1");
                map.put("sign", getSign(map));
                ApiRequest.getAllPaiMai(map, new MyCallBack<List<PaiMaiGoodsObj>>(mContext ) {
                    @Override
                    public void onSuccess(List<PaiMaiGoodsObj> list, int errorCode, String msg) {
                        pageNum = 2;
                        adapter.setList(list, true);
                    }
                });
            }
        });
    }

    @Override
    protected void initView() {
        app_title.setText("拍卖");
        app_title.setTextColor(ContextCompat.getColor(mContext,R.color.white));
        toolbar.setBackgroundColor(ContextCompat.getColor(mContext,R.color.title_bg));

        pcfl.disableWhenHorizontalMove(true);

        app_right_iv.setImageResource(R.drawable.wo);
        app_right_iv.setVisibility(View.VISIBLE);

        View auctionIncludeView = getLayoutInflater().inflate(R.layout.auction_include, null);
        bn_paimai_img_banner = auctionIncludeView.findViewById(R.id.bn_paimai_img_banner);
        tv_paimai_lingyuanpai = auctionIncludeView.findViewById(R.id.tv_paimai_lingyuanpai);
        iv_paimai_lingyuan = auctionIncludeView.findViewById(R.id.iv_paimai_lingyuan);
        tv_paimai_xingrentuijian = auctionIncludeView.findViewById(R.id.tv_paimai_xingrentuijian);
        iv_paimai_xinren = auctionIncludeView.findViewById(R.id.iv_paimai_xinren);
        tv_paimai_lishipaipin = auctionIncludeView.findViewById(R.id.tv_paimai_lishipaipin);
        iv_paimai_lishi = auctionIncludeView.findViewById(R.id.iv_paimai_lishi);
        ll_paimai_jijianjiepai_content = auctionIncludeView.findViewById(R.id.ll_paimai_jijianjiepai_content);
        ll_paimai_jijianjiepai = auctionIncludeView.findViewById(R.id.ll_paimai_jijianjiepai);


        adapter = new PaiMaiGoodsAdapter(mContext, R.layout.paimai_all_item, pageSize);
        adapter.addHeaderView(auctionIncludeView);
        adapter.setOnLoadMoreListener(this);

        rv_tuijian_all.setLayoutManager(new GridLayoutManager(mContext, 2));
        rv_tuijian_all.addItemDecoration(new BaseDividerGridItem(mContext, PhoneUtils.dip2px(mContext, 10), R.color.white));
        rv_tuijian_all.setAdapter(adapter);


    }

    @Override
    protected void initData() {
        showProgress();
        getBanner();
        getTuiJian();
        getData(1, false);
    }

    @Override
    protected void getOtherData() {
        super.getOtherData();
        getBanner();
        getTuiJian();
    }

    private void getBanner() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("rnd", getRnd());
        map.put("sign", getSign(map));
        ApiRequest.getPaiMaiBanner(map, new MyCallBack<PaiMaiBannerObj>(mContext) {
            @Override
            public void onSuccess(final PaiMaiBannerObj obj, int errorCode, String msg) {
                List<String> list = new ArrayList<>();
                if (notEmpty(obj.getShuffling_list())) {
                    for (int i = 0; i < obj.getShuffling_list().size(); i++) {
                        list.add(obj.getShuffling_list().get(i).getImg_url());
                    }
                }
                bn_paimai_img_banner.setLayoutParams(ImageSizeUtils.getImageSizeLayoutParams(mContext));
                bn_paimai_img_banner.setImages(list);
                bn_paimai_img_banner.setImageLoader(new GlideLoader());
                bn_paimai_img_banner.setOnBannerListener(new OnBannerListener() {
                    @Override
                    public void OnBannerClick(int position) {
                        Intent intent = new Intent();
                        intent.putExtra(IntentParam.goodsId, obj.getShuffling_list().get(position).getGoods_id());
                        STActivity(intent, AuctionDetailActivity.class);
                    }
                });
                bn_paimai_img_banner.start();

                tv_paimai_lingyuanpai.setText(obj.getZero_title());
                GlideUtils.into(mContext, obj.getZero_img(), iv_paimai_lingyuan);

                tv_paimai_xingrentuijian.setText(obj.getNew_title());
                GlideUtils.into(mContext, obj.getNew_img(), iv_paimai_xinren);

                tv_paimai_lishipaipin.setText(obj.getItem_title());
                GlideUtils.into(mContext, obj.getItem_img(), iv_paimai_lishi);
            }
        });
    }

    private void getTuiJian() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("rnd", getRnd());
        map.put("sign", getSign(map));
        ApiRequest.getPaiMaiTuiJian(map, new MyCallBack<List<PaiMaiGoodsObj>>(mContext) {
            @Override
            public void onSuccess(List<PaiMaiGoodsObj> list, int errorCode, String msg) {
                ll_paimai_jijianjiepai.removeAllViews();
                if (notEmpty(list)) {
                    for (int i = 0; i < list.size(); i++) {

                        final PaiMaiGoodsObj paiMaiGoodsObj = list.get(i);

                        View jiePaiView = getLayoutInflater().inflate(R.layout.auction_jijiangjiepai_include, null);

                        View ll_auction_jiepai = jiePaiView.findViewById(R.id.ll_auction_jiepai);
                        LinearLayout.LayoutParams layoutParamsContent = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        layoutParamsContent.width = (PhoneUtils.getScreenWidth(mContext) - PhoneUtils.dip2px(mContext, 40)) / 3;
                        ll_auction_jiepai.setLayoutParams(layoutParamsContent);

                        ImageView iv_auction_jjkp = jiePaiView.findViewById(R.id.iv_auction_jjkp);
                        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        layoutParams.width = (PhoneUtils.getScreenWidth(mContext) - PhoneUtils.dip2px(mContext, 40)) / 3;
                        layoutParams.height = (PhoneUtils.getScreenWidth(mContext) - PhoneUtils.dip2px(mContext, 40)) / 3;
                        iv_auction_jjkp.setLayoutParams(layoutParams);
                        GlideUtils.into(mContext, paiMaiGoodsObj.getGoods_image(), iv_auction_jjkp);

                        TextView tv_auction_jjjp_chujia = jiePaiView.findViewById(R.id.tv_auction_jjjp_chujia);
                        TextView tv_auction_jjjp_name = jiePaiView.findViewById(R.id.tv_auction_jjjp_name);
                        TextView tv_auction_jjjp_price = jiePaiView.findViewById(R.id.tv_auction_jjjp_price);

                        tv_auction_jjjp_chujia.setText(paiMaiGoodsObj.getChujia_num() + "人出价");
                        tv_auction_jjjp_name.setText(paiMaiGoodsObj.getGoods_name());
                        tv_auction_jjjp_price.setText("当前:¥" + paiMaiGoodsObj.getDangqian_price().toString());


                        ll_paimai_jijianjiepai.addView(jiePaiView);

                        jiePaiView.setOnClickListener(new MyOnClickListener() {
                            @Override
                            protected void onNoDoubleClick(View view) {
                                Intent intent=new Intent();
                                intent.putExtra(IntentParam.goodsId,paiMaiGoodsObj.getGoods_id());
                                STActivity(intent,AuctionDetailActivity.class);
                            }
                        });

                    }

                    ll_paimai_jijianjiepai_content.setVisibility(View.VISIBLE);
                } else {
                    ll_paimai_jijianjiepai_content.setVisibility(View.GONE);
                }

            }
        });

    }

    @Override
    protected void getData(int page, final boolean isLoad) {
        super.getData(page, isLoad);
        Map<String, String> map = new HashMap<String, String>();
        map.put("type", "0");
        map.put("pagesize", pagesize + "");
        map.put("page", page + "");
        map.put("sign", getSign(map));
        ApiRequest.getAllPaiMai(map, new MyCallBack<List<PaiMaiGoodsObj>>(mContext, pl_load, pcfl) {
            @Override
            public void onSuccess(List<PaiMaiGoodsObj> list, int errorCode, String msg) {
                if (isLoad) {
                    pageNum++;
                    adapter.addList(list, true);
                } else {
                    pageNum = 2;
                    adapter.setList(list, true);
                }
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        if (bn_paimai_img_banner != null) {
            bn_paimai_img_banner.stopAutoPlay();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (bn_paimai_img_banner != null) {
            bn_paimai_img_banner.startAutoPlay();
        }
    }


    @OnClick({R.id.app_right_iv})
    public void onViewClick(View v) {

    }
}
