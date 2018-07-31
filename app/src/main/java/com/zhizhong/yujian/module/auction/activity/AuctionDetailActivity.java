package com.zhizhong.yujian.module.auction.activity;

import android.graphics.Color;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.androidtools.PhoneUtils;
import com.github.baseclass.BaseDividerGridItem;
import com.github.baseclass.adapter.MyRecyclerViewHolder;
import com.github.fastshape.MyTextView;
import com.library.base.view.MyRecyclerView;
import com.zhizhong.yujian.IntentParam;
import com.zhizhong.yujian.R;
import com.zhizhong.yujian.adapter.MyAdapter;
import com.zhizhong.yujian.base.BaseActivity;
import com.zhizhong.yujian.base.GlideUtils;
import com.zhizhong.yujian.base.ImageSizeUtils;
import com.zhizhong.yujian.base.MyCallBack;
import com.zhizhong.yujian.module.auction.adapter.PaiMaiGoodsAdapter;
import com.zhizhong.yujian.module.auction.network.ApiRequest;
import com.zhizhong.yujian.module.auction.network.response.PaiMaiGoodsDetailObj;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class AuctionDetailActivity extends BaseActivity {
    @BindView(R.id.fl_paimai_goods_detail_banner)
    FrameLayout fl_paimai_goods_detail_banner;
    @BindView(R.id.tv_paimai_goods_detail_banner_video)
    MyTextView tv_paimai_goods_detail_banner_video;
    @BindView(R.id.tv_paimai_goods_detail_banner_image)
    MyTextView tv_paimai_goods_detail_banner_image;
    @BindView(R.id.ll_paimai_goods_detail_banner_type)
    LinearLayout ll_paimai_goods_detail_banner_type;
    @BindView(R.id.tv_paimai_goods_detail_time)
    TextView tv_paimai_goods_detail_time;
    @BindView(R.id.tv_paimai_goods_detail_type)
    MyTextView tv_paimai_goods_detail_type;
    @BindView(R.id.tv_paimai_goods_detail_name)
    TextView tv_paimai_goods_detail_name;
    @BindView(R.id.tv_paimai_goods_detail_jiajia)
    TextView tv_paimai_goods_detail_jiajia;
    @BindView(R.id.tv_paimai_goods_detail_now_price)
    TextView tv_paimai_goods_detail_now_price;
    @BindView(R.id.tv_paimai_goods_detail_weiguan)
    TextView tv_paimai_goods_detail_weiguan;
    @BindView(R.id.tv_paimai_goods_detail_tixing)
    TextView tv_paimai_goods_detail_tixing;
    @BindView(R.id.tv_paimai_goods_detail_chujia)
    TextView tv_paimai_goods_detail_chujia;
    @BindView(R.id.rv_paimai_goods_detail_chujia)
    MyRecyclerView rv_paimai_goods_detail_chujia;
    @BindView(R.id.ll_paimai_goods_detail_more_chujia)
    LinearLayout ll_paimai_goods_detail_more_chujia;
    @BindView(R.id.rv_paimai_goods_detail)
    RecyclerView rv_paimai_goods_detail;
    @BindView(R.id.rv_paimai_goods_detail_img)
    RecyclerView rv_paimai_goods_detail_img;
    @BindView(R.id.rv_paimai_goods_detail_tuijian)
    RecyclerView rv_paimai_goods_detail_tuijian;

    @BindView(R.id.tv_paimai_goods_detail_collection)
    MyTextView tv_paimai_goods_detail_collection;

    @BindView(R.id.tv_paimai_goods_detail_title)
    TextView tv_paimai_goods_detail_title;

    @BindView(R.id.rl_paimai_goods_detail_title)
    RelativeLayout rl_paimai_goods_detail_title;

    PaiMaiGoodsAdapter tuiJianAdapter;
    MyAdapter chuJiaAdapter;

    private String goodsId;
    private MyAdapter goodsDetailAdapter;
    private MyAdapter<String> goodsImageAdapter;

    @Override
    protected int getContentView() {
        return R.layout.auction_detail_act;
    }

    @Override
    protected void initView() {

        goodsId = getIntent().getStringExtra(IntentParam.goodsId);


        fl_paimai_goods_detail_banner.setLayoutParams(ImageSizeUtils.getImageSizeLayoutParams(mContext,1,1));
        rl_paimai_goods_detail_title.getBackground().mutate().setAlpha(0);

        nsv.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            int screenWidth = PhoneUtils.getScreenWidth(mContext)*2/3;
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY >= 0 && scrollY <= screenWidth) {
                    double alpha = (double) scrollY / screenWidth;
                    rl_paimai_goods_detail_title.getBackground().mutate().setAlpha((int) (alpha * 255));
                    tv_paimai_goods_detail_title.setVisibility(View.GONE);
                } else {
                    rl_paimai_goods_detail_title.getBackground().mutate().setAlpha(255);
                    tv_paimai_goods_detail_title.setVisibility(View.VISIBLE);
                }

            }
        });


        chuJiaAdapter =new MyAdapter(mContext,R.layout.paimai_chujia_item,pageSize) {
            @Override
            public void bindData(MyRecyclerViewHolder holder, int position, Object bean) {

            }
        };
        rv_paimai_goods_detail_chujia.setAdapter(chuJiaAdapter);





        goodsDetailAdapter =new MyAdapter<PaiMaiGoodsDetailObj.ProductParameterListBean>(mContext,R.layout.goods_detail_item,pageSize) {
            @Override
            public void bindData(MyRecyclerViewHolder holder, int position, PaiMaiGoodsDetailObj.ProductParameterListBean bean) {
                if(position%2==0){
                    holder.itemView.setBackgroundColor(Color.parseColor("#FFF7F7F7"));
                }else{
                    holder.itemView.setBackgroundColor(Color.WHITE);
                }
                holder.setText(R.id.tv_goods_detail_flag,bean.getParameter_name());
                holder.setText(R.id.tv_goods_detail_flag_content,bean.getParameter_value());
            }
        };
        rv_paimai_goods_detail.setLayoutManager(new LinearLayoutManager(mContext));
        rv_paimai_goods_detail.setNestedScrollingEnabled(false);
        rv_paimai_goods_detail.addItemDecoration(getItemDivider(0));
        rv_paimai_goods_detail.setAdapter(goodsDetailAdapter);


        goodsImageAdapter =new MyAdapter<String>(mContext,R.layout._item,pageSize) {
            @Override
            public MyRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                ImageView imageView=new ImageView(mContext);
                MyRecyclerViewHolder holder=new MyRecyclerViewHolder(mContext,imageView);
                return holder;
            }
            @Override
            public void bindData(MyRecyclerViewHolder holder, int position, String bean) {
                ImageView imageView= (ImageView) holder.itemView;
                GlideUtils.into(mContext,bean,imageView);
            }
        };
        rv_paimai_goods_detail_img.setLayoutManager(new LinearLayoutManager(mContext));
        rv_paimai_goods_detail_img.setNestedScrollingEnabled(false);
        rv_paimai_goods_detail_img.addItemDecoration(getItemDivider(0));
        rv_paimai_goods_detail_img.setAdapter(goodsImageAdapter);



        tuiJianAdapter=new PaiMaiGoodsAdapter(mContext,R.layout.paimai_all_item,pageSize);

        rv_paimai_goods_detail_tuijian.setLayoutManager(new GridLayoutManager(mContext, 2));
        rv_paimai_goods_detail_tuijian.setNestedScrollingEnabled(false);
        rv_paimai_goods_detail_tuijian.addItemDecoration(new BaseDividerGridItem(mContext, PhoneUtils.dip2px(mContext, 10), R.color.white));
        rv_paimai_goods_detail_tuijian.setAdapter(tuiJianAdapter);


    }

    @Override
    protected void initData() {
        showProgress();
        getData(1,false);
    }

    @Override
    protected void getData(int page, boolean isLoad) {
        super.getData(page, isLoad);
        Map<String,String> map=new HashMap<String,String>();
        map.put("goods_id",goodsId);
        map.put("user_id",getUserId());
        map.put("sign",getSign(map));
        ApiRequest.getPaiMaiGoodsDetail(map, new MyCallBack<PaiMaiGoodsDetailObj>(mContext,pl_load,pcfl) {
            @Override
            public void onSuccess(PaiMaiGoodsDetailObj obj, int errorCode, String msg) {
                tv_paimai_goods_detail_name.setText(getResources().getString(R.string.kg)+obj.getGoods_name());
                tv_paimai_goods_detail_jiajia.setText("加价幅度:"+obj.getRaise_price());
                tv_paimai_goods_detail_now_price.setText("¥"+obj.getClap_price().toString());
                tv_paimai_goods_detail_title.setText("¥"+obj.getClap_price().toString());
                tv_paimai_goods_detail_weiguan.setText("围观:"+obj.getSales_volume()+"人");
                tv_paimai_goods_detail_tixing.setText("提醒:"+obj.getTixing()+"人");
                tv_paimai_goods_detail_chujia.setText("出价:"+obj.getChujia_num()+"人");
                if(notEmpty(obj.getChujia_list())){
                    ll_paimai_goods_detail_more_chujia.setVisibility(View.VISIBLE);
                }else{
                    ll_paimai_goods_detail_more_chujia.setVisibility(View.GONE);
                }
                chuJiaAdapter.setList(obj.getChujia_list(),true);
                goodsDetailAdapter.setList(obj.getProduct_parameter_list(),true);
                goodsImageAdapter.setList(obj.getImg_list(),true);
                tuiJianAdapter.setList(obj.getTuijian_list(),true);





            }
        });
    }

    @OnClick({R.id.ll_paimai_goods_detail_more_chujia,R.id.tv_paimai_goods_detail_kefu, R.id.tv_paimai_goods_detail_collection, R.id.ll_paimai_goods_detail_tixing, R.id.ll_paimai_goods_detail_chujia})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.ll_paimai_goods_detail_more_chujia:
                break;
            case R.id.tv_paimai_goods_detail_kefu:
                break;
            case R.id.tv_paimai_goods_detail_collection:
                break;
            case R.id.ll_paimai_goods_detail_tixing:
                break;
            case R.id.ll_paimai_goods_detail_chujia:
                break;
        }
    }
}
