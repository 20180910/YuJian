package com.zhizhong.yujian.module.my.activity;

import android.text.TextUtils;
import android.view.View;

import com.github.androidtools.SPUtils;
import com.github.androidtools.inter.MyOnClickListener;
import com.github.fastshape.MyTextView;
import com.library.base.BaseObj;
import com.sdklibrary.base.qq.share.MyQQLoginCallback;
import com.sdklibrary.base.qq.share.MyQQShare;
import com.sdklibrary.base.qq.share.bean.MyQQUserInfo;
import com.sdklibrary.base.wx.share.MyWXLoginCallback;
import com.sdklibrary.base.wx.share.MyWXShare;
import com.sdklibrary.base.wx.share.MyWXUserInfo;
import com.zhizhong.yujian.AppXml;
import com.zhizhong.yujian.R;
import com.zhizhong.yujian.base.BaseActivity;
import com.zhizhong.yujian.base.MyCallBack;
import com.zhizhong.yujian.module.my.network.ApiRequest;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

public class AccountManageActivity extends BaseActivity {
    @BindView(R.id.tv_account_manage_wx)
    MyTextView tv_account_manage_wx;
    @BindView(R.id.tv_account_manage_wx_status)
    MyTextView tv_account_manage_wx_status;
    @BindView(R.id.tv_account_manage_qq)
    MyTextView tv_account_manage_qq;
    @BindView(R.id.tv_account_manage_qq_status)
    MyTextView tv_account_manage_qq_status;

    @Override
    protected int getContentView() {
        setAppTitle("第三方登录账户管理");
        return R.layout.account_manage_act;
    }

    @Override
    protected void initView() {
        setBind();
    }
    private void setBind() {
        String qq = SPUtils.getString(mContext, AppXml.qq_name, null);
        String wx = SPUtils.getString(mContext, AppXml.wechat_name, null);
        if(TextUtils.isEmpty(qq)){
            tv_account_manage_qq_status.setText("未绑定");
            tv_account_manage_qq.setOnClickListener(new MyOnClickListener() {
                @Override
                protected void onNoDoubleClick(View view) {
                    showLoading();
                    MyQQShare.newInstance(mContext).login(new MyQQLoginCallback() {
                        @Override
                        public void loginSuccess(MyQQUserInfo myQQUserInfo) {
                            loginForApp("1",myQQUserInfo.getOpenid(),myQQUserInfo.getNickname(),myQQUserInfo.getUserImageUrl());
                        }
                        @Override
                        public void loginFail() {
                            dismissLoading();
                            showMsg("授权失败");
                        }

                        @Override
                        public void loginCancel() {
                            dismissLoading();
                            showMsg("取消授权");
                        }
                    });
                }
            });
        }else{
            tv_account_manage_qq_status.setText(qq);
        }
        if(TextUtils.isEmpty(wx)){
            tv_account_manage_wx_status.setText("未绑定");
            tv_account_manage_wx.setOnClickListener(new MyOnClickListener() {
                @Override
                protected void onNoDoubleClick(View view) {
                    MyWXShare.newInstance(mContext).login(new MyWXLoginCallback() {
                        @Override
                        public void loginSuccess(MyWXUserInfo myWXUserInfo) {
                            loginForApp("2",myWXUserInfo.getUnionid(),myWXUserInfo.getNickname(),myWXUserInfo.getHeadimgurl());
                        }
                        @Override
                        public void loginFail() {
                            dismissLoading();
                            showMsg("授权失败");
                        }
                        @Override
                        public void loginCancel() {
                            dismissLoading();
                            showMsg("取消授权");
                        }
                    });
                }
            });
        }else{
            tv_account_manage_wx_status.setText(wx);
        }
    }

    public void loginForApp(final String type,String uid,final String nickname,String avatar ){
        Map<String,String> map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("type",type);
        map.put("open",uid);
        map.put("nickname",nickname);
        map.put("sign",getSign(map));
        ApiRequest.bindForApp(map, new MyCallBack<BaseObj>(mContext) {
            @Override
            public void onSuccess(BaseObj obj, int errorCode, String msg) {
                showMsg(msg);
                if("1".equals(type)){
                    SPUtils.setPrefString(mContext,AppXml.qq_name,nickname);
                }else{
                    SPUtils.setPrefString(mContext,AppXml.wechat_name,nickname);
                }
                setBind();
            }
        });

    }
    @Override
    protected void initData() {

    }
    public void onViewClick(View view) {
    }
}
