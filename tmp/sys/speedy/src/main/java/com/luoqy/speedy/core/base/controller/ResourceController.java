package com.luoqy.speedy.core.base.controller;

import com.luoqy.speedy.config.WebConfig;
import com.luoqy.speedy.data.MySqldbUtil;
import com.luoqy.speedy.util.FileHandle;
import com.luoqy.speedy.util.Result;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.InputStream;
import java.util.*;

/**
 * 对资源进行一个整合管理
 */
@Controller
@RequestMapping("/common/resource")
public class ResourceController {

    private String PREFIX = "WEB-INF/resources/";

    /**
     * @return
     */
    @RequestMapping("")
    public String index(Model model){

        return PREFIX+"resource";
    }

    @RequestMapping("/delete")
    @ResponseBody
    public Result delete(String fileName){
        Result result=new Result();
        File file=new File(fileName);
        if(file.exists()){
            file.delete();
            result.setState(1);
            result.setMessage("删除成功");
            result.setData(new String[0]);
        }else{
            result.setState(0);
            result.setMessage("删除失败，文件已不存在");
            result.setData(new String[0]);
        }
        return result;
    }
    /**
     * 加载资源列表
     * */
    @RequestMapping("/list")
    @ResponseBody
    public Result resources() {
        Result result = new Result();
        List<Map<String,String>> data= FileHandle.listFile(null);
        if(data.size()>0){
            result.setMessage("数据加载成功");
            result.setData(data);
            result.setState(1);
        }else{
            result.setMessage("没有数据");
            result.setData(data);
            result.setState(0);
        }
        return result;
    }
}
