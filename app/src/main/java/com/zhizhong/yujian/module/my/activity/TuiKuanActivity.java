package com.zhizhong.yujian.module.my.activity;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.androidtools.PhoneUtils;
import com.github.androidtools.inter.MyOnClickListener;
import com.github.baseclass.adapter.MyBaseRecyclerAdapter;
import com.github.baseclass.adapter.MyRecyclerViewHolder;
import com.github.mydialog.MySimpleDialog;
import com.github.rxbus.RxBus;
import com.github.rxbus.rxjava.MyFlowableSubscriber;
import com.library.base.BaseObj;
import com.library.base.tools.has.BitmapUtils;
import com.zhizhong.yujian.IntentParam;
import com.zhizhong.yujian.R;
import com.zhizhong.yujian.base.BaseActivity;
import com.zhizhong.yujian.base.GlideUtils;
import com.zhizhong.yujian.base.MyCallBack;
import com.zhizhong.yujian.module.mall.event.TuiKuanEvent;
import com.zhizhong.yujian.module.my.network.ApiRequest;
import com.zhizhong.yujian.module.my.network.response.TuiKuanMoneyObj;
import com.zhizhong.yujian.module.my.network.response.TuiKuanReasonObj;
import com.zhizhong.yujian.network.NetApiRequest;
import com.zhizhong.yujian.network.request.UploadImgBody;
import com.zhizhong.yujian.view.MyEditText;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.FlowableEmitter;
import io.reactivex.annotations.NonNull;
import top.zibin.luban.Luban;

public class TuiKuanActivity extends BaseActivity {
    @BindView(R.id.tv_tuikuan_type)
    TextView tv_tuikuan_type;
    @BindView(R.id.tv_tuikuan_shuoming)
    TextView tv_tuikuan_shuoming;
    @BindView(R.id.tv_tuikuan_reason)
    TextView tv_tuikuan_reason;
    @BindView(R.id.tv_tuikuan_money)
    TextView tv_tuikuan_money;
    @BindView(R.id.et_tuikuan_content)
    MyEditText et_tuikuan_content;
    @BindView(R.id.rv_tuikuan)
    RecyclerView rv_tuikuan;

    MyBaseRecyclerAdapter adapter;
    private String orderNo;
    private String tuiKuanType;
    private String reasonId;

    @Override
    protected int getContentView() {
        setAppTitle("申请退款");
        return R.layout.tuikuan_act;
    }

