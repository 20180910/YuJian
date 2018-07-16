package com.zhizhong.yujian.tools;

import android.graphics.Paint;
import android.widget.TextView;

/**
 * Created by Administrator on 2018/4/17.
 */

public class TextViewUtils {
    public static void underline(TextView view){
        view.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG| Paint.ANTI_ALIAS_FLAG);
    }
}
