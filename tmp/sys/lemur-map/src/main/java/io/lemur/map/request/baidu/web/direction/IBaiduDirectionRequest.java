package io.lemur.map.request.baidu.web.direction;

import cn.afterturn.http.annotation.IRequest;
import cn.afterturn.http.annotation.IRequestMethod;
import cn.afterturn.http.annotation.IRequestParam;
import cn.afterturn.http.entity.enums.RequestTypeEnum;
import io.lemur.map.model.baidu.web.direction.bus.unique.DirectionOfBusUniqueModel;
import io.lemur.map.model.baidu.web.direction.car.unique.DirectionOfCarUniqueModel;
import io.lemur.map.model.baidu.web.direction.walk.unique.DirectionOfWalkUniqueModel;

/**
 * 导航接口
 * @author JueYue
 * @date 2015年1月27日
 */
@IRequest("baiduDirectionRequest")
public interface IBaiduDirectionRequest {
    /**
     * 车辆导航
     * @param origin        起点
     * @param destination   终点  
     * @param originRegion  起点地区
     * @param destinationRegion 终点地区
     * @param coordType     坐标类型
     * @param tactics       导航策略
     * @param ak
     * @return
     */
    @IRequestMethod(type = RequestTypeEnum.GET, url = "http://api.map.baidu.com/direction/v1?mode=driving&output=json")
    public String directionCar(@IRequestParam("origin") String origin,
                               @IRequestParam("destination") String destination,
                               @IRequestParam("origin_region") String originRegion,
                               @IRequestParam("destination_region") String destinationRegion,
                               @IRequestParam("coord_type") String coordType,
                               @IRequestParam("tactics") String tactics,
                               @IRequestParam("ak") String ak);

    /**
     * 车辆导航--点到点
     * @param origin        起点
     * @param destination   终点  
     * @param originRegion  起点地区
     * @param destinationRegion 终点地区
     * @param coordType     坐标类型
     * @param tactics       导航策略
     * @param ak
     * @return
     */
    @IRequestMethod(type = RequestTypeEnum.GET, url = "http://api.map.baidu.com/direction/v1?mode=driving&output=json")
    public DirectionOfCarUniqueModel directionCarByPoint(@IRequestParam("origin") String origin,
                                                         @IRequestParam("destination") String destination,
                                                         @IRequestParam("origin_region") String originRegion,
                                                         @IRequestParam("destination_region") String destinationRegion,
                                                         @IRequestParam("coord_type") String coordType,
                                                         @IRequestParam("tactics") String tactics,
                                                         @IRequestParam("ak") String ak);

    /**
     * 公交导航
     * @param origin        起点
     * @param destination   终点  
     * @param region  起点地区
     * @param coordType     坐标类型
     * @param tactics       导航策略
     * @param ak
     * @return
     */
    @IRequestMethod(type = RequestTypeEnum.GET, url = "http://api.map.baidu.com/direction/v1?mode=transit&output=json")
    public String directionBus(@IRequestParam("origin") String origin,
                               @IRequestParam("destination") String destination,
                               @IRequestParam("region") String region,
                               @IRequestParam("coord_type") String coordType,
                               @IRequestParam("tactics") String tactics,
                               @IRequestParam("ak") String ak);

    /**
     * 公交导航--点到点
     * @param origin        起点
     * @param destination   终点  
     * @param region  起点地区
     * @param coordType     坐标类型
     * @param tactics       导航策略
     * @param ak
     * @return
     */
    @IRequestMethod(type = RequestTypeEnum.GET, url = "http://api.map.baidu.com/direction/v1?mode=transit&output=json")
    public DirectionOfBusUniqueModel directionBusByPoint(@IRequestParam("origin") String origin,
                                                         @IRequestParam("destination") String destination,
                                                         @IRequestParam("region") String region,
                                                         @IRequestParam("coord_type") String coordType,
                                                         @IRequestParam("tactics") String tactics,
                                                         @IRequestParam("ak") String ak);

    /**
     * 步行导航
     * @param origin        起点
     * @param destination   终点  
     * @param region  起点地区
     * @param coordType     坐标类型
     * @param tactics       导航策略
     * @param ak
     * @return
     */
    @IRequestMethod(type = RequestTypeEnum.GET, url = "http://api.map.baidu.com/direction/v1?mode=walking&output=json")
    public String directionWalk(@IRequestParam("origin") String origin,
                                @IRequestParam("destination") String destination,
                                @IRequestParam("region") String region,
                                @IRequestParam("coord_type") String coordType,
                                @IRequestParam("tactics") String tactics,
                                @IRequestParam("ak") String ak);

    /**
     * 步行导航---点到点
     * @param origin        起点
     * @param destination   终点  
     * @param region  起点地区
     * @param coordType     坐标类型
     * @param tactics       导航策略
     * @param ak
     * @return
     */
    @IRequestMethod(type = RequestTypeEnum.GET, url = "http://api.map.baidu.com/direction/v1?mode=walking&output=json")
    public DirectionOfWalkUniqueModel directionWalkByPoint(@IRequestParam("origin") String origin,
                                                           @IRequestParam("destination") String destination,
                                                           @IRequestParam("region") String region,
                                                           @IRequestParam("coord_type") String coordType,
                                                           @IRequestParam("tactics") String tactics,
                                                           @IRequestParam("ak") String ak);

}
