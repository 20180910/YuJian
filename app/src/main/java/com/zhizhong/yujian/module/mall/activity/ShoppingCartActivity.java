package com.zhizhong.yujian.module.mall.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.androidtools.AndroidUtils;
import com.github.androidtools.PhoneUtils;
import com.github.androidtools.inter.MyOnClickListener;
import com.github.baseclass.BaseDividerGridItem;
import com.github.baseclass.adapter.MyRecyclerViewHolder;
import com.github.fastshape.MyCheckBox;
import com.github.mydialog.MyDialog;
import com.google.gson.Gson;
import com.library.base.BaseObj;
import com.zhizhong.yujian.IntentParam;
import com.zhizhong.yujian.R;
import com.zhizhong.yujian.adapter.GoodsAdapter;
import com.zhizhong.yujian.adapter.MyAdapter;
import com.zhizhong.yujian.base.BaseActivity;
import com.zhizhong.yujian.base.GlideUtils;
import com.zhizhong.yujian.base.MyCallBack;
import com.zhizhong.yujian.module.home.activity.MainActivity;
import com.zhizhong.yujian.module.mall.network.ApiRequest;
import com.zhizhong.yujian.module.mall.network.response.ShoppingCartObj;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class ShoppingCartActivity extends BaseActivity {
    @BindView(R.id.rv_shopping_cart)
    RecyclerView rv_shopping_cart;
    @BindView(R.id.rv_shopping_cart_recommend)
    RecyclerView rv_shopping_cart_recommend;
    @BindView(R.id.cb_shopping_cart_all)
    MyCheckBox cb_shopping_cart_all;
    @BindView(R.id.tv_shopping_cart_total)
    TextView tv_shopping_cart_total;
    @BindView(R.id.ll_shopping_cart_bottom)
    LinearLayout ll_shopping_cart_bottom;

    GoodsAdapter goodsAdapter;
    MyAdapter adapter;
    private boolean isEdit;

    @Override
    protected int getContentView() {
        setAppTitle("购物车");
        setAppRightTitle("编辑");
        return R.layout.shopping_cart_act;
    }

    @Override
    protected void initView() {
        goodsAdapter=new GoodsAdapter(mContext,R.layout.tuijian_goods_item, pageSize);
        rv_shopping_cart_recommend.setLayoutManager(new GridLayoutManager(mContext,2));
        rv_shopping_cart_recommend.setNestedScrollingEnabled(false);
        rv_shopping_cart_recommend.addItemDecoration(new BaseDividerGridItem(mContext, PhoneUtils.dip2px(mContext,10),R.color.white));
        rv_shopping_cart_recommend.setAdapter(goodsAdapter);

        adapter=new MyAdapter<ShoppingCartObj.ShoppingCartListBean>(mContext,R.layout.shopping_cart_item,pageSize) {
            @Override
            public void bindData(MyRecyclerViewHolder holder, final int position, final ShoppingCartObj.ShoppingCartListBean bean) {

                View rl_shopping_cart = holder.getView(R.id.rl_shopping_cart);
                View rl_shopping_cart_edit = holder.getView(R.id.rl_shopping_cart_edit);

                MyCheckBox cb_shopping_cart_check = (MyCheckBox) holder.getView(R.id.cb_shopping_cart_check);
                cb_shopping_cart_check.setChecked(bean.isCheck());
                if(isEdit){
                    rl_shopping_cart.setVisibility(View.GONE);
                    rl_shopping_cart_edit.setVisibility(View.VISIBLE);
                }else{
                    rl_shopping_cart.setVisibility(View.VISIBLE);
                    rl_shopping_cart_edit.setVisibility(View.GONE);
                }

                cb_shopping_cart_check.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bean.setCheck(!bean.isCheck());
                        jisuanPrice();
                    }
                });
                ImageView iv_shopping_cart = holder.getImageView(R.id.iv_shopping_cart);
                GlideUtils.into(mContext,bean.getGoods_image(),iv_shopping_cart);

                holder.setText(R.id.tv_shopping_cart_price,"¥"+bean.getPrice());
                holder.setText(R.id.tv_shopping_cart_price_edit,"¥"+bean.getPrice());

                TextView tv_shopping_cart_num = holder.getTextView(R.id.tv_shopping_cart_num);
                TextView tv_shopping_cart_num_edit = holder.getTextView(R.id.tv_shopping_cart_num_edit);
                tv_shopping_cart_num.setText(bean.getNumber()+"");
                tv_shopping_cart_num_edit.setText(bean.getNumber()+"");

                View ib_shopping_jian = holder.getView(R.id.ib_shopping_jian);
                View ib_shopping_jian_edit = holder.getView(R.id.ib_shopping_jian_edit);
                ib_shopping_jian.setOnClickListener(prepareUpdateShoppingNum(0,bean,tv_shopping_cart_num,tv_shopping_cart_num_edit));
                ib_shopping_jian_edit.setOnClickListener(prepareUpdateShoppingNum(0,bean,tv_shopping_cart_num,tv_shopping_cart_num_edit));

                View ib_shopping_jia = holder.getView(R.id.ib_shopping_jia);
                View ib_shopping_jia_edit = holder.getView(R.id.ib_shopping_jia_edit);
                ib_shopping_jia.setOnClickListener(prepareUpdateShoppingNum(1,bean,tv_shopping_cart_num,tv_shopping_cart_num_edit));
                ib_shopping_jia_edit.setOnClickListener(prepareUpdateShoppingNum(1,bean,tv_shopping_cart_num,tv_shopping_cart_num_edit));

                View ib_shopping_cart_delete_edit = holder.getView(R.id.ib_shopping_cart_delete_edit);
                ib_shopping_cart_delete_edit.setOnClickListener(new MyOnClickListener() {
                    @Override
                    protected void onNoDoubleClick(View view) {
                        prepareDeleteGoods(bean.getId(),position);
                    }
                });
                

                holder.setText(R.id.tv_shopping_cart_name,bean.getGoods_name());



            }
        };
        rv_shopping_cart.setLayoutManager(new LinearLayoutManager(mContext));
        rv_shopping_cart.addItemDecoration(getItemDivider(PhoneUtils.dip2px(mContext,5)));
        rv_shopping_cart.setAdapter(adapter);

    }

    @NonNull
    private View.OnClickListener prepareUpdateShoppingNum(final int flag, final ShoppingCartObj.ShoppingCartListBean bean, final TextView textView, final TextView textViewEdit) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number = bean.getNumber();
                if(flag==0){//减
                    if(number>1){
                        number--;
                        updateShoppingNum(bean.getId(),number);
                    }
                }else{//加
                    number++;
                    if(number>bean.getStock()){
                        showMsg("库存不足");
                    }else{
                        updateShoppingNum(bean.getId(),number);
                    }
                }
            }
            private void updateShoppingNum(String id,final int number) {
                showLoading();
                Map<String,String>map=new HashMap<String,String>();
                map.put("user_id",getUserId());
                map.put("number",number+"");
                map.put("sc_id",id);
                map.put("sign",getSign(map));
                ApiRequest.updateShoppingCartNum(map, new MyCallBack<BaseObj>(mContext) {
                    @Override
                    public void onSuccess(BaseObj obj, int errorCode, String msg) {
                        bean.setNumber(number);
                        textView.setText(number+"");
                        textViewEdit.setText(number+"");
                        jisuanPrice();
                    }
                });
            }
        };
    }

    private BigDecimal jisuanPrice() {
        return jisuanPrice(false);
    }
    private BigDecimal jisuanPrice(boolean isSelectCheckAll) {
        List list = adapter.getList();
        BigDecimal total=new BigDecimal(0);
        int checkAllNum=0;
        for (int i = 0; i < list.size(); i++) {
            ShoppingCartObj.ShoppingCartListBean bean = (ShoppingCartObj.ShoppingCartListBean) list.get(i);
            if(isSelectCheckAll){
                bean.setCheck(cb_shopping_cart_all.isChecked());
            }
            if(bean.isCheck()){
                total=AndroidUtils.jiaFa(total,AndroidUtils.chengFa(bean.getPrice(),new BigDecimal(bean.getNumber())));
                checkAllNum++;
            }
        }
        cb_shopping_cart_all.setChecked(checkAllNum==list.size());
        tv_shopping_cart_total.setText("¥"+total.toString());
        if(isSelectCheckAll){
            adapter.notifyDataSetChanged();
        }
        return total;
    }


    private void prepareDeleteGoods(final String id, final int position) {
        MyDialog.Builder mDialog=new MyDialog.Builder(mContext);
        mDialog.setMessage("是否确认删除?");
        mDialog.setNegativeButton(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        mDialog.setPositiveButton(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                deleteGoods(id,position);
            }
        });
        mDialog.create().show();
    }

    private void deleteGoods(String id, final int position) {
        showLoading();
        Map<String,String>map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("sc_id",id);
        map.put("sign",getSign(map));
        ApiRequest.deleteShoppingCart(map, new MyCallBack<BaseObj>(mContext) {
            @Override
            public void onSuccess(BaseObj obj, int errorCode, String msg) {
                postGetShoppingCartNum();
                adapter.getList().remove(position);
                if(adapter.getList().size()>0){
                    adapter.notifyDataSetChanged();
                    jisuanPrice();
                }else{
                    setEmptyShoppingCart(false);
                }
            }
        });

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
        map.put("sign", getSign(map));
        ApiRequest.getShoppingCart(map, new MyCallBack<ShoppingCartObj>(mContext, pl_load, pcfl) {
            @Override
            public void onSuccess(ShoppingCartObj obj, int errorCode, String msg) {
                setEmptyShoppingCart(notEmpty(obj.getShopping_cart_list()));
                goodsAdapter.setList(obj.getTuijian(),true);
                if(notEmpty(obj.getShopping_cart_list())){
                    adapter.setList(obj.getShopping_cart_list(),true);
                }
            }
        });
    }
    public void setEmptyShoppingCart(boolean notEmpty){
        if(notEmpty){
            rv_shopping_cart.setVisibility(View.VISIBLE);
            nsv.setVisibility(View.GONE);
            ll_shopping_cart_bottom.setVisibility(View.VISIBLE);
            app_right_tv.setVisibility(View.VISIBLE);
        }else{
            rv_shopping_cart.setVisibility(View.GONE);
            nsv.setVisibility(View.VISIBLE);
            ll_shopping_cart_bottom.setVisibility(View.GONE);
            cb_shopping_cart_all.setChecked(false);
            tv_shopping_cart_total.setText("¥0");
            app_right_tv.setVisibility(View.GONE);
        }
    }
    @OnClick({R.id.app_right_tv,R.id.tv_shopping_cart_home, R.id.tv_shopping_cart_guangguang, R.id.cb_shopping_cart_all, R.id.tv_shopping_cart_jiesuan})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.app_right_tv:
                isEdit=!isEdit;
                changeEdit();
                break;
            case R.id.tv_shopping_cart_home:
                goHome(IntentParam.selectHome);
                break;
            case R.id.tv_shopping_cart_guangguang:
                goHome(IntentParam.mall);
                break;
            case R.id.cb_shopping_cart_all:
                jisuanPrice(true);
                break;
            case R.id.tv_shopping_cart_jiesuan:
                jieSuan();
                break;
        }
    }

    private void jieSuan() {
        List list = adapter.getList();
        List<ShoppingCartObj.ShoppingCartListBean>goodsBeanList=new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            ShoppingCartObj.ShoppingCartListBean bean = (ShoppingCartObj.ShoppingCartListBean) list.get(i);
            if(bean.isCheck()){
                goodsBeanList.add(bean);
            }
        }
        if(goodsBeanList.size()<=0){
            showMsg("请选择商品");
        }else{
            Intent intent=new Intent();
            intent.putExtra(IntentParam.shoppingCart,new Gson().toJson(goodsBeanList));
            STActivity(intent,SureOrderActivity.class);
        }
    }

    public void goHome(String action){
        Intent intent= new Intent(action);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        STActivity(intent,MainActivity.class);
    }
    private void changeEdit() {
        if(isEdit){
            setAppRightTitle("完成");
        }else{
            setAppRightTitle("编辑");
        }
        adapter.notifyDataSetChanged();
    }
}
