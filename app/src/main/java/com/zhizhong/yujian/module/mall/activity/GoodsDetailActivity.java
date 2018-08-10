package com.zhizhong.yujian.module.mall.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.TabLayout;
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

import com.github.androidtools.PhoneUtils;
import com.github.baseclass.BaseDividerGridItem;
import com.github.baseclass.adapter.MyRecyclerViewHolder;
import com.github.fastshape.MyTextView;
import com.github.rxbus.MyConsumer;
import com.github.rxbus.RxBus;
import com.google.gson.Gson;
import com.library.base.BaseObj;
import com.library.base.view.MyRecyclerView;
import com.zhizhong.yujian.IntentParam;
import com.zhizhong.yujian.R;
import com.zhizhong.yujian.adapter.GoodsAdapter;
import com.zhizhong.yujian.adapter.MyAdapter;
import com.zhizhong.yujian.base.BaseActivity;
import com.zhizhong.yujian.base.GlideUtils;
import com.zhizhong.yujian.base.ImageSizeUtils;
import com.zhizhong.yujian.base.MyCallBack;
import com.zhizhong.yujian.event.JoinShoppingCartEvent;
import com.zhizhong.yujian.module.mall.fragment.GoodsImageFragment;
import com.zhizhong.yujian.module.mall.fragment.GoodsVideoFragment;
import com.zhizhong.yujian.module.mall.network.ApiRequest;
import com.zhizhong.yujian.module.mall.network.response.GoodsDetailObj;
import com.zhizhong.yujian.module.mall.network.response.ShoppingCartObj;
import com.zhizhong.yujian.module.my.activity.LoginActivity;
import com.zhizhong.yujian.network.NetApiRequest;
import com.zhizhong.yujian.network.response.CollectObj;
import com.zhizhong.yujian.tools.TextViewUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class GoodsDetailActivity extends BaseActivity {


    @BindView(R.id.fl_goods_banner)
    FrameLayout fl_goods_banner;
    @BindView(R.id.tv_goods_detail_title)
    TextView tv_goods_detail_title;
    @BindView(R.id.tv_goods_detail_name)
    TextView tv_goods_detail_name;
    @BindView(R.id.tv_goods_detail_now_price)
    TextView tv_goods_detail_now_price;
    @BindView(R.id.tv_goods_detail_old_price)
    TextView tv_goods_detail_old_price;
    @BindView(R.id.tv_goods_detail_stock)
    TextView tv_goods_detail_stock;
    @BindView(R.id.tv_goods_detail_courier)
    TextView tv_goods_detail_courier;
    @BindView(R.id.tv_goods_detail_sales_volume)
    TextView tv_goods_detail_sales_volume;
    @BindView(R.id.tv_goods_detail_area)
    TextView tv_goods_detail_area;

    @BindView(R.id.tv_goods_detail_evaluation_num)
    TextView tv_goods_detail_evaluation_num;

    @BindView(R.id.ll_goods_detail_xiangqing)
    LinearLayout ll_goods_detail_xiangqing;

    @BindView(R.id.ll_goods_detail_tuijian)
    LinearLayout ll_goods_detail_tuijian;

    @BindView(R.id.rv_goods_evaluation)
    MyRecyclerView rv_goods_evaluation;
    @BindView(R.id.tv_goods_detail_look_evaluation)
    MyTextView tv_goods_detail_look_evaluation;
    @BindView(R.id.rv_goods_detail)
    RecyclerView rv_goods_detail;
    @BindView(R.id.rv_goods_img)
    RecyclerView rv_goods_img;
    @BindView(R.id.rv_goods_detail_tuijian)
    RecyclerView rv_goods_detail_tuijian;
    @BindView(R.id.tv_goods_detail_collection)
    MyTextView tv_goods_detail_collection;
    @BindView(R.id.tv_goods_shopping_num)
    MyTextView tv_goods_shopping_num;
    @BindView(R.id.ll_goods_detail_youhuiquan)
    LinearLayout ll_goods_detail_youhuiquan;
    @BindView(R.id.ll_goods_detail_evaluation)
    LinearLayout ll_goods_detail_evaluation;

    @BindView(R.id.ll_goods_banner_type)
    LinearLayout ll_goods_banner_type;

    @BindView(R.id.tv_goods_banner_video)
    MyTextView tv_goods_banner_video;

    @BindView(R.id.tv_goods_banner_image)
    MyTextView tv_goods_banner_image;

    @BindView(R.id.rl_goods_detail_title)
    RelativeLayout rl_goods_detail_title;

    @BindView(R.id.tb_goods_detail)
    TabLayout tb_goods_detail;

    TextView xiangQing,pingJia,tuiJian;

    MyAdapter evaluationAdapter;
    MyAdapter goodsDetailAdapter;
    MyAdapter goodsImageAdapter;

    GoodsAdapter goodsAdapter;

    GoodsDetailObj goodsDetailObj;

    private String goodsId;
    private GoodsImageFragment goodsImageFragment;
    private GoodsVideoFragment goodsVideoFragment;

    @Override
    protected int getContentView() {
        return R.layout.goods_detail_act;
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void initView() {
        goodsId = getIntent().getStringExtra(IntentParam.goodsId);
        fl_goods_banner.setLayoutParams(ImageSizeUtils.getImageSizeLayoutParams(mContext,1,1));

        goodsAdapter=new GoodsAdapter(mContext,R.layout.tuijian_goods_item,pageSize);

        rv_goods_detail_tuijian.setLayoutManager(new GridLayoutManager(mContext,2));
        rv_goods_detail_tuijian.setNestedScrollingEnabled(false);
        rv_goods_detail_tuijian.addItemDecoration(new BaseDividerGridItem(mContext, PhoneUtils.dip2px(mContext,10),R.color.white));
        rv_goods_detail_tuijian.setAdapter(goodsAdapter);

        evaluationAdapter=new MyAdapter<GoodsDetailObj.EvaluationObj>(mContext,R.layout.goods_detail_evaluation_item,pageSize) {
            @Override
            public void bindData(MyRecyclerViewHolder holder, int position, GoodsDetailObj.EvaluationObj bean) {
                ImageView imageView = holder.getImageView(R.id.iv_goods_evaluation);
                GlideUtils.intoD(mContext,bean.getPhoto(),imageView,R.drawable.default_person);
                holder.setText(R.id.tv_goods_evaluation_name,bean.getNickname());
                holder.setText(R.id.tv_goods_evaluation_time,bean.getAdd_time());
                holder.setText(R.id.tv_goods_evaluation_content,bean.getContent());
            }
        };
        rv_goods_evaluation.setNestedScrollingEnabled(false);
        rv_goods_evaluation.setAdapter(evaluationAdapter);

        goodsDetailAdapter=new MyAdapter<GoodsDetailObj.ProductParameterListBean>(mContext,R.layout.goods_detail_item,pageSize) {
            @Override
            public void bindData(MyRecyclerViewHolder holder, int position, GoodsDetailObj.ProductParameterListBean bean) {
                if(position%2==0){
                    holder.itemView.setBackgroundColor(Color.parseColor("#FFF7F7F7"));
                }else{
                    holder.itemView.setBackgroundColor(Color.WHITE);
                }
                holder.setText(R.id.tv_goods_detail_flag,bean.getParameter_name());
                holder.setText(R.id.tv_goods_detail_flag_content,bean.getParameter_value());
            }
        };
        rv_goods_detail.setLayoutManager(new LinearLayoutManager(mContext));
        rv_goods_detail.setNestedScrollingEnabled(false);
        rv_goods_detail.addItemDecoration(getItemDivider(0));
        rv_goods_detail.setAdapter(goodsDetailAdapter);



        goodsImageAdapter=new MyAdapter<String>(mContext,R.layout._item,pageSize) {
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
        rv_goods_img.setLayoutManager(new LinearLayoutManager(mContext));
        rv_goods_img.setNestedScrollingEnabled(false);
        rv_goods_img.addItemDecoration(getItemDivider(0));
        rv_goods_img.setAdapter(goodsImageAdapter);




        final List<View> viewList=new ArrayList<>();
        viewList.add(tv_goods_detail_evaluation_num);
        viewList.add(ll_goods_detail_xiangqing);
        viewList.add(ll_goods_detail_tuijian);



        pingJia = new TextView(mContext);
        pingJia.setHeight(PhoneUtils.dip2px(mContext,40));

        xiangQing = new TextView(mContext);
        xiangQing.setHeight(PhoneUtils.dip2px(mContext,40));

        tuiJian = new TextView(mContext);
        tuiJian.setHeight(PhoneUtils.dip2px(mContext,40));

        pingJia.setGravity(Gravity.CENTER);
        pingJia.setTextColor(getColorStateList());
        pingJia.setText("评价");
        pingJia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pingJia.setSelected(true);
                xiangQing.setSelected(false);
                tuiJian.setSelected(false);
                tb_goods_detail.getTabAt(0).select();
                nsv.scrollTo(0, tv_goods_detail_evaluation_num.getTop());
            }
        });

        tb_goods_detail.addTab(tb_goods_detail.newTab().setCustomView(pingJia));

        xiangQing.setGravity(Gravity.CENTER);
        xiangQing.setTextColor(getColorStateList());
        xiangQing.setText("详情");
        xiangQing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pingJia.setSelected(false);
                xiangQing.setSelected(true);
                tuiJian.setSelected(false);
                tb_goods_detail.getTabAt(1).select();
                nsv.scrollTo(0, ll_goods_detail_xiangqing.getTop());
            }
        });

        tb_goods_detail.addTab(tb_goods_detail.newTab().setCustomView(xiangQing));



        tuiJian.setGravity(Gravity.CENTER);
        tuiJian.setTextColor(getColorStateList());
        tuiJian.setText("推荐");
        tuiJian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xiangQing.setSelected(false);
                pingJia.setSelected(false);
                tuiJian.setSelected(true);
                tb_goods_detail.getTabAt(2).select();
                nsv.scrollTo(0, ll_goods_detail_tuijian.getTop());
            }
        });

        tb_goods_detail.addTab(tb_goods_detail.newTab().setCustomView(tuiJian));

        /*addTab(xiangQing,"详情",0);
        addTab(pingJia,"评价",1);
        addTab(tuiJian,"推荐",2);*/


        rl_goods_detail_title.getBackground().mutate().setAlpha(0);
        nsv.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            int screenWidth = PhoneUtils.getScreenWidth(mContext)*2/3;
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY >= 0 && scrollY <= screenWidth) {
                    double alpha = (double) scrollY / screenWidth;
                    rl_goods_detail_title.getBackground().mutate().setAlpha((int) (alpha * 255));
                    tb_goods_detail.setVisibility(View.GONE);
                    tv_goods_detail_title.setVisibility(View.GONE);
                } else {
                    rl_goods_detail_title.getBackground().mutate().setAlpha(255);
                    tb_goods_detail.setVisibility(View.VISIBLE);
                    tv_goods_detail_title.setVisibility(View.VISIBLE);
                }

                if (isEmpty(viewList)) {
                    return;
                }
                for (int i = 0; i < viewList.size(); i++) {
                    if (keJian(viewList.get(i))) {
                        switch (i){
                            case 0:
                                pingJia.setSelected(true);
                                xiangQing.setSelected(false);
                                tuiJian.setSelected(false);
                                tb_goods_detail.getTabAt(0).select();
                                break;
                            case 1:
                                pingJia.setSelected(false);
                                xiangQing.setSelected(true);
                                tuiJian.setSelected(false);
                                tb_goods_detail.getTabAt(1).select();
                                break;
                            case 2:
                                xiangQing.setSelected(false);
                                pingJia.setSelected(false);
                                tuiJian.setSelected(true);
                                tb_goods_detail.getTabAt(2).select();
                                break;
                        }
                    }
                }
            }
        });


    }
    private ColorStateList getColorStateList(){
        int[] colors = new int[] {ContextCompat.getColor(mContext,R.color.white) , ContextCompat.getColor(mContext,R.color.gray_99)};
        int[][] states = new int[2][];
        states[0] = new int[] { android.R.attr.state_selected };
        states[1] = new int[] { };
        ColorStateList stateList=new ColorStateList(states,colors);
        return stateList;
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
    @Override
    protected void initData() {
        showProgress();
        getShoppingNum();
        getData(1, false);
    }
    private void getShoppingNum() {
        if(noLogin()){
            tv_goods_shopping_num.setVisibility(View.GONE);
            return;
        }
        Map<String,String>map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("sign",getSign(map));
        NetApiRequest.getShoppingNum(map, new MyCallBack<BaseObj>(mContext) {
            @Override
            public void onSuccess(BaseObj obj, int errorCode, String msg) {
                if(obj.getShoppingCartCount()>0){
                    tv_goods_shopping_num.setText(obj.getShoppingCartCount()+"");
                    tv_goods_shopping_num.setVisibility(View.VISIBLE);
                }else{
                    tv_goods_shopping_num.setVisibility(View.GONE);
                }
            }
        });
    }
    @Override
    protected void getData(int page, boolean isLoad) {
        super.getData(page, isLoad);
        final Map<String, String> map = new HashMap<String, String>();
        map.put("user_id", getUserId());
        map.put("goods_id", goodsId);
        map.put("sign", getSign(map));
        ApiRequest.getGoodsDetail(map, new MyCallBack<GoodsDetailObj>(mContext, pl_load, pcfl) {
            @Override
            public void onSuccess(GoodsDetailObj obj, int errorCode, String msg) {
                goodsDetailObj=obj;
                setBanner(obj);

                if(isEmpty(obj.getPingjia_list())){
                    ll_goods_detail_evaluation.setVisibility(View.GONE);
                }else{
                    ll_goods_detail_evaluation.setVisibility(View.VISIBLE);
                }
                tv_goods_detail_name.setText(obj.getGoods_name());
                tv_goods_detail_title.setText("¥"+obj.getGoods_price());
                tv_goods_detail_now_price.setText("¥"+obj.getGoods_price());
                tv_goods_detail_old_price.setText("¥"+obj.getOriginal_price());
                TextViewUtils.underline(tv_goods_detail_old_price);

                tv_goods_detail_stock.setText(obj.getStock()+"");
                tv_goods_detail_courier.setText("快递"+obj.getCourier());
                tv_goods_detail_sales_volume.setText(""+obj.getSales_volume());
                tv_goods_detail_area.setText(obj.getAddresss());
                tv_goods_detail_evaluation_num.setText("评价("+obj.getPingjia_num()+")");


                goodsAdapter.setList(obj.getTuijian_list(),true);
                evaluationAdapter.setList(obj.getPingjia_list(),true);
                goodsDetailAdapter.setList(obj.getProduct_parameter_list(),true);
                goodsImageAdapter.setList(obj.getGoods_details_list(),true);



                if(obj.getIs_collect()==1){
                    tv_goods_detail_collection.setCompoundDrawablesRelativeWithIntrinsicBounds(0,R.drawable.collection,0,0);
                }else{
                    tv_goods_detail_collection.setCompoundDrawablesRelativeWithIntrinsicBounds(0,R.drawable.collection_goods,0,0);
                }
            }
        });

    }

    private void setBanner(GoodsDetailObj obj) {

        ArrayList<String> list=new ArrayList<>();
        list.addAll(obj.getImg_list());

        goodsImageFragment = GoodsImageFragment.newInstance(list);
        addFragment(R.id.fl_goods_banner, goodsImageFragment);

        if(TextUtils.isEmpty(obj.getGoods_video())){
            ll_goods_banner_type.setVisibility(View.GONE);
        }else{
            goodsVideoFragment = GoodsVideoFragment.newInstance(obj.getGoods_image(), obj.getGoods_video());
            addFragment(R.id.fl_goods_banner, goodsVideoFragment);
            hideFragment(goodsImageFragment);
            ll_goods_banner_type.setVisibility(View.VISIBLE);
        }

    }

    @OnClick({R.id.tv_goods_banner_video,R.id.ll_goods_detail_youhuiquan,R.id.tv_goods_detail_look_evaluation,
            R.id.tv_goods_banner_image,R.id.tv_goods_detail_kefu, R.id.tv_goods_detail_collection, R.id.tv_goods_detail_join_shoppincart, R.id.tv_goods_detail_buy, R.id.iv_goods_back, R.id.fl_goods_shopping, R.id.iv_goods_share})
    public void onViewClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.tv_goods_detail_look_evaluation:
                intent=new Intent();
                intent.putExtra(IntentParam.goodsId,goodsId);
                STActivity(intent,GoodsEvaluationActivity.class);
                break;
            case R.id.tv_goods_banner_video:
                tv_goods_banner_video.setTextColor(ContextCompat.getColor(mContext,R.color.white));
                tv_goods_banner_video.getViewHelper().setSolidColor(ContextCompat.getColor(mContext,R.color.gray_66)).complete();

                tv_goods_banner_image.setTextColor(ContextCompat.getColor(mContext,R.color.gray_66));
                tv_goods_banner_image.getViewHelper().setSolidColor(ContextCompat.getColor(mContext,R.color.white)).complete();

                showFragment(goodsVideoFragment);
                hideFragment(goodsImageFragment);
                break;
            case R.id.tv_goods_banner_image:
                tv_goods_banner_image.setTextColor(ContextCompat.getColor(mContext,R.color.white));
                tv_goods_banner_image.getViewHelper().setSolidColor(ContextCompat.getColor(mContext,R.color.gray_66)).complete();

                tv_goods_banner_video.setTextColor(ContextCompat.getColor(mContext,R.color.gray_66));
                tv_goods_banner_video.getViewHelper().setSolidColor(ContextCompat.getColor(mContext,R.color.white)).complete();


                showFragment(goodsImageFragment);
                hideFragment(goodsVideoFragment);
                break;
            case R.id.tv_goods_detail_kefu:
                goHX();
                break;
            case R.id.tv_goods_detail_collection:
                if(noLogin()){
                    STActivity(LoginActivity.class);
                    return;
                }
                collect();
                break;
            case R.id.tv_goods_detail_join_shoppincart:
                if(noLogin()){
                    STActivity(LoginActivity.class);
                    return;
                }
                joinShoppinCart();
                break;
            case R.id.tv_goods_detail_buy:
                List<ShoppingCartObj.ShoppingCartListBean>goodsBeanList=new ArrayList<>();
                ShoppingCartObj.ShoppingCartListBean bean=new ShoppingCartObj.ShoppingCartListBean();
                bean.setNumber(1);
                bean.setStock(goodsDetailObj.getStock());
                bean.setGoods_id(goodsDetailObj.getGoods_id());
                bean.setId("0");
                bean.setPrice(goodsDetailObj.getGoods_price());
                bean.setGoods_name(goodsDetailObj.getGoods_name());
                bean.setGoods_image(goodsDetailObj.getGoods_image());
                goodsBeanList.add(bean);

                intent=new Intent();
                intent.putExtra(IntentParam.shoppingCart,new Gson().toJson(goodsBeanList));
                STActivity(intent,SureOrderActivity.class);
                break;
            case R.id.iv_goods_back:
                finish();
                break;
            case R.id.fl_goods_shopping:
                if (noLogin()){
                    STActivity(LoginActivity.class);
                    return;
                }
                STActivity(ShoppingCartActivity.class);
                break;
            case R.id.ll_goods_detail_youhuiquan:
                STActivity(LingQuanZhongXinActivity.class);
                break;
            case R.id.iv_goods_share:
                showFenXiangDialog();
                break;
        }
    }

    private void joinShoppinCart() {
        showLoading();
        Map<String,String>map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("goods_id",goodsId);
        map.put("number","1");
        map.put("sign",getSign(map));
        NetApiRequest.addShoppingCart(map, new MyCallBack<BaseObj>(mContext) {
            @Override
            public void onSuccess(BaseObj obj, int errorCode, String msg) {
                showMsg(msg);
                getShoppingNum();
                RxBus.getInstance().postReplay(new JoinShoppingCartEvent());
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
                    tv_goods_detail_collection.setCompoundDrawablesRelativeWithIntrinsicBounds(0,R.drawable.collection,0,0);
                }else{
                    tv_goods_detail_collection.setCompoundDrawablesRelativeWithIntrinsicBounds(0,R.drawable.collection_goods,0,0);
                }
            }
        });

    }
}
