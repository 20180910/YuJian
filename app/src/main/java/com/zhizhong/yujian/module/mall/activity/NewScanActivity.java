package com.zhizhong.yujian.module.mall.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.github.androidtools.StatusBarUtils;
import com.uuzuche.lib_zxing.activity.CaptureFragment;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.zhizhong.yujian.GetSign;
import com.zhizhong.yujian.IntentParam;
import com.zhizhong.yujian.R;
import com.zhizhong.yujian.base.BaseActivity;
import com.zhizhong.yujian.base.MyCallBack;
import com.zhizhong.yujian.network.NetApiRequest;
import com.zhizhong.yujian.network.response.GoodsObj;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by administartor on 2017/10/24.
 */

public class NewScanActivity extends BaseActivity {
    @BindView(R.id.ll_scan_top)
    LinearLayout ll_scan_top;
    private boolean isLight;
    private CaptureFragment captureFragment;

    @Override
    protected int getContentView() {
        return R.layout.act_scan_new;
    }

    @Override
    protected void initView() {
        ll_scan_top.setPadding(0, StatusBarUtils.getStatusBarHeight(mContext), 0, 0);
        CodeUtils.AnalyzeCallback analyzeCallback = new CodeUtils.AnalyzeCallback() {
            @Override
            public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
                Log.i("===","==="+result);
                Map<String,String> map=new HashMap<String,String>();
                map.put("bar_code",result);
                map.put("sign", GetSign.getSign(map));
                NetApiRequest.scan(map, new MyCallBack<List<GoodsObj>>(mContext) {
                    @Override
                    public void onSuccess(List<GoodsObj> list, int errorCode, String msg) {
                        if(notEmpty(list)){
                            Intent intent=new Intent();
                            intent.putExtra(IntentParam.goodsId,list.get(0).getGoods_id());
                            STActivity(intent,GoodsDetailActivity.class);
                            finish();
                        }else{
                            showMsg("该条码没有查询到商品");
                            finish();
                        }
                    }
                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        Message restartMessage = new Message();
                        restartMessage.what = com.uuzuche.lib_zxing.R.id.restart_preview;
                        captureFragment.getHandler().sendMessageDelayed(restartMessage, 5000);
                    }
                });

            }

            @Override
            public void onAnalyzeFailed() {
                /*Intent resultIntent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_FAILED);
                bundle.putString(CodeUtils.RESULT_STRING, "");
                resultIntent.putExtras(bundle);
                setResult(RESULT_OK, resultIntent);
                finish();*/
            }
        };
        /**
         * 执行扫面Fragment的初始化操作
         */
        captureFragment = new CaptureFragment();
        // 为二维码扫描界面设置定制化界面
        CodeUtils.setFragmentArgs(captureFragment, R.layout.scan);

        captureFragment.setAnalyzeCallback(analyzeCallback);
        /**
         * 替换我们的扫描控件
         */getSupportFragmentManager().beginTransaction().replace(R.id.fl_my_container, captureFragment).commit();


    }


    @Override
    protected void initData() {

    }

    @OnClick({R.id.iv_scan_back,R.id.ll_scan_shou_dian})
    protected void onViewClick(View v) {
        switch (v.getId()){
            case R.id.iv_scan_back:
                finish();
                break;
            case R.id.ll_scan_shou_dian:
                CodeUtils.isLightEnable(!isLight);
                isLight=!isLight;
                break;
        }
    }

}
