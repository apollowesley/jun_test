package io.lemur.map.model.baidu.web.base;

import java.io.Serializable;
/**
 * 百度基础bean
 * @author JueYue
 * @date 2015年1月26日
 */
public class BaiduBaseModel implements Serializable {
    
    private static final long serialVersionUID = 1L;
    /**
     * 状态
     */
    private int    status;
    /**
     * 消息
     */
    private String message;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
