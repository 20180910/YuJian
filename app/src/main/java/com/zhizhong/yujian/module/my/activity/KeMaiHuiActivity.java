package com.zhizhong.yujian.module.my.activity;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.androidtools.PhoneUtils;
import com.github.androidtools.inter.MyOnClickListener;
import com.github.baseclass.adapter.MyBaseRecyclerAdapter;
import com.github.baseclass.adapter.MyRecyclerViewHolder;
import com.github.fastshape.MyTextView;
import com.github.mydialog.MySimpleDialog;
import com.library.base.BaseObj;
import com.library.base.view.MyRecyclerView;
import com.zhizhong.yujian.IntentParam;
import com.zhizhong.yujian.R;
import com.zhizhong.yujian.adapter.MyAdapter;
import com.zhizhong.yujian.base.BaseActivity;
import com.zhizhong.yujian.base.GlideUtils;
import com.zhizhong.yujian.base.MyCallBack;
import com.zhizhong.yujian.module.my.network.ApiRequest;
import com.zhizhong.yujian.module.my.network.response.KeMaiHuiObj;
import com.zhizhong.yujian.module.my.network.response.KuaiDiObj;
import com.zhizhong.yujian.view.MyEditText;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class KeMaiHuiActivity extends BaseActivity {
    @BindView(R.id.rv_kemaihui)
    RecyclerView rv_kemaihui;

    MyAdapter adapter;
    private KeMaiHuiObj keMaiHuiObj;
    private String expressNo;
    private String expressId;
    private String goodsId;
    private String goodsNo;

    @Override
    protected int getContentView() {
        setAppTitle("我的可卖回");
        return R.layout.kemaihui_act;
    }

    @Override
    protected void initView() {
        adapter=new MyAdapter<KeMaiHuiObj.List2Bean>(mContext,R.layout.kemaihui_item,pageSize) {
            @Override
            public void bindData(MyRecyclerViewHolder holder, final int position, final KeMaiHuiObj.List2Bean bean) {
                ImageView imageView = holder.getImageView(R.id.iv_kemaihui);
                GlideUtils.into(mContext,bean.getGoods_images(),imageView);

                holder.setText(R.id.tv_kemaihui_name,bean.getGoods_name());
                holder.setText(R.id.tv_kemaihui_buy_price,"买入价¥"+bean.getGoods_unitprice());
                holder.setText(R.id.tv_kemaihui_sales_price,"¥"+bean.getKemai_price());


                MyTextView tv_kemaihui_sales= (MyTextView) holder.getView(R.id.tv_kemaihui_sales);
                //状态(0一键卖回 1已发货 2已卖回)
                if(bean.getRansom_status()==0){
                    tv_kemaihui_sales.setText("一键卖回");
                    tv_kemaihui_sales.getViewHelper().setSolidColor(ContextCompat.getColor(mContext,R.color.red)).complete();
                    tv_kemaihui_sales.setVisibility(View.VISIBLE);
                    tv_kemaihui_sales.setOnClickListener(new MyOnClickListener() {
                        @Override
                        protected void onNoDoubleClick(View view) {
                            if(TextUtils.isEmpty(keMaiHuiObj.getAddress())){
                                showMsg("暂无寄回地址,请联系客服");
                            }else{
                                goodsId=bean.getGoods_id();
                                goodsNo=bean.getOrder_no();
                                jiChu();
                            }
                        }
                    });
                }else if(bean.getRansom_status()==1){
                    tv_kemaihui_sales.setText("已发货");
                    tv_kemaihui_sales.getViewHelper().setSolidColor(ContextCompat.getColor(mContext,R.color.gray_99)).complete();
                    tv_kemaihui_sales.setVisibility(View.VISIBLE);
                }else if(bean.getRansom_status()==2){
                    tv_kemaihui_sales.setText("已卖回");
                    tv_kemaihui_sales.getViewHelper().setSolidColor(ContextCompat.getColor(mContext,R.color.red)).complete();
                    tv_kemaihui_sales.setVisibility(View.VISIBLE);
                }else{
                    tv_kemaihui_sales.setVisibility(View.GONE);
                }

                holder.itemView.setOnClickListener(new MyOnClickListener() {
                    @Override
                    protected void onNoDoubleClick(View view) {
                        Intent intent=new Intent();
                        intent.putExtra(IntentParam.keMaiHui,keMaiHuiObj);
                        intent.putExtra(IntentParam.keMaiHuiGoods,bean);
                        STActivityForResult(intent,MyMaiHuiActivity.class,100);
                    }
                });
            }
        };
        adapter.setOnLoadMoreListener(this);

        rv_kemaihui.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        showProgress();
        getData(1,false);
    }

    @Override
    protected void getData(int page,final boolean isLoad) {
        super.getData(page, isLoad);
        Map<String,String> map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("pagesize",pagesize+"");
        map.put("page",page+"");
        map.put("sign",getSign(map));
        ApiRequest.keMaiHui(map, new MyCallBack<KeMaiHuiObj>(mContext,pl_load,pcfl) {
            @Override
            public void onSuccess(KeMaiHuiObj obj, int errorCode, String msg) {
                keMaiHuiObj = obj;
                if(isLoad){
                    pageNum++;
                    adapter.addList(obj.getList2(),true);
                }else{
                    pageNum=2;
                    adapter.setList(obj.getList2(),true);
                }
            }
        });

    }

    @Override
    protected void onViewClick(View v) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            switch (requestCode){
                case 100:
                    showLoading();
                    getData(1,false);
                break;
            }
        }
    }
    private void jiChu() {
        final MySimpleDialog dialog=new MySimpleDialog(mContext);

        View dialogView = getLayoutInflater().inflate(R.layout.my_kemaihui_popu, null);
        final TextView tv_kemaihui_kuaidi_name=dialogView.findViewById(R.id.tv_kemaihui_kuaidi_name);
        tv_kemaihui_kuaidi_name.setOnClickListener(new MyOnClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
                showLoading();
                Map<String,String>map=new HashMap<String,String>();
                map.put("rnd",getRnd());
                map.put("sign",getSign(map));
                ApiRequest.getKuaiDi(map, new MyCallBack<List<KuaiDiObj>>(mContext) {
                    @Override
                    public void onSuccess(List<KuaiDiObj> list, int errorCode, String msg) {
                        selectKuaiDi(tv_kemaihui_kuaidi_name,list);
                    }
                });

            }
        });
        final MyEditText et_kemaihui_kuaidi_num = dialogView.findViewById(R.id.et_kemaihui_kuaidi_num);
        dialogView.findViewById(R.id.tv_kemaihui_kuaidi_sure).setOnClickListener(new MyOnClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {

                if(TextUtils.isEmpty(expressId)){
                    showMsg("请选择快递");
                }else if(TextUtils.isEmpty(getSStr(et_kemaihui_kuaidi_num))){
                    showMsg("请填写快递单号");
                }else{
                    expressNo =getSStr(et_kemaihui_kuaidi_num);
                    maiHui();
                }
            }
        });
        dialog.setGravity(Gravity.CENTER);
        dialog.setRadius(PhoneUtils.dip2px(mContext,6));
        dialog.setWidth(PhoneUtils.getScreenWidth(mContext)*6/7);
        dialog.setContentView(dialogView);
        dialog.show();
    }
    private void selectKuaiDi(final TextView tv_kemaihui_kuaidi_name,List<KuaiDiObj> list) {
        final MySimpleDialog dialog=new MySimpleDialog(mContext);

        View dialogView = getLayoutInflater().inflate(R.layout.my_kemaihui_kuaidi_popu, null);
        MyRecyclerView rv_select_kuaidi = dialogView.findViewById(R.id.rv_select_kuaidi);
        MyBaseRecyclerAdapter adapter=new MyBaseRecyclerAdapter<KuaiDiObj>(mContext,android.R.layout.simple_list_item_1) {
            @Override
            public void bindData(MyRecyclerViewHolder holder, int i, final KuaiDiObj bean) {
                TextView textView = holder.getTextView(android.R.id.text1);
                textView.setText(bean.getTitle());
                textView.setOnClickListener(new MyOnClickListener() {
                    @Override
                    protected void onNoDoubleClick(View view) {
                        expressId=bean.getId();
                        tv_kemaihui_kuaidi_name.setText(bean.getTitle());
                        dialog.dismiss();
                    }
                });
            }
        };
        adapter.setList(list);
        rv_select_kuaidi.setAdapter(adapter);

        dialogView.findViewById(R.id.tv_select_kuaidi_cancel).setOnClickListener(new MyOnClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.setFullWidth();
        dialog.setGravity(Gravity.BOTTOM);
        dialog.setHeight(PhoneUtils.dip2px(mContext,250));
        dialog.setContentView(dialogView);
        dialog.show();
    }

    private void maiHui() {
        showLoading();
        Map<String,String> map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("goods_id", goodsId);
        map.put("express_id", expressId);
        map.put("express_no", expressNo);
        map.put("order_no", goodsNo);
        map.put("sign",getSign(map));
        ApiRequest.maiHui(map, new MyCallBack<BaseObj>(mContext) {
            @Override
            public void onSuccess(BaseObj obj, int errorCode, String msg) {
                showMsg(msg);
                setResult(RESULT_OK);
                finish();
            }
        });

    }
}
