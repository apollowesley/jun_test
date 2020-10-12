package com.sise.school.teach.bussiness.course.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sise.school.teach.bussiness.course.dao.CourseDao;
import com.sise.school.teach.bussiness.course.dao.CourseDetailDao;
import com.sise.school.teach.bussiness.course.dao.SpecialDao;
import com.sise.school.teach.bussiness.course.po.CourseDetailPO;
import com.sise.school.teach.bussiness.course.po.CoursePO;
import com.sise.school.teach.bussiness.course.po.SpecialPO;
import com.sise.school.teach.bussiness.course.vo.req.CourseDetailReqVO;
import com.sise.school.teach.bussiness.course.vo.req.CourseReqVO;
import com.sise.school.teach.bussiness.course.vo.req.SpecialReqVO;
import com.sise.school.teach.bussiness.course.vo.resp.CourseDetailRespVO;
import com.sise.school.teach.bussiness.course.vo.resp.CourseRespVO;
import com.sise.school.teach.bussiness.course.vo.resp.SpecialRespVO;
import com.sise.school.teach.common.PaginationRespVO;
import com.sise.school.teach.utils.ResponseBuilder;
import com.sise.school.teach.vo.resp.ResponseMsgVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.sise.school.teach.constants.PageQueryConstants.PAGE_SIZE;
import static com.sise.school.teach.utils.DateUtils.dateToString;
import static com.sise.school.teach.utils.LoginUtil.getUser;

/**
 * @author idea
 * @data 2018/10/2
 */
@Service
@Slf4j
public class CourseService {

    @Autowired
    private CourseDao courseDao;
    @Autowired
    private CourseDetailDao courseDetailDao;
    @Autowired
    private SpecialDao specialDao;

    private static String LOG_HEAD = "[课程服务]";

    public ResponseMsgVO<Boolean> updateSpecialStatusByName(String specialName, Integer status) {
        try {
            if (StringUtils.isEmpty(specialName) || status == null) {
                return ResponseBuilder.buildErrorParamResponVO();
            }
            specialDao.updateStatusByName(specialName, status);
            return ResponseBuilder.buildSuccessResponVO(true);
        } catch (Exception e) {
            log.error(LOG_HEAD + "更新课程状态出现异常，异常为{}", e);
            return ResponseBuilder.buildUnkownErrorResponseVO();
        }
    }


    public ResponseMsgVO<Boolean> updateCourseStatusByCode(String courseCode, Integer status) {
        try {
            if (StringUtils.isEmpty(courseCode) || status == null) {
                return ResponseBuilder.buildErrorParamResponVO();
            }
            courseDao.updateCourseStatusByCode(status,courseCode);
            return ResponseBuilder.buildSuccessResponVO(true);
        } catch (Exception e) {
            log.error(LOG_HEAD + "更新课程专题状态出现异常，异常为{}", e);
            return ResponseBuilder.buildUnkownErrorResponseVO();
        }
    }


    public ResponseMsgVO<List<SpecialRespVO>> selectNewSpecial() {
        try {
            List<SpecialRespVO> specialRespVOList = new ArrayList<>();
            List<SpecialPO> specialPOS = specialDao.selectNewSpecial();
            specialPOS.forEach((specialPO -> {
                SpecialRespVO specialRespVO = new SpecialRespVO();
                BeanUtils.copyProperties(specialPO, specialRespVO);
                specialRespVOList.add(specialRespVO);
            }));
            if (CollectionUtils.isEmpty(specialRespVOList)) {
                return ResponseBuilder.buildSuccessResponVO(Collections.emptyList());
            }
            return ResponseBuilder.buildSuccessResponVO(specialRespVOList);
        } catch (Exception e) {
            log.error(LOG_HEAD + "专题模块数据库查询异常，异常为{}", e);
            return ResponseBuilder.buildUnkownErrorResponseVO();
        }
    }

    public ResponseMsgVO<Boolean> add(CourseReqVO courseReqVO) {
        if (!courseReqVOValidate(courseReqVO)) {
            log.error(LOG_HEAD + "插入数据库参数错误，参数为{}", courseReqVO.toString());
            return ResponseBuilder.buildErrorParamResponVO();
        }
        try {
            CoursePO coursePO = new CoursePO();
            courseReqVO.setAuthor(getUser("auth"));
            BeanUtils.copyProperties(courseReqVO, coursePO);
            courseDao.insert(coursePO);
        } catch (Exception e) {
            log.error(LOG_HEAD + "插入数据库异常，异常为{}", e);
            return ResponseBuilder.buildUnkownErrorResponseVO();
        }
        return ResponseBuilder.buildSuccessResponVO(true);
    }

