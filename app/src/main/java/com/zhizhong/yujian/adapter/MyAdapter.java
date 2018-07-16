package com.zhizhong.yujian.adapter;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;

import com.github.baseclass.adapter.MyLoadMoreAdapter;
import com.github.baseclass.adapter.MyRecyclerViewHolder;

/**
 * Created by Administrator on 2018/6/20.
 */

public class MyAdapter<T> extends MyLoadMoreAdapter<T> {
    public MyAdapter(Context mContext, int layoutId, int pageSize) {
        super(mContext, layoutId, pageSize);
    }
    public MyAdapter(Context mContext, int layoutId, int pageSize, NestedScrollView nestedScrollView) {
        super(mContext, layoutId, pageSize, nestedScrollView);
    }
    @Override
    public void bindData(MyRecyclerViewHolder holder, int position, T item) {

    }

    @Override
    public void setHiddenPromptView(boolean hiddenPromptView) {
        super.setHiddenPromptView(true);
    }
}
