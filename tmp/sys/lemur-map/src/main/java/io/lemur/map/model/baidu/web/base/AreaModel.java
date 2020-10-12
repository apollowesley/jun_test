package io.lemur.map.model.baidu.web.base;

import java.io.Serializable;

/**
 * 区域类
 * @author JueYue
 * @date 2015年1月27日
 */
public class AreaModel implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /**
     * 城市名称
     */
    private String cname;
    /**
     * 城市编码
     */
    private String code;
    
    public String getCname() {
        return cname;
    }
    public void setCname(String cname) {
        this.cname = cname;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    
}
