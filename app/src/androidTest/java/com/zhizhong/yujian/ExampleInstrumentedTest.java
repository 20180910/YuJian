package com.zhizhong.yujian;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.tencent.imsdk.TIMCallBack;
import com.tencent.imsdk.TIMManager;

import org.junit.Test;
import org.junit.runner.RunWith;

import static com.hyphenate.chat.adapter.EMACallRtcImpl.TAG;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.zhizhong.yujian", appContext.getPackageName());
    }
    @Test
    public void asfd() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.zhizhong.yujian", appContext.getPackageName());

        String name="88885502";
        String uisg="eJxlj0FPgzAAhe-8CsLZSClU0cSTwEI6wjaYZl4Ig0KqW8G2IM3if9exJTbxXb8v7*WdDNM0rXyZ3ZZV1Q1MFlL1xDIfTQtYN3*w72ldlLJwef0PkqmnnBRlIwmfoYMQggDoDq0Jk7ShV8P-DUIAaoaoP4p55lLhAeDAOwiQrtB2hkm4fY7XwSGOErwQ9v0geBAisXnPgtx*Xb2MZLnAbZhWe5xiRg5iHbfZBOTwGewi2EXY3vhcqkxMKmfjTjbHvQpSRZOv7dtKJE-apKRHcv3ker774ENPoyPhgnZsFiBwkANdcI5lfBs-tfReZA__";
        TIMManager.getInstance().login(name,uisg, new TIMCallBack() {
            //                TIMManager.getInstance().login(getHXName(),userSig, new TIMCallBack() {
            @Override
            public void onError(int code, String desc) {
                //错误码 code 和错误描述 desc，可用于定位请求失败原因
                //错误码 code 列表请参见错误码表
                Log("==="+"login failed. code: " + code + " errmsg: " + desc);
            }
            @Override
            public void onSuccess() {
                Log("==="+"login succ");
            }
        });
    }

    public void Log(String msg) {
        Log.i(TAG + "@@@===", msg);
    }
}
