package io.lemur.map.model.baidu.web.direction.bus.point;

import java.io.Serializable;

import io.lemur.map.model.baidu.web.base.AreaModel;

/**
 * 起点或者终点
 * @author JueYue
 * @date 2015年1月27日
 */
public class KeyPointModel implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * 关键词
     */
    private String wd;
    
    private AreaModel city;

    public String getWd() {
        return wd;
    }

    public void setWd(String wd) {
        this.wd = wd;
    }

    public AreaModel getCity() {
        return city;
    }

    public void setCity(AreaModel city) {
        this.city = city;
    }

}
