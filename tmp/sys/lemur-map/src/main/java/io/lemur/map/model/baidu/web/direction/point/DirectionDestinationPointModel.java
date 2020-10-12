package io.lemur.map.model.baidu.web.direction.point;

import io.lemur.map.model.baidu.web.base.LocationModel;

/**
 * 导航起点信息
 * @author JueYue
 * @date 2015年1月27日
 */
public class DirectionDestinationPointModel extends DirectionPointModel {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private LocationModel     destinationPt;

    public LocationModel getDestinationPt() {
        return destinationPt;
    }

    public void setDestinationPt(LocationModel destinationPt) {
        this.destinationPt = destinationPt;
    }

    
}
