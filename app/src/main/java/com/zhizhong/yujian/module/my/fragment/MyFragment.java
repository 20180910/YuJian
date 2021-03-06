package com.zhizhong.yujian.module.my.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.androidtools.SPUtils;
import com.github.fastshape.MyImageView;
import com.github.fastshape.MyTextView;
import com.zhizhong.yujian.AppXml;
import com.zhizhong.yujian.IntentParam;
import com.zhizhong.yujian.R;
import com.zhizhong.yujian.base.BaseFragment;
import com.zhizhong.yujian.base.GlideUtils;
import com.zhizhong.yujian.base.MyCallBack;
import com.zhizhong.yujian.module.auction.activity.PaiMaiOrderActivity;
import com.zhizhong.yujian.module.my.activity.AddressListActivity;
import com.zhizhong.yujian.module.my.activity.HelpCenterActivity;
import com.zhizhong.yujian.module.my.activity.KeMaiHuiActivity;
import com.zhizhong.yujian.module.my.activity.MessageActivity;
import com.zhizhong.yujian.module.my.activity.MyBaoZhengJinActivity;
import com.zhizhong.yujian.module.my.activity.MyCollectionActivity;
import com.zhizhong.yujian.module.my.activity.MyDataActivity;
import com.zhizhong.yujian.module.my.activity.MyEvaluationActivity;
import com.zhizhong.yujian.module.my.activity.MyMoneyActivity;
import com.zhizhong.yujian.module.my.activity.MyOrderActivity;
import com.zhizhong.yujian.module.my.activity.MyTuiKuanListActivity;
import com.zhizhong.yujian.module.my.activity.SettingActivity;
import com.zhizhong.yujian.module.my.activity.VIPActivity;
import com.zhizhong.yujian.module.my.activity.YouHuiQuanActivity;
import com.zhizhong.yujian.module.my.network.ApiRequest;
import com.zhizhong.yujian.module.my.network.response.LoginObj;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/6/20.
 */

public class MyFragment extends BaseFragment {


    @BindView(R.id.iv_my_setting)
    ImageView iv_my_setting;
    @BindView(R.id.iv_my_message)
    ImageView iv_my_message;
    @BindView(R.id.iv_my_bg)
    ImageView iv_my_bg;
    @BindView(R.id.iv_my)
    MyImageView iv_my;
    @BindView(R.id.tv_my_nickname)
    TextView tv_my_nickname;
    @BindView(R.id.tv_my_balance)
    TextView tv_my_balance;
    @BindView(R.id.tv_my_coupon)
    TextView tv_my_coupon;

    @BindView(R.id.tv_my_vip)
    MyTextView tv_my_vip;

    private Intent intent;

    @Override
    protected int getContentView() {
        return R.layout.my_frag;
    }

    public static MyFragment newInstance() {

        Bundle args = new Bundle();

        MyFragment fragment = new MyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initView() {
    }

    @Override
    public void onResume() {
        super.onResume();
        setUserInfo();
    }

    @Override
    protected void onMyReStart() {
        super.onMyReStart();
        getUserInfo();
    }

    private void getUserInfo() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("user_id", getUserId());
        map.put("sign", getSign(map));
        ApiRequest.getUserInfo(map, new MyCallBack<LoginObj>(mContext) {
            @Override
            public void onSuccess(LoginObj obj, int errorCode, String msg) {
                setLoginObj(obj);
                setUserInfo();
            }
        });

    }
    private void setLoginObj(LoginObj obj) {
        SPUtils.setPrefString(mContext,AppXml.userId,obj.getUser_id());
        SPUtils.setPrefString(mContext,AppXml.mobile,obj.getMobile());
        SPUtils.setPrefString(mContext,AppXml.avatar,obj.getAvatar());
        SPUtils.setPrefString(mContext,AppXml.nick_name,obj.getNick_name());
        SPUtils.setPrefString(mContext,AppXml.sex,obj.getSex());
        SPUtils.setPrefString(mContext,AppXml.birthday,obj.getBirthday());
        SPUtils.setPrefString(mContext,AppXml.amount,obj.getAmount()+"");

        SPUtils.setPrefInt(mContext,AppXml.coupons_count,obj.getCoupons_count());
        SPUtils.setPrefInt(mContext,AppXml.message_sink,obj.getMessage_sink());

        SPUtils.setPrefString(mContext,AppXml.qq_name,obj.getQq_name());
        SPUtils.setPrefString(mContext,AppXml.wechat_name,obj.getWechat_name());

        SPUtils.setPrefString(mContext,AppXml.bg_img,obj.getBg_img());
        SPUtils.setPrefInt(mContext,AppXml.vip_levle,obj.getVip_levle());
    }



