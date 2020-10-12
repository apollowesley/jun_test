package io.lemur.map.model.baidu.web.direction.bus.route;

import io.lemur.map.model.baidu.web.base.LocationModel;

import java.io.Serializable;

/**
 * 公交出行步骤
 * @author JueYue
 * @date 2015年1月27日
 */
public class DirectionBusStepModel implements Serializable {

    /**
     * 
     */
    private static final long        serialVersionUID = 1L;

    /**
     * 方案距离 /米
     */
    private double                   distance;
    /**
     * 线路耗时 /秒
     */
    private double                   duration;
    /**
     * 道路起点
     */
    private LocationModel            stepOriginLocation;
    /**
     * 道路终点
     */
    private LocationModel            stepDestinationLocation;

    private String                   path;
    /**
     * 道路类型
     */
    private int                      type;
    /**
     * 路段说明
     */
    private String                   stepInstruction;

    private DirectionBusVehicleModel vehicle;

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public LocationModel getStepOriginLocation() {
        return stepOriginLocation;
    }

    public void setStepOriginLocation(LocationModel stepOriginLocation) {
        this.stepOriginLocation = stepOriginLocation;
    }

    public LocationModel getStepDestinationLocation() {
        return stepDestinationLocation;
    }

    public void setStepDestinationLocation(LocationModel stepDestinationLocation) {
        this.stepDestinationLocation = stepDestinationLocation;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getStepInstruction() {
        return stepInstruction;
    }

    public void setStepInstruction(String stepInstruction) {
        this.stepInstruction = stepInstruction;
    }

    public DirectionBusVehicleModel getVehicle() {
        return vehicle;
    }

    public void setVehicle(DirectionBusVehicleModel vehicle) {
        this.vehicle = vehicle;
    }

}
