package com.xinhuo.demo.controller;

import com.xinhuo.demo.Constants;
import com.xinhuo.demo.common.model.PageResponseMsg;
import com.xinhuo.demo.common.model.ResponseMsg;
import com.xinhuo.demo.model.User;
import com.xinhuo.demo.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * author 张新林
 * 时间 2019/1/20 18:14
 * 描述
 */
@RestController
@RequestMapping(value="/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value="/", method=RequestMethod.GET)
    @ResponseBody
    public PageResponseMsg view(int pageNum,User userFrom){
        QueryWrapper<User> queryWrapper = new QueryWrapper();

        if(StringUtils.isNotBlank(userFrom.getName())){
            queryWrapper.eq("name",userFrom.getName());//填写查询条件
        }
        //...，更多查询条件

        IPage<User> page = userService.page(new Page<>(pageNum,Constants.PAGE_SIZE),queryWrapper);

        PageResponseMsg result = new PageResponseMsg(page);

        return result;
    }

    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    @ResponseBody
    public ResponseMsg view(@PathVariable String id){
        User user = userService.getById(id);
        ResponseMsg responseMsg = new ResponseMsg();
        responseMsg.setData(user);
        return responseMsg;
    }

    @RequestMapping(value="/",method = RequestMethod.POST)
    @ResponseBody
    public ResponseMsg add(@RequestBody User user){
        ResponseMsg responseMsg = new ResponseMsg();
        boolean result = userService.save(user);
        if(result){
            responseMsg.setMessage("保存成功");
        }else{
            responseMsg.setCode(-1);
            responseMsg.setMessage("保存失败");
        }

        return responseMsg;
    }

    @RequestMapping(value="/{id}",method = RequestMethod.PUT)
    @ResponseBody
    public ResponseMsg update(@PathVariable String id,@RequestBody User user)throws Exception{
        ResponseMsg responseMsg = null;

        User preUser = userService.getById(id);
        if(preUser != null){
            //将user不为空的copy到preUser,更新user
            BeanUtils.copyProperties(user,preUser);
            boolean result = userService.updateById(preUser);
            if(result){
                responseMsg.setMessage("保存成功");
            }else{
                responseMsg = new ResponseMsg(-1,"保存失败");
            }

        }else{
            responseMsg = new ResponseMsg(-1,"该数据不存在");
        }

        return responseMsg;
    }

    @ResponseBody
    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public ResponseMsg delete(@PathVariable String id){
        ResponseMsg responseObject = null;
        if(StringUtils.isNotBlank(id)){
            boolean result = userService.removeById(id);
            if(result){
                responseObject = new ResponseMsg(0,"删除成功");
            }else{
                responseObject = new ResponseMsg(-1,"删除失败");
            }

        }else{
            responseObject = new ResponseMsg(-1,"参数错误");
        }
        return responseObject;
    }



}
