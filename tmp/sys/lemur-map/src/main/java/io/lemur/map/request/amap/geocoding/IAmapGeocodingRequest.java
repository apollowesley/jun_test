package io.lemur.map.request.amap.geocoding;

import cn.afterturn.http.annotation.IRequest;
import cn.afterturn.http.annotation.IRequestMethod;
import cn.afterturn.http.annotation.IRequestParam;
import cn.afterturn.http.entity.enums.RequestTypeEnum;
import io.lemur.map.model.amap.geocoding.AmapGeocodingToRealityModel;

/**
 * Geocoding API包括地址解析/逆地址解析
 * @author JueYue
 * @date 2015年1月26日
 */
@IRequest("amapgeocodingRequest")
public interface IAmapGeocodingRequest {

    /**
     * 逆地址解析
     * @param location
     * @param pois
     * @param coordtype
     * @param ak
     * @return
     */
    @IRequestMethod(type = RequestTypeEnum.GET, url = "http://restapi.amap.com/v3/geocode/regeo?")
    public AmapGeocodingToRealityModel toReality(@IRequestParam("location") String location,
                                                 @IRequestParam("key") String key);

}
