package com.dtdream.rdic.saas.def;

import com.dtdream.rdic.saas.httpresponse.ResponseBasic;
import com.dtdream.rdic.saas.utils.JsonUtils;

/**
 * Created by Ozz8 on 2015/07/01.
 */
public class StdRet {
    Result result;
    Object data;
    protected ResponseBasic response = new ResponseBasic();

    public boolean success () {
        return (null != result && result.success());
    }

    public boolean failure () {
        return (null == result || result.failure() || null == data);
    }

    public String result () {
        response.reset(result);
        return JsonUtils.json(response);
    }

    public StdRet() {
        result = new Result(Result.SUCCESS);
    }

    public StdRet(Result result) {
        this.result = result;
    }

    public StdRet(Object data) {
        this.data = data;
    }

    public void message (String msg) {
        if (null != result)
            result.setErrMessage(msg);
    }

    /**
     * result的message字段会被改写，请自行申请对象
     * @param result
     * @param msg
     */
    public StdRet(Result result, String msg) {
        result.setErrMessage(msg);
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
