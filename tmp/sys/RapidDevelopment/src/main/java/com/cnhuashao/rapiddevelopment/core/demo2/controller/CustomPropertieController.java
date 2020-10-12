package com.cnhuashao.rapiddevelopment.core.demo2.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <b>com.cnhuashao.rapiddevelopment.core.demo2</b><br>
 * 本软件仅对本次教程负责，版权所有 <a href="http://www.cnhuashao.com ">cn華少</a><br>
 *
 * @Description: TODO 创建人：cnHuaShao
 * @CreateDate 2019 2019/10/29 22:28 10
 * <a href="mailto:lz2392504@gmail.com">cnHuaShao</a>
 * @since V1.0
 */
@RestController
public class CustomPropertieController {

    private Logger log=LoggerFactory.getLogger(CustomPropertieController.class);

    /**
     * 读取systemc中的username属性
     */
    @Value("${system.username}")
    private String username;

    /**
     * 读取sytemc中的password属性
     */
    @Value("${system.password}")
    private String password;

    /**
     * 进行打印相关属性参数
     * @return
     */
    @RequestMapping("/getDemo2")
    public String getCustomPropertie(){
        StringBuffer systemToString = new StringBuffer();
        systemToString.append("username: ");
        systemToString.append(username);
        systemToString.append(" ");
        systemToString.append("password: ");
        systemToString.append(password);
        return systemToString.toString();
    }
}