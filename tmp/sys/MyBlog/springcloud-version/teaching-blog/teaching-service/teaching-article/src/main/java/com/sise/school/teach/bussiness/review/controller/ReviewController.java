package com.sise.school.teach.bussiness.review.controller;

import com.sise.school.teach.annotation.ValidateLogin;
import com.sise.school.teach.bussiness.review.service.DialogService;
import com.sise.school.teach.bussiness.review.service.ReviewService;
import com.sise.school.teach.bussiness.review.vo.DialogVO;
import com.sise.school.teach.bussiness.review.vo.ReviewVO;
import com.sise.school.teach.utils.ResponseBuilder;
import com.sise.school.teach.utils.TokenUtil;
import com.sise.school.teach.utils.redis.JedisUtil;
import com.sise.school.teach.utils.redis.dto.CommentDTO;
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

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.sise.school.teach.constants.UnionCodePrefix.TOKEN_KEY;

/**
 * 留言评论控制器
 *
 * @author idea
 * @data 2018/11/11
 */
@Api(description = "评论控制器")
@RestController
@RequestMapping(value = "/review")
public class ReviewController {

    @Autowired
    private DialogService dialogService;
    @Autowired
    private ReviewService reviewService;

    @PostMapping(value = "/addDialog")
    @ApiOperation(value = "评论添加对话")
    @ValidateLogin
    public ResponseMsgVO<Boolean> addDialog(@RequestBody HeadBodyVO<HeadVO, DialogVO> param) {
        dialogService.save(param.getBody(), param.getBody().getReviewCode(),param.getHead().getToken());
        return ResponseBuilder.buildSuccessResponVO(true);
    }

    @PostMapping(value = "/addReview")
    @ApiOperation(value = "添加评论")
    @ValidateLogin
    public ResponseMsgVO<Boolean> addReview(@RequestBody HeadBodyVO<HeadVO, ReviewVO> param) {
        reviewService.save(param.getBody(),param.getHead().getToken());
        return ResponseBuilder.buildSuccessResponVO(true);
    }

    @PostMapping(value = "/listReview")
    @ApiOperation(value = "对话列表查询")
    public ResponseMsgVO<List<ReviewVO>> listReview(String videoCode, HttpServletRequest request) {
        String token=TokenUtil.getToken(request);
        return ResponseBuilder.buildSuccessResponVO(reviewService.findAllByVideoCode(videoCode,token));
    }

    @PostMapping(value = "/addGood")
    @ApiOperation(value = "评论点赞")
    @ValidateLogin
    public ResponseMsgVO<Boolean> addGood(@RequestBody HeadBodyVO<HeadVO, ReviewVO> param) {
        return ResponseBuilder.buildSuccessResponVO(reviewService.addGood(param.getBody().getReviewCode(), param.getHead().getToken()));
    }

    @PostMapping(value = "/addBad")
    @ApiOperation(value = "评论差评")
    @ValidateLogin
    public ResponseMsgVO<Boolean> addBad(@RequestBody HeadBodyVO<HeadVO, ReviewVO> param) {
        return ResponseBuilder.buildSuccessResponVO(reviewService.addBad(param.getBody().getReviewCode(), param.getHead().getToken()));
    }


    @PostMapping(value = "/addDialogGood")
    @ApiOperation(value = "留言点赞")
    @ValidateLogin
    public ResponseMsgVO<Boolean> addDialogGood(@RequestBody HeadBodyVO<HeadVO, DialogVO> param) {
        return ResponseBuilder.buildSuccessResponVO(dialogService.addGood(param.getBody().getReviewCode(), param.getBody().getDialogCode(), param.getHead().getToken()));
    }

    @PostMapping(value = "/addDialogBad")
    @ApiOperation(value = "留言差评")
    @ValidateLogin
    public ResponseMsgVO<Boolean> addDialogBad(@RequestBody HeadBodyVO<HeadVO, DialogVO> param) {
        return ResponseBuilder.buildSuccessResponVO(dialogService.addBad(param.getBody().getReviewCode(), param.getBody().getDialogCode(), param.getHead().getToken()));
    }
}
