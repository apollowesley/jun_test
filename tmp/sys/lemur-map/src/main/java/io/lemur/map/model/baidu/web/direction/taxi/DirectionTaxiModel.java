package io.lemur.map.model.baidu.web.direction.taxi;

import java.io.Serializable;
import java.util.List;

/**
 * 出租车信息
 * @author JueYue
 * @date 2015年1月27日
 */
public class DirectionTaxiModel implements Serializable {

    /**
     * 
     */
    private static final long              serialVersionUID = 1L;

    /**
     * 方案距离 /米
     */
    private double                         distance;
    /**
     * 线路耗时 /秒
     */
    private double                         duration;
    /**
     * 全程6.3公里;3.0公里起步；燃油附加费1.0元；里程超过15.0公里，超过部分每公里收3.5元。
     */
    private String                         remark;

    private List<DirectionTaxiDetailModel> detail;

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<DirectionTaxiDetailModel> getDetail() {
        return detail;
    }

    public void setDetail(List<DirectionTaxiDetailModel> detail) {
        this.detail = detail;
    }

}
