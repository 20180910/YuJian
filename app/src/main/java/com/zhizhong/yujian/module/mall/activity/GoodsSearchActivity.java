package com.zhizhong.yujian.module.mall.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.androidtools.PhoneUtils;
import com.github.androidtools.SPUtils;
import com.github.androidtools.inter.MyOnClickListener;
import com.github.fastshape.FlowLayout;
import com.github.fastshape.MyTextView;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.zhizhong.yujian.AppXml;
import com.zhizhong.yujian.IntentParam;
import com.zhizhong.yujian.R;
import com.zhizhong.yujian.base.BaseActivity;
import com.zhizhong.yujian.base.MyCallBack;
import com.zhizhong.yujian.module.mall.network.ApiRequest;
import com.zhizhong.yujian.tools.JsonParser;
import com.zhizhong.yujian.view.MyEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class GoodsSearchActivity extends BaseActivity {
    @BindView(R.id.ib_search_scan)
    ImageButton ib_search_scan;

    @BindView(R.id.et_search_content)
    MyEditText et_search_content;

    @BindView(R.id.fl_history_search)
    FlowLayout fl_history_search;

    @BindView(R.id.fl_hot_search)
    FlowLayout fl_hot_search;

    @BindView(R.id.ll_goods_search_yuyin)
    LinearLayout ll_goods_search_yuyin;



    // 语音听写对象
    private SpeechRecognizer mIat;
    // 引擎类型
    private String mEngineType = SpeechConstant.TYPE_CLOUD;
    // 用HashMap存储听写结果
    private HashMap<String, String> mIatResults = new LinkedHashMap<String, String>();
    int ret = 0; // 函数调用返回值
    private boolean startActivityComplete;

    @Override
    protected int getContentView() {
        setAppRightTitle("确定");
        return R.layout.goods_search_act;
    }

    @Override
    protected void initView() {
        et_search_content.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId== EditorInfo.IME_ACTION_SEARCH){
                    searchGoods();
                }
                return false;
            }
        });

        ll_goods_search_yuyin.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    mIat.stopListening();
                    PhoneUtils.showKeyBoard(mContext,et_search_content);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    startActivityComplete=false;
                    startYuYin();
                }
                return false;
            }
        });
        // 初始化识别无UI识别对象
        // 使用SpeechRecognizer对象，可根据回调消息自定义界面；
        mIat = SpeechRecognizer.createRecognizer(this, mInitListener);

        // 初始化听写Dialog，如果只使用有UI听写功能，无需创建SpeechRecognizer
        // 使用UI听写功能，请根据sdk文件目录下的notice.txt,放置布局文件和图片资源
    }

    private void startYuYin() {
        // 移动数据分析，收集开始听写事件
        et_search_content.setText(null);// 清空显示内容
        mIatResults.clear();
        // 设置参数
        setParam();
        // 不显示听写对话框
        ret = mIat.startListening(mRecognizerListener);
        if (ret != ErrorCode.SUCCESS) {
            showMsg("听写失败,错误码:"+ret);
        } else {
            showMsg("请开始说话");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        String[] searchContent = getSearchContent();
        fl_history_search.removeAllViews();
        StringBuilder historyStr=new StringBuilder();//防止历史记录保存过多
        for (int i = 0; i < searchContent.length; i++) {
            if(i<=9){
                String str=searchContent[i];
                if(!TextUtils.isEmpty(str)){
                    historyStr.append(str+",");
                    final MyTextView textView=new MyTextView(mContext);
                    FlowLayout.LayoutParams layoutParams = new FlowLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    layoutParams.setMargins(0,0,PhoneUtils.dip2px(mContext,10),PhoneUtils.dip2px(mContext,10));
                    textView.setLayoutParams(layoutParams);
                    textView.setText(str);
                    textView.setTextColor(ContextCompat.getColor(mContext,R.color.gray_66));
                    textView.getViewHelper().setSolidColor(Color.parseColor("#FFF5F5F5")).setRadius(PhoneUtils.dip2px(mContext,3)).complete();
                    textView.setPadding(PhoneUtils.dip2px(mContext,18),PhoneUtils.dip2px(mContext,6),PhoneUtils.dip2px(mContext,18),PhoneUtils.dip2px(mContext,6));
                    textView.setOnClickListener(new MyOnClickListener() {
                        @Override
                        protected void onNoDoubleClick(View view) {
                            goSearch(textView.getText().toString());
                        }
                    });
                    fl_history_search.addView(textView);
                }
            }else{
                break;
            }
        }
        SPUtils.setPrefString(mContext, AppXml.searchContent,historyStr.toString());
    }

    private void searchGoods() {
        if (TextUtils.isEmpty(et_search_content.getText().toString())) {
            showMsg("请输入搜索内容");
        } else {
            String string = et_search_content.getText().toString();
            goSearch(string);
        }
    }
    public void goSearch(String string){
        setSearchContent(string);
        Intent intent = new Intent();
        intent.putExtra(IntentParam.searchStr,string);
        STActivityForResult(intent, SearchResultActivity.class,200);
    }
    public String[] getSearchContent(){
        String history = SPUtils.getString(mContext, AppXml.searchContent, null);
        if(TextUtils.isEmpty(history)){
            return new String[]{""};
        }else{
            String[] split = history.split(",");
            return split;
        }
    }
    public void setSearchContent(String string){
        String history = SPUtils.getString(mContext, AppXml.searchContent, null);
        if(TextUtils.isEmpty(history)){
            SPUtils.setPrefString(mContext, AppXml.searchContent,string);
        }else{
            history=string+","+history.replace(string,"");
            SPUtils.setPrefString(mContext, AppXml.searchContent,history);
        }
    }
    @Override
    protected void initData() {
        getData(1,false);
    }

    @Override
    protected void getData(int page, boolean isLoad) {
        super.getData(page, isLoad);
        Map<String,String> map=new HashMap<String,String>();
        map.put("rnd",getRnd());
        map.put("sign",getSign(map));
        ApiRequest.getHotSearch(map, new MyCallBack<List<String>>(mContext) {
            @Override
            public void onSuccess(List<String> list, int errorCode, String msg) {
                fl_hot_search.removeAllViews();
                if(notEmpty(list)){
                    for (int i = 0; i < list.size(); i++) {
                        final MyTextView textView=new MyTextView(mContext);
                        FlowLayout.LayoutParams layoutParams = new FlowLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        layoutParams.setMargins(0,0,PhoneUtils.dip2px(mContext,10),PhoneUtils.dip2px(mContext,10));
                        textView.setLayoutParams(layoutParams);
                        textView.setText(list.get(i));
                        textView.setTextColor(ContextCompat.getColor(mContext,R.color.gray_66));
                        textView.getViewHelper().setSolidColor(Color.parseColor("#FFF5F5F5")).setRadius(PhoneUtils.dip2px(mContext,3)).complete();
                        textView.setPadding(PhoneUtils.dip2px(mContext,18),PhoneUtils.dip2px(mContext,6),PhoneUtils.dip2px(mContext,18),PhoneUtils.dip2px(mContext,6));
                        textView.setOnClickListener(new MyOnClickListener() {
                            @Override
                            protected void onNoDoubleClick(View view) {
                                goSearch(textView.getText().toString());
                            }
                        });
                        fl_hot_search.addView(textView);
                    }
                }
            }
        });

    }

    @OnClick({R.id.ib_search_scan,R.id.app_right_tv})
    protected void onViewClick(View v) {
        switch (v.getId()){
            case R.id.ib_search_scan:
                STActivity(NewScanActivity.class);
            break;
            case R.id.app_right_tv:
                searchGoods();
            break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            switch (requestCode){
                case 200:
                    getData(1,false);
                break;
            }
        }
    }

    public void setParam() {
        // 清空参数
        mIat.setParameter(SpeechConstant.PARAMS, null);

        // 设置听写引擎
        mIat.setParameter(SpeechConstant.ENGINE_TYPE, mEngineType);
        // 设置返回结果格式
        mIat.setParameter(SpeechConstant.RESULT_TYPE, "json");



        /*String lag = mSharedPreferences.getString("iat_language_preference",
                "mandarin");
        if (lag.equals("en_us")) {
            // 设置语言
            mIat.setParameter(SpeechConstant.LANGUAGE, "en_us");
            mIat.setParameter(SpeechConstant.ACCENT, null);
        } else {*/
        // 设置语言
        mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        // 设置语言区域
        mIat.setParameter(SpeechConstant.ACCENT, "mandarin");
//        }

        // 设置语音前端点:静音超时时间，即用户多长时间不说话则当做超时处理
        mIat.setParameter(SpeechConstant.VAD_BOS, "4000");

        // 设置语音后端点:后端点静音检测时间，即用户停止说话多长时间内即认为不再输入， 自动停止录音
        mIat.setParameter(SpeechConstant.VAD_EOS, "15000");

        // 设置标点符号,设置为"0"返回结果无标点,设置为"1"返回结果有标点
        mIat.setParameter(SpeechConstant.ASR_PTT, "0");//mSharedPreferences.getString("iat_punc_preference", "0"));

        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        // 注：AUDIO_FORMAT参数语记需要更新版本才能生效
        mIat.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");
        mIat.setParameter(SpeechConstant.ASR_AUDIO_PATH, Environment.getExternalStorageDirectory() + "/msc/iat.wav");
    }

    /**
     * 听写监听器。
     */
    private RecognizerListener mRecognizerListener = new RecognizerListener() {

        @Override
        public void onBeginOfSpeech() {
            // 此回调表示：sdk内部录音机已经准备好了，用户可以开始语音输入
            showMsg("开始说话");
        }

        @Override
        public void onError(SpeechError error) {
            // Tips：
            // 错误码：10118(您没有说话)，可能是录音机权限被禁，需要提示用户打开应用的录音权限。
            // 如果使用本地功能（语记）需要提示用户开启语记的录音权限。
            if (false && error.getErrorCode() == 14002) {
                showMsg(error.getPlainDescription(true) + "\n请确认是否已开通翻译功能");
            } else {
                showMsg(error.getPlainDescription(true));
            }
        }

        @Override
        public void onEndOfSpeech() {
            // 此回调表示：检测到了语音的尾端点，已经进入识别过程，不再接受语音输入
            showMsg("结束说话");
        }

        @Override
        public void onResult(RecognizerResult results, boolean isLast) {
            Log.d("TAG", results.getResultString());
//          /*  if( results.  ){
//                printTransResult( results );
//            }else{*/
            printResult(results);
//            }

            if (isLast) {
                // TODO 最后的结果
            }
        }

        @Override
        public void onVolumeChanged(int volume, byte[] data) {
            showMsg("当前正在说话，音量大小：" + volume);
            Log.d("TAG", "返回音频数据：" + data.length);
        }

        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
            // 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
            // 若使用本地能力，会话id为null
            //	if (SpeechEvent.EVENT_SESSION_ID == eventType) {
            //		String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
            //		Log.d(TAG, "session id =" + sid);
            //	}
        }
    };

    private void printResult(RecognizerResult results) {
        String text = JsonParser.parseIatResult(results.getResultString());

        String sn = null;
        // 读取json结果中的sn字段
        try {
            JSONObject resultJson = new JSONObject(results.getResultString());
            sn = resultJson.optString("sn");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mIatResults.put(sn, text);

        StringBuffer resultBuffer = new StringBuffer();
        for (String key : mIatResults.keySet()) {
            resultBuffer.append(mIatResults.get(key));
        }

        et_search_content.setText(resultBuffer.toString());
        et_search_content.setSelection(et_search_content.length());

        if (TextUtils.isEmpty(et_search_content.getText().toString())) {
            showMsg("请输入搜索内容");
        } else {
            if(!startActivityComplete){
                startActivityComplete=true;
                PhoneUtils.hiddenKeyBoard(mContext,et_search_content);
                Intent intent = new Intent();
                intent.putExtra(IntentParam.searchStr, et_search_content.getText().toString());
                STActivityForResult(intent, SearchResultActivity.class, 100);
            }

        }

    }

    /**
     * 初始化监听器。
     */
    private InitListener mInitListener = new InitListener() {

        @Override
        public void onInit(int code) {
            Log.d("TAG", "SpeechRecognizer init() code = " + code);
            if (code != ErrorCode.SUCCESS) {
                showMsg("初始化失败，错误码：" + code);
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (null != mIat) {
            // 退出时释放连接
            mIat.cancel();
            mIat.destroy();
        }
    }
}
