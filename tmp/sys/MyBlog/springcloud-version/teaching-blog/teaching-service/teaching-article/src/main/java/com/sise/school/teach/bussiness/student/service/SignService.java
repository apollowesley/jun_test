package com.sise.school.teach.bussiness.student.service;

import com.sise.school.teach.bussiness.student.dao.SignDao;
import com.sise.school.teach.bussiness.student.po.SignPO;
import com.sise.school.teach.bussiness.student.vo.req.SignReqVO;
import com.sise.school.teach.utils.ResponseBuilder;
import com.sise.school.teach.vo.resp.ResponseMsgVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author idea
 * @data 2018/10/3
 */
@Service
@Slf4j
public class SignService {

    @Autowired
    private SignDao signDao;

    private static String LOG_HEAD = "[报名服务]";

    public ResponseMsgVO<Boolean> add(SignReqVO signReqVO) {
        if(!signReqVOValidate(signReqVO)){
            return ResponseBuilder.buildErrorParamResponVO();
        }
        try {
            SignPO signPO = new SignPO();
            BeanUtils.copyProperties(signReqVO, signPO);
            signPO.setCreateTime(new Date());
            signPO.setUpdateTime(new Date());
            signDao.insert(signPO);
            return ResponseBuilder.buildSuccessResponVO(true);
        }catch (Exception e){
            log.error(LOG_HEAD+"课程报名出现异常,异常为{}",e);
            return ResponseBuilder.buildUnkownErrorResponseVO();
        }
    }

    /**
     * 报名vo校验器
     * @param signReqVO
     * @return
     */
    private boolean signReqVOValidate(SignReqVO signReqVO) {
        return StringUtils.isNotBlank(signReqVO.getStuNo()) && signReqVO.getCourseNo()!=null;
    }

}
