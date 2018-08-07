package com.zhizhong.yujian.module.my.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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
import com.github.baseclass.adapter.RecyclerViewHolder;
import com.github.fastshape.FlowLayout;
import com.github.mydialog.MyDialog;
import com.github.rxbus.rxjava.MyFlowableSubscriber;
import com.library.base.BaseObj;
import com.library.base.tools.has.BitmapUtils;
import com.willy.ratingbar.BaseRatingBar;
import com.zhizhong.yujian.IntentParam;
import com.zhizhong.yujian.R;
import com.zhizhong.yujian.base.BaseActivity;
import com.zhizhong.yujian.base.MyCallBack;
import com.zhizhong.yujian.module.my.network.ApiRequest;
import com.zhizhong.yujian.module.my.network.response.EvaluationDetailObj;
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

public class AgainEvaluationActivity extends BaseActivity {

    @BindView(R.id.et_evaluate_detail)
    EditText et_evaluate_detail;
    @BindView(R.id.iv_evaluate_detail)
    ImageView iv_evaluate_detail;
    @BindView(R.id.brb_again_make_num)
    BaseRatingBar brb_again_make_num;
    @BindView(R.id.tv_evaluate_detail_star)
    TextView tv_evaluate_detail_star;
    @BindView(R.id.tv_evaluate_detail_content)
    TextView tv_evaluate_detail_content;
    @BindView(R.id.tv_evaluate_detail_commit)
    TextView tv_evaluate_detail_commit;
    @BindView(R.id.fl_evaluate_detail)
    FlowLayout fl_evaluate_detail;
    @BindView(R.id.rv_evaluate_detail_addimg)
    RecyclerView rv_evaluate_detail_addimg;

    private BaseDividerListItem dividerListItem;

    private int selectImgIndex;
    private BottomSheetDialog selectPhotoDialog;


    private String evaluateId;
    private BaseRecyclerAdapter addImgAdapter;

    private boolean isLookEvaluate;

    @Override
    protected int getContentView() {
        setAppTitle("发表评价");
        return R.layout.again_evaluate_act;
    }

