package com.sise.school.teach.bussiness.question.service;

import com.sise.school.teach.bussiness.question.dao.QuestionDao;
import com.sise.school.teach.bussiness.question.po.QuestionPO;
import com.sise.school.teach.bussiness.question.vo.req.QuestionReqVO;
import com.sise.school.teach.bussiness.question.vo.resp.QuestionRespVO;
import com.sise.school.teach.utils.ResponseBuilder;
import com.sise.school.teach.vo.resp.ResponseMsgVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author idea
 * @data 2018/10/7
 */
@Service
@Slf4j
public class QuestionService {

    @Autowired
    private QuestionDao questionDao;

    private static String LOG_HEAD = "[答疑服务]";

    /**
     * 插入元素
     *
     * @param questionReqVO
     * @return
     */
    public ResponseMsgVO<Boolean> insert(QuestionReqVO questionReqVO) {
        if (!validateQuestionReqVO(questionReqVO)) {
            log.error(LOG_HEAD + "答疑区域内，请求参数有误！请求参数为{}",questionReqVO.toString());
            return ResponseBuilder.buildErrorParamResponVO();
        }
        try {
            QuestionPO questionPO = new QuestionPO();
            BeanUtils.copyProperties(questionReqVO, questionPO);
            questionDao.insert(questionPO);
        } catch (Exception e) {
            log.error(LOG_HEAD + "插入出现未知异常，异常信息为{}", e);
            return ResponseBuilder.buildUnkownErrorResponseVO();
        }
        return ResponseBuilder.buildSuccessResponVO(true);
    }

    /**
     * 查询某一小结的课程答疑内容
     *
     * @param detailId
     * @return
     */
    public ResponseMsgVO<List<QuestionRespVO>> selectOneByCourseDetailId(Integer detailId) {
        try {
            if (detailId == null) {
                log.error(LOG_HEAD + "答疑区域内，请求参数有误！请求参数为{}",detailId);
                return ResponseBuilder.buildErrorParamResponVO();
            }
            List<QuestionPO> questionRespVOList = questionDao.selectOneByCourseDetailId(detailId);
            List<QuestionRespVO> questionRespVOS = buildQuestionRespVOList(questionRespVOList);
            return ResponseBuilder.buildSuccessResponVO(questionRespVOS);
        } catch (Exception e) {
            log.error(LOG_HEAD + "查询出现未知异常，异常信息为{}", e);
            return ResponseBuilder.buildUnkownErrorResponseVO();
        }
    }


    /**
     * list转换器
     *
     * @param questionRespVOList
     * @return
     */
    private List<QuestionRespVO> buildQuestionRespVOList(List<QuestionPO> questionRespVOList) {
        if (CollectionUtils.isEmpty(questionRespVOList)) {
            return Collections.emptyList();
        }
        List<QuestionRespVO> questionRespVOS = new ArrayList<>();
        questionRespVOList.forEach(questionPO -> {
            QuestionRespVO questionRespVO = new QuestionRespVO();
            BeanUtils.copyProperties(questionPO, questionRespVO);
            questionRespVOS.add(questionRespVO);
        });
        return questionRespVOS;
    }

    /**
     * 参数校验器
     *
     * @param questionReqVO
     * @return
     */
    private boolean validateQuestionReqVO(QuestionReqVO questionReqVO) {
        if (questionReqVO == null) {
            return false;
        }
        return questionReqVO.getCourseId() != null
                && questionReqVO.getCourseDetailId() != null
                && questionReqVO.getQuestionContent() != null
                && questionReqVO.getNo() != null;
    }
}
