package com.zhizhong.yujian.module.my.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;

import com.github.androidtools.PhoneUtils;
import com.github.androidtools.inter.MyOnClickListener;
import com.github.baseclass.adapter.MyRecyclerViewHolder;
import com.github.fastshape.MyTextView;
import com.github.mydialog.MyDialog;
import com.library.base.BaseObj;
import com.zhizhong.yujian.IntentParam;
import com.zhizhong.yujian.R;
import com.zhizhong.yujian.adapter.MyAdapter;
import com.zhizhong.yujian.base.BaseActivity;
import com.zhizhong.yujian.base.MyCallBack;
import com.zhizhong.yujian.module.my.network.ApiRequest;
import com.zhizhong.yujian.module.my.network.response.AddressObj;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class AddressListActivity extends BaseActivity {
    @BindView(R.id.rv_address_list)
    RecyclerView rv_address_list;
    @BindView(R.id.tv_address_list_add)
    MyTextView tv_address_list_add;

    MyAdapter adapter;

    @Override
    protected int getContentView() {
        setAppTitle("我的地址");
        return R.layout.address_list_act;
    }

    @Override
    protected void initView() {
        adapter=new MyAdapter<AddressObj>(mContext,R.layout.address_list_item,pageSize) {
            @Override
            public void bindData(MyRecyclerViewHolder holder, int position, final AddressObj bean) {
                holder.setText(R.id.tv_address_name,bean.getRecipient());
                holder.setText(R.id.tv_address_phone,bean.getPhone());
                holder.setText(R.id.tv_address_content,bean.getProvince()+bean.getCity()+bean.getArea()+bean.getShipping_address_details());

                CheckBox cb_address = (CheckBox) holder.getView(R.id.cb_address);
                cb_address.setChecked(bean.getIs_default()==1?true:false);
                cb_address.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setDefulat(bean.getAddress_id());
                    }
                });

                View tv_address_edit = holder.getView(R.id.tv_address_edit);
                tv_address_edit.setOnClickListener(new MyOnClickListener() {
                    @Override
                    protected void onNoDoubleClick(View view) {
                        Intent intent=new Intent();
                        intent.putExtra(IntentParam.addressBean,bean);
                        STActivityForResult(intent,AddAddressActivity.class,100);
                    }
                });
                View tv_address_delete = holder.getView(R.id.tv_address_delete);
                tv_address_delete.setOnClickListener(new MyOnClickListener() {
                    @Override
                    protected void onNoDoubleClick(View view) {
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
                                deleteAddress(bean.getAddress_id());
                            }
                        });
                        mDialog.create().show();
                    }
                });
            }
        };
        adapter.setOnLoadMoreListener(this);
        rv_address_list.setLayoutManager(new LinearLayoutManager(mContext));
        rv_address_list.addItemDecoration(getItemDivider(PhoneUtils.dip2px(mContext,5)));
        rv_address_list.setAdapter(adapter);

    }

    private void setDefulat(String address_id) {
        showLoading();
        Map<String,String>map=new HashMap<String,String>();
        map.put("userid",getUserId());
        map.put("address_id",address_id);
        map.put("sign",getSign(map));
        ApiRequest.setDefulatAddress(map, new MyCallBack<BaseObj>(mContext,true) {
            @Override
            public void onSuccess(BaseObj obj, int errorCode, String msg) {
                getData(1,false);
            }
        });

    }

    private void deleteAddress(String address_id) {
        showLoading();
        Map<String,String>map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("address_id",address_id);
        map.put("sign",getSign(map));
        ApiRequest.deleteAddress(map, new MyCallBack<BaseObj>(mContext,true) {
            @Override
            public void onSuccess(BaseObj obj, int errorCode, String msg) {
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
        ApiRequest.getAddressList(map, new MyCallBack<List<AddressObj>>(mContext,pl_load,pcfl) {
            @Override
            public void onSuccess(List<AddressObj> list, int errorCode, String msg) {
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

    @OnClick({R.id.tv_address_list_add})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.tv_address_list_add:
                STActivityForResult(AddAddressActivity.class,100);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            switch (requestCode){
                case 100:
                    getData(1,false);
                break;
            }
        }
    }
}
