package io.lemur.map.model.base;

/**
 * 地图点信息
 * @author JueYue
 * @date 2015年1月31日
 */
public class PoiPoint extends Point {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     *  地点名称
     **/
    private String            name;
    /**
     *  地点类型
     **/
    private String            type;
    /**
     *  地址
     **/
    private String            address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