    private void setUserInfo() {

        String avatar = SPUtils.getString(mContext, AppXml.avatar, "");
        String bg_img = SPUtils.getString(mContext, AppXml.bg_img, "");
        String nick_name =SPUtils.getString(mContext,AppXml.nick_name,"");
        int coupons_count =SPUtils.getInt(mContext,AppXml.coupons_count,0);
        String amount =SPUtils.getString(mContext,AppXml.amount,"");
        int vip =SPUtils.getInt(mContext,AppXml.vip_levle,0);

            GlideUtils.intoD(mContext,avatar,iv_my,R.drawable.default_person);
            GlideUtils.intoD(mContext,bg_img,iv_my_bg,R.drawable.my_bg);

            tv_my_nickname.setText(nick_name);
            tv_my_balance.setText("¥"+amount);
            tv_my_coupon.setText(coupons_count+"");

        if(vip==1){
            tv_my_vip.setText("VIP");
            tv_my_vip.setTextColor(Color.parseColor("#FFE0D28F"));
            tv_my_vip.getViewHelper().setBorderColor(Color.parseColor("#FFE0D28F")).complete();
            tv_my_vip.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.vip,0,0,0);
        }else{
            tv_my_vip.setText("普通用户");
            tv_my_vip.setTextColor(Color.parseColor("#FF9A9A9A"));
            tv_my_vip.getViewHelper().setBorderColor(Color.parseColor("#FF9A9A9A")).complete();
            tv_my_vip.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,0,0);
            tv_my_vip.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
        }

    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.tv_my_vip,R.id.ll_my_yue,R.id.ll_my_youhuiquan,R.id.iv_my,R.id.iv_my_setting, R.id.iv_my_message, R.id.ll_my_allorder, R.id.ll_my_daifukuan, R.id.ll_my_daifahuo,R.id.ll_my_daishouhuo, R.id.ll_my_daipingjia, R.id.tv_my_wodepaimai, R.id.tv_my_wodetuikuan, R.id.tv_my_wodekemaihui, R.id.tv_my_wodeshoucang, R.id.tv_my_wodedizhi, R.id.tv_my_wodebaozhengjin, R.id.tv_my_wodepingjia, R.id.tv_my_help})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.tv_my_vip:
                STActivity(VIPActivity.class);
                break;
            case R.id.ll_my_yue:
                STActivity(MyMoneyActivity.class);
                break;
            case R.id.ll_my_youhuiquan:
                STActivity(YouHuiQuanActivity.class);
                break;
            case R.id.iv_my:
                STActivity(MyDataActivity.class);
                break;
            case R.id.iv_my_setting:
                STActivity(SettingActivity.class);
                break;
            case R.id.iv_my_message:
                STActivity(MessageActivity.class);
                break;
            case R.id.ll_my_allorder:
                STActivity(MyOrderActivity.class);
                break;
            case R.id.ll_my_daifukuan:
                intent = new Intent();
                intent.putExtra(IntentParam.type,MyOrderFragment.type_1);
                STActivity(intent,MyOrderActivity.class);
                break;
            case R.id.ll_my_daifahuo:
                intent = new Intent();
                intent.putExtra(IntentParam.type,MyOrderFragment.type_2);
                STActivity(intent,MyOrderActivity.class);
                break;
            case R.id.ll_my_daishouhuo:
                intent = new Intent();
                intent.putExtra(IntentParam.type,MyOrderFragment.type_3);
                STActivity(intent,MyOrderActivity.class);
                break;
            case R.id.ll_my_daipingjia:
                intent = new Intent();
                intent.putExtra(IntentParam.type,MyOrderFragment.type_4);
                STActivity(intent,MyOrderActivity.class);
                break;
            case R.id.tv_my_wodepaimai:
                STActivity(PaiMaiOrderActivity.class);
                break;
            case R.id.tv_my_wodetuikuan:
                STActivity(MyTuiKuanListActivity.class);
                break;
            case R.id.tv_my_wodekemaihui:
                STActivity(KeMaiHuiActivity.class);
                break;
            case R.id.tv_my_wodeshoucang:
                STActivity(MyCollectionActivity.class);
                break;
            case R.id.tv_my_wodedizhi:
                STActivity(AddressListActivity.class);
                break;
            case R.id.tv_my_wodebaozhengjin:
                STActivity(MyBaoZhengJinActivity.class);
                break;
            case R.id.tv_my_wodepingjia:
                STActivity(MyEvaluationActivity.class);
                break;
            case R.id.tv_my_help:
                STActivity(HelpCenterActivity.class);
                break;
        }
    }
}