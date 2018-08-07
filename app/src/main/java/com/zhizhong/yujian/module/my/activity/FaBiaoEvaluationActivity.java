package com.zhizhong.yujian.module.my.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.androidtools.PhoneUtils;
import com.github.androidtools.inter.MyOnClickListener;
import com.github.baseclass.BaseDividerListItem;
import com.github.baseclass.adapter.BaseRecyclerAdapter;
import com.github.baseclass.adapter.MyBaseRecyclerAdapter;
import com.github.baseclass.adapter.MyRecyclerViewHolder;
import com.github.baseclass.adapter.RecyclerViewHolder;
import com.github.mydialog.MyDialog;
import com.github.rxbus.RxBus;
import com.github.rxbus.rxjava.MyFlowableSubscriber;
import com.library.base.BaseObj;
import com.library.base.tools.has.BitmapUtils;
import com.willy.ratingbar.BaseRatingBar;
import com.zhizhong.yujian.IntentParam;
import com.zhizhong.yujian.R;
import com.zhizhong.yujian.base.BaseActivity;
import com.zhizhong.yujian.base.MyCallBack;
import com.zhizhong.yujian.module.my.event.RefreshMyOrderEvent;
import com.zhizhong.yujian.module.my.network.ApiRequest;
import com.zhizhong.yujian.module.my.network.request.FaBiaoEvaluationBody;
import com.zhizhong.yujian.module.my.network.response.FaBiaoEvaluationObj;
import com.zhizhong.yujian.network.NetApiRequest;
import com.zhizhong.yujian.network.request.UploadImgBody;

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

public class FaBiaoEvaluationActivity extends BaseActivity {
    @BindView(R.id.rv_fabiao_evaluation)
    RecyclerView rv_fabiao_evaluation;

    @BindView(R.id.tv_fabiao_evaluation)
    TextView tv_fabiao_evaluation;

    MyBaseRecyclerAdapter adapter;


    private BaseDividerListItem dividerListItem;
    private String orderNo;

    private int selectImgIndex, selectImgItemIndex;
    @Override
    protected int getContentView() {
        setAppTitle("发表评价");
        return R.layout.fabiao_evaluation_act;
    }

