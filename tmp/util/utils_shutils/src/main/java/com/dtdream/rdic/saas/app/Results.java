package com.dtdream.rdic.saas.app;

import com.dtdream.rdic.saas.def.Result;

public class Results {
    public static final int MID = 1;
    public static final Result SUCCESS                   = new Result(0,      0,  "操作成功完成");
    public static final Result FAIL_COMMON               = new Result(MID,    1,  "发生一般错误");
    public static final Result FAIL_NULL                 = new Result(MID,    2,  "参数不存在");
    public static final Result FAIL_EMPTY                = new Result(MID,    3,  "参数为空");
    public static final Result FAIL_NUMBER_LEQ_ZERO      = new Result(MID,    4,  "数值不大于0");
    public static final Result FAIL_UPDT_DB              = new Result(MID,    5,  "更新数据库失败");
    public static final Result FAIL_GEN_MIME_HELPER      = new Result(MID,    6,  "创建邮件助手失败");
    public static final Result FAIL_GEN_ANNEX_NAME       = new Result(MID,    7,  "附件名称编码失败");
    public static final Result FAIL_NO_RECIPIENTS        = new Result(MID,    8,  "邮件收件人为空");
    public static final Result FAIL_NO_CC                = new Result(MID,    9,  "邮件抄送者为空");
    public static final Result FAIL_NO_BODY              = new Result(MID,    10, "邮件正文为空");
    public static final Result FAIL_NO_SUBJECT           = new Result(MID,    11, "邮件主题为空");
    public static final Result FAIL_NO_SENDER            = new Result(MID,    12, "邮件发送器为空");
    public static final Result FAIL_FILE_NO_NAME         = new Result(MID,    13, "文件名不存在");
    public static final Result FAIL_FILE_UPLOAD          = new Result(MID,    14, "文件上传错误");
    public static final Result FAIL_FILE_NONEXIST        = new Result(MID,    15, "文件不存在");
    public static final Result FAIL_IO_OPEN              = new Result(MID,    16, "打开输出流失败");
    public static final Result FAIL_IO_WRITE             = new Result(MID,    17, "写入输出流失败");
    public static final Result FAIL_IO_READ              = new Result(MID,    18, "读取IO流失败");
    public static final Result FAIL_JSON_PARSE           = new Result(MID,    19, "JSON解析失败");
}
