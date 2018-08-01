package com.zhizhong.yujian.module.mall.network;

import com.github.retrofitutil.NoNetworkException;
import com.library.base.BaseApiRequest;
import com.zhizhong.yujian.Config;
import com.zhizhong.yujian.base.MyCallBack;
import com.zhizhong.yujian.module.mall.network.request.CommitOrderBody;

import java.util.Map;

/**
 * Created by Administrator on 2017/6/28.
 */

public class ApiRequest extends BaseApiRequest {
 
    public static void getMallGoodsType(Map map, MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return;  }
        getGeneralClient(IRequest.class).getMallGoodsType(map).enqueue(callBack);
    }
    public static void getMallTuiJian(Map map, MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return;  }
        getGeneralClient(IRequest.class).getMallTuiJian(map).enqueue(callBack);
    }
    public static void getGoodsDetail(Map map, MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return;  }
        getGeneralClient(IRequest.class).getGoodsDetail(map).enqueue(callBack);
    }
    public static void getShoppingCart(Map map, MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return;  }
        getGeneralClient(IRequest.class).getShoppingCart(map).enqueue(callBack);
    }
    public static void deleteShoppingCart(Map map, MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return;  }
        getGeneralClient(IRequest.class).deleteShoppingCart(map).enqueue(callBack);
    }
    public static void updateShoppingCartNum(Map map, MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return;  }
        getGeneralClient(IRequest.class).updateShoppingCartNum(map).enqueue(callBack);
    }
    public static void getYouHuiQuan(Map map, MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return;  }
        getGeneralClient(IRequest.class).getYouHuiQuan(map).enqueue(callBack);
    }
    public static void keYongYouHuiQuan(Map map, MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return;  }
        getGeneralClient(IRequest.class).keYongYouHuiQuan(map).enqueue(callBack);
    }
    public static void getDefaultAddress(Map map, MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return;  }
        getGeneralClient(IRequest.class).getDefaultAddress(map).enqueue(callBack);
    }
    public static void lingQuYouHuiQuan(Map map, MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return;  }
        getGeneralClient(IRequest.class).lingQuYouHuiQuan(map).enqueue(callBack);
    }
    public static void getGoodsAllEvaluationNum(Map map, MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return;  }
        getGeneralClient(IRequest.class).getGoodsAllEvaluationNum(map).enqueue(callBack);
    }
    public static void getGoodsAllEvaluation(Map map, MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return;  }
        getGeneralClient(IRequest.class).getGoodsAllEvaluation(map).enqueue(callBack);
    }
    public static void getGoodsClassList(Map map, MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return;  }
        getGeneralClient(IRequest.class).getGoodsClassList(map).enqueue(callBack);
    }
    public static void commitOrder(Map map, CommitOrderBody body, MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return;  }
        getGeneralClient(IRequest.class).commitOrder(map,body).enqueue(callBack);
    }

}
