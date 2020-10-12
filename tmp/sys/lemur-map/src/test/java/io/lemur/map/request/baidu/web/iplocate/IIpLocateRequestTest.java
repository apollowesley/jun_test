package io.lemur.map.request.baidu.web.iplocate;

import static org.junit.Assert.*;
import io.lemur.common.util.json.JSONUtil;
import io.lemur.map.model.baidu.web.iplocate.IpLocateModel;
import io.lemur.map.model.base.enmus.CoordinateSystem;
import io.lemur.map.request.baidu.web.iplocate.IBaiduIpLocateRequest;
import io.lemur.map.request.spring.SpringTxTestCase;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class IIpLocateRequestTest extends SpringTxTestCase {

    @Autowired
    private IBaiduIpLocateRequest ipLocateRequest;

    @Test
    public void testGetIpAddr() {
        IpLocateModel model = ipLocateRequest.getIpAddr("175.9.55.32",
            CoordinateSystem.BD09.getValue(), "7fcf6b7f0ef2fcff63cc42ad4330345d");
        System.out.println(JSONUtil.toJson(model));
    }
}
