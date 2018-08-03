package com.zhizhong.yujian.module.my.activity;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.github.androidtools.inter.MyOnClickListener;
import com.github.baseclass.adapter.MyRecyclerViewHolder;
import com.library.base.BaseObj;
import com.zhizhong.yujian.IntentParam;
import com.zhizhong.yujian.R;
import com.zhizhong.yujian.adapter.MyAdapter;
import com.zhizhong.yujian.base.BaseActivity;
import com.zhizhong.yujian.base.GlideUtils;
import com.zhizhong.yujian.base.MyCallBack;
import com.zhizhong.yujian.module.my.network.ApiRequest;
import com.zhizhong.yujian.module.my.network.response.BankAccountObj;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class BankListActivity extends BaseActivity {
    @BindView(R.id.rv_bank_list)
    RecyclerView rv_bank_list;

    MyAdapter adapter;

    @Override
    protected int getContentView() {
        setAppTitle("选择银行卡");
        setAppRightImg(R.drawable.add_right);
        return R.layout.bank_list_act;
    }

    @Override
    protected void initView() {
        pcfl.disableWhenHorizontalMove(true);
        adapter=new MyAdapter<BankAccountObj>(mContext,R.layout.bank_list_item,pageSize) {
            @Override
            public void bindData(MyRecyclerViewHolder holder, int position, final BankAccountObj bean) {
                ImageView imageView = holder.getImageView(R.id.iv_bank_account);
                GlideUtils.into(mContext,bean.getBank_image(),imageView);

                holder.setText(R.id.tv_bank_account_name,bean.getBank_name());
                holder.setText(R.id.tv_bank_account_card,bean.getBank_card());

                holder.getView(R.id.ll_bank_account).setOnClickListener(new MyOnClickListener() {
                    @Override
                    protected void onNoDoubleClick(View view) {
                        Intent intent=new Intent();
                        intent.putExtra(IntentParam.bankAccount,bean.getBank_card());
                        intent.putExtra(IntentParam.bankId,bean.getId());
                        setResult(RESULT_OK,intent);
                        finish();
                    }
                });
                holder.getView(R.id.tv_bank_account_delete).setOnClickListener(new MyOnClickListener() {
                    @Override
                    protected void onNoDoubleClick(View view) {
                        deleteBankAccount(bean.getId());
                    }
                });
            }
        };
        adapter.setOnLoadMoreListener(this);

        rv_bank_list.setAdapter(adapter);
    }

    private void deleteBankAccount(String id) {
        showLoading();
        Map<String,String>map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("account_id",id);
        map.put("sign",getSign(map));
        ApiRequest.deleteBankAccount(map, new MyCallBack<BaseObj>(mContext,true) {
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
        ApiRequest.getBankAccount(map, new MyCallBack<List<BankAccountObj>>(mContext,pl_load,pcfl) {
            @Override
            public void onSuccess(List<BankAccountObj> list, int errorCode, String msg) {
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
                STActivityForResult(AddBankActivity.class,100);
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
