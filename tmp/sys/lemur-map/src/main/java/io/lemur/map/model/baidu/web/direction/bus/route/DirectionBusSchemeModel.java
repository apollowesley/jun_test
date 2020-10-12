package io.lemur.map.model.baidu.web.direction.bus.route;

import io.lemur.map.model.baidu.web.base.LocationModel;

import java.io.Serializable;

/**
 * 公交计划信息
 * @author JueYue
 * @date 2015年1月27日
 */
public class DirectionBusSchemeModel implements Serializable {

    /**
     * 
     */
    private static final long         serialVersionUID = 1L;

    /**
     * 方案距离 /米
     */
    private double                    distance;
    /**
     * 线路耗时 /秒
     */
    private double                    duration;
    /**
     * 道路起点
     */
    private LocationModel             originLocation;
    /**
     * 道路终点
     */
    private LocationModel             destinationLocation;

    private DirectionBusStepModel[][] steps;

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

    public LocationModel getOriginLocation() {
        return originLocation;
    }

    public void setOriginLocation(LocationModel originLocation) {
        this.originLocation = originLocation;
    }

    public LocationModel getDestinationLocation() {
        return destinationLocation;
    }

    public void setDestinationLocation(LocationModel destinationLocation) {
        this.destinationLocation = destinationLocation;
    }

    public DirectionBusStepModel[][] getSteps() {
        return steps;
    }

    public void setSteps(DirectionBusStepModel[][] steps) {
        this.steps = steps;
    }

}
