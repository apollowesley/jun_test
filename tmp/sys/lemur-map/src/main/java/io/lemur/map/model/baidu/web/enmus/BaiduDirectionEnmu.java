package io.lemur.map.model.baidu.web.enmus;

/**
 * 公交、驾车、步行查询
 * @author JueYue
 * @date 2015年1月26日
 */
public enum BaiduDirectionEnmu {

    DRIVING ("driving" , "驾车") , WALKING ("walking" , "步行") , TRANSIT ("transit" , "公交");

    private String value;
    private String name;

    BaiduDirectionEnmu(String value, String name) {
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
