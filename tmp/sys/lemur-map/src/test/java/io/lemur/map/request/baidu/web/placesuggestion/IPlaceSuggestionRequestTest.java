package io.lemur.map.request.baidu.web.placesuggestion;

import static org.junit.Assert.*;
import io.lemur.common.util.json.JSONUtil;
import io.lemur.map.model.baidu.web.placesuggestion.PlaceSuggestionModel;
import io.lemur.map.request.baidu.web.placesuggestion.IBaiduPlaceSuggestionRequest;
import io.lemur.map.request.spring.SpringTxTestCase;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class IPlaceSuggestionRequestTest extends SpringTxTestCase {

    @Autowired
    private IBaiduPlaceSuggestionRequest iPlaceSuggestionRequest;

    @Test
    public void testPlaceQuery() {
        PlaceSuggestionModel model = iPlaceSuggestionRequest.placeQuery("湘邮", "长沙",
            "7fcf6b7f0ef2fcff63cc42ad4330345d");
        System.out.println(JSONUtil.toJson(model));
    }

}
