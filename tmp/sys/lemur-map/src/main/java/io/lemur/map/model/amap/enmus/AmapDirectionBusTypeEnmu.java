package io.lemur.map.model.amap.enmus;
/**
 * 公交出行路线选择
 * @author JueYue
 * @date 2015年1月28日
 */
public enum AmapDirectionBusTypeEnmu {

    BEST (0 , "最佳线路") , LESS_WALK (2 , "少步行") , LESS_TRANSFER (3 , "少换成") , NO_metro (5 , "不坐地铁");

    private int    value;
    private String name;

    AmapDirectionBusTypeEnmu(int value, String name) {
        this.name = name;
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
