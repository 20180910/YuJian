package com.zhizhong.yujian.module.my.activity;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.github.baseclass.adapter.MyRecyclerViewHolder;
import com.zhizhong.yujian.R;
import com.zhizhong.yujian.adapter.MyAdapter;
import com.zhizhong.yujian.base.BaseActivity;
import com.zhizhong.yujian.base.MyCallBack;
import com.zhizhong.yujian.module.my.network.ApiRequest;
import com.zhizhong.yujian.module.my.network.response.HelpCenterObj;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class HelpCenterActivity extends BaseActivity {
    @BindView(R.id.rv_help_center)
    RecyclerView rv_help_center;

    @BindView(R.id.tv_help_center_fankui)
    TextView tv_help_center_fankui;
    MyAdapter adapter;
    @Override
    protected int getContentView() {
        setAppTitle("帮助中心");
        return R.layout.help_center_act;
    }

    @Override
    protected void initView() {
        adapter=new MyAdapter<HelpCenterObj>(mContext,R.layout.help_center_item,pageSize) {
            @Override
            public void bindData(MyRecyclerViewHolder holder, int position, final HelpCenterObj bean) {
                CheckBox cb_help_center_title = (CheckBox) holder.getView(R.id.cb_help_center_title);
                final TextView textView = holder.getTextView(R.id.tv_help_center_content);

                cb_help_center_title.setText(bean.getTitle());
                cb_help_center_title.setChecked(bean.isCheck());

                if(bean.isCheck()){
                    textView.setVisibility(View.VISIBLE);
                    textView.setText(bean.getContent());
                }else{
                    textView.setVisibility(View.GONE);
                }
                cb_help_center_title.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bean.setCheck(!bean.isCheck());
                        if(bean.isCheck()){
                            textView.setVisibility(View.VISIBLE);
                            textView.setText(bean.getContent());
                        }else{
                            textView.setVisibility(View.GONE);
                        }
                    }
                });
            }
        };
        adapter.setOnLoadMoreListener(this);
        rv_help_center.setAdapter(adapter);
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
        map.put("rnd",getRnd());
        map.put("sign",getSign(map));
        ApiRequest.helpCenter(map, new MyCallBack<List<HelpCenterObj>>(mContext,pl_load,pcfl) {
            @Override
            public void onSuccess(List<HelpCenterObj> list, int errorCode, String msg) {
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

    @OnClick({R.id.tv_help_center_fankui})
    protected void onViewClick(View v) {
        switch (v.getId()){
            case R.id.tv_help_center_fankui:
                STActivity(YiJianFanKuiActivity.class);
            break;
        }
    }
}
