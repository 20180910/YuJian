package com.zhizhong.yujian.view;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by Administrator on 2018/1/2.
 */

public class MyScrollView extends NestedScrollView {
    boolean flag=true;
    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
//PhoneUtils.dip2px(getContext(),60)
    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        if(flag){
            LinearLayout childAt = (LinearLayout) getChildAt(0);
            int height = childAt.getChildAt(0).getHeight();
            boolean hiddenTop = dy > 0 && getScrollY() < height;
            boolean showTop = dy < 0 && getScrollY() > 0 && !ViewCompat.canScrollVertically(target, -1);

            if (hiddenTop || showTop)
            {
                scrollBy(0, dy);
                consumed[1] = dy;
            }
        }else{
            super.onNestedPreScroll(target,   dx,   dy,   consumed);
        }

    }

    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        if(flag){
            LinearLayout childAt = (LinearLayout) getChildAt(0);
            int height = childAt.getChildAt(0).getHeight();
            if (getScrollY() >= height) return false;
            fling((int) velocityY);
            return true;
        }else{
            return super.onNestedPreFling(target,   velocityX,   velocityY);
        }

    }

    public void setCanScroll(boolean flag) {
        this.flag=flag;
    }

}
