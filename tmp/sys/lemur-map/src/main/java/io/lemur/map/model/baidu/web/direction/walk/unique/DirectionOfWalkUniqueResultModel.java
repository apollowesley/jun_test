package io.lemur.map.model.baidu.web.direction.walk.unique;

import io.lemur.map.model.baidu.web.direction.car.route.DirectionCarRouteModel;
import io.lemur.map.model.baidu.web.direction.point.DirectionDestinationPointModel;
import io.lemur.map.model.baidu.web.direction.point.DirectionOriginPointModel;
import io.lemur.map.model.baidu.web.direction.taxi.DirectionTaxiModel;

import java.io.Serializable;
import java.util.List;

public class DirectionOfWalkUniqueResultModel implements Serializable {

    /**
     * 
     */
    private static final long              serialVersionUID = 1L;
    /**
     * 道路信息
     */
    private List<DirectionCarRouteModel>      routes;
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

    public List<DirectionCarRouteModel> getRoutes() {
        return routes;
    }

    public void setRoutes(List<DirectionCarRouteModel> routes) {
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
