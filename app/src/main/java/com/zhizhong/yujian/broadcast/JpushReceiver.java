package com.zhizhong.yujian.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by administartor on 2017/10/24.
 */

public class JpushReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            /*Log.i("JpushReceiver--TAG", "用户点击打开了通知");
            Intent inte = new Intent(context,MyOrderActivity.class);
            inte.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            inte.putExtra(Constant.IParam.orderType,1);
            context.startActivity(inte);*/
        }
    }
}
