package io.lemur.map.model.baidu.web.direction.bus.inconclusive;

import io.lemur.map.model.baidu.web.direction.bus.point.KeyPointModel;
import io.lemur.map.model.baidu.web.direction.poi.DirectionPoiModel;

import java.io.Serializable;
import java.util.List;

public class DirectionOfBusInconclusiveResultModel implements Serializable {

    /**
     * 
     */
    private static final long       serialVersionUID = 1L;
    /**
     * 起点
     */
    private KeyPointModel           originInfo;
    /**
     * 终点
     */
    private KeyPointModel           destinationInfo;

    /**
     * 起点列表
     */
    private List<DirectionPoiModel> origin;
    /**
     * 终点列表
     */
    private List<DirectionPoiModel> destination;

    public KeyPointModel getOriginInfo() {
        return originInfo;
    }

    public void setOriginInfo(KeyPointModel originInfo) {
        this.originInfo = originInfo;
    }

    public KeyPointModel getDestinationInfo() {
        return destinationInfo;
    }

    public void setDestinationInfo(KeyPointModel destinationInfo) {
        this.destinationInfo = destinationInfo;
    }

    public List<DirectionPoiModel> getOrigin() {
        return origin;
    }

    public void setOrigin(List<DirectionPoiModel> origin) {
        this.origin = origin;
    }

    public List<DirectionPoiModel> getDestination() {
        return destination;
    }

    public void setDestination(List<DirectionPoiModel> destination) {
        this.destination = destination;
    }

}
