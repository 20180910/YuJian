package com.zhizhong.yujian.module.my.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.github.baseclass.adapter.MyRecyclerViewHolder;
import com.zhizhong.yujian.R;
import com.zhizhong.yujian.adapter.MyAdapter;
import com.zhizhong.yujian.base.BaseActivity;
import com.zhizhong.yujian.base.MyCallBack;
import com.zhizhong.yujian.module.my.network.ApiRequest;
import com.zhizhong.yujian.module.my.network.response.MessageObj;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class MessageActivity extends BaseActivity {
    @BindView(R.id.rv_message)
    RecyclerView rv_message;

    MyAdapter adapter;
    @Override
    protected int getContentView() {
        setAppTitle("消息");
        return R.layout.message_act;
    }

    @Override
    protected void initView() {
        adapter=new MyAdapter<MessageObj>(mContext,R.layout.system_message_item,pageSize) {
            @Override
            public void bindData(MyRecyclerViewHolder holder, int position, MessageObj bean) {
                holder.setText(R.id.tv_message_time,bean.getAdd_time());
                holder.setText(R.id.tv_message_title,bean.getTitle());
                holder.setText(R.id.tv_message_content,bean.getContent());
            }
        };
        adapter.setOnLoadMoreListener(this);
        adapter.setBottomViewBackground(R.color.gray_bg);

        rv_message.setLayoutManager(new LinearLayoutManager(mContext));
        rv_message.addItemDecoration(getItemDivider(1,R.color.transparent));
        rv_message.setAdapter(adapter);

    }

    @Override
    protected void getData(int page,final boolean isLoad) {
        super.getData(page, isLoad);
        Map<String,String> map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("pagesize",pagesize+"");
        map.put("page",page+"");
        map.put("sign",getSign(map));
        ApiRequest.getMessageList(map, new MyCallBack<List<MessageObj>>(mContext,pl_load,pcfl) {
            @Override
            public void onSuccess(List<MessageObj> list, int errorCode, String msg) {
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

    @Override
    protected void initData() {
        showProgress();
        getData(1,false);
    }

    @Override
    protected void onViewClick(View v) {

    }
}
