package com.zhizhong.yujian.module.mall.fragment;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.github.androidtools.PhoneUtils;
import com.github.androidtools.inter.MyOnClickListener;
import com.github.baseclass.BaseDividerGridItem;
import com.github.baseclass.adapter.MyRecyclerViewHolder;
import com.github.fastshape.MyTextView;
import com.github.rxbus.MyConsumer;
import com.library.base.BaseObj;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;
import com.zhizhong.yujian.IntentParam;
import com.zhizhong.yujian.R;
import com.zhizhong.yujian.adapter.GoodsAdapter;
import com.zhizhong.yujian.adapter.MyAdapter;
import com.zhizhong.yujian.base.BaseFragment;
import com.zhizhong.yujian.base.GlideLoader;
import com.zhizhong.yujian.base.GlideUtils;
import com.zhizhong.yujian.base.ImageSizeUtils;
import com.zhizhong.yujian.base.MyCallBack;
import com.zhizhong.yujian.event.JoinShoppingCartEvent;
import com.zhizhong.yujian.module.mall.activity.GoodsClassActivity;
import com.zhizhong.yujian.module.mall.activity.GoodsDetailActivity;
import com.zhizhong.yujian.module.mall.activity.NewScanActivity;
import com.zhizhong.yujian.module.mall.activity.ShoppingCartActivity;
import com.zhizhong.yujian.module.mall.network.ApiRequest;
import com.zhizhong.yujian.module.mall.network.response.MallGoodsObj;
import com.zhizhong.yujian.module.my.activity.LoginActivity;
import com.zhizhong.yujian.module.my.activity.MessageActivity;
import com.zhizhong.yujian.network.NetApiRequest;
import com.zhizhong.yujian.network.response.GoodsObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class MallFragment extends BaseFragment {
    @BindView(R.id.tv_mall_shopping_num)
    MyTextView tv_mall_shopping_num;
    /*@BindView(R.id.bn_mall)
    Banner bn_mall;
    @BindView(R.id.rv_mall_goods_type)
    RecyclerView rv_mall_goods_type;*/

    Banner bn_mall;
    RecyclerView rv_mall_goods_type;


    @BindView(R.id.rv_mall_tuijian)
    RecyclerView rv_mall_tuijian;

    MyAdapter goodsTypeAdapter;
    GoodsAdapter tuiJianAdapter;

    private List<String> bannerList = new ArrayList<String>();
    @Override
    protected int getContentView() {
        return R.layout.mall_frag;
    }

    @Override
    protected void initView() {
        pcfl.disableWhenHorizontalMove(true);


        View mallView = getLayoutInflater().inflate(R.layout.mall_include, null);
        bn_mall=mallView.findViewById(R.id.bn_mall);
        rv_mall_goods_type=mallView.findViewById(R.id.rv_mall_goods_type);


        goodsTypeAdapter=new MyAdapter<MallGoodsObj.GoodsTypeBean>(mContext,R.layout.mall_goods_type_item,pageSize) {
            @Override
            public void bindData(MyRecyclerViewHolder holder, int position, final MallGoodsObj.GoodsTypeBean bean) {
                holder.setText(R.id.tv_mall_goods_type_name,bean.getGoods_type_name());
                ImageView imageView = holder.getImageView(R.id.iv_mall_goods_type);
                GlideUtils.into(mContext,bean.getGoods_type_img(),imageView);

                holder.itemView.setOnClickListener(new MyOnClickListener() {
                    @Override
                    protected void onNoDoubleClick(View view) {
                        Intent intent=new Intent();
                        intent.putExtra(IntentParam.goodsTypeId,bean.getGoods_type_id());
                        intent.putExtra(IntentParam.title,bean.getGoods_type_name());
                        STActivity(intent,GoodsClassActivity.class);
                    }
                });
            }
        };
        rv_mall_goods_type.setLayoutManager(new GridLayoutManager(mContext,4));
        rv_mall_goods_type.setAdapter(goodsTypeAdapter);


        tuiJianAdapter=new GoodsAdapter(mContext,R.layout.tuijian_goods_item,pageSize);
        tuiJianAdapter.setOnLoadMoreListener(this);

        tuiJianAdapter.addHeaderView(mallView);

        rv_mall_tuijian.setLayoutManager(new GridLayoutManager(mContext,2));
        rv_mall_tuijian.addItemDecoration(new BaseDividerGridItem(mContext,PhoneUtils.dip2px(mContext,10),R.color.white));
        rv_mall_tuijian.setAdapter(tuiJianAdapter);
    }

    @Override
    protected void initData() {
        showProgress();
        getShoppingNum();
        getOtherData();
        getData(1,false);
    }

    @Override
    protected void initRxBus() {
        super.initRxBus();
        getEventReplay(JoinShoppingCartEvent.class, new MyConsumer<JoinShoppingCartEvent>() {
            @Override
            public void onAccept(JoinShoppingCartEvent event) {
                getShoppingNum();
            }
        });
    }

    private void getShoppingNum() {
        if(noLogin()){
            tv_mall_shopping_num.setVisibility(View.GONE);
            return;
        }
        Map<String,String>map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("sign",getSign(map));
        NetApiRequest.getShoppingNum(map, new MyCallBack<BaseObj>(mContext) {
            @Override
            public void onSuccess(BaseObj obj, int errorCode, String msg) {
                if(obj.getShoppingCartCount()>0){
                    tv_mall_shopping_num.setText(obj.getShoppingCartCount()+"");
                    tv_mall_shopping_num.setVisibility(View.VISIBLE);
                }else{
                    tv_mall_shopping_num.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    protected void getOtherData() {
        super.getOtherData();
        Map<String,String>map=new HashMap<String,String>();
        map.put("rnd",getUserId());
        map.put("sign",getSign(map));
        ApiRequest.getMallGoodsType(map, new MyCallBack<MallGoodsObj>(mContext) {
            @Override
            public void onSuccess(MallGoodsObj obj, int errorCode, String msg) {
                if(notEmpty(obj.getMall_shuffling_list())){
                    final List<MallGoodsObj.MallShufflingListBean> list = obj.getMall_shuffling_list();
                    for (int i = 0; i < list.size(); i++) {
                        bannerList.add(list.get(i).getImg_url());
                    }
                    bn_mall.setLayoutParams(ImageSizeUtils.getImageSizeLayoutParams(mContext));
                    bn_mall.setImages(bannerList);
                    bn_mall.setImageLoader(new GlideLoader());
                    bn_mall.setOnBannerListener(new OnBannerListener() {
                        @Override
                        public void OnBannerClick(int position) {
                            Intent intent=new Intent();
                            intent.putExtra(IntentParam.goodsId,list.get(position).getGoods_id());
                            STActivity(intent,GoodsDetailActivity.class);
                        }
                    });
                    bn_mall.start();
                }
                goodsTypeAdapter.setList(obj.getGoods_type(),true);
            }
        });

    }
    @Override
    protected void getData(int page,final boolean isLoad) {
        super.getData(page, isLoad);
        Map<String,String> map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("pagesize",pagesize+"");
        map.put("page",page+"");
        map.put("sign",getSign(map));
        ApiRequest.getMallTuiJian(map, new MyCallBack<List<GoodsObj>>(mContext,pl_load,pcfl) {
            @Override
            public void onSuccess(List<GoodsObj> list, int errorCode, String msg) {
                if(isLoad){
                    pageNum++;
                    tuiJianAdapter.addList(list,true);
                }else{
                    pageNum=2;
                    tuiJianAdapter.setList(list,true);
                }
            }
        });

    }
    @Override
    public void onStop() {
        super.onStop();
        if (bn_mall != null && bannerList != null) {
            bn_mall.stopAutoPlay();
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        if (bn_mall != null && bannerList != null) {
            bn_mall.startAutoPlay();
        }
    }
    @OnClick({R.id.iv_mall_scan, R.id.tv_mall_search, R.id.fl_mall_shopping, R.id.iv_mall_msg})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.iv_mall_scan:
                STActivity(NewScanActivity.class);
                break;
            case R.id.tv_mall_search:
                break;
            case R.id.fl_mall_shopping:
                if (noLogin()){
                    STActivity(LoginActivity.class);
                    return;
                }
                STActivity(ShoppingCartActivity.class);
                break;
            case R.id.iv_mall_msg:
                STActivity(MessageActivity.class);
                break;
        }
    }
}