    public ResponseMsgVO<Boolean> updateStatus(CourseReqVO courseReqVO) {
        if (!courseUpdateVOValidate(courseReqVO)) {
            log.error(LOG_HEAD + "更新操作参数错误，参数为{}", courseReqVO.toString());
            return ResponseBuilder.buildErrorParamResponVO();
        }
        try {
            courseDao.updateStatus(courseReqVO.getStatus(), courseReqVO.getId());
        } catch (Exception e) {
            log.error(LOG_HEAD + "更新数据库异常，异常为{}", e);
            return ResponseBuilder.buildUnkownErrorResponseVO();
        }
        return ResponseBuilder.buildSuccessResponVO(true);
    }

    public ResponseMsgVO<Boolean> deleteCourseDetail(CourseDetailReqVO courseDetailReqVO) {
        if (courseDetailReqVO.getSpecialId() == null) {
            log.error(LOG_HEAD + "删除数据参数错误，参数为{}", courseDetailReqVO.toString());
            return ResponseBuilder.buildErrorParamResponVO();
        }
        try {
            courseDetailDao.deleteByCourseId(courseDetailReqVO.getSpecialId());
        } catch (Exception e) {
            log.error(LOG_HEAD + "\"删除数据库异常，异常为{}", e);
            return ResponseBuilder.buildUnkownErrorResponseVO();
        }
        return ResponseBuilder.buildSuccessResponVO(true);
    }

    public ResponseMsgVO<List<CourseRespVO>> selectAll() {
        try {
            List<CourseRespVO> courseRespVOS = new ArrayList<>();
            List<CoursePO> coursePOS = courseDao.selectAll();
            coursePOS.forEach((coursePO -> {
                CourseRespVO courseRespVO = new CourseRespVO();
                BeanUtils.copyProperties(coursePO, courseRespVO);
                courseRespVOS.add(courseRespVO);
            }));
            if (CollectionUtils.isEmpty(coursePOS)) {
                return ResponseBuilder.buildSuccessResponVO(Collections.emptyList());
            }
            return ResponseBuilder.buildSuccessResponVO(courseRespVOS);
        } catch (Exception e) {
            log.error(LOG_HEAD + "数据库查询异常，异常为{}", e);
            return ResponseBuilder.buildUnkownErrorResponseVO();
        }
    }

    public ResponseMsgVO<PaginationRespVO<CourseRespVO>> selectAllBySpecialId(CourseReqVO courseReqVO) {
        try {
            List<CourseRespVO> courseRespVOS = new ArrayList<>();
            PageHelper.startPage(courseReqVO.getPage(), PAGE_SIZE);
            CoursePO coursePO = new CoursePO();
            BeanUtils.copyProperties(courseReqVO, coursePO);
            List<CoursePO> coursePOS = courseDao.selectAllBySpecialIdInPage(coursePO);
            PageInfo<CoursePO> pageInfo = new PageInfo<>(coursePOS);
            coursePOS.forEach((item -> {
                CourseRespVO courseRespVO = new CourseRespVO();
                BeanUtils.copyProperties(item, courseRespVO);
                courseRespVOS.add(courseRespVO);
            }));
            if (CollectionUtils.isEmpty(coursePOS)) {
                PaginationRespVO<CourseRespVO> paginationRespVO = new PaginationRespVO(0, Collections.emptyList());
                return ResponseBuilder.buildSuccessResponVO(paginationRespVO);
            }
            PaginationRespVO<CourseRespVO> paginationRespVO = new PaginationRespVO((int) pageInfo.getTotal(), pageInfo.getList());
            return ResponseBuilder.buildSuccessResponVO(paginationRespVO);
        } catch (Exception e) {
            log.error(LOG_HEAD + "数据库查询异常，异常为{}", e);
            return ResponseBuilder.buildUnkownErrorResponseVO();
        }
    }

    public ResponseMsgVO<Integer> countAllByName(String name) {
        try {
            int count = courseDao.countAllByName(name);
            return ResponseBuilder.buildSuccessResponVO(count);
        } catch (Exception e) {
            log.error(LOG_HEAD + "数据库统计异常，异常为{}", e);
            return ResponseBuilder.buildUnkownErrorResponseVO();
        }
    }

