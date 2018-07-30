package com.zhizhong.yujian.module.mall.activity;

import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.androidtools.PhoneUtils;
import com.github.androidtools.inter.MyOnClickListener;
import com.github.baseclass.BaseDividerGridItem;
import com.github.baseclass.view.MyPopupwindow;
import com.github.fastshape.MyLinearLayout;
import com.zhizhong.yujian.IntentParam;
import com.zhizhong.yujian.R;
import com.zhizhong.yujian.adapter.GoodsAdapter;
import com.zhizhong.yujian.base.BaseActivity;
import com.zhizhong.yujian.base.MyCallBack;
import com.zhizhong.yujian.module.mall.network.ApiRequest;
import com.zhizhong.yujian.network.response.GoodsObj;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class GoodsClassActivity extends BaseActivity {
    @BindView(R.id.dl_goods_class)
    DrawerLayout dl_goods_class;
    @BindView(R.id.rv_goods_class)
    RecyclerView rv_goods_class;

    GoodsAdapter adapter;
    @BindView(R.id.tv_goods_price)
    TextView tv_goods_price;
    @BindView(R.id.tv_goods_xl)
    TextView tv_goods_xl;
    @BindView(R.id.ll_order_class_sx)
    LinearLayout ll_order_class_sx;

    @BindView(R.id.ll_goods_order)
    MyLinearLayout ll_goods_order;

    @BindView(R.id.et_goods_class_minprice)
    EditText et_goods_class_minprice;

    @BindView(R.id.et_goods_class_maxprice)
    EditText et_goods_class_maxprice;

    @BindView(R.id.tv_goods_class_reset)
    TextView tv_goods_class_reset;

    @BindView(R.id.tv_goods_class_complete)
    TextView tv_goods_class_complete;

    private String title;

    private String goodsTypeId;
    private String minPrice = "0";
    private String maxPrice = "0";
    private String salesVolumeSort = "0";
    private String priceSort = "0";
    private MyPopupwindow xiaoliangPopu;
    private MyPopupwindow pricePopu;

    @Override
    protected int getContentView() {
        return R.layout.goods_class_act;
    }

    @Override
    protected void initView() {
        title = getIntent().getStringExtra(IntentParam.title);
        goodsTypeId = getIntent().getStringExtra(IntentParam.goodsTypeId);
        setAppTitle(title);


        adapter = new GoodsAdapter(mContext, R.layout.tuijian_goods_item, pageSize);
        adapter.setOnLoadMoreListener(this);

        rv_goods_class.setLayoutManager(new GridLayoutManager(mContext, 2));
        rv_goods_class.addItemDecoration(new BaseDividerGridItem(mContext, PhoneUtils.dip2px(mContext, 10), R.color.white));
        rv_goods_class.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        showProgress();
        getData(1, false);
    }

    @Override
    protected void getData(int page, final boolean isLoad) {
        super.getData(page, isLoad);
        Map<String, String> map = new HashMap<String, String>();
        map.put("user_id", getUserId());
        map.put("type_id", goodsTypeId);
        map.put("min_price", minPrice);
        map.put("max_price", maxPrice);
        map.put("sales_volume_sort", salesVolumeSort);
        map.put("price_sort", priceSort);
        map.put("pagesize", pagesize + "");
        map.put("page", page + "");
        map.put("sign", getSign(map));
        ApiRequest.getGoodsClassList(map, new MyCallBack<List<GoodsObj>>(mContext, pl_load, pcfl) {
            @Override
            public void onSuccess(List<GoodsObj> list, int errorCode, String msg) {
                if (isLoad) {
                    pageNum++;
                    adapter.addList(list, true);
                } else {
                    pageNum = 2;
                    adapter.setList(list, true);
                }
            }
        });

    }


    @OnClick({R.id.tv_goods_price, R.id.tv_goods_xl, R.id.ll_order_class_sx, R.id.ll_goods_order,R.id.tv_goods_class_reset,R.id.tv_goods_class_complete})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.tv_goods_price:
                showPriceSort();
                break;
            case R.id.tv_goods_xl:
                showXiaoLiangSort();
                break;
            case R.id.ll_order_class_sx:
                dl_goods_class.openDrawer(Gravity.RIGHT);
                break;
            case R.id.tv_goods_class_reset:
                et_goods_class_minprice.setText(null);
                et_goods_class_maxprice.setText(null);
                minPrice="0";
                maxPrice="0";
                break;
            case R.id.tv_goods_class_complete:
                if(TextUtils.isEmpty(et_goods_class_minprice.getText().toString())){
                    showMsg("请输入最低价");
                    return;
                }else if(TextUtils.isEmpty(et_goods_class_maxprice.getText().toString())){
                    showMsg("请输入最高价");
                    return;
                }
                int min = Integer.parseInt(et_goods_class_minprice.getText().toString());
                int max = Integer.parseInt(et_goods_class_maxprice.getText().toString());
                if(min>max){
                    showMsg("最低价不能大于最高价");
                    return;
                }
                minPrice=et_goods_class_minprice.getText().toString();
                maxPrice=et_goods_class_maxprice.getText().toString();
                dl_goods_class.closeDrawer(Gravity.RIGHT);
                showLoading();
                getData(1,false);
                break;
        }
    }

    private void  showPriceSort(){
        View pricePopuView = getLayoutInflater().inflate(R.layout.goods_class_price_popu, null);
        View defaultView = pricePopuView.findViewById(R.id.tv_goods_class_price_default);
        View gaoView =pricePopuView.findViewById(R.id.tv_goods_class_price_gao);
        View diView =pricePopuView.findViewById(R.id.tv_goods_class_price_di);

        defaultView.setOnClickListener(getSortListener(0,0));
        gaoView.setOnClickListener(getSortListener(0,1));
        diView.setOnClickListener(getSortListener(0,2));

        pricePopu = new MyPopupwindow(mContext,pricePopuView);
        pricePopu.setElevat(PhoneUtils.dip2px(mContext,4));
        pricePopu.setWidth(PhoneUtils.getScreenWidth(mContext)/3);
        pricePopu.showAsDropDown(ll_goods_order);
    }

    @NonNull
    private MyOnClickListener getSortListener(final int flag,final int index) {
        return new MyOnClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
                if(flag==0){
                    pricePopu.dismiss();
                    switch (index){
                        case 0:
                            tv_goods_price.setText("价格排序");
                        break;
                        case 1:
                            tv_goods_price.setText("价格从高到低");
                        break;
                        case 2:
                            tv_goods_price.setText("价格从低到高");
                        break;
                    }
                    priceSort=index+"";
                }else{
                    xiaoliangPopu.dismiss();
                    switch (index){
                        case 0:
                            tv_goods_xl.setText("销量排序");
                            break;
                        case 1:
                            tv_goods_xl.setText("销量从高到低");
                            break;
                        case 2:
                            tv_goods_xl.setText("销量从低到高");
                            break;
                    }
                    salesVolumeSort=index+"";
                }
                showLoading();
                getData(1,false);
            }
        };
    }

    private void showXiaoLiangSort() {
        View xiaoliangPopuView = getLayoutInflater().inflate(R.layout.goods_class_xiaoliang_popu, null);
        View defaultView = xiaoliangPopuView.findViewById(R.id.tv_goods_class_xiaoliang_default);
        View gaoView =xiaoliangPopuView.findViewById(R.id.tv_goods_class_xiaoliang_gao);
        View diView =xiaoliangPopuView.findViewById(R.id.tv_goods_class_xiaoliang_di);

        defaultView.setOnClickListener(getSortListener(1,0));
        gaoView.setOnClickListener(getSortListener(1,1));
        diView.setOnClickListener(getSortListener(1,2));

        xiaoliangPopu = new MyPopupwindow(mContext, xiaoliangPopuView);
        xiaoliangPopu.setElevat(PhoneUtils.dip2px(mContext,4));
        xiaoliangPopu.setWidth(PhoneUtils.getScreenWidth(mContext)/3);
        xiaoliangPopu.showAsDropDown(ll_goods_order,PhoneUtils.getScreenWidth(mContext)/3,0);
    }
}
