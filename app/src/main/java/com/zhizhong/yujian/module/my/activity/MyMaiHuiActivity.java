package com.zhizhong.yujian.module.my.activity;

import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import butterknife.OnClick;

public class MyMaiHuiActivity extends BaseActivity {

    @BindView(R.id.tv_kemaihui_detail_jihui)
    TextView tv_kemaihui_detail_jihui;
    @BindView(R.id.tv_kemaihui_detail_name)
    TextView tv_kemaihui_detail_name;
    @BindView(R.id.tv_kemaihui_detail_phone)
    TextView tv_kemaihui_detail_phone;
    @BindView(R.id.tv_kemaihui_detail_address)
    TextView tv_kemaihui_detail_address;
    @BindView(R.id.ll_kemaihui_detail_address_content)
    LinearLayout ll_kemaihui_detail_address_content;
    @BindView(R.id.iv_kemaihui_detail)
    ImageView iv_kemaihui_detail;
    @BindView(R.id.tv_kemaihui_detail_goods_name)
    TextView tv_kemaihui_detail_goods_name;
    @BindView(R.id.tv_kemaihui_detail_buy_price)
    TextView tv_kemaihui_detail_buy_price;
    @BindView(R.id.tv_kemaihui_detail_sales_price)
    TextView tv_kemaihui_detail_sales_price;

    @BindView(R.id.tv_kemaihui_detail_sales)
    MyTextView tv_kemaihui_detail_sales;

    private KeMaiHuiObj keMaiHuiObj;
    private KeMaiHuiObj.List2Bean goods;
    private String expressId;
    private String expressNo;

    @Override
    protected int getContentView() {
        return R.layout.my_maihui_act;
    }

    @Override
    protected void initView() {
        keMaiHuiObj = (KeMaiHuiObj) getIntent().getSerializableExtra(IntentParam.keMaiHui);
        goods = (KeMaiHuiObj.List2Bean) getIntent().getSerializableExtra(IntentParam.keMaiHuiGoods);

        if(TextUtils.isEmpty(keMaiHuiObj.getAddress())){
//            tv_kemaihui_detail_sales.setVisibility(View.GONE);
            tv_kemaihui_detail_jihui.setText("寄回地址:无");
            ll_kemaihui_detail_address_content.setVisibility(View.GONE);
        }else{
//            tv_kemaihui_detail_sales.setVisibility(View.VISIBLE);
            ll_kemaihui_detail_address_content.setVisibility(View.VISIBLE);
        }

        if(goods.getRansom_status()==0){
            tv_kemaihui_detail_sales.setText("确定卖回");
            tv_kemaihui_detail_sales.getViewHelper().setSolidColor(ContextCompat.getColor(mContext,R.color.red)).complete();
        }else if(goods.getRansom_status()==1){
            tv_kemaihui_detail_sales.setText("已发货");
            tv_kemaihui_detail_sales.getViewHelper().setSolidColor(ContextCompat.getColor(mContext,R.color.gray_99)).complete();
            tv_kemaihui_detail_sales.setEnabled(false);
        }else if(goods.getRansom_status()==2){
            tv_kemaihui_detail_sales.setText("已卖回");
            tv_kemaihui_detail_sales.getViewHelper().setSolidColor(ContextCompat.getColor(mContext,R.color.red)).complete();
            tv_kemaihui_detail_sales.setEnabled(false);
        }else{
            tv_kemaihui_detail_sales.setVisibility(View.GONE);
        }


        tv_kemaihui_detail_name.setText(keMaiHuiObj.getRecipient());
        tv_kemaihui_detail_phone.setText(keMaiHuiObj.getPhone());
        tv_kemaihui_detail_address.setText(keMaiHuiObj.getAddress());


        GlideUtils.into(mContext,goods.getGoods_images(),iv_kemaihui_detail);
        tv_kemaihui_detail_goods_name.setText(goods.getGoods_name());
        tv_kemaihui_detail_buy_price.setText("买入价¥"+goods.getGoods_unitprice());
        tv_kemaihui_detail_sales_price.setText("¥"+goods.getKemai_price());

    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.tv_kemaihui_detail_sales})
    public void onViewClick(View v) {
        switch (v.getId()){
            case R.id.tv_kemaihui_detail_sales:
                if (goods.getRansom_status()==0) {
                    if(TextUtils.isEmpty(keMaiHuiObj.getAddress())){
                        showMsg("暂无寄回地址,请联系客服");
                    }else{
                        jiChu();
                    }
                }
            break;
        }
    }

    private void selectKuaiDi(final TextView tv_kemaihui_kuaidi_name, List<KuaiDiObj> list) {
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
                    expressNo=getSStr(et_kemaihui_kuaidi_num);
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

    private void maiHui() {
        showLoading();
        Map<String,String> map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("goods_id",goods.getGoods_id());
        map.put("express_id", expressId);
        map.put("express_no", expressNo);
        map.put("order_no",goods.getOrder_no());
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
