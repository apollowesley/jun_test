package io.lemur.map.request.baidu.web.geocoding;

import static org.junit.Assert.*;
import io.lemur.common.util.json.JSONUtil;
import io.lemur.map.model.baidu.web.geocoding.BaiduGeocodingToMapModel;
import io.lemur.map.model.baidu.web.geocoding.BaiduGeocodingToRealityModel;
import io.lemur.map.model.base.enmus.CoordinateSystem;
import io.lemur.map.request.baidu.web.geocoding.IBaiduGeocodingRequest;
import io.lemur.map.request.spring.SpringTxTestCase;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class IGeocodingRequestTest extends SpringTxTestCase {

    @Autowired
    private IBaiduGeocodingRequest geocodingRequest;

    //@Test
    public void testToMap() {
        BaiduGeocodingToMapModel model = geocodingRequest.toMap("北京市海淀区中关村南大街27号", "长沙",
            "7fcf6b7f0ef2fcff63cc42ad4330345d");
        System.out.println(JSONUtil.toJson(model));
    }

    @Test
    public void testToReality() {
        BaiduGeocodingToRealityModel model = geocodingRequest.toReality("28.21347823,112.97935279", 2,
            CoordinateSystem.BD09.getValue(), "7fcf6b7f0ef2fcff63cc42ad4330345d");
        System.out.println(JSONUtil.toJson(model));
    }

}
