package com.zhizhong.yujian.module.mall.network;

import com.github.retrofitutil.NoNetworkException;
import com.library.base.BaseApiRequest;
import com.zhizhong.yujian.Config;
import com.zhizhong.yujian.base.MyCallBack;
import com.zhizhong.yujian.module.home.network.*;

import java.util.Map;

/**
 * Created by Administrator on 2017/6/28.
 */

public class ApiRequest extends BaseApiRequest {
 
    public static void getHomeZiXun(Map map, MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return;  }
        getGeneralClient(com.zhizhong.yujian.module.home.network.IRequest.class).getHomeZiXun(map).enqueue(callBack);
    }

}
