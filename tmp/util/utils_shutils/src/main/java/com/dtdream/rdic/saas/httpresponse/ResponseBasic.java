package com.dtdream.rdic.saas.httpresponse;

import com.dtdream.rdic.saas.app.Results;
import com.dtdream.rdic.saas.utils.*;
import com.dtdream.rdic.saas.def.Result;

import java.util.ArrayList;

/**
 * Created by Ozz8 on 2015/06/10.
 */
public class ResponseBasic {

    long retCode = Results.SUCCESS.errCode;
    String retMessage = Results.SUCCESS.errMessage;
    Object retData = new ArrayList(0);

    public ResponseBasic() {

    }

    public ResponseBasic(Result result) {
        if (null == result) {
            this.retCode = Results.FAIL_COMMON.errCode;
            this.retMessage = Results.FAIL_COMMON.errMessage;
        } else {
            this.retCode = result.errCode;
            this.retMessage = result.errMessage;
        }
    }

    public void reset (Result result) {
        if (null == result) {
            this.retCode = Results.FAIL_COMMON.errCode;
            this.retMessage = Results.FAIL_COMMON.errMessage;
        } else {
            this.retCode = result.errCode;
            this.retMessage = result.errMessage;
        }
    }

    public String result() {
        return JsonUtils.json(this);
    }

    public long getRetCode() {
        return retCode;
    }

    public void setRetCode(long retCode) {
        this.retCode = retCode;
    }

    public String getRetMessage() {
        return retMessage;
    }

    public void setRetMessage(String retMessage) {
        this.retMessage = retMessage;
    }

    public Object getRetData() {
        return retData;
    }

    public void setRetData(Object retData) {
        this.retData = retData;
    }
}
