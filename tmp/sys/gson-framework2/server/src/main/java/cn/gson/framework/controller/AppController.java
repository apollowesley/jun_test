package cn.gson.framework.controller;

import cn.gson.framework.annotation.JwtUser;
import cn.gson.framework.common.JsonResponse;
import cn.gson.framework.common.ResourceAuthorityProperties;
import cn.gson.framework.model.pojo.Account;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>****************************************************************************</p>
 * <p><b>Copyright © 2010-2019 soho team All Rights Reserved<b></p>
 * <ul style="margin:15px;">
 * <li>Description : cn.gson.framework.controller</li>
 * <li>Version     : 1.0</li>
 * <li>Creation    : 2019年01月25日</li>
 * <li>@author     : ____′↘夏悸</li>
 * </ul>
 * <p>****************************************************************************</p>
 */
@RestController
@RequestMapping("/")
@Slf4j
public class AppController {

    @Autowired
    ResourceAuthorityProperties resources;

    @GetMapping("init")
    public JsonResponse index(@JwtUser Account userDto) {
        return JsonResponse.success("", userDto);
    }
}
