package com.sise.school.teach.bussiness.teacher.service;

import com.sise.school.teach.bussiness.teacher.dao.TeacherDao;
import com.sise.school.teach.bussiness.teacher.po.TeacherPO;
import com.sise.school.teach.bussiness.teacher.vo.req.TeacherReqVO;
import com.sise.school.teach.utils.DecodeUtil;
import com.sise.school.teach.utils.EncodeUtil;
import com.sise.school.teach.utils.ResponseBuilder;
import com.sise.school.teach.vo.resp.ResponseMsgVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

import static com.sise.school.teach.utils.EncodeUtil.creatSalt;

/**
 * @author idea
 * @data 2018/10/1
 */
@Service
@Slf4j
public class TeacherService {

    @Autowired
    private TeacherDao teacherDao;

    private static String LOG_HEAD = "[教师服务]";

    public ResponseMsgVO<Boolean> register(TeacherReqVO teacherReqVO) {
        if (!teacherReqVOValidate(teacherReqVO)) {
            return ResponseBuilder.buildErrorParamResponVO();
        }
        try {
            teacherDao.insert(buildTeacherPO(teacherReqVO));
        } catch (Exception e) {
            log.error(LOG_HEAD + "插入数据库异常，异常为{}", e);
            return ResponseBuilder.buildUnkownErrorResponseVO();
        }
        return ResponseBuilder.buildSuccessResponVO(true);
    }

    public ResponseMsgVO<Boolean> login(String code, String password) {
        if (StringUtils.isBlank(code)
                || StringUtils.isBlank(password)) {
            return ResponseBuilder.buildErrorParamResponVO();
        }
        try {
            TeacherPO teacherPO = teacherDao.login(code);
            if (teacherPO == null) {
                log.info(LOG_HEAD + "登录失败，工号为{}", code);
                return ResponseBuilder.buildSuccessResponVO(false);
            } else {
                log.info(LOG_HEAD + "成功登录，工号为{}", code);
                return ResponseBuilder.buildSuccessResponVO(password.equals(DecodeUtil.decodeStrWithSalt(teacherPO.getPassword(), teacherPO.getSalt())));
            }
        } catch (Exception e) {
            log.error(LOG_HEAD + "数据库查询异常，异常为{}", e);
            return ResponseBuilder.buildUnkownErrorResponseVO();
        }
    }

    /**
     * 教师属性校验
     *
     * @return
     */
    private boolean teacherReqVOValidate(TeacherReqVO teacherReqVO) {
        if (teacherReqVO == null) {
            return false;
        }
        if (StringUtils.isBlank(teacherReqVO.getName())
                || StringUtils.isBlank(teacherReqVO.getPassword())
                || StringUtils.isBlank(teacherReqVO.getCode())
                || StringUtils.isBlank(teacherReqVO.getPicture())
                || teacherReqVO.getType() == null) {
            return false;
        }
        return true;
    }

    /**
     * 构建教师PO
     *
     * @param teacherReqVO
     * @return
     */
    private TeacherPO buildTeacherPO(TeacherReqVO teacherReqVO) {
        TeacherPO teacherPO = new TeacherPO();
        String salt = creatSalt();
        teacherReqVO.setPassword(EncodeUtil.encodeStrWithSalt(teacherReqVO.getPassword(), salt));
        teacherReqVO.setSalt(salt);
        BeanUtils.copyProperties(teacherReqVO, teacherPO);
        teacherPO.setCreateTime(new Date());
        teacherPO.setUpdateTime(new Date());
        return teacherPO;
    }
}
