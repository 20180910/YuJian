package com.zhizhong.yujian.module.my.activity;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.github.androidtools.inter.MyOnClickListener;
import com.github.baseclass.adapter.MyRecyclerViewHolder;
import com.library.base.BaseObj;
import com.zhizhong.yujian.IntentParam;
import com.zhizhong.yujian.R;
import com.zhizhong.yujian.adapter.MyAdapter;
import com.zhizhong.yujian.base.BaseActivity;
import com.zhizhong.yujian.base.MyCallBack;
import com.zhizhong.yujian.module.my.network.ApiRequest;
import com.zhizhong.yujian.module.my.network.response.AliAccountListObj;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class SelectZhiFuBaoActivity extends BaseActivity {
    @BindView(R.id.rv_zhifubao)
    RecyclerView rv_zhifubao;
    MyAdapter adapter;

    @Override
    protected int getContentView() {
        setAppTitle("选择支付宝");
        setAppRightImg(R.drawable.add_right);
        return R.layout.select_zhifubao_act;
    }

    @Override
    protected void initView() {
        pcfl.disableWhenHorizontalMove(true);
        adapter=new MyAdapter<AliAccountListObj>(mContext,R.layout.select_zhifubao_item,pageSize) {
            @Override
            public void bindData(MyRecyclerViewHolder holder, int position, final AliAccountListObj bean) {
                TextView tv_select_alipay_delete = holder.getTextView(R.id.tv_select_alipay_delete);
                tv_select_alipay_delete.setOnClickListener(new MyOnClickListener() {
                    @Override
                    protected void onNoDoubleClick(View view) {
                        deleteAccount(bean.getAlipay_id());
                    }
                });

                TextView tv_select_alipay_account = holder.getTextView(R.id.tv_select_alipay_account);
                tv_select_alipay_account.setText("支付宝账户: "+bean.getAlipay_number());

                tv_select_alipay_account.setOnClickListener(new MyOnClickListener() {
                    @Override
                    protected void onNoDoubleClick(View view) {
                        Intent intent=new Intent();
                        intent.putExtra(IntentParam.alipayAccount,bean.getAlipay_number());
                        intent.putExtra(IntentParam.alipayId,bean.getAlipay_id());
                        setResult(RESULT_OK,intent);
                        finish();
                    }
                });
            }
        };
        adapter.setOnLoadMoreListener(this);

        rv_zhifubao.setAdapter(adapter);
    }

    private void deleteAccount(String alipay_id) {

        showLoading();
        Map<String,String>map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("alipay_id",alipay_id);
        map.put("sign",getSign(map));
        ApiRequest.deleteAliAccount(map, new MyCallBack<BaseObj>(mContext,true) {
            @Override
            public void onSuccess(BaseObj obj, int errorCode, String msg) {
                showMsg(msg);
                getData(1,false);
            }
        });

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
        map.put("sign",getSign(map));
        ApiRequest.getAliAccountList(map, new MyCallBack<List<AliAccountListObj>>(mContext,pl_load,pcfl) {
            @Override
            public void onSuccess(List<AliAccountListObj> list, int errorCode, String msg) {
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

    @OnClick({R.id.app_right_iv})
    protected void onViewClick(View v) {
        switch (v.getId()){
            case R.id.app_right_iv:
                STActivityForResult(AddZhiFuBaoActivity.class,100);
            break;
        }
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
}
