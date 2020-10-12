package io.lemur.map.request.amap.direction;

import static org.junit.Assert.*;
import io.lemur.common.util.json.JSONUtil;
import io.lemur.map.model.amap.bus.AmapBusDirectionModel;
//import io.lemur.map.model.amap.station.AmapStationModel;
import io.lemur.map.request.spring.SpringTxTestCase;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class IAmapDirectionRequestTest extends SpringTxTestCase {

    @Autowired
    private IAmapDirectionRequest amapDirectionRequest;

    @Test
    public void testBusDirection() {
        AmapBusDirectionModel model = amapDirectionRequest.busDirection("0731", "0", "113.006661,28.200204",
            "112.91931,28.21386", "90c9e3b1530ae18fcbe32809f4b8823e");
        System.out.println(JSONUtil.toJson(model));
    }
    
    //@Test
    public void testBusLatAndLon() {
    	String model = amapDirectionRequest.busLatAndLon("b0a7db0b3a30f944a21c3682064dc70ef5b738b062f6479a5eca39725798b1ee300bd8d5de3a4ae3", 
    							1000, "麓景路口", "长沙", 5306);
    	String[] str = model.split("=");
    	//AmapStationModel station = JSONUtil.parseJson(str[1], AmapStationModel.class); 	
    	//System.out.println(JSONUtil.toJson(station));
    }

}
