package com.zhizhong.yujian.module.my.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.androidtools.SPUtils;
import com.github.fastshape.MyImageView;
import com.zhizhong.yujian.AppXml;
import com.zhizhong.yujian.IntentParam;
import com.zhizhong.yujian.R;
import com.zhizhong.yujian.base.BaseFragment;
import com.zhizhong.yujian.base.GlideUtils;
import com.zhizhong.yujian.base.MyCallBack;
import com.zhizhong.yujian.module.auction.activity.PaiMaiOrderActivity;
import com.zhizhong.yujian.module.my.activity.AddressListActivity;
import com.zhizhong.yujian.module.my.activity.HelpCenterActivity;
import com.zhizhong.yujian.module.my.activity.MessageActivity;
import com.zhizhong.yujian.module.my.activity.MyCollectionActivity;
import com.zhizhong.yujian.module.my.activity.MyDataActivity;
import com.zhizhong.yujian.module.my.activity.MyOrderActivity;
import com.zhizhong.yujian.module.my.activity.SettingActivity;
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
    @BindView(R.id.iv_my)
    MyImageView iv_my;
    @BindView(R.id.tv_my_nickname)
    TextView tv_my_nickname;
    @BindView(R.id.tv_my_balance)
    TextView tv_my_balance;
    @BindView(R.id.tv_my_coupon)
    TextView tv_my_coupon;
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
    }



    private void setUserInfo() {

        String avatar = SPUtils.getString(mContext, AppXml.avatar, "");
        String nick_name =SPUtils.getString(mContext,AppXml.nick_name,"");
        int coupons_count =SPUtils.getInt(mContext,AppXml.coupons_count,0);
        String amount =SPUtils.getString(mContext,AppXml.amount,"");

            GlideUtils.intoD(mContext,avatar,iv_my,R.drawable.default_person);

            tv_my_nickname.setText(nick_name);
            tv_my_balance.setText("¥"+coupons_count);
            tv_my_coupon.setText(amount+"");
    }


    @Override
    protected void initData() {

    }

    @OnClick({R.id.iv_my,R.id.iv_my_setting, R.id.iv_my_message, R.id.ll_my_allorder, R.id.ll_my_daifukuan, R.id.ll_my_daifahuo, R.id.ll_my_daipingjia, R.id.tv_my_wodepaimai, R.id.tv_my_wodetuikuan, R.id.tv_my_wodekemaihui, R.id.tv_my_wodeshoucang, R.id.tv_my_wodedizhi, R.id.tv_my_wodebaozhengjin, R.id.tv_my_wodepingjia, R.id.tv_my_help})
    public void onViewClick(View view) {
        switch (view.getId()) {
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
            case R.id.ll_my_daipingjia:
                intent = new Intent();
                intent.putExtra(IntentParam.type,MyOrderFragment.type_4);
                STActivity(intent,MyOrderActivity.class);
                break;
            case R.id.tv_my_wodepaimai:
                STActivity(PaiMaiOrderActivity.class);
                break;
            case R.id.tv_my_wodetuikuan:
                break;
            case R.id.tv_my_wodekemaihui:
                break;
            case R.id.tv_my_wodeshoucang:
                STActivity(MyCollectionActivity.class);
                break;
            case R.id.tv_my_wodedizhi:
                STActivity(AddressListActivity.class);
                break;
            case R.id.tv_my_wodebaozhengjin:
                break;
            case R.id.tv_my_wodepingjia:
                break;
            case R.id.tv_my_help:
                STActivity(HelpCenterActivity.class);
                break;
        }
    }
}