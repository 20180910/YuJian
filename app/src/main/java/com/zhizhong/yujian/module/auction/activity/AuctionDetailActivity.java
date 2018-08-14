package com.zhizhong.yujian.module.auction.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.androidtools.AndroidUtils;
import com.github.androidtools.DateUtils;
import com.github.androidtools.PhoneUtils;
import com.github.androidtools.inter.MyOnClickListener;
import com.github.baseclass.BaseDividerGridItem;
import com.github.baseclass.adapter.MyRecyclerViewHolder;
import com.github.fastshape.MyTextView;
import com.github.mydialog.MySimpleDialog;
import com.github.rxbus.RxBus;
import com.library.base.BaseObj;
import com.library.base.view.MyRecyclerView;
import com.zhizhong.yujian.IntentParam;
import com.zhizhong.yujian.R;
import com.zhizhong.yujian.adapter.MyAdapter;
import com.zhizhong.yujian.base.BaseActivity;
import com.zhizhong.yujian.base.GlideUtils;
import com.zhizhong.yujian.base.ImageSizeUtils;
import com.zhizhong.yujian.base.MyCallBack;
import com.zhizhong.yujian.module.auction.adapter.PaiMaiGoodsAdapter;
import com.zhizhong.yujian.module.auction.event.CountdownEvent;
import com.zhizhong.yujian.module.auction.fragment.ChuJiaActivity;
import com.zhizhong.yujian.module.auction.network.ApiRequest;
import com.zhizhong.yujian.module.auction.network.response.BaoZhengJinObj;
import com.zhizhong.yujian.module.auction.network.response.ChuJiaObj;
import com.zhizhong.yujian.module.auction.network.response.PaiMaiGoodsDetailObj;
import com.zhizhong.yujian.module.mall.fragment.GoodsImageFragment;
import com.zhizhong.yujian.module.mall.fragment.GoodsVideoFragment;
import com.zhizhong.yujian.module.my.activity.LoginActivity;
import com.zhizhong.yujian.network.NetApiRequest;
import com.zhizhong.yujian.network.response.CollectObj;
import com.zhizhong.yujian.tools.DateFormatUtils;

import org.reactivestreams.Subscription;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

