package com.zhizhong.yujian.module.mall.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.androidtools.AndroidUtils;
import com.github.baseclass.adapter.MyRecyclerViewHolder;
import com.github.rxbus.RxBus;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.library.base.BaseObj;
import com.zhizhong.yujian.IntentParam;
import com.zhizhong.yujian.R;
import com.zhizhong.yujian.adapter.MyAdapter;
import com.zhizhong.yujian.base.BaseActivity;
import com.zhizhong.yujian.base.GlideUtils;
import com.zhizhong.yujian.base.MyCallBack;
import com.zhizhong.yujian.event.JoinShoppingCartEvent;
import com.zhizhong.yujian.module.mall.network.ApiRequest;
import com.zhizhong.yujian.module.mall.network.request.CommitOrderBody;
import com.zhizhong.yujian.module.mall.network.response.ShoppingCartObj;
import com.zhizhong.yujian.module.mall.network.response.YouHuiQuanObj;
import com.zhizhong.yujian.module.my.activity.AddressListActivity;
import com.zhizhong.yujian.module.my.network.response.AddressObj;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class SureOrderActivity extends BaseActivity {

    @BindView(R.id.tv_sure_order_add)
    TextView tv_sure_order_add;
    @BindView(R.id.ll_sure_order_select_address)
    LinearLayout ll_sure_order_select_address;
    @BindView(R.id.tv_sure_order_name)
    TextView tv_sure_order_name;
    @BindView(R.id.tv_sure_order_phone)
    TextView tv_sure_order_phone;
    @BindView(R.id.tv_sure_order_address)
    TextView tv_sure_order_address;
    @BindView(R.id.ll_sure_order_address)
    LinearLayout ll_sure_order_address;
    @BindView(R.id.rv_sure_order)
    RecyclerView rv_sure_order;
    @BindView(R.id.tv_sure_order_num)
    TextView tv_sure_order_num;
    @BindView(R.id.tv_sure_order_xiaoji)
    TextView tv_sure_order_xiaoji;
    @BindView(R.id.et_sure_order_liuyan)
    EditText et_sure_order_liuyan;
    @BindView(R.id.tv_sure_order_vouchers)
    TextView tv_sure_order_vouchers;
    @BindView(R.id.tv_sure_order_heji)
    TextView tv_sure_order_heji;

    private List<ShoppingCartObj.ShoppingCartListBean> goodsBeanList;
    private String addressId;

    private BigDecimal heJi, xiaoJi;

    MyAdapter adapter;
    private String youhuiId="0";
    private BigDecimal youhuiMoney=new BigDecimal(0);
    private BigDecimal youHuiQuanFullMoney=new BigDecimal(0);
    private int youHuiQuanNum;

    @Override
    protected int getContentView() {
        setAppTitle("确认订单");
        return R.layout.sure_order_act;
    }

    @Override
    protected void initView() {
        String json = getIntent().getStringExtra(IntentParam.shoppingCart);
        Type type = new TypeToken<List<ShoppingCartObj.ShoppingCartListBean>>(){}.getType();
        goodsBeanList = new Gson().fromJson(json, type);


        adapter=new MyAdapter<ShoppingCartObj.ShoppingCartListBean>(mContext,R.layout.sure_order_item,pageSize) {
            @Override
            public void bindData(MyRecyclerViewHolder holder, int position, ShoppingCartObj.ShoppingCartListBean bean) {
                ImageView imageView = holder.getImageView(R.id.iv_sure_order);
                GlideUtils.into(mContext,bean.getGoods_image(),imageView);

                holder.setText(R.id.tv_sure_order_name,bean.getGoods_name());
                holder.setText(R.id.tv_sure_order_price,"¥"+bean.getPrice());
                TextView tv_sure_order_num = holder.getTextView(R.id.tv_sure_order_num);
                tv_sure_order_num.setText(bean.getNumber()+"");

                View iv_sure_order_jian = holder.getView(R.id.iv_sure_order_jian);
                View iv_sure_order_jia = holder.getView(R.id.iv_sure_order_jia);

                iv_sure_order_jian.setOnClickListener(updateGoodsNum(0,bean,tv_sure_order_num));
                iv_sure_order_jia.setOnClickListener(updateGoodsNum(1,bean,tv_sure_order_num));

            }
        };
        tv_sure_order_num.setText("共"+goodsBeanList.size()+"件商品");
        adapter.setList(goodsBeanList);
        rv_sure_order.setNestedScrollingEnabled(false);
        rv_sure_order.setAdapter(adapter);
        jisuanPrice();
    }
    @NonNull
    private View.OnClickListener updateGoodsNum(final int flag, final ShoppingCartObj.ShoppingCartListBean bean, final TextView textView) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number = bean.getNumber();
                if(flag==0){//减
                    if(number>1){
                        number--;
                        bean.setNumber(number);
                        textView.setText(number+"");
                        if(xiaoJi.doubleValue()<youHuiQuanFullMoney.doubleValue()){
                            youhuiId="0";
                            youhuiMoney=new BigDecimal(0);
                            youHuiQuanFullMoney=new BigDecimal(0);
                            getYouHuiQuanNum();
                        }
                        jisuanPrice();
                    }
                }else{//加
                    number++;
                    if(number>bean.getStock()){
                        showMsg("库存不足");
                    }else{
                        bean.setNumber(number);
                        textView.setText(number+"");
                        jisuanPrice();
                    }
                }
            }

        };
    }
    private BigDecimal jisuanPrice() {
        List list = adapter.getList();
        BigDecimal total=new BigDecimal(0);
        for (int i = 0; i < list.size(); i++) {
            ShoppingCartObj.ShoppingCartListBean bean = (ShoppingCartObj.ShoppingCartListBean) list.get(i);
            total= AndroidUtils.jiaFa(total,AndroidUtils.chengFa(bean.getPrice(),new BigDecimal(bean.getNumber())));
        }
        tv_sure_order_xiaoji.setText("¥"+total.toString());
        xiaoJi =total;

        total=AndroidUtils.jianFa(total,youhuiMoney);
        heJi=total;

        tv_sure_order_heji.setText("¥"+total.toString());
        return total;
    }

    @Override
    protected void initData() {
        showProgress();
        getYouHuiQuanNum();
        getData(1, false);
    }

    @Override
    protected void getData(int page, boolean isLoad) {
        super.getData(page, isLoad);
        Map<String, String> map = new HashMap<String, String>();
        map.put("user_id", getUserId());
        map.put("sign", getSign(map));
        ApiRequest.getDefaultAddress(map, new MyCallBack<List<AddressObj>>(mContext, pl_load, pcfl) {
            @Override
            public void onSuccess(List<AddressObj> list, int errorCode, String msg) {
                if (notEmpty(list)) {
                    AddressObj addressObj = list.get(0);
                    ll_sure_order_select_address.setVisibility(View.GONE);
                    ll_sure_order_address.setVisibility(View.VISIBLE);

                   setAddress(addressObj);
                } else {
                    ll_sure_order_select_address.setVisibility(View.VISIBLE);
                    ll_sure_order_address.setVisibility(View.GONE);
                }
            }


        });

    }
    private void setAddress(AddressObj addressObj) {
        addressId=addressObj.getAddress_id();
        tv_sure_order_name.setText(addressObj.getRecipient());
        tv_sure_order_phone.setText(addressObj.getPhone());
        tv_sure_order_address.setText("收货地址："+addressObj.getShipping_address()+addressObj.getShipping_address_details());
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            switch (requestCode){
                case 100:
                    AddressObj addressObj = (AddressObj) data.getSerializableExtra(IntentParam.addressBean);
                    setAddress(addressObj);
                break;
                case 200:
                    youhuiId=data.getStringExtra(IntentParam.youHuiQuanId);
                    youhuiMoney= (BigDecimal) data.getSerializableExtra(IntentParam.youHuiQuanMoney);
                    youHuiQuanFullMoney= (BigDecimal) data.getSerializableExtra(IntentParam.youHuiQuanFullMoney);
                    if("0".equals(youhuiId)){
                        getYouHuiQuanNum();
                    }else{
                        tv_sure_order_vouchers.setText("-¥"+youhuiMoney);
                    }
                    jisuanPrice();
                break;
            }
        }
    }
    public void getYouHuiQuanNum(){
        Map<String,String> map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("money",xiaoJi+"");
        map.put("sign",getSign(map));
        ApiRequest.keYongYouHuiQuan(map, new MyCallBack<List<YouHuiQuanObj>>(mContext,pl_load,pcfl) {
            @Override
            public void onSuccess(List<YouHuiQuanObj> list, int errorCode, String msg) {
                if(notEmpty(list)){
                    youHuiQuanNum=list.size();
                }else{
                    youHuiQuanNum =0;
                }
                tv_sure_order_vouchers.setText(youHuiQuanNum+"张可用");
            }
        });

    }

    @OnClick({R.id.ll_sure_order_youhui, R.id.tv_sure_order_commit,R.id.ll_sure_order_select_address,R.id.ll_sure_order_address})
    public void onViewClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.ll_sure_order_youhui:
                intent=new Intent();
                intent.putExtra(IntentParam.useYouHuiQuan,true);
                intent.putExtra(IntentParam.money,xiaoJi.toString());
                STActivityForResult(intent,LingQuanZhongXinActivity.class,200);
                break;
            case R.id.tv_sure_order_commit:
                if(TextUtils.isEmpty(addressId)){
                    showMsg("请选择地址");
                    return;
                }
                commit();
                break;
            case R.id.ll_sure_order_address:
            case R.id.ll_sure_order_select_address:
                intent=new Intent(IntentParam.selectAddress);
                STActivityForResult(intent,AddressListActivity.class,100);
                break;
        }
    }

    private void commit() {
        showLoading();
        Map<String,String>map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("addres_id",addressId);
        map.put("invoice_type","");
        map.put("invoice_code","");
        map.put("invoice_name","");
        map.put("invoice_tax_number","");
        map.put("buyer_msg",getSStr(et_sure_order_liuyan));
        map.put("coupon_id",youhuiId);
        map.put("sign",getSign(map));

        CommitOrderBody body=new CommitOrderBody();

        List<CommitOrderBody.BodyBean> bodyList=new ArrayList<>();
        for (int i = 0; i < adapter.getList().size(); i++) {

            ShoppingCartObj.ShoppingCartListBean shoppingCartListBean = (ShoppingCartObj.ShoppingCartListBean) adapter.getList().get(i);
            CommitOrderBody.BodyBean bodyBean=new CommitOrderBody.BodyBean();
            bodyBean.setShopping_cart_id(shoppingCartListBean.getId());
            bodyBean.setGoods_id(shoppingCartListBean.getGoods_id());
            bodyBean.setNumber(shoppingCartListBean.getNumber());

            bodyList.add(bodyBean);
        }

        body.setBody(bodyList);

        ApiRequest.commitOrder(map,body,new MyCallBack<BaseObj>(mContext) {
            @Override
            public void onSuccess(BaseObj obj, int errorCode, String msg) {
                RxBus.getInstance().postReplay(new JoinShoppingCartEvent());
                finish();
            }
        });

    }
}
