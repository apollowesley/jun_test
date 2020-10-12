package com.cdh.springboot.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cdh.springboot.common.ResponseData;
import com.cdh.springboot.entity.User;
import com.cdh.springboot.service.IDeptService;
import com.cdh.springboot.service.IRoleService;
import com.cdh.springboot.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author CDHong
 * @since 2018-11-25
 */
@Controller
@RequestMapping("/user")
public class UserController {


}
