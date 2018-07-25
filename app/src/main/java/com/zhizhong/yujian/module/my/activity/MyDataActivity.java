package com.zhizhong.yujian.module.my.activity;

import android.content.Intent;
import android.support.design.widget.BottomSheetDialog;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.github.androidtools.DateUtils;
import com.github.androidtools.SPUtils;
import com.github.androidtools.inter.MyOnClickListener;
import com.github.fastshape.MyImageView;
import com.github.rxbus.rxjava.MyFlowableSubscriber;
import com.library.base.BaseObj;
import com.library.base.tools.has.BitmapUtils;
import com.zhizhong.yujian.AppXml;
import com.zhizhong.yujian.R;
import com.zhizhong.yujian.base.BaseActivity;
import com.zhizhong.yujian.base.GlideUtils;
import com.zhizhong.yujian.base.MyCallBack;
import com.zhizhong.yujian.module.my.network.ApiRequest;
import com.zhizhong.yujian.module.my.network.request.UpdateInfoBody;
import com.zhizhong.yujian.network.NetApiRequest;
import com.zhizhong.yujian.network.request.UploadImgBody;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.FlowableEmitter;
import io.reactivex.annotations.NonNull;
import top.zibin.luban.Luban;

public class MyDataActivity extends BaseActivity {
    @BindView(R.id.iv_mydata_img)
    MyImageView iv_mydata_img;
    @BindView(R.id.tv_mydata_nickname)
    TextView tv_mydata_nickname;
    @BindView(R.id.tv_mydata_birthday)
    TextView tv_mydata_birthday;
    @BindView(R.id.tv_mydata_sex)
    TextView tv_mydata_sex;

    @Override
    protected int getContentView() {
        setAppTitle("我的资料");
        return R.layout.my_data_act;
    }

    @Override
    protected void initView() {
        String imgUrl = SPUtils.getString(mContext, AppXml.avatar, null);
        GlideUtils.intoD(mContext,imgUrl,iv_mydata_img,R.drawable.default_person);

        setInfo();
    }

    private void setInfo() {
        String nickName = SPUtils.getString(mContext, AppXml.nick_name, null);
        String birthday = SPUtils.getString(mContext, AppXml.birthday, null);
        String sex = SPUtils.getString(mContext, AppXml.sex, null);
        tv_mydata_nickname.setText(nickName);
        tv_mydata_birthday.setText(birthday);
        tv_mydata_sex.setText(sex);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        setInfo();
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.ll_mydata_img, R.id.ll_mydata_nickname, R.id.ll_mydata_birthday, R.id.ll_mydata_sex})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.ll_mydata_img:
                showSelectPhotoDialog();
                break;
            case R.id.ll_mydata_nickname:
                STActivity(SetNameActivity.class);
                break;
            case R.id.ll_mydata_birthday:
                selectBirthday();
                break;
            case R.id.ll_mydata_sex:
                final BottomSheetDialog dialog=new BottomSheetDialog(mContext);
                View selectSexView = getLayoutInflater().inflate(R.layout.sex_popu, null);
                selectSexView.findViewById(R.id.tv_select_sex_boy).setOnClickListener(new MyOnClickListener() {
                    @Override
                    protected void onNoDoubleClick(View view) {
                        dialog.dismiss();
                        updateSex("男");
                    }
                });
                selectSexView.findViewById(R.id.tv_select_sex_girl).setOnClickListener(new MyOnClickListener() {
                    @Override
                    protected void onNoDoubleClick(View view) {
                        dialog.dismiss();
                        updateSex("女");
                    }
                });
                selectSexView.findViewById(R.id.tv_select_sex_cancel).setOnClickListener(new MyOnClickListener() {
                    @Override
                    protected void onNoDoubleClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.setContentView(selectSexView);
                dialog.show();
                break;
        }
    }

    private void selectBirthday() {
        TimePickerView pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                String birthday= DateUtils.dateToString(date,"yyyy-MM-dd");
                updateBirthDay(birthday);
            }

            private void updateBirthDay(final String birthday) {
                showLoading();
                Map<String,String> map=new HashMap<String,String>();
                map.put("user_id",getUserId());
                map.put("sign",getSign(map));
                UpdateInfoBody body=new UpdateInfoBody();
                body.setBirthday(birthday);
                ApiRequest.updateUserInfo(map,body, new MyCallBack<BaseObj>(mContext) {
                    @Override
                    public void onSuccess(BaseObj obj, int errorCode, String msg) {
                        showMsg(msg);
                        SPUtils.setPrefString(mContext,AppXml.birthday,birthday);
                        tv_mydata_birthday.setText(birthday);
                    }
                });

            }
        }).setRange(1950, Calendar.getInstance().get(Calendar.YEAR)).setType(new boolean[]{true, true, true, false, false, false}).build();
        pvTime.show();
    }

    private void updateSex(final String sex) {
        showLoading();
        Map<String,String> map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("sign",getSign(map));
        UpdateInfoBody body=new UpdateInfoBody();
        body.setSex(sex);
        ApiRequest.updateUserInfo(map,body, new MyCallBack<BaseObj>(mContext) {
            @Override
            public void onSuccess(BaseObj obj, int errorCode, String msg) {
                showMsg(msg);
                SPUtils.setPrefString(mContext,AppXml.sex,sex);
                tv_mydata_sex.setText(sex);
            }
        });

    }
    private void updateIMG(final String photoPath) {
        showLoading();
        RXStart(new MyFlowableSubscriber<String>() {
            @Override
            public void subscribe(@NonNull FlowableEmitter<String> subscriber) {
                try {
                    List<File> files = Luban.with(mContext).load(photoPath).get();
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
                Map<String,String> map=new HashMap<String,String>();
                map.put("rnd",getRnd());
                map.put("sign",getSign(map));
                UploadImgBody body=new UploadImgBody();
                body.setFile(base64);
                NetApiRequest.uploadImg(map,body, new MyCallBack<BaseObj>(mContext,true) {
                    @Override
                    public void onSuccess(BaseObj obj, int errorCode, String msg) {
                        upLoadUserImg(obj.getImg());
                    }
                });
            }
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                dismissLoading();
                showToastS("更改用户头像失败");
            }
        });
    }
    private void upLoadUserImg(final String img) {
        Map<String,String>map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("avatar",img);
        map.put("sign",getSign(map));
        NetApiRequest.setUserImg(map, new MyCallBack<BaseObj>(mContext) {
            @Override
            public void onSuccess(BaseObj obj, int errorCode, String msg) {
                SPUtils.setPrefString(mContext,AppXml.avatar,img);

                GlideUtils.intoD(mContext,img,iv_mydata_img,R.drawable.default_person);
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            switch (requestCode){
                case result_select_photo:
                    if(data!=null){
                        String photoPath = getSelectPhotoPath(data);
                        updateIMG(photoPath);
                    }
                    break;
                case result_take_photo:
                    updateIMG(takePhotoImgSavePath);
                    break;
            }
        }
    }

}
