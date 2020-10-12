package com.dtdream.rdic.saas.def;

/**
 * Created by Ozz8 on 2015/07/01.
 */
public class CommonException extends Exception {
    public Result error;
    public CommonException(Result result) {
        super(result.errMessage);
        this.error = result;
    }
    public CommonException(Result result, Object extra) {
        super(result.errMessage);
        int mid = Long.valueOf(result.errCode>>32).intValue();
        int code = Long.valueOf(result.errCode&0xFFFFFFFF).intValue();
        this.error = new Result(mid, code, result.errMessage);
        this.error.errMessage.concat("(").concat(extra.toString()).concat(")");
    }
}
