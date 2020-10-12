package io.lemur.map.model.baidu.web.direction.walk.inconclusive;

import io.lemur.map.model.baidu.web.direction.poi.DirectionPoiExtendModel;

import java.io.Serializable;
import java.util.List;

public class DirectionWalkPointListModel implements Serializable {

    /**
     * 
     */
    private static final long             serialVersionUID = 1L;

    private String                        cityName;

    private String                        listType;

    /**
     * 起点列表
     */
    private List<DirectionPoiExtendModel> content;

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getListType() {
        return listType;
    }

    public void setListType(String listType) {
        this.listType = listType;
    }

    public List<DirectionPoiExtendModel> getContent() {
        return content;
    }

    public void setContent(List<DirectionPoiExtendModel> content) {
        this.content = content;
    }

}
