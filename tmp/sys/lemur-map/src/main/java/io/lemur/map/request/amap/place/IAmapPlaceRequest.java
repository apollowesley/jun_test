package io.lemur.map.request.amap.place;

import cn.afterturn.http.annotation.IRequest;
import cn.afterturn.http.annotation.IRequestMethod;
import cn.afterturn.http.annotation.IRequestParam;
import cn.afterturn.http.entity.enums.RequestTypeEnum;
import io.lemur.map.model.amap.place.AmapPlaceModel;

/**
 * 获取高德地理信息
 * @author JueYue
 * @date 2015年1月31日
 */
@IRequest("amapPlaceRequest")
public interface IAmapPlaceRequest {
    /**
     * 地点查询
     * @param offset 返回条数
     * @param page   当前页数
     * @param city   城市编码
     * @param keywords 关键字
     * @param key    密钥
     * @return
     */
    @IRequestMethod(type = RequestTypeEnum.GET, url = "http://restapi.amap.com/v3/place/text?s=rsv3")
    public AmapPlaceModel placeQuery(@IRequestParam("offset") String offset,
                                     @IRequestParam("page") String page,
                                     @IRequestParam("city") String city,
                                     @IRequestParam("keywords") String keywords,
                                     @IRequestParam("key") String key);

}
