package com.sise.school.teach.bussiness.course.controller;

import com.sise.school.teach.bussiness.course.service.CourseService;
import com.sise.school.teach.bussiness.course.service.tx.CourseTxService;
import com.sise.school.teach.bussiness.course.vo.req.CourseDetailReqVO;
import com.sise.school.teach.bussiness.course.vo.req.CourseReqVO;
import com.sise.school.teach.bussiness.course.vo.req.SpecialReqVO;
import com.sise.school.teach.bussiness.course.vo.resp.CourseDetailRespVO;
import com.sise.school.teach.bussiness.course.vo.resp.CourseRespVO;
import com.sise.school.teach.bussiness.course.vo.resp.SpecialRespVO;
import com.sise.school.teach.common.PaginationRespVO;
import com.sise.school.teach.vo.req.HeadBodyVO;
import com.sise.school.teach.vo.req.HeadVO;
import com.sise.school.teach.vo.resp.ResponseMsgVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author idea
 * @data 2018/10/2
 */
@Api(description = "课程控制器")
@RestController
@RequestMapping(value = "/course")
public class CourseController {

    @Autowired
    private CourseService courseService;
    @Autowired
    private CourseTxService courseTxService;

    @PostMapping(value = "/updateSpecialStatusByName")
    @ApiOperation(value = "更新课程专题")
    public ResponseMsgVO<Boolean> updateSpecialStatusByName(String name, Integer status) {
        return courseService.updateSpecialStatusByName(name, status);
    }

    @PostMapping(value = "/updateCourseStatusByCode")
    @ApiOperation(value = "更新课程小结内容")
    public ResponseMsgVO<Boolean> updateCourseStatusByCode(String code, Integer stauts) {
        return courseService.updateCourseStatusByCode(code, stauts);
    }

    @PostMapping(value = "/selectNewSpecial")
    @ApiOperation(value = "查询新的专题模块内容")
    public ResponseMsgVO<List<SpecialRespVO>> selectNewSpecial() {
        return courseService.selectNewSpecial();
    }

    @PostMapping(value = "/getFirstOneBySpecialId")
    @ApiOperation(value = "查询专题模块课程第一小结的内容")
    public ResponseMsgVO<CourseDetailRespVO> getFirstOneBySpecialId(Integer id) {
        return courseService.getFirstOneCourseDetailRespVOBySpecialId(id);
    }

    @PostMapping(value = "/selectAll")
    @ApiOperation(value = "显示所有的课程")
    public ResponseMsgVO<List<CourseRespVO>> selectAll() {
        return courseService.selectAll();
    }

    @PostMapping(value = "/selectAllInPage")
    @ApiOperation(value = "查询专栏课程")
    public ResponseMsgVO<PaginationRespVO<SpecialRespVO>> selectAllInPage(@RequestBody HeadBodyVO<HeadVO, SpecialReqVO> param) {
        return courseService.selectAllInPage(param.getBody());
    }

    @PostMapping(value = "/selectAllBySpecialId")
    @ApiOperation(value = "查询专栏课程的详情内容")
    public ResponseMsgVO<PaginationRespVO<CourseRespVO>> selectAllBySpecialId(@RequestBody HeadBodyVO<HeadVO, CourseReqVO> param) {
        return courseService.selectAllBySpecialId(param.getBody());
    }

    @PostMapping(value = "/getCourseDetail")
    @ApiOperation(value = "查询课程小结的详情，包括课程名称集合")
    public ResponseMsgVO<CourseDetailRespVO> getCourseDetail(String title) {
        return courseService.getCourseDetail(title);
    }

    @PostMapping(value = "/add")
    @ApiOperation(value = "添加课程")
    public ResponseMsgVO<Boolean> add(@RequestBody HeadBodyVO<HeadVO, CourseReqVO> param) {
        return courseService.add(param.getBody());
    }

    @PostMapping(value = "/updateStatus")
    @ApiOperation(value = "更新课程状态")
    public ResponseMsgVO<Boolean> updateStatus(@RequestBody HeadBodyVO<HeadVO, CourseReqVO> param) {
        return courseService.updateStatus(param.getBody());
    }

    @PostMapping(value = "/updateSpecialVisitCount")
    @ApiOperation(value = "更新课程的观看数量")
    public ResponseMsgVO<Boolean> updateSpecialVisitCount(Integer id) {
        return courseService.updateSpecialVisitCount(id);
    }

    @PostMapping(value = "/deleteCourseDetail")
    @ApiOperation(value = "删除课程内容")
    public ResponseMsgVO<Boolean> deleteCourseDetail(@RequestBody HeadBodyVO<HeadVO, CourseDetailReqVO> param) {
        return courseService.deleteCourseDetail(param.getBody());
    }

    @PostMapping(value = "/countAllByName")
    @ApiOperation(value = "统计名称")
    public ResponseMsgVO<Integer> countAllByName(String name) {
        return courseService.countAllByName(name);
    }

}
