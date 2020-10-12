package io.lemur.map.model.baidu.web.direction.bus.unique;

import io.lemur.map.model.baidu.web.direction.bus.route.DirectionBusRouteModel;
import io.lemur.map.model.baidu.web.direction.point.DirectionDestinationPointModel;
import io.lemur.map.model.baidu.web.direction.point.DirectionOriginPointModel;
import io.lemur.map.model.baidu.web.direction.taxi.DirectionTaxiModel;

import java.io.Serializable;
import java.util.List;

public class DirectionOfBusUniqueResultModel implements Serializable {

    /**
     * 
     */
    private static final long              serialVersionUID = 1L;
    /**
     * 道路信息
     */
    private List<DirectionBusRouteModel>      routes;
    /**
     * 起点信息
     */
    private DirectionOriginPointModel      origin;
    /**
     * 终端信息
     */
    private DirectionDestinationPointModel destination;
    /**
     * 出租车信息
     */
    private DirectionTaxiModel             taxi;

    public List<DirectionBusRouteModel> getRoutes() {
        return routes;
    }

    public void setRoutes(List<DirectionBusRouteModel> routes) {
        this.routes = routes;
    }

    public DirectionOriginPointModel getOrigin() {
        return origin;
    }

    public void setOrigin(DirectionOriginPointModel origin) {
        this.origin = origin;
    }

    public DirectionDestinationPointModel getDestination() {
        return destination;
    }

    public void setDestination(DirectionDestinationPointModel destination) {
        this.destination = destination;
    }

    public DirectionTaxiModel getTaxi() {
        return taxi;
    }

    public void setTaxi(DirectionTaxiModel taxi) {
        this.taxi = taxi;
    }

}
