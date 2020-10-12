package com.iotechn.iot.gw;

import com.alibaba.dubbo.config.annotation.Reference;
import com.iotechn.iot.executor.api.ExecutionOpenService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IotGwApplicationTests {

    @Reference
    private ExecutionOpenService executionOpenService;

    @Test
    public void contextLoads() {
    }

    @Test
    public void invokeMethod() {
        executionOpenService.test("acccc");
    }

}
