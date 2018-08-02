package com.zhizhong.yujian.module.my.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.github.baseclass.adapter.MyRecyclerViewHolder;
import com.zhizhong.yujian.IntentParam;
import com.zhizhong.yujian.R;
import com.zhizhong.yujian.adapter.MyAdapter;
import com.zhizhong.yujian.base.BaseActivity;
import com.zhizhong.yujian.base.MyCallBack;
import com.zhizhong.yujian.module.my.network.ApiRequest;
import com.zhizhong.yujian.network.response.WuLiuObj;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

public class WuLiuActivity extends BaseActivity {

    @BindView(R.id.tv_wu_liu_no)
    TextView tv_wu_liu_no;
    @BindView(R.id.tv_wu_liu_kuaidi)
    TextView tv_wu_liu_kuaidi;
    @BindView(R.id.tv_wu_liu_phone)
    TextView tv_wu_liu_phone;
    @BindView(R.id.rv_wu_liu)
    RecyclerView rv_wu_liu;

    MyAdapter adapter;

    private String orderNo;

    @Override
    protected int getContentView() {
        setAppTitle("物流信息");
        return R.layout.wuliu_act;
    }

    @Override
    protected void initView() {
        orderNo = getIntent().getStringExtra(IntentParam.orderNo);


        adapter=new MyAdapter<WuLiuObj.CourierListBean>(mContext,R.layout.wu_liu_item,pageSize) {
            @Override
            public void bindData(MyRecyclerViewHolder holder, int position, WuLiuObj.CourierListBean bean) {
                holder.setText(R.id.tv_wu_liu_content,bean.getContext());
                holder.setText(R.id.tv_wu_liu_date,bean.getTime());

                View fl_wu_liu_first_point = holder.getView(R.id.fl_wu_liu_first_point);
                View tv_wu_liu_point = holder.getView(R.id.tv_wu_liu_point);

                if(position==0){
                    fl_wu_liu_first_point.setVisibility(View.VISIBLE);
                    tv_wu_liu_point.setVisibility(View.GONE);
                }else{
                    fl_wu_liu_first_point.setVisibility(View.GONE);
                    tv_wu_liu_point.setVisibility(View.VISIBLE);
                }
            }
        };

        rv_wu_liu.setLayoutManager(new LinearLayoutManager(mContext));
        rv_wu_liu.setAdapter(adapter);

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
        map.put("order_no", orderNo);
        map.put("sign", getSign(map));
        ApiRequest.getWuLiuDetail(map, new MyCallBack<WuLiuObj>(mContext, pl_load, pcfl) {
            @Override
            public void onSuccess(WuLiuObj obj, int errorCode, String msg) {
                tv_wu_liu_no.setText(obj.getCourier_number());
                tv_wu_liu_kuaidi.setText(obj.getCourier_name());
                tv_wu_liu_phone.setText(obj.getCourier_phone());

                adapter.setList(obj.getCourier_list(),true);
            }
        });

    }

    @Override
    protected void onViewClick(View v) {

    }
}
