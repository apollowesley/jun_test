package com.sise.school.teach.bussiness.admin.controller;

import com.sise.school.teach.bussiness.admin.service.AdminService;
import com.sise.school.teach.bussiness.admin.vo.req.AdminReqVO;
import com.sise.school.teach.vo.req.HeadBodyVO;
import com.sise.school.teach.vo.req.HeadVO;
import com.sise.school.teach.vo.resp.ResponseMsgVO;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author idea
 * @data 2018/10/14
 */
@Api(description = "管理员登录控制器")
@RestController
@RequestMapping(value = "/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping(value = "/login")
    public ResponseMsgVO<Boolean> login(@RequestBody HeadBodyVO<HeadVO,AdminReqVO> param){
        return adminService.login(param.getBody().getName(),param.getBody().getPassword(),param.getBody().getStatus());
    }
}
