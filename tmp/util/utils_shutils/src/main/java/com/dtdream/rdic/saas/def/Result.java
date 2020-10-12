package com.dtdream.rdic.saas.def;

import com.dtdream.rdic.saas.utils.ThreadUtils;

/**
 * Created by Ozz8 on 2015/06/12.
 */
public class Result {
    private static int index = 0;

    public long errCode;
    public String errMessage;

    public String filename;
    public String classname;
    public String functionname;
    public int lineno;

    public final static Result SUCCESS                      = new Result(0,  0, "操作成功完成");
    public final static Result FAILURE                      = new Result(0, -- index, "操作失败");
    public final static Result FAILURE_INV_PARAM            = new Result(0, -- index, "参数不正确");
    public final static Result FAILURE_COMMON               = new Result(0, -- index, "发生一般错误");
    public final static Result FAILURE_UNEXPECTED_TYPE      = new Result(0, -- index, "不可预期的类型");
    public final static Result FAILURE_NULL                 = new Result(0, -- index, "参数为空");
    public final static Result FAILURE_UNSUPPORTED_ENC      = new Result(0, -- index, "不支持的编码类型");
    public final static Result FAILURE_EMPTY                = new Result(0, -- index, "内容为空");
    public final static Result FAILURE_FILE_ISDIR           = new Result(0, -- index, "指定路径是目录");
    public final static Result FAILURE_NULL_TEMPLATE        = new Result(0, -- index, "TemplateUtils未指定");
    public final static Result FAILURE_NULL_CONTEXT         = new Result(0, -- index, "上下文不能为空");
    public final static Result FAILURE_NULL_TEXTOR          = new Result(0, -- index, "TextUtils未指定");
    public final static Result FAILURE_NOSUCH_TEMPLATE      = new Result(0, -- index, "模板不存在");
    public final static Result FAILURE_NOINIT_TEMPLATE      = new Result(0, -- index, "模板未初始化");
    public final static Result FAILURE_FILEUTILS_NOAPPROOT  = new Result(0, -- index, "FileUtils未设置approot");
    public final static Result FAILURE_SEDN_EMAIL           = new Result(0, -- index, "邮件发送失败");

    public Result(Result result) {
        if (null != result) {
            this.errCode = result.getErrCode();
            this.errMessage = result.getErrMessage();
        } else {
            this.errCode = Result.SUCCESS.getErrCode();
            this.errMessage = Result.SUCCESS.getErrMessage();
        }
    }

    public Result(Result result, String msg) {
        this.errCode = result.errCode;
        if (result.equals(FAILURE_INV_PARAM))
            this.errMessage = "参数无法识别：".concat(msg).concat("!");
        else
            this.errMessage = new StringBuilder(result.errMessage).append('(').append(msg).append(')').toString();
    }

    public Result(int mid, int code, String message) {
        this.errCode = (mid << 32) | code;
        this.errMessage = message;
    }

    public Result(int mid, int code, String message, int frame) {
        this.errCode = (mid << 32) | code;
        this.errMessage = message;
        int frm = frame + 1;
        StringBuilder sbstack = ThreadUtils.info(frm);
        this.errMessage =
            new StringBuilder(message).append('(').append(sbstack).append(')').toString();
    }

    public String toString() {
        return new StringBuilder("errCode: ").append(errCode).append(" errMessage: ").append(errMessage).toString();
    }

    public boolean success () {
        return (0xffffffff & errCode) == 0;
    }

    public boolean failure () {
        return errCode != 0;
    }

    public long getErrCode() {
        return errCode;
    }

    public void setErrCode(long errCode) {
        this.errCode = errCode;
    }

    public String getErrMessage() {
        return errMessage;
    }

    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public String getFunctionname() {
        return functionname;
    }

    public void setFunctionname(String functionname) {
        this.functionname = functionname;
    }

    public int getLineno() {
        return lineno;
    }

    public void setLineno(int lineno) {
        this.lineno = lineno;
    }
}
