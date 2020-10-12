package io.lemur.map.model.baidu.web.direction.bus.route;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 公交信息
 * @author JueYue
 * @date 2015年1月27日
 */
public class DirectionBusVehicleModel implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /**
     * 公交线路起点名称
     */
    @JsonProperty("start_name")
    private String            start_name;
    /**
     * 公交线路终点名称
     */
    @JsonProperty("end_name")
    private String            end_name;
    /**
     * 公交线路的末班车时间
     */
    @JsonProperty("end_time")
    private String            end_time;
    /**
     * 公交线路终点id
     */
    @JsonProperty("end_uid")
    private String            end_uid;
    /**
     * 公交线路名称
     */
    private String            name;
    @JsonProperty("start_time")
    /**
     * 公交线路首班车时间
     */
    private String            start_time;
    /**
     * 路段经过的站点数量
     */
    @JsonProperty("stop_num")
    private String            stop_num;
    /**
     * 公交线路起点id
     */
    @JsonProperty("start_uid")
    private String            start_uid;
    /**
     * 价格
     */
    @JsonProperty("total_price")
    private String            total_price;
    /**
     * 区间价
     */
    @JsonProperty("zone_price")
    private String            zone_price;
    /**
     * 公交线路类型
     */
    private String            type;
    /**
     * 公交线路id
     */
    private String            uid;

    public String getStart_name() {
        return start_name;
    }

    public void setStart_name(String start_name) {
        this.start_name = start_name;
    }

    public String getEnd_name() {
        return end_name;
    }

    public void setEnd_name(String end_name) {
        this.end_name = end_name;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getEnd_uid() {
        return end_uid;
    }

    public void setEnd_uid(String end_uid) {
        this.end_uid = end_uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getStop_num() {
        return stop_num;
    }

    public void setStop_num(String stop_num) {
        this.stop_num = stop_num;
    }

    public String getStart_uid() {
        return start_uid;
    }

    public void setStart_uid(String start_uid) {
        this.start_uid = start_uid;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    public String getZone_price() {
        return zone_price;
    }

    public void setZone_price(String zone_price) {
        this.zone_price = zone_price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

}
