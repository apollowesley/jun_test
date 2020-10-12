package io.lemur.map.model.baidu.web.direction.walk.route;

import io.lemur.map.model.baidu.web.base.LocationModel;

import java.io.Serializable;
import java.util.List;

/**
 * 导航道路信息
 * @author JueYue
 * @date 2015年1月27日
 */
public class DirectionWalkRouteModel implements Serializable {

    /**
     * 
     */
    private static final long             serialVersionUID = 1L;
    /**
     * 方案距离 /米
     */
    private double                        distance;
    /**
     * 线路耗时 /秒
     */
    private double                        duration;
    /**
     * 道路起点
     */
    private LocationModel                 originLocation;
    /**
     * 道路终点
     */
    private LocationModel                 destinationLocation;
    /**
     * 道路信息
     */
    private List<DirectionWalkRouteStepModel> steps;

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

    public List<DirectionWalkRouteStepModel> getSteps() {
        return steps;
    }

    public void setSteps(List<DirectionWalkRouteStepModel> steps) {
        this.steps = steps;
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

}
