package com.sise.school.teach.bussiness.admin.service;

import com.sise.school.teach.bussiness.admin.dao.AdminDao;
import com.sise.school.teach.bussiness.admin.po.AdminPO;
import com.sise.school.teach.utils.DecodeUtil;
import com.sise.school.teach.utils.ResponseBuilder;
import com.sise.school.teach.vo.resp.ResponseMsgVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author idea
 * @data 2018/10/14
 */
@Service
@Slf4j
public class AdminService {

    @Autowired
    private AdminDao adminDao;

    private final static String LOG_HEAD = "【管理员服务】";

    public ResponseMsgVO<Boolean> login(String name, String password,Integer status) {
        if (StringUtils.isBlank(name) || StringUtils.isBlank(password)) {
            log.error(LOG_HEAD + "请求参数异常，参数为:username:" + name);
            return ResponseBuilder.buildErrorParamResponVO();
        }
        try {
            AdminPO adminPO = adminDao.selectOneByUsernameAndStatus(name, String.valueOf(status));
            if (adminPO != null) {
                return ResponseBuilder.buildSuccessResponVO(password.equals(DecodeUtil.decodeStrWithSalt(adminPO.getPassword(), adminPO.getSalt())));
            } else {
                log.info(LOG_HEAD + "登录失败，参数为:username:" + name);
                return ResponseBuilder.buildSuccessResponVO(false);
            }
        } catch (Exception e) {
            log.error(LOG_HEAD + "请求过程中出现异常，异常为{}", e);
            return ResponseBuilder.buildUnkownErrorResponseVO();
        }
    }


}
