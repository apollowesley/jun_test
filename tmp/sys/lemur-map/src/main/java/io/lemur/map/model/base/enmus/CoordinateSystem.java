package io.lemur.map.model.base.enmus;

/**
 * 坐标系枚举
 * 坐标的类型，目前支持的坐标类型包括：bd09ll（百度经纬度坐标）、gcj02ll（国测局经纬度坐标）、wgs84ll（ GPS经纬度）
 * 除了百度的是bd09,其他的都是gcj02
 * @author JueYue
 * @date 2015年1月26日
 */
public enum CoordinateSystem {

    BD09 ("bd09ll" , "百度经纬度坐标") , GCJ02 ("gcj02ll" , "国测局经纬度坐标") , WGS84 ("wgs84ll" , "GPS经纬度");

    private String value;
    private String name;

    CoordinateSystem(String value, String name) {
        this.name = name;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
