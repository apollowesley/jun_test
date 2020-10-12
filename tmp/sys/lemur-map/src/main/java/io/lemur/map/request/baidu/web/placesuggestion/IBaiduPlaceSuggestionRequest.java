package io.lemur.map.request.baidu.web.placesuggestion;

import cn.afterturn.http.annotation.IRequest;
import cn.afterturn.http.annotation.IRequestMethod;
import cn.afterturn.http.annotation.IRequestParam;
import cn.afterturn.http.entity.enums.RequestTypeEnum;
import io.lemur.map.model.baidu.web.placesuggestion.PlaceSuggestionModel;

/**
 * 名称查询
 * @author JueYue
 * @date 2015年1月26日
 */
@IRequest("baiduPlaceSuggestionRequest")
public interface IBaiduPlaceSuggestionRequest {
    /**
     * 推荐查询
     * @param query 查询名称
     * @param region地区名称
     * @param ak
     * @return
     */
    @IRequestMethod(type = RequestTypeEnum.GET, url = "http://api.map.baidu.com/place/v2/suggestion?output=json")
    public PlaceSuggestionModel placeQuery(@IRequestParam("query") String query,
                                           @IRequestParam("region") String region,
                                           @IRequestParam("ak") String ak);

}
