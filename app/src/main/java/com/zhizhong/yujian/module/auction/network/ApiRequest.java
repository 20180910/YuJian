package com.zhizhong.yujian.module.auction.network;

import com.github.retrofitutil.NoNetworkException;
import com.library.base.BaseApiRequest;
import com.zhizhong.yujian.Config;
import com.zhizhong.yujian.base.MyCallBack;

import java.util.Map;

/**
 * Created by Administrator on 2017/6/28.
 */

public class ApiRequest extends BaseApiRequest {
 
    public static void getPaiMaiBanner(Map map, MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return;  }
        getGeneralClient(IRequest.class).getPaiMaiBanner(map).enqueue(callBack);
    }
    public static void getPaiMaiTuiJian(Map map, MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return;  }
        getGeneralClient(IRequest.class).getPaiMaiTuiJian(map).enqueue(callBack);
    }
    public static void getAllPaiMai(Map map, MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return;  }
        getGeneralClient(IRequest.class).getAllPaiMai(map).enqueue(callBack);
    }
    public static void getPaiMaiGoodsDetail(Map map, MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return;  }
        getGeneralClient(IRequest.class).getPaiMaiGoodsDetail(map).enqueue(callBack);
    }
    public static void paiMaiDetailTiXing(Map map, MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return;  }
        getGeneralClient(IRequest.class).paiMaiDetailTiXing(map).enqueue(callBack);
    }
    public static void getChuJiaPrice(Map map, MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return;  }
        getGeneralClient(IRequest.class).getChuJiaPrice(map).enqueue(callBack);
    }
    public static void chuJia(Map map, MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return;  }
        getGeneralClient(IRequest.class).chuJia(map).enqueue(callBack);
    }
    public static void getBaoZhengJin(Map map, MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return;  }
        getGeneralClient(IRequest.class).getBaoZhengJin(map).enqueue(callBack);
    }
    public static void getBaoZhengJinContent(Map map, MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return;  }
        getGeneralClient(IRequest.class).getBaoZhengJinContent(map).enqueue(callBack);
    }
    public static void getAllChuJia(Map map, MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return;  }
        getGeneralClient(IRequest.class).getAllChuJia(map).enqueue(callBack);
    }
    public static void getPaiMaiOrder(Map map, MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return;  }
        getGeneralClient(IRequest.class).getPaiMaiOrder(map).enqueue(callBack);
    }
    public static void getPaiMaiOrderDetail(Map map, MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return;  }
        getGeneralClient(IRequest.class).getPaiMaiOrderDetail(map).enqueue(callBack);
    }
    public static void paiMaiSetAddress(Map map, MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return;  }
        getGeneralClient(IRequest.class).paiMaiSetAddress(map).enqueue(callBack);
    }

}
