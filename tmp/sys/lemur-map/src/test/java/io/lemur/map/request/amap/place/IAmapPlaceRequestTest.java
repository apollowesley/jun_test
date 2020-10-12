package io.lemur.map.request.amap.place;

import static org.junit.Assert.*;
import io.lemur.common.util.json.JSONUtil;
import io.lemur.map.model.amap.place.AmapPlaceModel;
import io.lemur.map.model.baidu.web.placesuggestion.PlaceSuggestionModel;
import io.lemur.map.request.spring.SpringTxTestCase;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class IAmapPlaceRequestTest extends SpringTxTestCase {
    
    @Autowired
    private IAmapPlaceRequest amapPlaceRequest;

    @Test
    public void testPlaceQuery() {
        AmapPlaceModel model = amapPlaceRequest.placeQuery("10", "1", "0731", "长房时代", "90c9e3b1530ae18fcbe32809f4b8823e");
        System.out.println(JSONUtil.toJson(model));
    }

}
