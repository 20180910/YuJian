package com.zhizhong.yujian.module.mall.activity;

import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.androidtools.PhoneUtils;
import com.github.baseclass.BaseDividerGridItem;
import com.github.baseclass.adapter.MyRecyclerViewHolder;
import com.github.fastshape.MyTextView;
import com.library.base.view.MyRecyclerView;
import com.zhizhong.yujian.IntentParam;
import com.zhizhong.yujian.R;
import com.zhizhong.yujian.adapter.GoodsAdapter;
import com.zhizhong.yujian.adapter.MyAdapter;
import com.zhizhong.yujian.base.BaseActivity;
import com.zhizhong.yujian.base.GlideUtils;
import com.zhizhong.yujian.base.MyCallBack;
import com.zhizhong.yujian.module.mall.network.ApiRequest;
import com.zhizhong.yujian.module.mall.network.response.GoodsDetailObj;
import com.zhizhong.yujian.tools.TextViewUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class GoodsDetailActivity extends BaseActivity {


    @BindView(R.id.vp_goods_detail)
    ViewPager vp_goods_detail;
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

    MyAdapter evaluationAdapter;
    MyAdapter goodsDetailAdapter;
    MyAdapter goodsImageAdapter;

    GoodsAdapter goodsAdapter;


    private String goodsId;

    @Override
    protected int getContentView() {
        return R.layout.goods_detail_act;
    }

    @Override
    protected void initView() {
        goodsId = getIntent().getStringExtra(IntentParam.goodsId);
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
                    holder.itemView.setBackgroundColor(Color.WHITE);
                }else{
                    holder.itemView.setBackgroundColor(Color.parseColor("#FFF7F7F7"));
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

    }

    @Override
    protected void initData() {
        showProgress();
        getData(1, false);
    }

    @Override
    protected void getData(int page, boolean isLoad) {
        super.getData(page, isLoad);
        Map<String, String> map = new HashMap<String, String>();
        map.put("user_id", getUserId());
        map.put("goods_id", goodsId);
        map.put("sign", getSign(map));
        ApiRequest.getGoodsDetail(map, new MyCallBack<GoodsDetailObj>(mContext, pl_load, pcfl) {
            @Override
            public void onSuccess(GoodsDetailObj obj, int errorCode, String msg) {
                if(isEmpty(obj.getPingjia_list())){
                    ll_goods_detail_evaluation.setVisibility(View.GONE);
                }else{
                    ll_goods_detail_evaluation.setVisibility(View.VISIBLE);
                }
                tv_goods_detail_name.setText(obj.getGoods_name());
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

            }
        });

    }

    @Override
    protected void onViewClick(View v) {

    }

    @OnClick({R.id.tv_goods_detail_kefu, R.id.tv_goods_detail_collection, R.id.tv_goods_detail_join_shoppincart, R.id.tv_goods_detail_buy, R.id.iv_goods_back, R.id.fl_goods_shopping, R.id.iv_goods_share})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_goods_detail_kefu:
                break;
            case R.id.tv_goods_detail_collection:
                break;
            case R.id.tv_goods_detail_join_shoppincart:
                break;
            case R.id.tv_goods_detail_buy:
                break;
            case R.id.iv_goods_back:
                break;
            case R.id.fl_goods_shopping:
                break;
            case R.id.iv_goods_share:
                break;
        }
    }
}
