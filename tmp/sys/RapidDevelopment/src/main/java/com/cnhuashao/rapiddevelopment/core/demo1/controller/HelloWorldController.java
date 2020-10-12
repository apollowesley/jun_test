package com.cnhuashao.rapiddevelopment.core.demo1.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <b>TODO(com.cnhuashao.rapiddevelopment.core.demo1.controller @TODO)</b><br>
 * 本软件仅对本次教程负责，版权所有 <a href="http://www.whndj.com
 * <p>
 * ">中国，華少</a><br>
 *
 * @Description: 创建人：cnHuaShao
 * @CreateDate 2019 2019/10/24 22:43 10 修改人：cnHuaShao
 * <a href="mailto:lz2392504@gmail.com
 * <p>
 * ">cnHuaShao</a>
 * 修改备注：
 * @since V1.0
 */
@RestController
public class HelloWorldController {

    private Logger log = LoggerFactory.getLogger(HelloWorldController.class);

    @RequestMapping("/")
    public String getHelloWorld(){
        log.info("HelloWorld方法被调用");
        return "Hello World SpringBoot";
    }
}