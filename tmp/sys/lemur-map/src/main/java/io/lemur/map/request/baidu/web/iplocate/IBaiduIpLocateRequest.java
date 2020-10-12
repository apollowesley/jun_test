package io.lemur.map.request.baidu.web.iplocate;

import cn.afterturn.http.annotation.IRequest;
import cn.afterturn.http.annotation.IRequestMethod;
import cn.afterturn.http.annotation.IRequestParam;
import cn.afterturn.http.entity.enums.RequestTypeEnum;
import io.lemur.map.model.baidu.web.iplocate.IpLocateModel;

/**
 * IP地址获取地理
 * @author JueYue
 * @date 2015年1月26日
 */
@IRequest("baiduIpLocateRequest")
public interface IBaiduIpLocateRequest {
    /**
     * 根据IP获取地址信息
     * @param ip
     * @param coor
     * @param ak
     * @return
     */
    @IRequestMethod(type = RequestTypeEnum.GET, url = "http://api.map.baidu.com/location/ip?")
    public IpLocateModel getIpAddr(@IRequestParam("ip") String ip,
                                   @IRequestParam("coor") String coor,
                                   @IRequestParam("ak") String ak);

}
