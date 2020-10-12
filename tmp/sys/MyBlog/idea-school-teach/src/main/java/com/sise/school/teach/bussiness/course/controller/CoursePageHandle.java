package com.sise.school.teach.bussiness.course.controller;

import com.sise.school.teach.bussiness.course.service.tx.CourseTxService;
import com.sise.school.teach.bussiness.course.vo.req.CourseDetailReqVO;
import io.swagger.annotations.ApiOperation;
import org.sise.idea.service.ApplicationSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.sise.school.teach.constants.ApplicationConstants.ADMIN_COURSE_URL_KEY;

/**
 * 由于前段需要，特殊设置一个页面跳转控制器
 *
 * @author idea
 * @data 2018/10/20
 */
@Controller
@RequestMapping("course/page")
public class CoursePageHandle {

    @Autowired
    private CourseTxService courseTxService;

    @PostMapping(value = "/create")
    @ApiOperation(value = "接收课程内容编辑信息")
    public void create(CourseDetailReqVO courseDetailReqVO, HttpServletResponse response) throws IOException {
        courseTxService.addDetail(courseDetailReqVO);
        response.sendRedirect(ApplicationSettingService.getValue(ADMIN_COURSE_URL_KEY));
    }

    @PostMapping(value = "/update")
    @ApiOperation(value = "接收课程内容进行更新信息")
    public void update(CourseDetailReqVO courseDetailReqVO, HttpServletResponse response) throws IOException {
        courseTxService.update(courseDetailReqVO);
        response.sendRedirect(ApplicationSettingService.getValue(ADMIN_COURSE_URL_KEY));
    }
}