    @Override
    protected void initView() {
        orderNo = getIntent().getStringExtra(IntentParam.orderNo);

        adapter=new MyBaseRecyclerAdapter<String>(mContext,R.layout.tuikuan_item) {
            @Override
            public void bindData(MyRecyclerViewHolder holder,final int position, String bean) {
                View view = holder.getView(R.id.fl_fankui);
                View ll_fankui_add = holder.getView(R.id.ll_fankui_add);
                ImageView addImg = holder.getImageView(R.id.iv_fankui_add);
                if(getItemViewType(position)==1){
                    view.setVisibility(View.GONE);
                    addImg.setVisibility(View.VISIBLE);
                    ll_fankui_add.setVisibility(View.VISIBLE);

                    ll_fankui_add.setOnClickListener(new MyOnClickListener() {
                        @Override
                        protected void onNoDoubleClick(View view) {
                            addImg();
                        }
                    });
                }else{
                    view.setVisibility(View.VISIBLE);
                    addImg.setVisibility(View.GONE);
                    ll_fankui_add.setVisibility(View.GONE);

                    ImageView imageView = holder.getImageView(R.id.iv_fankui_img);
                    GlideUtils.into(mContext,bean,imageView);
                    holder.getImageView(R.id.iv_fankui_delete).setOnClickListener(new MyOnClickListener() {
                        @Override
                        protected void onNoDoubleClick(View view) {
                            mList.remove(position);
                            notifyDataSetChanged();
                        }
                    });
                }
            }

            @Override
            public int getItemViewType(int position) {
                if(mList==null||(position==mList.size()&&mList.size()<3)){
                    return 1;
                }else{
                    return 0;
                }
            }

            @Override
            public int getItemCount() {
                if(isEmpty(mList)){
                    tv_tuikuan_shuoming.setVisibility(View.VISIBLE);
                }else{
                    tv_tuikuan_shuoming.setVisibility(View.GONE);
                }
                if(mList!=null&&mList.size()>=3){
                    return mList.size();
                }else{
                    return mList==null?1:mList.size()+1;
                }
            }
        };
        List<String>list=new ArrayList<>();
        adapter.setList(list);
        rv_tuikuan.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL,false));
        rv_tuikuan.addItemDecoration(getItemDivider(PhoneUtils.dip2px(mContext,5),R.color.white));
        rv_tuikuan.setAdapter(adapter);

    }

    @Override
    protected void initData() {
        showProgress();
        getData(1,false);
    }

    @Override
    protected void getData(int page, boolean isLoad) {
        super.getData(page, isLoad);
        showLoading();
        Map<String,String> map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("order_no",orderNo);
        map.put("sign",getSign(map));
        ApiRequest.tuiKuanMoney(map, new MyCallBack<TuiKuanMoneyObj>(mContext,pl_load,pcfl) {
            @Override
            public void onSuccess(TuiKuanMoneyObj obj, int errorCode, String msg) {
                tv_tuikuan_money.setText("最多可退"+obj.getMoney()+"元");
            }
        });

    }

    private void addImg() {
        showSelectPhotoDialog();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            switch (requestCode){
                case result_take_photo:
                    uploadImg(takePhotoImgSavePath);
                    break;
                case result_select_photo:
                    String photoPath = getSelectPhotoPath(data);
                    uploadImg(photoPath);
                    break;
            }
        }
    }

    private void uploadImg(final String takePhotoImgSavePath) {
        showLoading();
        RXStart(new MyFlowableSubscriber<String>() {
            @Override
            public void subscribe(@NonNull FlowableEmitter<String> subscriber) {
                try {
                    List<File> files = Luban.with(mContext).load(takePhotoImgSavePath).get();
                    File file = files.get(0);
                    String imgStr = BitmapUtils.fileToString(file);
                    subscriber.onNext(imgStr);
                    subscriber.onComplete();
                } catch (Exception e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                }
            }
            @Override
            public void onNext(String base64) {
                Map<String,String>map=new HashMap<String,String>();
                map.put("rnd",getRnd());
                map.put("sign",getSign(map));
                UploadImgBody body=new UploadImgBody();
                body.setFile(base64);
                NetApiRequest.uploadImg(map,body, new MyCallBack<BaseObj>(mContext) {
                    @Override
                    public void onSuccess(BaseObj obj, int errorCode, String msg) {
                        adapter.getList().add(obj.getImg());
                        adapter.notifyDataSetChanged();
                    }
                });
            }
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                dismissLoading();
                showToastS("图片插入失败");
            }
        });

    }

    @OnClick({R.id.tv_tuikuan_type, R.id.tv_tuikuan_reason, R.id.tv_tuikuan_commit})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.tv_tuikuan_type:
                tuiKuanType();
                break;
            case R.id.tv_tuikuan_reason:
                tuiKuanReason();
                break;
            case R.id.tv_tuikuan_commit:
                String type = getSStr(tv_tuikuan_type);
                String reason = getSStr(tv_tuikuan_reason);
                String content = getSStr(et_tuikuan_content);
                if(TextUtils.isEmpty(type)){
                    showMsg("请选择退款类型");
                    return;
                }else if(TextUtils.isEmpty(reason)){
                    showMsg("请选择退款原因");
                    return;
                }else if(TextUtils.isEmpty(content)){
                    showMsg("请输入退款说明");
                    return;
                }
                tuiKuan();
                break;
        }
    }


    private void tuiKuan() {
        showLoading();
        Map<String,String>map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("order_no",orderNo);
        map.put("refund_type",tuiKuanType);
        map.put("refund_reason_id",reasonId);
        map.put("refund_instruction",getSStr(et_tuikuan_content));
        if(isEmpty(adapter.getList())){
            map.put("refund_voucher1","");
            map.put("refund_voucher2","");
            map.put("refund_voucher3","");
        }else if(adapter.getList().size()==1){
            map.put("refund_voucher1",adapter.getList().get(0).toString());
            map.put("refund_voucher2","");
            map.put("refund_voucher3","");
        }else if(adapter.getList().size()==2){
            map.put("refund_voucher1",adapter.getList().get(0).toString());
            map.put("refund_voucher2",adapter.getList().get(1).toString());
            map.put("refund_voucher3","");
        }else{
            map.put("refund_voucher1",adapter.getList().get(0).toString());
            map.put("refund_voucher2",adapter.getList().get(1).toString());
            map.put("refund_voucher3",adapter.getList().get(2).toString());
        }

        map.put("sign",getSign(map));
        ApiRequest.tuiKuan(map, new MyCallBack<BaseObj>(mContext) {
            @Override
            public void onSuccess(BaseObj obj, int errorCode, String msg) {
                RxBus.getInstance().post(new TuiKuanEvent());
                showMsg(msg);
                finish();
            }
        });
    }

    private void tuiKuanReason() {
        showLoading();
        Map<String,String>map=new HashMap<String,String>();
        map.put("rnd",getRnd());
        map.put("sign",getSign(map));
        ApiRequest.tuiKuanReason(map, new MyCallBack<List<TuiKuanReasonObj>>(mContext) {
            @Override
            public void onSuccess(List<TuiKuanReasonObj> list, int errorCode, String msg) {
                showReason(list);
            }
        });
    }
    private void showReason(final List<TuiKuanReasonObj> list) {
        final MySimpleDialog dialog=new MySimpleDialog(mContext);

        View dialogView = getLayoutInflater().inflate(R.layout.tuikuan_reason_popu, null);
        LinearLayout ll_tuikuan_reason=dialogView.findViewById(R.id.ll_tuikuan_reason);
        ll_tuikuan_reason.removeAllViews();
        for (int i = 0; i < list.size(); i++) {
            TextView textView=new TextView(mContext);
            textView.setText(list.get(i).getContent());
            textView.setTag(list.get(i).getId());
            textView.setHeight(PhoneUtils.dip2px(mContext,45));
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0,0,0,2);
            textView.setLayoutParams(layoutParams);
            textView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.white));
            textView.setGravity(Gravity.CENTER);
            final int finalI = i;
            textView.setOnClickListener(new MyOnClickListener() {
                @Override
                protected void onNoDoubleClick(View view) {
                    dialog.dismiss();
                    tv_tuikuan_reason.setText(list.get(finalI).getContent());
                    reasonId=list.get(finalI).getId();
                }
            });
            ll_tuikuan_reason.addView(textView);
        }

        dialogView.findViewById(R.id.tv_tuikuan_reason_cancel).setOnClickListener(new MyOnClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.setFullWidth();
        dialog.setGravity(Gravity.BOTTOM);
        dialog.setContentView(dialogView);
        dialog.show();

    }

    private void tuiKuanType() {
        final MySimpleDialog dialog=new MySimpleDialog(mContext);

        View dialogView = getLayoutInflater().inflate(R.layout.tuikuan_popu, null);
        dialogView.findViewById(R.id.tv_tuikuan_type_one).setOnClickListener(new MyOnClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
                dialog.dismiss();
                tv_tuikuan_type.setText("仅退款");
                tuiKuanType="1";
            }
        });
        dialogView.findViewById(R.id.tv_tuikuan_type_second).setOnClickListener(new MyOnClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
                dialog.dismiss();
                tv_tuikuan_type.setText("退货退款");
                tuiKuanType="2";
            }
        });
        dialogView.findViewById(R.id.tv_tuikuan_type_cancel).setOnClickListener(new MyOnClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.setFullWidth();
        dialog.setGravity(Gravity.BOTTOM);
        dialog.setContentView(dialogView);
        dialog.show();


    }
}
