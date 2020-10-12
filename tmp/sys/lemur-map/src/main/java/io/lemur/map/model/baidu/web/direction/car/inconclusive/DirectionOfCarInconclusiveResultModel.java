package io.lemur.map.model.baidu.web.direction.car.inconclusive;

import io.lemur.map.model.baidu.web.direction.poi.DirectionPoiExtendModel;
import io.lemur.map.model.baidu.web.direction.point.DirectionPointModel;

import java.io.Serializable;
import java.util.List;

public class DirectionOfCarInconclusiveResultModel implements Serializable {

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
    private List<DirectionPoiExtendModel> origin;
    /**
     * 终点列表
     */
    private List<DirectionPoiExtendModel> destination;

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

    public List<DirectionPoiExtendModel> getOrigin() {
        return origin;
    }

    public void setOrigin(List<DirectionPoiExtendModel> origin) {
        this.origin = origin;
    }

    public List<DirectionPoiExtendModel> getDestination() {
        return destination;
    }

    public void setDestination(List<DirectionPoiExtendModel> destination) {
        this.destination = destination;
    }

}
