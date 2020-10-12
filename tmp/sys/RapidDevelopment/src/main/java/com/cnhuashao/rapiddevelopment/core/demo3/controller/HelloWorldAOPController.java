package com.cnhuashao.rapiddevelopment.core.demo3.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 类 {@code HelloWorldAOPController} 使用AOP进行日志处理 <br> 进行统一日志请求处理.
 *
 * 本软件仅对本次教程负责，版权所有 <a href="http://www.cnhuashao.com">中国，華少</a><br>
 *
 * @Description: 创建人：cnHuaShao
 * <a href="mailto:lz2392504@gmail.com
 * <p>
 * ">cnHuaShao</a>
 * 修改备注：
 * @version v1.0.1 2019/11/4 18:54
 */
@RestController
public class HelloWorldAOPController {
    private Logger log = LoggerFactory.getLogger(HelloWorldAOPController.class);
    /**
     * hello请求测试 实例：http://localhost:8081/hello?name=world
     * @param name 打招呼的姓名
     * @return 返回打招呼的整体语句
     */
    @RequestMapping("/hello")
    public String hello(String name){
        StringBuffer hellos = new StringBuffer();
        hellos.append("Hello ");
        hellos.append(name);
        return hellos.toString();
    }


}
