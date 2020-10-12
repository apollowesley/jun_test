package com.xinhuo.demo.common.model;

import com.alibaba.fastjson.JSONObject;

/**
 * author 张新林
 * 时间 2019/6/16 16:11
 * 描述
 */
public class RequestMsg {
    JSONObject data;

    public JSONObject getData() {
        return data;
    }

    public void setData(JSONObject data) {
        this.data = data;
    }
}
