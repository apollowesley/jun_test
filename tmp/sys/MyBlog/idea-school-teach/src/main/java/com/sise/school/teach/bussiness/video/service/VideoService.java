package com.sise.school.teach.bussiness.video.service;

import com.github.pagehelper.PageHelper;
import com.sise.school.teach.bussiness.video.dao.VideoDao;
import com.sise.school.teach.bussiness.video.po.VideoPO;
import com.sise.school.teach.bussiness.video.vo.req.VideoReqVO;
import com.sise.school.teach.bussiness.video.vo.resp.VideoRespVO;
import com.sise.school.teach.constants.CodePrefixConstants;
import com.sise.school.teach.utils.CodeGenerateUtil;
import com.sise.school.teach.utils.LoginUtil;
import com.sise.school.teach.utils.ResponseBuilder;
import com.sise.school.teach.vo.resp.ResponseMsgVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

import static com.sise.school.teach.constants.PageQueryConstants.PAGE_SIZE;
import static com.sise.school.teach.utils.DateUtils.dateToString;

/**
 * @author idea
 * @data 2018/10/27
 */
@Service
@Slf4j
public class VideoService {

    @Autowired
    private VideoDao videoDao;

    private ReentrantLock reentrantLock = new ReentrantLock();

    private String LOG_HEAD = "【视频服务】";

    public ResponseMsgVO<VideoRespVO> selectOneByVideoCode(String videoCode) {
        try {
            VideoPO videoPO = videoDao.selectOneByVideoCode(videoCode);
            VideoRespVO videoRespVO = new VideoRespVO();
            BeanUtils.copyProperties(videoPO, videoRespVO);
            return ResponseBuilder.buildSuccessResponVO(videoRespVO);
        } catch (Exception e) {
            log.error(LOG_HEAD + "教学视频代码查询出现异常，异常为{}", e);
        }
        return ResponseBuilder.buildUnkownErrorResponseVO();
    }


    public ResponseMsgVO<Boolean> updateStatusByCode(String videoCode,Integer status) {
        try {
            if(StringUtils.isBlank(videoCode)||status==null){
                return ResponseBuilder.buildErrorParamResponVO();
            }
            videoDao.updateStatusByCode(videoCode,status);
            return ResponseBuilder.buildSuccessResponVO(true);
        } catch (Exception e) {
            log.error(LOG_HEAD + "教学视频状态更新出现异常，异常为{}", e);
        }
        return ResponseBuilder.buildUnkownErrorResponseVO();
    }

    public ResponseMsgVO<List<VideoRespVO>> selectNewVideo() {
        try {
            List<VideoRespVO> videoRespVOS = new ArrayList<>();
            List<VideoPO> videoPOList = videoDao.selectNewVideo();
            videoPOList.forEach((videoPO -> {
                VideoRespVO videoRespVO = new VideoRespVO();
                BeanUtils.copyProperties(videoPO, videoRespVO);
                videoRespVOS.add(videoRespVO);
            }));
            if (CollectionUtils.isEmpty(videoRespVOS)) {
                return ResponseBuilder.buildSuccessResponVO(Collections.emptyList());
            }
            return ResponseBuilder.buildSuccessResponVO(videoRespVOS);
        } catch (Exception e) {
            log.error(LOG_HEAD + "最新视频--数据库查询异常，异常为{}", e);
            return ResponseBuilder.buildUnkownErrorResponseVO();
        }
    }

    public ResponseMsgVO<Boolean> updateVideoVisitCount(String videoCode) {
        try {
            videoDao.updateVideoVisitCount(videoCode);
            return ResponseBuilder.buildSuccessResponVO(true);
        } catch (Exception e) {
            log.error(LOG_HEAD + "视频观看次数更新异常，异常为{}", e);
            return ResponseBuilder.buildUnkownErrorResponseVO();
        }
    }


    public ResponseMsgVO<List<VideoRespVO>> selectVideoByStatus(Integer status) {
        try {
            List<VideoRespVO> videoRespVOS = new ArrayList<>();
            List<VideoPO> videoPOList = videoDao.selectVideoByStatus(status);
            videoPOList.forEach((videoPO -> {
                VideoRespVO videoRespVO = new VideoRespVO();
                BeanUtils.copyProperties(videoPO, videoRespVO);
                videoRespVOS.add(videoRespVO);
            }));
            if (CollectionUtils.isEmpty(videoRespVOS)) {
                return ResponseBuilder.buildSuccessResponVO(Collections.emptyList());
            }
            return ResponseBuilder.buildSuccessResponVO(videoRespVOS);
        } catch (Exception e) {
            log.error(LOG_HEAD + "视频--数据库查询异常，异常为{}", e);
            return ResponseBuilder.buildUnkownErrorResponseVO();
        }
    }

    public ResponseMsgVO<List<VideoRespVO>> selectAllVideo(VideoReqVO videoReqVO) {
        try {
            List<VideoRespVO> videoRespVOS = new ArrayList<>();
            PageHelper.startPage(videoReqVO.getPage(), PAGE_SIZE);
            HashMap<String, Object> paramMap = new HashMap<>();
            paramMap.put("videoName", videoReqVO.getVideoName());
            paramMap.put("createUser", videoReqVO.getCreateUser());
            paramMap.put("des", videoReqVO.getDes());
            List<VideoPO> videoPOList = videoDao.selectAll(paramMap);
            videoPOList.forEach((videoPO -> {
                VideoRespVO videoRespVO = new VideoRespVO();
                BeanUtils.copyProperties(videoPO, videoRespVO);
                videoRespVO.setCreateTime(dateToString(videoPO.getCreateTime()));
                videoRespVO.setUpdateTime(dateToString(videoPO.getUpdateTime()));
                videoRespVOS.add(videoRespVO);
            }));
            if (CollectionUtils.isEmpty(videoRespVOS)) {
                return ResponseBuilder.buildSuccessResponVO(Collections.emptyList());
            }
            return ResponseBuilder.buildSuccessResponVO(videoRespVOS);
        } catch (Exception e) {
            log.error(LOG_HEAD + "视频--数据库查询异常，异常为{}", e);
            return ResponseBuilder.buildUnkownErrorResponseVO();
        }
    }

    /**
     * 上传视频和图片文件
     *
     * @param videoReqVO
     * @return
     * @des 防止并发操作，需要加重入锁
     */
    public boolean insert(VideoReqVO videoReqVO) {
        reentrantLock.lock();
        try {
            String videoName = videoReqVO.getVideoName();
            VideoPO videoPO = videoDao.selectOneByVideoName(videoName);
            //原来已经存在
            if (videoPO != null) {
                if (videoPO.getPicture().equals(StringUtils.EMPTY)) {
                    videoPO.setPicture(videoReqVO.getPicture());
                }
                if (videoPO.getVideoUrl().equals(StringUtils.EMPTY)) {
                    videoPO.setVideoUrl(videoReqVO.getVideoUrl());
                }
                videoDao.updateByVideoName(videoPO);
                return true;
            } else {
                videoPO = new VideoPO();
            }
            BeanUtils.copyProperties(videoReqVO, videoPO);
            videoPO.setCreateUser(LoginUtil.getUser());
            videoPO.setVideoCode(CodeGenerateUtil.codeGenerate(CodePrefixConstants.VIDEO_PREFIX));
            videoPO.setCreateTime(new Date());
            videoPO.setUpdateTime(new Date());
            videoDao.insert(videoPO);
            return true;
        } catch (Exception e) {
            log.error(LOG_HEAD + "视频--插入异常，异常为{}", e);
            return false;
        } finally {
            reentrantLock.unlock();
        }
    }
}
