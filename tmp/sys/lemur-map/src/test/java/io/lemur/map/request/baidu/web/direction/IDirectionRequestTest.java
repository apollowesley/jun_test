package io.lemur.map.request.baidu.web.direction;

import static org.junit.Assert.*;
import io.lemur.common.util.json.JSONUtil;
import io.lemur.map.model.baidu.web.direction.bus.unique.DirectionOfBusUniqueModel;
import io.lemur.map.model.baidu.web.direction.car.unique.DirectionOfCarUniqueModel;
import io.lemur.map.model.base.enmus.CoordinateSystem;
import io.lemur.map.request.baidu.web.direction.IBaiduDirectionRequest;
import io.lemur.map.request.spring.SpringTxTestCase;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class IDirectionRequestTest extends SpringTxTestCase {

    @Autowired
    private IBaiduDirectionRequest directionRequest;

   // @Test
    public void testDirectionCar() {
        DirectionOfCarUniqueModel model = directionRequest.directionCarByPoint(
            "28.225509,112.909788", "28.219589,112.93415", "长沙", "长沙",
            CoordinateSystem.BD09.getValue(), "11", "7fcf6b7f0ef2fcff63cc42ad4330345d");
        System.out.println(JSONUtil.toJson(model));
    }

    @Test
    public void testDirectionBus() {
        DirectionOfBusUniqueModel model = directionRequest.directionBusByPoint(
            "28.200204,113.006661", "28.21386,112.91931", "长沙", CoordinateSystem.BD09.getValue(),
            "12", "7fcf6b7f0ef2fcff63cc42ad4330345d");
        System.out.println(JSONUtil.toJson(model));
    }

    @Test
    public void testDirectionWalk() {

    }

}
