package io.lemur.map.model.baidu.web.base;

import io.lemur.map.model.base.Point;

import java.io.Serializable;

/**
 * 兴趣点信息
 * @author JueYue
 * @date 2015年1月26日
 */
public class PoiModel implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * 地址
     */
    private String            addr;
    /**
     * 来源
     */
    private String            cp;
    /**
     * 距离
     */
    private String            distance;
    /**
     * poi名称
     */
    private String            name;
    /**
     * poi类型
     */
    private String            poiType;
    /**
     * 电话
     */
    private String            tel;
    /**
     * id
     */
    private String            uid;
    /**
     * 邮编
     */
    private String            zip;
    /**
     * 经纬度
     */
    private Point             point;

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPoiType() {
        return poiType;
    }

    public void setPoiType(String poiType) {
        this.poiType = poiType;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

}
