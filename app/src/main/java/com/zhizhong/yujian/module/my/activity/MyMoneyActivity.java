package com.zhizhong.yujian.module.my.activity;

import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.github.baseclass.adapter.MyRecyclerViewHolder;
import com.library.base.view.MyRecyclerView;
import com.zhizhong.yujian.R;
import com.zhizhong.yujian.adapter.MyAdapter;
import com.zhizhong.yujian.base.BaseActivity;
import com.zhizhong.yujian.base.MyCallBack;
import com.zhizhong.yujian.module.my.network.ApiRequest;
import com.zhizhong.yujian.module.my.network.response.MyBalanceObj;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class MyMoneyActivity extends BaseActivity {
    @BindView(R.id.tv_my_balance_money)
    TextView tv_my_balance_money;
    @BindView(R.id.rv_my_balance_mingxi)
    MyRecyclerView rv_my_balance_mingxi;

    MyAdapter adapter;

    @Override
    protected int getContentView() {
        setAppTitle("我的余额");
        return R.layout.my_money_act;
    }

    @Override
    protected void initView() {

        adapter=new MyAdapter<MyBalanceObj.BalanceDetailListBean>(mContext,R.layout.my_money_item,pageSize) {
            @Override
            public void bindData(MyRecyclerViewHolder holder, int position, MyBalanceObj.BalanceDetailListBean bean) {
                holder.setText(R.id.tv_balance_mingxi_time,bean.getAdd_time());
                holder.setText(R.id.tv_balance_mingxi_flag,bean.getRemark());


                TextView tv_balance_mingxi_money = holder.getTextView(R.id.tv_balance_mingxi_money);
                if(bean.getValue()<0){
                    tv_balance_mingxi_money.setTextColor(ContextCompat.getColor(mContext,R.color.gray_66));
                    tv_balance_mingxi_money.setText(bean.getValue()+"");
                }else{
                    tv_balance_mingxi_money.setTextColor(ContextCompat.getColor(mContext,R.color.red));
                    tv_balance_mingxi_money.setText("+"+bean.getValue());
                }
            }
        };
        adapter.setOnLoadMoreListener(this);

        rv_my_balance_mingxi.setAdapter(adapter);

    }

    @Override
    protected void initData() {
        showProgress();
        getData(1, false);
    }
    @Override
    protected void onMyReStart() {
        super.onMyReStart();
        getData(1,false);
    }
    @Override
    protected void getData(int page,final boolean isLoad) {
        super.getData(page, isLoad);
        Map<String, String> map = new HashMap<String, String>();
        map.put("user_id", getUserId());
        map.put("pagesize", pagesize + "");
        map.put("page", page + "");
        map.put("sign", getSign(map));
        ApiRequest.getMyBalance(map, new MyCallBack<MyBalanceObj>(mContext, pl_load, pcfl) {
            @Override
            public void onSuccess(MyBalanceObj obj, int errorCode, String msg) {
                tv_my_balance_money.setText(obj.getBalance().toString());
                if(isLoad){
                    pageNum++;
                    adapter.addList(obj.getBalance_detail_list(),true);
                }else{
                    pageNum=2;
                    adapter.setList(obj.getBalance_detail_list(),true);
                }
            }
        });

    }
    @OnClick({R.id.ll_my_balance_chongzhi, R.id.ll_my_balance_tixian})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.ll_my_balance_chongzhi:
                STActivityForResult(ZhangHuChongZhiActivity.class,1000);
                break;
            case R.id.ll_my_balance_tixian:
                STActivity(TiXianActivity.class);
                break;
        }
    }
}
