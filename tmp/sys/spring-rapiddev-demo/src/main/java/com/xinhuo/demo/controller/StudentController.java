package com.xinhuo.demo.controller;

import com.xinhuo.demo.model.Student;
import com.xinhuo.demo.service.StudentService;
import com.xinhuo.demo.Constants;
import com.xinhuo.demo.common.model.PageResponseMsg;
import com.xinhuo.demo.common.model.ResponseMsg;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 描述: 
 * author: 新林
 * date: 2019-06-16
 */
@RestController
@RequestMapping(value="/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @RequestMapping(value="/", method=RequestMethod.GET)
    @ResponseBody
    public PageResponseMsg view(int pageNum,Student studentFrom){
        QueryWrapper<Student> queryWrapper = new QueryWrapper();
//        if(StringUtils.isNotBlank(studentFrom.getName())){
//            queryWrapper.eq("name",studentFrom.getName());//填写查询条件
//        }
        //...，更多查询条件
        IPage<Student> page = studentService.page(new Page<>(pageNum,Constants.PAGE_SIZE),queryWrapper);

        PageResponseMsg result = new PageResponseMsg(page);

        return result;
    }
    @RequestMapping(value="/{sno}", method=RequestMethod.GET)
    @ResponseBody
    public ResponseMsg view(@PathVariable String sno){
        Student student = studentService.getById(sno);
        ResponseMsg responseMsg = new ResponseMsg();
        responseMsg.setData(student);
        return responseMsg;
    }

    @RequestMapping(value="/",method = RequestMethod.POST)
    @ResponseBody
    public ResponseMsg add(@RequestBody Student student){
        ResponseMsg responseMsg = new ResponseMsg();
        boolean result = studentService.save(student);
        if(result){
            responseMsg.setMessage("保存成功");
        }else{
            responseMsg.setCode(-1);
            responseMsg.setMessage("保存失败");
        }

        return responseMsg;
    }

    @RequestMapping(value="/{sno}",method = RequestMethod.PUT)
    @ResponseBody
    public ResponseMsg update(@PathVariable String sno,@RequestBody Student student)throws Exception{
        ResponseMsg responseMsg = null;

        Student preStudent = studentService.getById(sno);
        if(preStudent != null){
            //将student不为空的copy到preStudent,更新student
            BeanUtils.copyProperties(student,preStudent);
            boolean result = studentService.updateById(preStudent);
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
    @RequestMapping(value="/{sno}", method=RequestMethod.DELETE)
    public ResponseMsg delete(@PathVariable String sno){
        ResponseMsg responseObject = null;
        if(StringUtils.isNotBlank(sno)){
            boolean result = studentService.removeById(sno);
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
