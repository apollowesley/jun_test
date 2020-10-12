package io.lemur.map.model.amap.base;

import java.io.Serializable;

/**
 * 高德基础类
 * @author JueYue
 * @date 2015年1月28日
 */
public class AmapBaseModel implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /**
     * 状态
     */
    private int               status;
    /**
     * 信息
     */
    private String            info;
    /**
     * 数量
     */
    private int               count;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

}
