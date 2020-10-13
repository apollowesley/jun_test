package com.pflm;

import com.alibaba.fastjson.JSONObject;
import com.pflm.modules.wechat.service.WxMenService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WecahtRenrenApplicationTests {
    @Autowired
    WxMenService wxMenService;
    /**
     * 模拟MVC对象，通过MockMvcBuilders.webAppContextSetup(this.wac).build()初始化。
     */
    private MockMvc mockMvc;

    /**
     * 注入WebApplicationContext
     */
    @Autowired
    private WebApplicationContext wac;

    /**
     * 在测试开始前初始化工作
     */
    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }
    @Test
    public void contextLoads() throws Exception{
//        MvcResult result = mockMvc.perform(post("/api/token/get").content(JSONObject.toJSONString(null)))
//                // 模拟向testRest发送get请求
//                .andExpect(status().isOk())
//                // 预期返回值的媒体类型application/json
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//                // 返回执行请求的结果
//                .andReturn();
//        System.err.println("返回结果：**********"+result.getResponse().getContentAsString());
    }

}
