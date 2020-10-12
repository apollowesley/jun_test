package com.sise.school.teach.bussiness.student.controller;

import com.sise.school.teach.annotation.ValidateLogin;
import com.sise.school.teach.bussiness.student.service.StudentCollectionService;
import com.sise.school.teach.bussiness.student.vo.resp.CollectionRespVO;
import com.sise.school.teach.utils.TokenUtil;
import com.sise.school.teach.vo.resp.ResponseMsgVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author idea
 * @data 2018/12/4
 */
@Api(description = "收藏控制器")
@RequestMapping(value = "collection")
@RestController
public class StudentCollectionController {

    @Autowired
    private StudentCollectionService studentCollectionService;

    @PostMapping(value = "/insert")
    @ApiOperation("插入收藏内容")
    @ValidateLogin
    public ResponseMsgVO<Boolean> insert(String token, String code) {
        return studentCollectionService.insert(token, code);
    }

    @PostMapping(value = "/delete")
    @ApiOperation("删除收藏内容")
    public ResponseMsgVO<Boolean> delete(String token, String code) {
        return studentCollectionService.deleteByCode(code, token);
    }

    @PostMapping(value = "/selectAllByToken")
    @ApiOperation("查找我的收藏")
    @ValidateLogin
    public ResponseMsgVO<List<CollectionRespVO>> selectAllByToken(HttpServletRequest request) {
        return studentCollectionService.selectAllByCode(TokenUtil.getToken(request));
    }
}
