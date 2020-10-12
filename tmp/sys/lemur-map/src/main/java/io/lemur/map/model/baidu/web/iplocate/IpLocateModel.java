package io.lemur.map.model.baidu.web.iplocate;

import io.lemur.map.model.baidu.web.base.BaiduBaseModel;

/**
 * IP定位
 * @author JueYue
 * @date 2015年1月26日
 */
public class IpLocateModel extends BaiduBaseModel {

    /**
     * 
     */
    private static final long    serialVersionUID = 1L;
    /**
     * 地址信息
     */
    private String               address;
    /**
     * 地址详情
     */
    private IpContentDetailModel content;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public IpContentDetailModel getContent() {
        return content;
    }

    public void setContent(IpContentDetailModel content) {
        this.content = content;
    }

}