    @Override
    protected void initView() {
        evaluateId = getIntent().getStringExtra(IntentParam.evaluateId);
        boolean isZhuiPing = getIntent().getBooleanExtra(IntentParam.zhuiPing, false);
        isLookEvaluate=!isZhuiPing;

        if(isLookEvaluate){
            tv_evaluate_detail_commit.setVisibility(View.GONE);
            et_evaluate_detail.setEnabled(false);
        }

        dividerListItem=new BaseDividerListItem(mContext,PhoneUtils.dip2px(mContext,10),R.color.white);
        addImgAdapter = new BaseRecyclerAdapter<String>(mContext, R.layout.tuikuan_item) {
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
                if(!isLookEvaluate){
                    itemHolder.itemView.setOnClickListener(new MyOnClickListener() {
                        @Override
                        protected void onNoDoubleClick(View view) {
                            if(itemPosition==mList.size()){
                                selectImgIndex =-1;
                            }else{
                                selectImgIndex =itemPosition;
                            }
                            addImg();
                        }
                    });
                }

                if(getItemViewType(itemPosition)==1){


                }else{
                    ImageView imageView = itemHolder.getImageView(R.id.iv_fankui_img);
                    Glide.with(mContext).load(bean).error(R.color.c_press).into(imageView);

                    if(isLookEvaluate){
                        itemHolder.getImageView(R.id.iv_fankui_delete).setVisibility(View.GONE);
                    }else{
                        itemHolder.getImageView(R.id.iv_fankui_delete).setVisibility(View.VISIBLE);
                        itemHolder.getImageView(R.id.iv_fankui_delete).setOnClickListener(new MyOnClickListener() {
                            @Override
                            protected void onNoDoubleClick(View view) {
                                mList.remove(itemPosition);
                                notifyDataSetChanged();
                            }
                        });
                    }
                }
            }
            @Override
            public int getItemViewType(int itemPosition) {
                if(isLookEvaluate){
                    return 0;
                }
                if(itemPosition==mList.size()&&mList.size()<3){
                    return 1;
                }else{
                    return 0;
                }
            }
            @Override
            public int getItemCount() {
                if(isLookEvaluate){
                    return mList==null?0:mList.size();
                }
                if(mList==null){
                    return 0;
                }else if(mList.size()>=3){
                    return mList.size();
                }else{
                    return mList==null?0:mList.size()+1;
                }
            }
        };
        addImgAdapter.setList(new ArrayList());
        rv_evaluate_detail_addimg.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL,false));
        rv_evaluate_detail_addimg.removeItemDecoration(dividerListItem);
        rv_evaluate_detail_addimg.addItemDecoration(dividerListItem);
        rv_evaluate_detail_addimg.setAdapter(addImgAdapter);

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
                        if(selectImgIndex ==-1){
                            if(isEmpty(addImgAdapter.getList())){
                                List<String> list=new ArrayList<String>();
                                list.add(obj.getImg());
                                addImgAdapter.setList(list);
                            }else{
                                addImgAdapter.getList().add(obj.getImg());
                            }
                        }else{
                            addImgAdapter.getList().set(selectImgIndex,obj.getImg());
                        }
                        addImgAdapter.notifyDataSetChanged();
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
    @Override
    protected void initData() {
        showProgress();
        getData(1,false);
    }

    @Override
    protected void getData(int page, boolean isLoad) {
        super.getData(page, isLoad);
        Map<String,String> map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("appraise_id",evaluateId);
        map.put("sign",getSign(map));
        ApiRequest.evaluationDetail(map, new MyCallBack<EvaluationDetailObj>(mContext,pl_load,pcfl) {
            @Override
            public void onSuccess(EvaluationDetailObj obj, int errorCode, String msg) {

                Glide.with(mContext).load(obj.getGoods_img()).error(R.color.c_press).into(iv_evaluate_detail);
                brb_again_make_num.setNumStars(5);
                brb_again_make_num.setRating(obj.getNumber_stars());
                tv_evaluate_detail_star.setText(obj.getType());
                if(TextUtils.isEmpty(obj.getContent())){
                    tv_evaluate_detail_content.setText("暂无评价");
                }else{
                    tv_evaluate_detail_content.setText(obj.getContent());
                }
                fl_evaluate_detail.removeAllViews();
                if(notEmpty(obj.getImage_list())){
                    for (int i = 0; i < obj.getImage_list().size(); i++) {
                        ImageView imageView=new ImageView(mContext);
                        FlowLayout.LayoutParams layoutParams = new FlowLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        layoutParams.width= PhoneUtils.dip2px(mContext,100);
                        layoutParams.height=PhoneUtils.dip2px(mContext,100);
                        layoutParams.setMargins(0,0,PhoneUtils.dip2px(mContext,10),0);
                        imageView.setLayoutParams(layoutParams);
                        imageView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.c_press));
                        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        Glide.with(mContext).load(obj.getImage_list().get(i)).error(R.color.c_press).into(imageView);
                        fl_evaluate_detail.addView(imageView);
                    }
                }
                if(isLookEvaluate){
                    if(obj.getAfter_review()!=null){
                        et_evaluate_detail.setText(obj.getAfter_review().getContent());
                        if(notEmpty(obj.getAfter_review().getImage_list())){
                            List<String>list=new ArrayList<>();
                            for (int i = 0; i < obj.getAfter_review().getImage_list().size(); i++) {
                                list.add(obj.getAfter_review().getImage_list().get(i));
                            }
                            addImgAdapter.setList(list,true);
                        }
                    }
                }
            }
        });

    }

    @OnClick({R.id.tv_evaluate_detail_commit})
    protected void onViewClick(View v) {
        switch (v.getId()){
            case R.id.tv_evaluate_detail_commit:
                if(TextUtils.isEmpty(getSStr(et_evaluate_detail))){
                    showMsg("请输入评价内容");
                    return;
                }
                MyDialog.Builder mDialog=new MyDialog.Builder(mContext);
                mDialog.setMessage("确认发布追加评论吗?");
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
                        evaluateCommit();
                    }
                });
                mDialog.create().show();
            break;
        }
    }

    private void evaluateCommit() {
        showLoading();
        Map<String,String>map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("appraise_id",evaluateId);
        map.put("sign",getSign(map));

        FaBiaoEvaluationObj item=new FaBiaoEvaluationObj();
        item.setContent(getSStr(et_evaluate_detail));
        item.setImage_list(addImgAdapter.getList());

        ApiRequest.againEvaluate(map,item, new MyCallBack<BaseObj>(mContext){
            @Override
            public void onSuccess(BaseObj obj, int errorCode, String msg) {
                showMsg(msg);
                setResult(RESULT_OK);
                finish();
            }
        });

    }
}
