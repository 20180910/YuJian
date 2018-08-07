package com.zhizhong.yujian.module.my.network.request;

import com.zhizhong.yujian.module.my.network.response.FaBiaoEvaluationObj;

import java.util.List;

public class FaBiaoEvaluationBody {
    private List<FaBiaoEvaluationObj> body;

    public List<FaBiaoEvaluationObj> getBody() {
        return body;
    }

    public void setBody(List<FaBiaoEvaluationObj> body) {
        this.body = body;
    }
}
