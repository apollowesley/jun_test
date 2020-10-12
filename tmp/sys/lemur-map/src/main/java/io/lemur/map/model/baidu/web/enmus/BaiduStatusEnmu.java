package io.lemur.map.model.baidu.web.enmus;

/**
 * 状态枚举
 * @author JueYue
 * @date 2015年1月26日
 */
public enum BaiduStatusEnmu {

    NORMAL (0 , "正常") ,
    ERROR (1 , "服务器内部错误") ,
    PARAMS_ERROR (2 , "请求参数非法") ,
    AUTHORIZED (3 , "权限校验失败") ,
    QUOTA (4 , "配额校验失败") ,
    NO_KEY (4 , "ak不存在或者非法") ,
    FORBIDDEN (101 , "服务禁用") ,
    SECURITY_CODE (102 , "不通过白名单或者安全码不对");

    private int    status;

    private String msg;

    BaiduStatusEnmu(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
