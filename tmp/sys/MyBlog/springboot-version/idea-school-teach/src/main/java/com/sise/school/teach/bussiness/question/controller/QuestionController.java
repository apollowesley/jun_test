package com.sise.school.teach.bussiness.question.controller;

import com.sise.school.teach.bussiness.question.service.QuestionService;
import com.sise.school.teach.bussiness.question.vo.req.QuestionReqVO;
import com.sise.school.teach.bussiness.question.vo.resp.QuestionRespVO;
import com.sise.school.teach.vo.req.HeadBodyVO;
import com.sise.school.teach.vo.req.HeadVO;
import com.sise.school.teach.vo.resp.ResponseMsgVO;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author idea
 * @data 2018/10/7
 */
@Api(description = "课程答疑控制器")
@RestController
@RequestMapping(value = "/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @PostMapping(value = "/insert")
    public ResponseMsgVO<Boolean> insert(@RequestBody HeadBodyVO<HeadVO, QuestionReqVO> param) {
        return questionService.insert(param.getBody());
    }

    @PostMapping(value = "/selectOneByCourseDetailId")
    public ResponseMsgVO<List<QuestionRespVO>> selectOneByCourseDetailId(@RequestBody HeadBodyVO<HeadVO, QuestionReqVO> param) {
        return questionService.selectOneByCourseDetailId(param.getBody().getCourseDetailId());
    }
}
