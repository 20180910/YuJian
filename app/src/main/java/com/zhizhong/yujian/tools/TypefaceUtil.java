package com.zhizhong.yujian.tools;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.lang.reflect.Field;

public class TypefaceUtil {
    /**
     * <p>Replace the font of specified view and it's children</p>
     * @param root The root view.
     * @param fontPath font file path relative to 'assets' directory.
     */
    public static void replaceFont(@NonNull View root, String fontPath) {
        if (root == null || TextUtils.isEmpty(fontPath)) {
            return;
        }
        if (root instanceof TextView) { // If view is TextView or it's subclass, replace it's font
            TextView textView = (TextView)root;
            int style = Typeface.NORMAL;
            if (textView.getTypeface() != null) {
                style = textView.getTypeface().getStyle();
            }
            textView.setTypeface(createTypeface(root.getContext(), fontPath), style);
        } else if (root instanceof ViewGroup) { // If view is ViewGroup, apply this method on it's child views
            ViewGroup viewGroup = (ViewGroup) root;
            for (int i = 0; i < viewGroup.getChildCount(); ++i) {
                replaceFont(viewGroup.getChildAt(i), fontPath);
            }
        }
    }
    /**
     * <p>Replace the font of specified view and it's children</p>
     * 通过递归批量替换某个View及其子View的字体改变Activity内部控件的字体(TextView,Button,EditText,CheckBox,RadioButton等)
     * @param context The view corresponding to the activity.
     * @param fontPath font file path relative to 'assets' directory.
     */
    public static void replaceFont(@NonNull Activity context, String fontPath) {
        replaceFont(getRootView(context),fontPath);
    }
    /*
     * Create a Typeface instance with your font file
     */
    public static Typeface createTypeface(Context context, String fontPath) {
        return Typeface.createFromAsset(context.getAssets(), fontPath);
    }
    /**
     * 从Activity 获取 rootView 根节点
     * @param context
     * @return 当前activity布局的根节点
     */
    public static View getRootView(Activity context)
    {
        return ((ViewGroup)context.findViewById(android.R.id.content)).getChildAt(0);
    }

    /**
     * 设置自定义字体
     *
     * @param context
     * @param staticTypefaceFieldName 需要替换的系统字体样式
     * @param fontAssetName           替换后的字体样式
     */
    public static void setDefaultTypeface(Context context, String staticTypefaceFieldName, String fontAssetName) {
        // 根据路径得到Typeface
        Typeface regular = Typeface.createFromAsset(context.getAssets(), fontAssetName);
        // 设置全局字体样式
        replaceTypeface(staticTypefaceFieldName, regular);
    }
    private static void replaceTypeface(String staticTypefaceFieldName, final Typeface newTypeface) {
        try {
            final Field staticField = Typeface.class.getDeclaredField(staticTypefaceFieldName);
            staticField.setAccessible(true);
            //替换系统字体样式
            staticField.set(null, newTypeface);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}