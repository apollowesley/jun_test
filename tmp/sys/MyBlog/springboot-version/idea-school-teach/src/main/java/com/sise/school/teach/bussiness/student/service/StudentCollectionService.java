package com.sise.school.teach.bussiness.student.service;

import com.sise.school.teach.bussiness.course.dao.CourseDetailDao;
import com.sise.school.teach.bussiness.course.dao.SpecialDao;
import com.sise.school.teach.bussiness.course.po.CourseDetailPO;
import com.sise.school.teach.bussiness.course.po.SpecialPO;
import com.sise.school.teach.bussiness.student.dao.StudentCollectionDao;
import com.sise.school.teach.bussiness.student.em.CollectionEnum;
import com.sise.school.teach.bussiness.student.po.StudentCollectionPO;
import com.sise.school.teach.bussiness.student.vo.resp.CollectionRespVO;
import com.sise.school.teach.bussiness.video.dao.VideoDao;
import com.sise.school.teach.bussiness.video.po.VideoPO;
import com.sise.school.teach.common.em.StatusEnum;
import com.sise.school.teach.constants.CodePrefixConstants;
import com.sise.school.teach.utils.DateUtils;
import com.sise.school.teach.utils.ResponseBuilder;
import com.sise.school.teach.utils.TokenUtil;
import com.sise.school.teach.utils.redis.dto.RedisTokenDTO;
import com.sise.school.teach.vo.resp.ResponseMsgVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author idea
 * @data 2018/12/4
 */
@Service
@Slf4j
public class StudentCollectionService {

    @Autowired
    private StudentCollectionDao studentCollectionDao;
    @Autowired
    private CourseDetailDao courseDetailDao;
    @Autowired
    private VideoDao videoDao;
    @Autowired
    private SpecialDao specialDao;

    public ResponseMsgVO<Boolean> insert(String token, String code) {
        try {
            if (StringUtils.isBlank(token) || StringUtils.isBlank(code)) {
                return ResponseBuilder.buildErrorParamResponVO();
            }
            RedisTokenDTO redisTokenDTO = TokenUtil.getObjByToken(token);
            if (studentCollectionDao.selectOneByStuCodeAndColCode(redisTokenDTO.getUnionCode(), code) == null) {
                StudentCollectionPO studentCollectionPO = new StudentCollectionPO();
                studentCollectionPO.setCollectionCode(code);
                studentCollectionPO.setStuCode(redisTokenDTO.getUnionCode());
                studentCollectionPO.setCreateTime(new Date());
                studentCollectionPO.setUpdateTime(new Date());
                studentCollectionDao.insert(studentCollectionPO);
                return ResponseBuilder.buildSuccessResponVO(true);
            } else {
                return ResponseBuilder.buildSuccessResponVO(false);
            }
        } catch (Exception e) {
            return ResponseBuilder.buildUnkownErrorResponseVO();
        }
    }

    /**
     * 根据代码查询
     *
     * @param token
     * @return
     */
    public ResponseMsgVO<List<CollectionRespVO>> selectAllByCode(String token) {
        try {
            if (StringUtils.isBlank(token)) {
                return ResponseBuilder.buildErrorParamResponVO();
            }
            RedisTokenDTO redisTokenDTO = TokenUtil.getObjByToken(token);
            List<StudentCollectionPO> studentCollectionPOS = studentCollectionDao.selectAllByCode(redisTokenDTO.getUnionCode());
            List<CollectionRespVO> collectionRespVOS = new ArrayList<>();
            for (StudentCollectionPO studentCollectionPO : studentCollectionPOS) {
                CollectionRespVO collectionRespVO = new CollectionRespVO();
                BeanUtils.copyProperties(studentCollectionPO, collectionRespVO);
                collectionRespVO.setCreateTime(DateUtils.dateToString(studentCollectionPO.getCreateTime()));
                collectionRespVO.setUpdateTime(DateUtils.dateToString(studentCollectionPO.getUpdateTime()));

                String collectionCode = collectionRespVO.getCollectionCode();
                if (collectionCode.startsWith(CodePrefixConstants.COURSE_PREFIX)) {
                    CourseDetailPO courseDetailPO = courseDetailDao.selectCourseByCode(collectionCode);
                    if (courseDetailPO != null) {
                        Integer specialId = courseDetailPO.getSpecialId();
                        SpecialPO specialPO = specialDao.selectOneById(specialId);
                        collectionRespVO.setCollectionName(courseDetailPO.getTitle());
                        collectionRespVO.setImgUrl(specialPO.getImageUrl());
                        collectionRespVO.setDes(specialPO.getDes());
                        collectionRespVO.setCollectionType(CollectionEnum.ARTICLE_TYPE.getCode());
                        if (specialPO.getStatus().equals(StatusEnum.IS_UP.getCode())) {
                            collectionRespVO.setExistStatus(true);
                        } else {
                            collectionRespVO.setExistStatus(false);
                        }
                    }
                } else if (collectionCode.startsWith(CodePrefixConstants.VIDEO_PREFIX)) {
                    VideoPO videoPO = videoDao.selectOneByVideoCode(collectionCode);
                    if (videoPO != null) {
                        collectionRespVO.setCollectionName(videoPO.getVideoName());
                        collectionRespVO.setImgUrl(videoPO.getPicture());
                        collectionRespVO.setDes(videoPO.getDes());
                        collectionRespVO.setCollectionType(CollectionEnum.VIDEO_TYPE.getCode());
                        if (videoPO.getStatus().equals(StatusEnum.IS_UP.getCode())) {
                            collectionRespVO.setExistStatus(true);
                        } else {
                            collectionRespVO.setExistStatus(false);
                        }
                    }
                }
                collectionRespVOS.add(collectionRespVO);
            }
            return ResponseBuilder.buildSuccessResponVO(collectionRespVOS);
        } catch (Exception e) {
            log.error("[学生收藏]数据库查询出现异常，异常为{}", e);
            return ResponseBuilder.buildUnkownErrorResponseVO();
        }
    }

    /**
     * 根据代码删除
     *
     * @param collectionCode
     * @param token
     * @return
     */
    public ResponseMsgVO<Boolean> deleteByCode(String collectionCode, String token) {
        try {
            if (StringUtils.isBlank(token) || StringUtils.isBlank(collectionCode)) {
                return ResponseBuilder.buildErrorParamResponVO();
            }
            RedisTokenDTO redisTokenDTO = TokenUtil.getObjByToken(token);
            studentCollectionDao.deleteByCode(redisTokenDTO.getUnionCode(), collectionCode);
            return ResponseBuilder.buildSuccessResponVO(true);
        } catch (Exception e) {
            return ResponseBuilder.buildUnkownErrorResponseVO();
        }
    }

}
