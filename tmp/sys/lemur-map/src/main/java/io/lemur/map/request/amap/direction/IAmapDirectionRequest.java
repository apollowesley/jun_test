package io.lemur.map.request.amap.direction;

import cn.afterturn.http.annotation.IRequest;
import cn.afterturn.http.annotation.IRequestMethod;
import cn.afterturn.http.annotation.IRequestParam;
import cn.afterturn.http.entity.enums.RequestTypeEnum;
import io.lemur.map.model.amap.bus.AmapBusDirectionModel;

/**
 * 
 * @author JueYue
 * @date 2015年1月31日
 */
@IRequest("amapDirectionRequest")
public interface IAmapDirectionRequest {

    @IRequestMethod(type = RequestTypeEnum.GET, url = "http://restapi.amap.com/v3/direction/transit/integrated?extensions=all&s=rsv3&rf=h5&utm_source=litemap")
    public AmapBusDirectionModel busDirection(@IRequestParam("city") String city,
                                              @IRequestParam("strategy") String strategy,
                                              @IRequestParam("origin") String origin,
                                              @IRequestParam("destination") String destination,
                                              @IRequestParam("key") String key);

    @IRequestMethod(type = RequestTypeEnum.GET, url = "http://apis.mapabc.com/gss/simple?encode=utf-8&srctype=BUS&type=%E5%85%AC%E4%BA%A4%E7%AB%99&number=10&batch=1&range=3000&resType=json&retvalue=0")
    public String busLatAndLon(@IRequestParam("key") String key,
                                              @IRequestParam("sid") int sid,
                                              @IRequestParam("keyword") String keyword,
                                              @IRequestParam("city") String city,
                                              @IRequestParam("rid") int rid);
}
