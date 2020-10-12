package io.lemur.map.model.baidu.web.direction.taxi;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 出租信息详情
 * @author JueYue
 * @date 2015年1月27日
 */
public class DirectionTaxiDetailModel implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /**
     * 描述
     * 白天（05:00-23:00）
     */
    private String            desc;
    /**
     * 每公里价格    
     */
    @JsonProperty("km_price")
    private String            kmPrice;
    /**
     * 起步价
     */
    @JsonProperty("start_price")
    private String            startPrice;
    /**
     * 总价格
     */
    @JsonProperty("total_price")
    private String            totalPrice;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getKmPrice() {
        return kmPrice;
    }

    public void setKmPrice(String kmPrice) {
        this.kmPrice = kmPrice;
    }

    public String getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(String startPrice) {
        this.startPrice = startPrice;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

}
