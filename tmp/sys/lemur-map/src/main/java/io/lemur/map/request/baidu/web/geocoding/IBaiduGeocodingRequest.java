package io.lemur.map.request.baidu.web.geocoding;

import cn.afterturn.http.annotation.IRequest;
import cn.afterturn.http.annotation.IRequestMethod;
import cn.afterturn.http.annotation.IRequestParam;
import cn.afterturn.http.entity.enums.RequestTypeEnum;
import io.lemur.map.model.baidu.web.geocoding.BaiduGeocodingToMapModel;
import io.lemur.map.model.baidu.web.geocoding.BaiduGeocodingToRealityModel;

/**
 * Geocoding API包括地址解析/逆地址解析
 * @author JueYue
 * @date 2015年1月26日
 */
@IRequest("baiduGeocodingRequest")
public interface IBaiduGeocodingRequest {
    /**
     * 地址解析
     * @param address
     * @param city
     * @param ak
     * @return
     */
    @IRequestMethod(type = RequestTypeEnum.GET, url = "http://api.map.baidu.com/geocoder/v2/?output=json")
    public BaiduGeocodingToMapModel toMap(@IRequestParam("address") String address,
                                     @IRequestParam("city") String city,
                                     @IRequestParam("ak") String ak);

    /**
     * 逆地址解析
     * @param location
     * @param pois
     * @param coordtype
     * @param ak
     * @return
     */
    @IRequestMethod(type = RequestTypeEnum.GET, url = "http://api.map.baidu.com/geocoder/v2/?output=json")
    public BaiduGeocodingToRealityModel toReality(@IRequestParam("location") String location,
                                             @IRequestParam("pois") int pois,
                                             @IRequestParam("coordtype") String coordtype,
                                             @IRequestParam("ak") String ak);

}
