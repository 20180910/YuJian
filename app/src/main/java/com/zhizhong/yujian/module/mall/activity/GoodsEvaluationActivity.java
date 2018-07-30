package com.zhizhong.yujian.module.mall.activity;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.github.androidtools.PhoneUtils;
import com.github.baseclass.adapter.MyRecyclerViewHolder;
import com.github.fastshape.MyTextView;
import com.zhizhong.yujian.IntentParam;
import com.zhizhong.yujian.R;
import com.zhizhong.yujian.adapter.MyAdapter;
import com.zhizhong.yujian.base.BaseActivity;
import com.zhizhong.yujian.base.GlideUtils;
import com.zhizhong.yujian.base.MyCallBack;
import com.zhizhong.yujian.module.mall.network.ApiRequest;
import com.zhizhong.yujian.module.mall.network.response.GoodsEvaluationNumObj;
import com.zhizhong.yujian.module.mall.network.response.GoodsEvaluationObj;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class GoodsEvaluationActivity extends BaseActivity {
    @BindView(R.id.fl_goods_evaluation_num)
    LinearLayout fl_goods_evaluation_num;

    @BindView(R.id.rv_goods_evaluation)
    RecyclerView rv_goods_evaluation;

    MyAdapter adapter;

    String evaluationType="0";
    private String goodsId;


    MyTextView selectView;

    @Override
    protected int getContentView() {
        setAppTitle("评价");
        return R.layout.goods_evaluation_act;
    }

    @Override
    protected void initView() {
        goodsId = getIntent().getStringExtra(IntentParam.goodsId);

        adapter=new MyAdapter<GoodsEvaluationObj>(mContext,R.layout.goods_evaluation_item,pageSize) {
            @Override
            public void bindData(MyRecyclerViewHolder holder, int position, GoodsEvaluationObj bean) {
                ImageView imageView = holder.getImageView(R.id.iv_goods_allevaluation);
                GlideUtils.into(mContext,bean.getPhoto(),imageView);

                holder.setText(R.id.tv_goods_allevaluation_name,bean.getNickname());
                holder.setText(R.id.tv_goods_allevaluation_date,bean.getAdd_time());
                holder.setText(R.id.tv_goods_allevaluation_content,bean.getContent());
                LinearLayout ll_goods_allevaluation_img = (LinearLayout) holder.getView(R.id.ll_goods_allevaluation_img);
                ll_goods_allevaluation_img.removeAllViews();
                List<String> img_list = bean.getImg_list();
                if(notEmpty(img_list)){
                    for (int i = 0; i < img_list.size(); i++) {
                        ImageView evaluationImg=new ImageView(mContext);
                        evaluationImg.setBackgroundColor(ContextCompat.getColor(mContext,R.color.c_press));
                        evaluationImg.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        layoutParams.width=(PhoneUtils.getScreenWidth(mContext)-PhoneUtils.dip2px(mContext,40))/3;
                        layoutParams.height=(PhoneUtils.getScreenWidth(mContext)-PhoneUtils.dip2px(mContext,40))/3;
                        layoutParams.setMargins(0,0,PhoneUtils.dip2px(mContext,10),0);
                        evaluationImg.setLayoutParams(layoutParams);
                        GlideUtils.into(mContext,img_list.get(i),evaluationImg);
                        ll_goods_allevaluation_img.addView(evaluationImg);
                    }
                }


                View ll_goods_allevaluation_again = holder.getView(R.id.ll_goods_allevaluation_again);
                List<GoodsEvaluationObj.AfterReviewBean> after_review = bean.getAfter_review();
                if(notEmpty(after_review)){
                    GoodsEvaluationObj.AfterReviewBean afterReviewBean = after_review.get(0);
                    ll_goods_allevaluation_again.setVisibility(View.VISIBLE);

                    holder.setText(R.id.tv_goods_allevaluation_again_content,afterReviewBean.getContent());
                    LinearLayout ll_goods_allevaluation_again_img = (LinearLayout) holder.getView(R.id.ll_goods_allevaluation_again_img);
                    ll_goods_allevaluation_again_img.removeAllViews();
                    List<String> againImgList = afterReviewBean.getImg_list();
                    if(notEmpty(againImgList)){
                        for (int i = 0; i < againImgList.size(); i++) {
                            ImageView evaluationImg=new ImageView(mContext);
                            evaluationImg.setBackgroundColor(ContextCompat.getColor(mContext,R.color.c_press));
                            evaluationImg.setScaleType(ImageView.ScaleType.FIT_CENTER);
                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            layoutParams.width=(PhoneUtils.getScreenWidth(mContext)-PhoneUtils.dip2px(mContext,40))/3;
                            layoutParams.height=(PhoneUtils.getScreenWidth(mContext)-PhoneUtils.dip2px(mContext,40))/3;
                            layoutParams.setMargins(0,0,PhoneUtils.dip2px(mContext,10),0);
                            evaluationImg.setLayoutParams(layoutParams);
                            GlideUtils.into(mContext,againImgList.get(i),evaluationImg);
                            ll_goods_allevaluation_again_img.addView(evaluationImg);
                        }
                    }
                }else{
                    ll_goods_allevaluation_again.setVisibility(View.GONE);
                }
            }
        };
        adapter.setOnLoadMoreListener(this);

        rv_goods_evaluation.setLayoutManager(new LinearLayoutManager(mContext));
        rv_goods_evaluation.addItemDecoration(getItemDivider());
        rv_goods_evaluation.setAdapter(adapter);

    }

    @Override
    protected void initData() {
        showProgress();
        getNum();
        getData(1,false);
    }

    private void getNum() {
        Map<String,String>map=new HashMap<String,String>();
        map.put("goods_id",goodsId);
        map.put("sign",getSign(map));
        ApiRequest.getGoodsAllEvaluationNum(map, new MyCallBack<GoodsEvaluationNumObj>(mContext) {
            private MyTextView chaPingView;
            private MyTextView zhongPingView;
            private MyTextView haoPingView;
            private MyTextView allPingLunView;
            @Override
            public void onSuccess(GoodsEvaluationNumObj obj, int errorCode, String msg) {
                fl_goods_evaluation_num.removeAllViews();
                allPingLunView = getTabView("全部");
                selectView=allPingLunView;
                haoPingView = getTabView("好评(" + obj.getHaopin() + ")");
                zhongPingView = getTabView("中评("+obj.getZhongpin()+")");
                chaPingView = getTabView("差评("+obj.getChapin()+")");

                fl_goods_evaluation_num.addView(allPingLunView);
                fl_goods_evaluation_num.addView(haoPingView);
                fl_goods_evaluation_num.addView(zhongPingView);
                fl_goods_evaluation_num.addView(chaPingView);
                allPingLunView.setOnClickListener(getL("0"));
                haoPingView.setOnClickListener(getL("1"));
                zhongPingView.setOnClickListener(getL("2"));
                chaPingView.setOnClickListener(getL("3"));
            }
            @NonNull
            private View.OnClickListener getL(final String type) {
                return new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(selectView==v){
                            return;
                        }
                        selectView.getViewHelper().setSolidColor(Color.parseColor("#FFEDB3B3")).complete();
                        switch (type){
                            case "0":
                                allPingLunView.getViewHelper().setSolidColor(ContextCompat.getColor(mContext,R.color.red)).complete();
                                selectView=allPingLunView;
                            break;
                            case "1":
                                haoPingView.getViewHelper().setSolidColor(ContextCompat.getColor(mContext,R.color.red)).complete();
                                selectView=haoPingView;
                            break;
                            case "2":
                                zhongPingView.getViewHelper().setSolidColor(ContextCompat.getColor(mContext,R.color.red)).complete();
                                selectView=zhongPingView;
                            break;
                            case "3":
                                chaPingView.getViewHelper().setSolidColor(ContextCompat.getColor(mContext,R.color.red)).complete();
                                selectView=chaPingView;
                            break;
                        }
                        evaluationType=type;
                        showLoading();
                        getData(1,false);
                    }
                };
            }
        });
    }

    public MyTextView getTabView(String text){
        MyTextView textView=new MyTextView(mContext);
        textView.setText(text);
        textView.setTextColor(ContextCompat.getColor(mContext,R.color.white));
        if("全部".equals(text)){
            textView.getViewHelper().setSolidColor(ContextCompat.getColor(mContext,R.color.red));
        }else{
            textView.getViewHelper().setSolidColor(Color.parseColor("#FFEDB3B3"));
        }
        textView.setPadding(PhoneUtils.dip2px(mContext,15),PhoneUtils.dip2px(mContext,4),PhoneUtils.dip2px(mContext,15),PhoneUtils.dip2px(mContext,4));
        textView.setTextSize(14);
        textView.getViewHelper().setRadius(PhoneUtils.dip2px(mContext,5));
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(PhoneUtils.dip2px(mContext,15),0,0,0);
        textView.setLayoutParams(layoutParams);
        textView.complete();
        return textView;
    }
    @Override
    protected void getData(int page,final boolean isLoad) {
        super.getData(page, isLoad);
        Map<String,String> map=new HashMap<String,String>();
        map.put("goods_id",goodsId);
        map.put("type",evaluationType);
        map.put("pagesize",pagesize+"");
        map.put("page",page+"");
        map.put("sign",getSign(map));
        ApiRequest.getGoodsAllEvaluation(map, new MyCallBack<List<GoodsEvaluationObj>>(mContext,pl_load,pcfl) {
            @Override
            public void onSuccess(List<GoodsEvaluationObj> list, int errorCode, String msg) {
                if(isLoad){
                    pageNum++;
                    adapter.addList(list,true);
                }else{
                    pageNum=2;
                    adapter.setList(list,true);
                }
            }
        });

    }

    @Override
    protected void onViewClick(View v) {

    }
}
