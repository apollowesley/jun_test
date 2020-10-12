package io.lemur.map.model.base;

import java.io.Serializable;

/**
 * 点信息
 * @author JueYue
 * @date 2015年1月30日
 */
public class Point implements Serializable {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /**
     * ID
     */
    private String  id;
    /**
     * 经度
     */
    private String x;
    /**
     * 纬度
     */
    private String y;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    
}
