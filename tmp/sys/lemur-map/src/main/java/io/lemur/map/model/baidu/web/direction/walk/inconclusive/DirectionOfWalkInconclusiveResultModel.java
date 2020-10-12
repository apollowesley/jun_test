package io.lemur.map.model.baidu.web.direction.walk.inconclusive;

import io.lemur.map.model.baidu.web.direction.point.DirectionPointModel;

import java.io.Serializable;

public class DirectionOfWalkInconclusiveResultModel implements Serializable {

    /**
     * 
     */
    private static final long             serialVersionUID = 1L;
    /**
     * 起点
     */
    private DirectionPointModel           originInfo;
    /**
     * 终点
     */
    private DirectionPointModel           destinationInfo;
    /**
     * 起点列表
     */
    private DirectionWalkPointListModel origin;
    /**
     * 终点列表
     */
    private DirectionWalkPointListModel destination;

    public DirectionPointModel getOriginInfo() {
        return originInfo;
    }

    public void setOriginInfo(DirectionPointModel originInfo) {
        this.originInfo = originInfo;
    }

    public DirectionPointModel getDestinationInfo() {
        return destinationInfo;
    }

    public void setDestinationInfo(DirectionPointModel destinationInfo) {
        this.destinationInfo = destinationInfo;
    }

    public DirectionWalkPointListModel getOrigin() {
        return origin;
    }

    public void setOrigin(DirectionWalkPointListModel origin) {
        this.origin = origin;
    }

    public DirectionWalkPointListModel getDestination() {
        return destination;
    }

    public void setDestination(DirectionWalkPointListModel destination) {
        this.destination = destination;
    }

}
