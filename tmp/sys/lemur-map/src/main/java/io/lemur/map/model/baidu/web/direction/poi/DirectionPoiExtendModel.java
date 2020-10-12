package io.lemur.map.model.baidu.web.direction.poi;

/**
 * POI扩展信息
 * @author JueYue
 * @date 2015年1月27日
 */
public class DirectionPoiExtendModel extends DirectionPoiModel {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /**
     * 地点名称
     */
    private String            cityName;

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

}
