package io.lemur.map.model.base;

import java.io.Serializable;
import java.util.List;

/**
 * 线
 * @author JueYue
 * @date 2015年1月30日
 */
public class Line implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * 时间
     */
    private double            duration;
    /**
     * 距离
     */
    private double            distance;
    /**
     * 点信息
     */
    private List<Point>       points;

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public List<Point> getPoints() {
        return points;
    }

    public void setPoints(List<Point> points) {
        this.points = points;
    }

}
