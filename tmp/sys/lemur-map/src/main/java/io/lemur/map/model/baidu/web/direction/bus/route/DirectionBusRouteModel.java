package io.lemur.map.model.baidu.web.direction.bus.route;

import java.io.Serializable;
import java.util.List;

/**
 * 公交车道路信息
 * @author JueYue
 * @date 2015年1月27日
 */
public class DirectionBusRouteModel implements Serializable {

    /**
     * 
     */
    private static final long             serialVersionUID = 1L;

    private List<DirectionBusSchemeModel> scheme;

    public List<DirectionBusSchemeModel> getScheme() {
        return scheme;
    }

    public void setScheme(List<DirectionBusSchemeModel> scheme) {
        this.scheme = scheme;
    }

}
