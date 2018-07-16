package com.library.base.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.github.baseclass.BaseDividerListItem;
import com.library.R;

/**
 * Created by Administrator on 2017/12/18.
 */

public class MyRecyclerView extends RecyclerView {
    private static final String TAG="MyRecyclerView";

    public MyRecyclerView(Context context) {
        super(context);
    }

    public MyRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setAdapter(Adapter adapter) {
        setAdapter(adapter,2);
    }
    public void setAdapter(Adapter adapter,int offset) {
        if (getLayoutManager() == null) {
            setLayoutManager(new LinearLayoutManager(getContext()));
            addItemDecoration(new BaseDividerListItem(getContext(),offset,R.color.divider_color));
        }
        super.setAdapter(adapter);
    }
}