import static com.github.androidtools.DateUtils.ymdhms;

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


    private GoodsImageFragment goodsImageFragment;
    private GoodsVideoFragment goodsVideoFragment;
    private BigDecimal raisePrice;
    BigDecimal chuJiaResult;


    private long endTime;
    private long beginTime;
    private MySimpleDialog chuJiaDialog;
    private int baozhengjin;

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


        chuJiaAdapter =new MyAdapter<ChuJiaObj>(mContext,R.layout.paimai_chujia_item,pageSize) {
            @Override
            public void bindData(MyRecyclerViewHolder holder, int position, ChuJiaObj bean) {
                ImageView imageView = holder.getImageView(R.id.iv_chujia_person);
                GlideUtils.into(mContext,bean.getPhoto(),imageView);
                holder.setText(R.id.tv_chujia_name,bean.getNickname());
                holder.setText(R.id.tv_chujia_price,"出价:¥"+bean.getPrice().toString());
                holder.setText(R.id.tv_chujia_time,bean.getAdd_time());


                MyTextView tv_chujia_flag = (MyTextView) holder.getView(R.id.tv_chujia_flag);
                if(position==0){
                    tv_chujia_flag.setText("领先");
                    tv_chujia_flag.getViewHelper().setSolidColor(ContextCompat.getColor(mContext,R.color.red)).complete();
                }else{
                    tv_chujia_flag.setText("出局");
                    tv_chujia_flag.getViewHelper().setSolidColor(ContextCompat.getColor(mContext,R.color.gray_99)).complete();
                }
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
                beginTime = obj.getBegin_time();
                endTime = obj.getEnd_time();
                baozhengjin = obj.getIs_baozhengjin();
                if(beginTime>new Date().getTime()){
                    tv_paimai_goods_detail_time.setText(DateUtils.dateToString(new Date(beginTime),ymdhms)+"开始");
                }else if(endTime<new Date().getTime()){
                    tv_paimai_goods_detail_time.setText("已结束");
                }else{
                    tv_paimai_goods_detail_time.setText(DateUtils.dateToString(new Date(endTime),ymdhms)+"结束");
                }
                if(obj.getType()==1){
                    tv_paimai_goods_detail_type.setVisibility(View.VISIBLE);
                    tv_paimai_goods_detail_name.setText(getResources().getString(R.string.kg)+obj.getGoods_name());
                }else{
                    tv_paimai_goods_detail_type.setVisibility(View.GONE);
                    tv_paimai_goods_detail_name.setText(obj.getGoods_name());
                }
                setBanner(obj);
                if(obj.getIs_collect()==1){
                    tv_paimai_goods_detail_collection.setCompoundDrawablesRelativeWithIntrinsicBounds(0,R.drawable.collection,0,0);
                }else{
                    tv_paimai_goods_detail_collection.setCompoundDrawablesRelativeWithIntrinsicBounds(0,R.drawable.collection_goods,0,0);
                }
                raisePrice = obj.getRaise_price();
                tv_paimai_goods_detail_jiajia.setText("加价幅度:"+obj.getRaise_price().toString());
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
    private void setBanner(PaiMaiGoodsDetailObj obj) {

        ArrayList<String> list=new ArrayList<>();
        list.addAll(obj.getImg_list());

        goodsImageFragment = GoodsImageFragment.newInstance(list);
        addFragment(R.id.fl_paimai_goods_detail_banner, goodsImageFragment);

        if(TextUtils.isEmpty(obj.getGoods_video())){
            ll_paimai_goods_detail_banner_type.setVisibility(View.GONE);
        }else{
            goodsVideoFragment = GoodsVideoFragment.newInstance(obj.getGoods_image(), obj.getGoods_video());
            addFragment(R.id.fl_paimai_goods_detail_banner, goodsVideoFragment);
            hideFragment(goodsImageFragment);
            ll_paimai_goods_detail_banner_type.setVisibility(View.VISIBLE);
        }

    }
    @OnClick({R.id.iv_paimai_goods_share,R.id.iv_paimai_goods_back,R.id.tv_paimai_goods_detail_banner_video,R.id.tv_paimai_goods_detail_banner_image,R.id.ll_paimai_goods_detail_more_chujia,R.id.tv_paimai_goods_detail_kefu, R.id.tv_paimai_goods_detail_collection, R.id.ll_paimai_goods_detail_tixing, R.id.ll_paimai_goods_detail_chujia})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.iv_paimai_goods_back:
                finish();
                break;
            case R.id.iv_paimai_goods_share:
                showFenXiangDialog();
                break;
            case R.id.tv_paimai_goods_detail_banner_video:
                tv_paimai_goods_detail_banner_video.setTextColor(ContextCompat.getColor(mContext,R.color.white));
                tv_paimai_goods_detail_banner_video.getViewHelper().setSolidColor(ContextCompat.getColor(mContext,R.color.gray_66)).complete();

                tv_paimai_goods_detail_banner_video.setTextColor(ContextCompat.getColor(mContext,R.color.gray_66));
                tv_paimai_goods_detail_banner_video.getViewHelper().setSolidColor(ContextCompat.getColor(mContext,R.color.white)).complete();

                showFragment(goodsVideoFragment);
                hideFragment(goodsImageFragment);
                break;
            case R.id.tv_paimai_goods_detail_banner_image:
                tv_paimai_goods_detail_banner_image.setTextColor(ContextCompat.getColor(mContext,R.color.white));
                tv_paimai_goods_detail_banner_image.getViewHelper().setSolidColor(ContextCompat.getColor(mContext,R.color.gray_66)).complete();

                tv_paimai_goods_detail_banner_video.setTextColor(ContextCompat.getColor(mContext,R.color.gray_66));
                tv_paimai_goods_detail_banner_video.getViewHelper().setSolidColor(ContextCompat.getColor(mContext,R.color.white)).complete();


                showFragment(goodsImageFragment);
                hideFragment(goodsVideoFragment);
                break;
            case R.id.ll_paimai_goods_detail_more_chujia:
                Intent intent=new Intent();
                intent.putExtra(IntentParam.goodsId,goodsId);
                STActivity(intent,ChuJiaActivity.class);
                break;
            case R.id.tv_paimai_goods_detail_kefu:
                goHX();
                break;
            case R.id.tv_paimai_goods_detail_collection:
                if(noLogin()){
                    STActivity(LoginActivity.class);
                    return;
                }
                collect();
                break;
            case R.id.ll_paimai_goods_detail_tixing:
                if(noLogin()){
                    STActivity(LoginActivity.class);
                    return;
                }
                tiXing();
                break;
            case R.id.ll_paimai_goods_detail_chujia:
                if(beginTime>new Date().getTime()){
                    showMsg("拍卖未开始");
                    return;
                }else if(endTime<new Date().getTime()){
                    showMsg("拍卖已结束");
                    return;
                }else if(baozhengjin==0){
                    getBaoZhengJin();
                }else{
                    getChuJiaPrice();
                }
                break;
        }
    }

    private void getBaoZhengJin() {
        showLoading();
        Map<String,String>map=new HashMap<String,String>();
        map.put("rnd",getRnd());
        map.put("sign",getSign(map));
        ApiRequest.getBaoZhengJin(map, new MyCallBack<BaoZhengJinObj>(mContext) {
            @Override
            public void onSuccess(BaoZhengJinObj obj, int errorCode, String msg) {
                payBaoZhengJin(obj.getCash_deposit());
            }
        });

    }

    private void payBaoZhengJin(final BigDecimal cash_deposit) {
        View jiaonaView = getLayoutInflater().inflate(R.layout.jiaona_tishi_popu, null);
        final MySimpleDialog dialog = new MySimpleDialog(mContext);
        dialog.setGravity(Gravity.CENTER);
        dialog.setContentView(jiaonaView);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        jiaonaView.findViewById(R.id.iv_baozhengjin_close).setOnClickListener(new MyOnClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
                dialog.dismiss();
            }
        });
        TextView tv_baozhengjin_tishi = jiaonaView.findViewById(R.id.tv_baozhengjin_tishi);
        tv_baozhengjin_tishi.setText("请先缴纳"+cash_deposit.toString()+"元保证金再出价,如果违约会自动扣除,如未违约拍卖结束后可自行提现");
        jiaonaView.findViewById(R.id.tv_baozhengjin_jiaona).setOnClickListener(new MyOnClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
                dialog.dismiss();
                if(noLogin()){
                    STActivity(LoginActivity.class);
                }else{
                    Intent intent=new Intent();
                    intent.putExtra(IntentParam.baoZhengJin,cash_deposit.toString());
                    STActivityForResult(intent,JiaoNaJinActivity.class,1000);
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            switch (requestCode){
                case 1000:
                    showLoading();
                    getData(1,false);
                break;
            }
        }
    }

    private void getChuJiaPrice() {
        showLoading();
        Map<String,String>map=new HashMap<String,String>();
        map.put("goods_id",goodsId);
        map.put("sign",getSign(map));
        ApiRequest.getChuJiaPrice(map, new MyCallBack<ChuJiaObj>(mContext) {
            @Override
            public void onSuccess(ChuJiaObj obj, int errorCode, String msg) {
                showChuJiaPopu(obj.getPrice());
            }
        });


    }
    private void showChuJiaPopu(final BigDecimal price) {
        chuJiaResult=price;
        View chuJiaView = getLayoutInflater().inflate(R.layout.paimai_chujia_popu, null);

        TextView tv_paimai_chujia_time = chuJiaView.findViewById(R.id.tv_paimai_chujia_time);
        TextView tv_paimai_chujia_jiajia = chuJiaView.findViewById(R.id.tv_paimai_chujia_jiajia);
        final TextView tv_paimai_chujia_price = chuJiaView.findViewById(R.id.tv_paimai_chujia_price);
        tv_paimai_chujia_time.setText(""+ DateFormatUtils.getXCTime(new Date().getTime(),endTime,true));
        daoJiShi(tv_paimai_chujia_time,beginTime,endTime);
        tv_paimai_chujia_jiajia.setText(raisePrice.toString()+"元");
        tv_paimai_chujia_price.setText(price.toString());


        chuJiaView.findViewById(R.id.iv_paimai_chujia_jian).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chuJiaResult = AndroidUtils.jianFa(chuJiaResult, raisePrice);
                if(chuJiaResult.doubleValue()>0){
                    tv_paimai_chujia_price.setText(chuJiaResult.toString());
                }
            }
        });
        chuJiaView.findViewById(R.id.iv_paimai_chujia_jia).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chuJiaResult = AndroidUtils.jiaFa(chuJiaResult, raisePrice);
                tv_paimai_chujia_price.setText(chuJiaResult.toString());
            }
        });
        chuJiaView.findViewById(R.id.tv_paimai_chujia_commit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chuJia(chuJiaResult);
            }
        });

        chuJiaDialog = new MySimpleDialog(mContext);
        chuJiaDialog.setGravity(Gravity.BOTTOM);
        chuJiaDialog.setFullWidth();
        chuJiaDialog.setContentView(chuJiaView);
        chuJiaDialog.setCanceledOnTouchOutside(true);
        chuJiaDialog.show();
    }

    private void chuJia(BigDecimal chuJiaResult) {
        showLoading();
        Map<String,String>map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("goods_id",goodsId);
        map.put("price",chuJiaResult.toString());
        map.put("sign",getSign(map));
        ApiRequest.chuJia(map, new MyCallBack<BaseObj>(mContext,true) {
            @Override
            public void onSuccess(BaseObj obj, int errorCode, String msg) {
                if(chuJiaDialog!=null){
                    chuJiaDialog.dismiss();
                }
                showMsg(msg);
                getData(1,false);
            }
        });

    }

    public void daoJiShi(final TextView textView, final long beginTime,final long endTime){
        Flowable.interval(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new FlowableSubscriber<Long>() {
                    @Override
                    public void onSubscribe(@NonNull Subscription s) {
                        s.request(Long.MAX_VALUE);
                    }
                    @Override
                    public void onNext(Long aLong) {
                            textView.setText(""+ DateFormatUtils.getXCTime(new Date().getTime(),endTime,true));
                            if(endTime<new Date().getTime()){
                                RxBus.getInstance().post(new CountdownEvent());
                            }
                    }
                    @Override
                    public void onError(Throwable t) {
                    }
                    @Override
                    public void onComplete() {

                    }
                });
    }


    private void tiXing() {
        showLoading();
        Map<String,String>map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("goods_id",goodsId);
        map.put("sign",getSign(map));
        ApiRequest.paiMaiDetailTiXing(map, new MyCallBack<BaseObj>(mContext) {
            @Override
            public void onSuccess(BaseObj obj, int errorCode, String msg) {
                showMsg(msg);
            }
        });
    }

    private void collect() {
        showLoading();
        Map<String,String>map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("goods_id",goodsId);
        map.put("sign",getSign(map));
        NetApiRequest.collectGoods(map, new MyCallBack<CollectObj>(mContext) {
            @Override
            public void onSuccess(CollectObj obj, int errorCode, String msg) {
                if(obj.getIs_collect()==1){
                    tv_paimai_goods_detail_collection.setCompoundDrawablesRelativeWithIntrinsicBounds(0,R.drawable.collection,0,0);
                }else{
                    tv_paimai_goods_detail_collection.setCompoundDrawablesRelativeWithIntrinsicBounds(0,R.drawable.collection_goods,0,0);
                }
            }
        });

    }
}
