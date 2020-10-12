package io.lemur.map.model.baidu.web.iplocate;

import java.io.Serializable;

import io.lemur.map.model.baidu.web.base.AddrDetailModel;
import io.lemur.map.model.base.Point;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * IP定位详情
 * @author JueYue
 * @date 2015年1月26日
 */
public class IpContentDetailModel implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /**
     * 地址
     */
    private String          address;
    /**
     * 地址详情
     */
    @JsonProperty("address_detail")
    private AddrDetailModel addrDetail;
    /**
     * 经纬度
     */
    private Point           point;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public AddrDetailModel getAddrDetail() {
        return addrDetail;
    }

    public void setAddrDetail(AddrDetailModel addrDetail) {
        this.addrDetail = addrDetail;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

}