    @Override
    protected void initView() {
        dividerListItem=getItemDivider(PhoneUtils.dip2px(mContext,10),R.color.white);
        orderNo = getIntent().getStringExtra(IntentParam.orderNo);

        adapter=new MyBaseRecyclerAdapter<FaBiaoEvaluationObj>(mContext,R.layout.fabiao_evaluation_item) {
            @Override
            public MyRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                final MyRecyclerViewHolder holder=super.onCreateViewHolder(parent, viewType);
                EditText editText = holder.getEditText(R.id.et_fa_biao_content);
                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    }
                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    }
                    @Override
                    public void afterTextChanged(Editable editable) {
                        getList().get(holder.getAdapterPosition()).setContent(editable.toString());
                    }
                });
                final TextView tv_fa_biao_pingjia = holder.getTextView(R.id.tv_fa_biao_pingjia);
                BaseRatingBar rb_fa_biao_pingjia = (BaseRatingBar) holder.getView(R.id.brb_my_make_num);
                rb_fa_biao_pingjia.setOnRatingChangeListener(new BaseRatingBar.OnRatingChangeListener() {
                    @Override
                    public void onRatingChange(BaseRatingBar baseRatingBar, float rating) {
                        if(rating==1||rating==0){
                            tv_fa_biao_pingjia.setText("差");
                        }else if(rating==2||rating==3){
                            tv_fa_biao_pingjia.setText("一般");
                        }else{
                            tv_fa_biao_pingjia.setText("好");
                        }
                        mList.get(holder.getAdapterPosition()).setStars_num((int)rating);
                    }
                });
                return holder;
            }
            @Override
            public void bindData(MyRecyclerViewHolder holder,final int position, FaBiaoEvaluationObj bean) {
                ImageView imageView = holder.getImageView(R.id.iv_fa_biao_pingjia);
                Glide.with(mContext).load(bean.getGoods_images()).error(R.color.c_press).into(imageView);

                TextView tv_fa_biao_pingjia = holder.getTextView(R.id.tv_fa_biao_pingjia);
                if(bean.getStars_num()<=1){
                    tv_fa_biao_pingjia.setText("差");
                }else if(bean.getStars_num()>1&&bean.getStars_num()<=3){
                    tv_fa_biao_pingjia.setText("一般");
                }else{
                    tv_fa_biao_pingjia.setText("好");
                }

                BaseRatingBar brb_my_make_num = (BaseRatingBar) holder.getView(R.id.brb_my_make_num);
                brb_my_make_num.setRating(bean.getStars_num());


                EditText et_fa_biao_content = holder.getEditText(R.id.et_fa_biao_content);
                et_fa_biao_content.setText(getList().get(position).getContent());

                RecyclerView rv_fa_biao_addimg = (RecyclerView) holder.getView(R.id.rv_fa_biao_addimg);
                rv_fa_biao_addimg.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL,false));
                rv_fa_biao_addimg.removeItemDecoration(dividerListItem);
                rv_fa_biao_addimg.addItemDecoration(dividerListItem);

                BaseRecyclerAdapter addImgAdapter=new BaseRecyclerAdapter<String>(mContext,R.layout.tuikuan_item) {
                    @Override
                    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                        RecyclerViewHolder itemHolder;
                        View itemView = getLayoutInflater().inflate(R.layout.tuikuan_item, null);
                        View ll_fankui_add = itemView.findViewById(R.id.ll_fankui_add);
                        View fl_fankui = itemView.findViewById(R.id.fl_fankui);
                        itemHolder = new RecyclerViewHolder(this.mContext,itemView);

                        if(viewType==1){
                            ll_fankui_add.setVisibility(View.VISIBLE);
                            fl_fankui.setVisibility(View.GONE);
                        }else{
                            ll_fankui_add.setVisibility(View.GONE);
                            fl_fankui.setVisibility(View.VISIBLE);
                        }
                        return itemHolder;
                    }
                    @Override
                    public void bindData(RecyclerViewHolder itemHolder,final int itemPosition, String bean) {
                        itemHolder.itemView.setOnClickListener(new MyOnClickListener() {
                            @Override
                            protected void onNoDoubleClick(View view) {
                                if(itemPosition==mList.size()){
                                    selectImgItemIndex =-1;
                                    selectImgIndex =position;
                                }else{
                                    selectImgIndex =position;
                                    selectImgItemIndex =itemPosition;
                                }
                                addImg();
                            }
                        });
                        if(getItemViewType(itemPosition)==1){

                        }else{
                            ImageView imageView = itemHolder.getImageView(R.id.iv_fankui_img);
                            Glide.with(mContext).load(bean).error(R.color.c_press).into(imageView);
                            itemHolder.getImageView(R.id.iv_fankui_delete).setOnClickListener(new MyOnClickListener() {
                                @Override
                                protected void onNoDoubleClick(View view) {
                                    mList.remove(itemPosition);
                                    notifyDataSetChanged();
                                }
                            });
                        }
                    }
                    @Override
                    public int getItemViewType(int itemPosition) {
                        if(itemPosition==mList.size()&&mList.size()<3){
                            return 1;
                        }else{
                            return 0;
                        }
                    }
                    @Override
                    public int getItemCount() {
                        if(mList==null){
                            return 0;
                        }else if(mList.size()>=3){
                            return mList.size();
                        }else{
                            return mList==null?0:mList.size()+1;
                        }
                    }
                };
                addImgAdapter.setList(bean.getImage_list());
                rv_fa_biao_addimg.setAdapter(addImgAdapter);
            }
        };


        rv_fabiao_evaluation.setLayoutManager(new LinearLayoutManager(mContext));
        rv_fabiao_evaluation.addItemDecoration(getItemDivider(PhoneUtils.dip2px(mContext,10)));
        rv_fabiao_evaluation.setAdapter(adapter);
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
        map.put("order_no",orderNo);
        map.put("sign",getSign(map));
        ApiRequest.faBiaoEvaluationShow(map, new MyCallBack<List<FaBiaoEvaluationObj>>(mContext,pl_load,pcfl) {
            @Override
            public void onSuccess(List<FaBiaoEvaluationObj> list, int errorCode, String msg) {
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

    @OnClick({R.id.tv_fabiao_evaluation})
    protected void onViewClick(View v) {
        switch (v.getId()){
            case R.id.tv_fabiao_evaluation:
                MyDialog.Builder mDialog=new MyDialog.Builder(mContext);
                mDialog.setMessage("是否确认发布评价?");
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
                        pingJiaCommit();
                    }
                });
                mDialog.create().show();
            break;
        }
    }

    private void pingJiaCommit() {
        showLoading();
        Map<String,String>map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("order_no",orderNo);
        map.put("sign",getSign(map));
        FaBiaoEvaluationBody item=new FaBiaoEvaluationBody();
        item.setBody(adapter.getList());

        ApiRequest.faBiaoEvaluation(map,item,new MyCallBack<BaseObj>(mContext) {
            @Override
            public void onSuccess(BaseObj obj, int errorCode, String msg) {
                showMsg(obj.getMsg());
                RxBus.getInstance().post(new RefreshMyOrderEvent());
                finish();
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
                        if(selectImgItemIndex ==-1){
                            if(isEmpty(((FaBiaoEvaluationObj)adapter.getList().get(selectImgIndex)).getImage_list())){
                                //.setImage_list(list);
                                List<String> list=new ArrayList<String>();
                                list.add(obj.getImg());
                                ((FaBiaoEvaluationObj)adapter.getList().get(selectImgIndex)).setImage_list(list);
                            }else{
                                ((FaBiaoEvaluationObj)adapter.getList().get(selectImgIndex)).getImage_list().add(obj.getImg());
                            }
                        }else{
                            ((FaBiaoEvaluationObj)adapter.getList().get(selectImgIndex)).getImage_list().set(selectImgItemIndex,obj.getImg());
                        }
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
}
