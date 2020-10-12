package io.lemur.map.model.baidu.web.direction.point;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 导航点信息
 * @author JueYue
 * @date 2015年1月27日
 */
public class DirectionPointModel implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /**
     * 起点所在城市代码
     */
    @JsonProperty("area_id")
    private String            areaId;
    /**
     * 城市名称
     */
    private String            cname;
    /**
     * 起点的id
     */
    private String            uid;
    /**
     * 起点的搜索关键字
     */
    private String            wd;

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getWd() {
        return wd;
    }

    public void setWd(String wd) {
        this.wd = wd;
    }

}
