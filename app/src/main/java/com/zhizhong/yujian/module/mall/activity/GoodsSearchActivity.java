package com.zhizhong.yujian.module.mall.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageButton;
import android.widget.TextView;

import com.github.androidtools.PhoneUtils;
import com.github.androidtools.SPUtils;
import com.github.androidtools.inter.MyOnClickListener;
import com.github.fastshape.FlowLayout;
import com.github.fastshape.MyTextView;
import com.zhizhong.yujian.AppXml;
import com.zhizhong.yujian.IntentParam;
import com.zhizhong.yujian.R;
import com.zhizhong.yujian.base.BaseActivity;
import com.zhizhong.yujian.view.MyEditText;

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
    }

    @Override
    protected void onResume() {
        super.onResume();
        String[] searchContent = getSearchContent();
        fl_history_search.removeAllViews();
        for (int i = 0; i < searchContent.length; i++) {
            String str=searchContent[i];
            if(!TextUtils.isEmpty(str)){
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
        }
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

    }

    @OnClick({R.id.ib_search_scan,R.id.app_right_tv})
    protected void onViewClick(View v) {
        switch (v.getId()){
            case R.id.ib_search_scan:
                STActivity(ScanActivity.class);
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
}
