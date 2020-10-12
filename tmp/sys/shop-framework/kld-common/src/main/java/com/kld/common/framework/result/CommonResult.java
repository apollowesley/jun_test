package com.kld.common.framework.result;

import java.io.Serializable;

public class CommonResult<T>  implements Serializable {
    private static final long serialVersionUID = -7393535371067917688L;

    /**
     * 返回代码,参见代码参照表
     */
    private String returnCode;
    /**
     * 返回消息
     */
    private String returnMessage;
    /**
     * 返回状态 true正确 false出错
     */
    private boolean returnStatus;
    /**
     * 返回对象总数,只有当data是list时才有值
     */
    private int total;

    public CommonResult() {
    }

    public CommonResult(String returnCode, String returnMessage) {
        this.returnCode = returnCode;
        this.returnMessage = returnMessage;
    }

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnMessage() {
        return returnMessage;
    }

    public void setReturnMessage(String returnMessage) {
        this.returnMessage = returnMessage;
    }

    public boolean getReturnStatus() {
        return returnStatus;
    }

    public void setReturnStatus(boolean returnStatus) {
        this.returnStatus = returnStatus;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

}
