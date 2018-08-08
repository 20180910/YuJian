package com.zhizhong.yujian.module.my.activity;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.github.androidtools.PhoneUtils;
import com.github.fastshape.MyTextView;
import com.zhizhong.yujian.R;
import com.zhizhong.yujian.base.BaseActivity;
import com.zhizhong.yujian.base.MyCallBack;
import com.zhizhong.yujian.module.my.network.ApiRequest;
import com.zhizhong.yujian.module.my.network.response.VIPObj;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

public class VIPActivity extends BaseActivity {
    @BindView(R.id.sb_vip)
    SeekBar sb_vip;
    @BindView(R.id.tv_vip_shuoming)
    MyTextView tv_vip_shuoming;
    @BindView(R.id.tv_vip_putong_content)
    TextView tv_vip_putong_content;
    @BindView(R.id.tv_vip_content)
    TextView tv_vip_content;

    @Override
    protected int getContentView() {
        setAppTitle("会员中心");
        return R.layout.vip_act;
    }

    @Override
    protected void initView() {

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
        map.put("sign",getSign(map));
        ApiRequest.VIP(map, new MyCallBack<VIPObj>(mContext,pl_load,pcfl) {
            @Override
            public void onSuccess(VIPObj obj, int errorCode, String msg) {
                tv_vip_shuoming.setText("还需要消费"+obj.getHaixu()+"元,可升级至VIP会员");
                tv_vip_putong_content.setText(obj.getRegular_member());
                tv_vip_content.setText(obj.getVip_member());
                sb_vip.setMax((int) obj.getZong());
                sb_vip.setProgress((int) (obj.getZong()-obj.getHaixu()));
                GradientDrawable gradientDrawable=new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT,new int[]{Color.parseColor("#FF9A9A9A"),Color.parseColor("#FFE0D28F")});
//                sb_vip.setBackground(gradientDrawable);
                sb_vip.setProgressDrawable(gradientDrawable);

                GradientDrawable seekBarGD=new GradientDrawable();
                seekBarGD.setColor(Color.parseColor("#FFE0D28F"));
                seekBarGD.setSize(PhoneUtils.dip2px(mContext,8),PhoneUtils.dip2px(mContext,8));
                seekBarGD.setCornerRadius(PhoneUtils.dip2px(mContext,4));

                sb_vip.setThumb(seekBarGD);
                sb_vip.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        return true;
                    }
                });
//                sb_vip.setEnabled(false);
            }
        });
    }

    @Override
    protected void onViewClick(View v) {

    }
}
