package com.sise.school.teach.bussiness.course.service.tx;

import com.sise.school.teach.bussiness.course.dao.CourseDao;
import com.sise.school.teach.bussiness.course.dao.CourseDetailDao;
import com.sise.school.teach.bussiness.course.dao.SpecialDao;
import com.sise.school.teach.bussiness.course.po.CourseDetailPO;
import com.sise.school.teach.bussiness.course.po.CoursePO;
import com.sise.school.teach.bussiness.course.vo.req.CourseDetailReqVO;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static com.sise.school.teach.utils.CharacterFiliter.filiter;

/**
 * @author idea
 * @data 2018/10/21
 */
@Service
@Slf4j
public class CourseTxService {
    @Autowired
    private CourseDao courseDao;
    @Autowired
    private CourseDetailDao courseDetailDao;
    @Autowired
    private SpecialDao specialDao;

    private static String LOG_HEAD = "[课程服务]";

    @Transactional(rollbackFor = Exception.class)
    public ResponseMsgVO<Boolean> addDetail(CourseDetailReqVO courseDetailReqVO) {
        if (!courseDetailReqVOValidate(courseDetailReqVO)) {
            log.error(LOG_HEAD + "插入详情参数错误，参数为{}", courseDetailReqVO.toString());
            return ResponseBuilder.buildErrorParamResponVO();
        }
        try {
            String code = CodeGenerateUtil.codeGenerate(CodePrefixConstants.COURSE_PREFIX);
            CoursePO coursePO = buildCoursePO(courseDetailReqVO.getSpecialId(), code, "1",courseDetailReqVO.getTitle());
            courseDao.insert(coursePO);
            CourseDetailPO courseDetailPO = new CourseDetailPO();
            BeanUtils.copyProperties(courseDetailReqVO, courseDetailPO);
            //过滤掉无用的\r\n\t字符
            courseDetailPO.setContent(filiter(courseDetailPO.getContent()));
            courseDetailPO.setCourseCode(code);
            courseDetailPO.setCreateTime(new Date());
            courseDetailPO.setUpdateTime(new Date());
            courseDetailDao.insert(courseDetailPO);
        } catch (Exception e) {
            log.error(LOG_HEAD + "插入详情异常，异常为{}", e);
            return ResponseBuilder.buildUnkownErrorResponseVO();
        }
        return ResponseBuilder.buildSuccessResponVO(true);
    }



    @Transactional(rollbackFor = Exception.class)
    public ResponseMsgVO<Boolean> update(CourseDetailReqVO courseDetailReqVO) {
        if (!courseDetailReqVOValidate(courseDetailReqVO)) {
            log.error(LOG_HEAD + "插入详情参数错误，参数为{}", courseDetailReqVO.toString());
            return ResponseBuilder.buildErrorParamResponVO();
        }
        try {
            CoursePO coursePO = new CoursePO();
            coursePO.setCourseCode(courseDetailReqVO.getCourseCode());
            coursePO.setTitle(courseDetailReqVO.getTitle());
            coursePO.setAuthor(LoginUtil.getUser("idea"));
            coursePO.setUpdateTime(new Date());
            courseDao.update(coursePO);
            CourseDetailPO courseDetailPO = new CourseDetailPO();
            BeanUtils.copyProperties(courseDetailReqVO, courseDetailPO);
            //过滤掉无用的\r\n\t字符
            courseDetailPO.setContent(filiter(courseDetailPO.getContent()));
            courseDetailPO.setUpdateTime(new Date());
            courseDetailDao.update(courseDetailPO);
        } catch (Exception e) {
            log.error(LOG_HEAD + "插入详情异常，异常为{}", e);
            return ResponseBuilder.buildUnkownErrorResponseVO();
        }
        return ResponseBuilder.buildSuccessResponVO(true);
    }


    /**
     * 课程详情vo校验器
     *
     * @param courseDetailReqVO
     * @return
     */
    private boolean courseDetailReqVOValidate(CourseDetailReqVO courseDetailReqVO) {
        return StringUtils.isNoneBlank(courseDetailReqVO.getTitle())
                && StringUtils.isNoneBlank(courseDetailReqVO.getContent());
    }


    /**
     * 构建课程PO
     *
     * @return
     */
    private CoursePO buildCoursePO(Integer specialId, String code, String type,String title) {
        CoursePO coursePO = new CoursePO();
        coursePO.setCourseCode(code);
        coursePO.setSpecialId(specialId);
        //todo
        coursePO.setAuthor(LoginUtil.getUser("idea"));
        coursePO.setType(type);
        coursePO.setStatus(1);
        coursePO.setTitle(title);
        coursePO.setLoveNums(0);
        coursePO.setCreateTime(new Date());
        coursePO.setUpdateTime(new Date());
        return coursePO;
    }
}
