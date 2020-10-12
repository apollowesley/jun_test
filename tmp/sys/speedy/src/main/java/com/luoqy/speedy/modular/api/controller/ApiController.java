package com.luoqy.speedy.modular.api.controller;

import com.luoqy.speedy.util.Result;
import com.luoqy.speedy.util.aop.log.Log;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api2")
public class ApiController {
    @RequestMapping("/test")
    @ResponseBody
    @Log({"调用了接口","api类型"})
    public Result test(){
        Result result=new Result();
        result.setMessage("调用成功");
        result.setData(new String[0]);
        result.setState(1);
        return result;
    }
}