    public ResponseMsgVO<Boolean> updateSpecialVisitCount(Integer id) {
        try {
            specialDao.updateVideoVisitCount(id);
            return ResponseBuilder.buildSuccessResponVO(true);
        } catch (Exception e) {
            log.error(LOG_HEAD + "数据库统计异常，异常为{}", e);
            return ResponseBuilder.buildUnkownErrorResponseVO();
        }
    }

    public ResponseMsgVO<CourseDetailRespVO> getCourseDetail(String title) {
        try {
            CourseDetailPO courseDetailPO = courseDetailDao.selectNewCourseDetailByTitle(title);
            if (courseDetailPO == null) {
                return ResponseBuilder.buildSuccessResponVO(null);
            }
            CourseDetailRespVO courseDetailRespVO = new CourseDetailRespVO();
            BeanUtils.copyProperties(courseDetailPO, courseDetailRespVO);
            List<CourseRespVO> courseRespVOS = new ArrayList<>();
            //查询和该名称课程属于同一专题的其他课程信息
            List<CoursePO> coursePOS = courseDao.selectAllBySpecialId(courseDetailPO.getSpecialId());
            coursePOS.forEach((coursePO -> {
                CourseRespVO courseRespVO = new CourseRespVO();
                BeanUtils.copyProperties(coursePO, courseRespVO);
                courseRespVOS.add(courseRespVO);
            }));
            courseDetailRespVO.setCourseRespVOS(courseRespVOS);
            return ResponseBuilder.buildSuccessResponVO(courseDetailRespVO);
        } catch (Exception e) {
            log.error(LOG_HEAD + "数据库查询异常，异常为{}", e);
            return ResponseBuilder.buildUnkownErrorResponseVO();
        }
    }


    public ResponseMsgVO<PaginationRespVO<SpecialRespVO>> selectAllInPage(SpecialReqVO specialReqVO) {
        try {
            SpecialPO specialPO = new SpecialPO();
            BeanUtils.copyProperties(specialReqVO, specialPO);
            PageHelper.startPage(specialReqVO.getPage(), PAGE_SIZE);
            List<SpecialPO> specialPOS = specialDao.selectAllInPage(specialPO);
            PageInfo<SpecialPO> pageInfo = new PageInfo<>(specialPOS);
            List<SpecialRespVO> specialRespVOS = new ArrayList<>();
            pageInfo.getList().forEach((specialPOItem -> {
                SpecialRespVO specialRespVO = new SpecialRespVO();
                BeanUtils.copyProperties(specialPOItem, specialRespVO);
                specialRespVO.setCreateTime(dateToString(specialPOItem.getCreateTime()));
                specialRespVO.setUpdateTime(dateToString(specialPOItem.getUpdateTime()));
                specialRespVOS.add(specialRespVO);
            }));
            PaginationRespVO<SpecialRespVO> paginationRespVO = new PaginationRespVO<>((int) pageInfo.getTotal(), specialRespVOS);
            return ResponseBuilder.buildSuccessResponVO(paginationRespVO);
        } catch (Exception e) {
            log.error(LOG_HEAD + "数据库查询异常，异常为{}", e);
            return ResponseBuilder.buildUnkownErrorResponseVO();
        }
    }


    public ResponseMsgVO<CourseDetailRespVO> getFirstOneCourseDetailRespVOBySpecialId(Integer id) {
        try {
            CourseDetailPO courseDetailPO = courseDetailDao.getFirstOneBySpecialId(id);
            CourseDetailRespVO courseDetailRespVO = new CourseDetailRespVO();
            if (courseDetailPO == null) {
                return ResponseBuilder.buildSuccessResponVO(null);
            }
            BeanUtils.copyProperties(courseDetailPO, courseDetailRespVO);
            return ResponseBuilder.buildSuccessResponVO(courseDetailRespVO);
        } catch (Exception e) {
            log.error(LOG_HEAD + "数据查询异常，异常为{}", e);
            return ResponseBuilder.buildUnkownErrorResponseVO();
        }
    }

    /**
     * 课程vo校验器
     *
     * @param courseReqVO
     * @return
     */
    private boolean courseReqVOValidate(CourseReqVO courseReqVO) {
        return StringUtils.isNoneBlank(courseReqVO.getTitle())
                && StringUtils.isNoneBlank(courseReqVO.getType());
    }

    /**
     * 课程状态更新VO校验器
     *
     * @param courseReqVO
     * @return
     */
    private boolean courseUpdateVOValidate(CourseReqVO courseReqVO) {
        return courseReqVO.getId() != null && courseReqVO.getStatus() != null;
    }


}
