package com.sise.school.teach.bussiness.student.controller;

import com.sise.school.teach.annotation.ValidateLogin;
import com.sise.school.teach.bussiness.course.vo.resp.CourseRespVO;
import com.sise.school.teach.bussiness.student.vo.req.StudentReqVO;
import com.sise.school.teach.bussiness.student.vo.resp.StudentRespVO;
import com.sise.school.teach.bussiness.student.service.StudentService;
import com.sise.school.teach.utils.TokenUtil;
import com.sise.school.teach.vo.req.HeadBodyVO;
import com.sise.school.teach.vo.req.HeadVO;
import com.sise.school.teach.vo.resp.ResponseMsgVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author idea
 * @data 2018/10/3
 */
@Api(description = "学生控制器")
@RequestMapping(value = "student")
@RestController
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping(value = "/updateByCode")
    @ApiOperation("更新学生信息")
    @ValidateLogin
    public ResponseMsgVO<Boolean> updateByCode(@RequestBody HeadBodyVO<HeadVO,StudentReqVO> params,HttpServletRequest request, HttpServletResponse response){
        return studentService.updateByCode(TokenUtil.getToken(request),params.getBody());
    }

    @PostMapping(value = "/login")
    @ApiOperation("登录操作")
    public ResponseMsgVO<String> login(@RequestBody HeadBodyVO<HeadVO,StudentReqVO> params, HttpServletResponse response){
        return studentService.login(params.getBody().getAccount(),params.getBody().getPassword(),response);
    }

    @PostMapping(value = "/selectOne")
    @ApiOperation("单独查询")
    public ResponseMsgVO<StudentRespVO> selectOne(String key,String value){
        return studentService.selectOne(key,value);
    }

    @PostMapping(value = "/selectMyCourse")
    @ApiOperation("查询我的课程")
    public ResponseMsgVO<List<CourseRespVO>> selectMyCourse(@RequestBody HeadBodyVO<HeadVO,StudentReqVO> param){
        return studentService.selectMyCourse(param.getBody().getStuCode());
    }

    @PostMapping(value = "/register")
    @ApiOperation("新生注册")
    public ResponseMsgVO<Boolean> register(@RequestBody HeadBodyVO<HeadVO,StudentReqVO> param){
        return studentService.register(param.getBody());
    }

}
