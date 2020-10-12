package com.sise.school.teach.bussiness.video.controller;

import com.sise.school.teach.bussiness.video.service.VideoService;
import com.sise.school.teach.bussiness.video.vo.req.VideoReqVO;
import com.sise.school.teach.bussiness.video.vo.resp.VideoRespVO;
import com.sise.school.teach.utils.LockUtil;
import com.sise.school.teach.utils.ResponseBuilder;
import com.sise.school.teach.vo.resp.ResponseMsgVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author idea
 * @data 2018/10/27
 */
@Api(description = "视频控制器")
@RestController
@RequestMapping(value = "/video")
public class VideoController {

    @Autowired
    private VideoService videoService;

    @PostMapping(value = "/selectNewVideo")
    @ApiOperation(value = "查询新上线的视频内容")
    public ResponseMsgVO<List<VideoRespVO>> selectNewVideo() {
        return videoService.selectNewVideo();
    }

    @PostMapping(value = "/selectVideoByStatus")
    @ApiOperation(value = "查询已经上线的视频内容")
    public ResponseMsgVO<List<VideoRespVO>> selectVideoList() {
        return videoService.selectVideoByStatus(3);
    }


    @PostMapping(value = "/updateStatusByCode")
    @ApiOperation(value = "更新上线的视频内容")
    public ResponseMsgVO<Boolean> selectNewVideo(String videoCode,Integer status) {
        return videoService.updateStatusByCode(videoCode, status);
    }

    @PostMapping(value = "/updateVideoVisitCount")
    @ApiOperation(value = "更新上线的视频的观看次数")
    public ResponseMsgVO<Boolean> updateVideoVisitCount(String videoCode) {
        return videoService.updateVideoVisitCount(videoCode);
    }

    @PostMapping(value = "/selectOneByVideoCode")
    @ApiOperation(value = "视频代码查询")
    public ResponseMsgVO<VideoRespVO> selectOneByVideoCode(String videoCode) {
        return videoService.selectOneByVideoCode(videoCode);
    }

    @PostMapping(value = "/selectAll")
    @ApiOperation(value = "视频查询")
    public ResponseMsgVO<List<VideoRespVO>> selectAll(@RequestBody VideoReqVO videoReqVO) {
        return videoService.selectAllVideo(videoReqVO);
    }

    @PostMapping(value = "/video/recommend")
    @ApiOperation(value = "视频推荐")
    public ResponseMsgVO<List<VideoRespVO>> recommend(String videoCode) {
        return videoService.selectNewVideo();
    }

}
