package com.zhizhong.yujian.module.my.activity;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.github.baseclass.adapter.MyRecyclerViewHolder;
import com.zhizhong.yujian.IntentParam;
import com.zhizhong.yujian.R;
import com.zhizhong.yujian.adapter.MyAdapter;
import com.zhizhong.yujian.base.BaseActivity;
import com.zhizhong.yujian.base.MyCallBack;
import com.zhizhong.yujian.module.auction.activity.JiaoNaJinActivity;
import com.zhizhong.yujian.module.my.network.ApiRequest;
import com.zhizhong.yujian.module.my.network.response.MyBaoZhengJinObj;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class MyBaoZhengJinActivity extends BaseActivity {
    @BindView(R.id.tv_my_baozhengjin_money)
    TextView tv_my_baozhengjin_money;

    @BindView(R.id.tv_my_baozhengjin_weiyue_money)
    TextView tv_my_baozhengjin_weiyue_money;

    @BindView(R.id.rv_baozhengjin)
    RecyclerView rv_baozhengjin;

    MyAdapter adapter;
    private double cashDeposit;

    @Override
    protected int getContentView() {
        setAppTitle("我的保证金");
//        setAppRightTitle("保证金说明");
        return R.layout.my_baozhengjin_act;
    }

    @Override
    protected void initView() {
        adapter=new MyAdapter<MyBaoZhengJinObj.BalanceDetailListBean>(mContext,R.layout.my_baozhengjin_item,pageSize) {
            @Override
            public void bindData(MyRecyclerViewHolder holder, int position, MyBaoZhengJinObj.BalanceDetailListBean bean) {
                holder.setText(R.id.tv_my_baozhengjin_time,bean.getAdd_time());
                holder.setText(R.id.tv_my_baozhengjin_shuoming,bean.getRemark());
                TextView tv_my_baozhengjin_money = holder.getTextView(R.id.tv_my_baozhengjin_money);
                if(bean.getValue()>0){
                    tv_my_baozhengjin_money.setTextColor(ContextCompat.getColor(mContext,R.color.red));
                    tv_my_baozhengjin_money.setText("+"+bean.getValue());
                }else{
                    tv_my_baozhengjin_money.setTextColor(ContextCompat.getColor(mContext,R.color.gray_66));
                    tv_my_baozhengjin_money.setText(""+bean.getValue());
                }
            }
        };
        adapter.setOnLoadMoreListener(this);

        rv_baozhengjin.setAdapter(adapter);
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
        ApiRequest.myBaoZhengJin(map, new MyCallBack<MyBaoZhengJinObj>(mContext,pl_load,pcfl) {
            @Override
            public void onSuccess(MyBaoZhengJinObj obj, int errorCode, String msg) {
                cashDeposit = obj.getCash_deposit();
                tv_my_baozhengjin_money.setText(obj.getCash_deposit()+"");
                        tv_my_baozhengjin_weiyue_money.setText(obj.getDeduct_cash_deposit()+"");
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

    @Override
    protected void onMyReStart() {
        super.onMyReStart();
        getData(1,false);
    }
    @OnClick({R.id.tv_my_baozhengjin_jiaona,R.id.tv_my_baozhengjin_tixian})
    protected void onViewClick(View v) {
        switch (v.getId()){
            case R.id.tv_my_baozhengjin_jiaona:
                STActivity(JiaoNaJinActivity.class);
            break;
            case R.id.tv_my_baozhengjin_tixian:
                Intent intent=new Intent();
                intent.putExtra(IntentParam.isBaoZhengJin,true);
                intent.putExtra(IntentParam.baoZhengJinMoney,new BigDecimal(cashDeposit));
                STActivity(intent,TiXianActivity.class);
            break;
        }
    }
}
