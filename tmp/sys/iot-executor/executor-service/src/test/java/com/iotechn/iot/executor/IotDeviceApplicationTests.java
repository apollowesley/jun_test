package com.iotechn.iot.executor;

import com.iotechn.iot.executor.mapper.ExecutorDeviceLogMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ExecutorApplication.class)
public class IotDeviceApplicationTests {

    @Autowired
    private ExecutorDeviceLogMapper executorDeviceLogMapper;


    @Test
    public void load() throws Exception {

    }

    @Test
    public void testExecutorLog() {
        List<String> list = executorDeviceLogMapper.selectKeys("xxx");
        System.out.println(list);
    }

}
