package com.jfast.web.admin.service;

import com.jfast.common.base.BaseService;
import com.jfast.common.constants.EnumConstants;
import com.jfast.common.model.UserSession;
import com.jfast.common.utils.IpUtils;
import com.jfast.common.utils.RequestUtils;
import com.jfast.common.utils.ResultCode;
import com.jfast.common.utils.ObjectUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2019/4/12 20:54
 */
@Service
public class AdminApiService extends BaseService {

    private static final Logger logger = LoggerFactory.getLogger(AdminApiService.class);

    /**
     * 保存系统操作日志
     * @param operationDesc
     */
    public void saveSystemLog(String operationDesc) {
        Map params = new HashMap<>();
        params.put("operation_desc", operationDesc);
        params.put("operation_ip", IpUtils.getAddressIp(RequestUtils.getRequest()));
        params.put("create_date", new Date());
        params.put("user_id", getUserId());
        params.put("operation_name", getUser().get("login_name"));
        params.put("platform_type", EnumConstants.PlatformType.WEB_ADMIN.getValue());
        super.save("system.log.save", params);
    }

    public ResultCode saveOrUpdate(boolean updateFlag, String operationDesc, Map params) {
        try {
            checkParams(params);
            Map dataMap = new HashMap<>();
            String sqlId = (String)params.get("sqlId");
            params.remove("sqlId");
            String message = "";
            if (updateFlag) {
                checkParams(params);
                dataMap.put("params", params);
                message = "修改";
                sqlSessionTemplate.update(sqlId, dataMap);
            } else {
                params.put("create_date", new Date());
                dataMap.put("params", params);
                sqlSessionTemplate.insert(sqlId, dataMap);
                message = "添加";
            }
            this.saveSystemLog(message + operationDesc);
            return new ResultCode(ResultCode.SUCCESS, message + "成功");
        } catch (Exception e) {
            logger.error("操作异常", e);
        }
        return new ResultCode(ResultCode.FAIL, "操作异常");
    }


    public Map getUser() {
        UserSession userSession = getUserSession();
        if (ObjectUtils.isNotEmpty(userSession)) {
            return userSession.getUserMap();
        }
        return null;
    }


    public Integer getUserId() {
        Map admin = getUser();
        if (ObjectUtils.isNotEmpty(admin)) {
            return (Integer)admin.get("id");
        }
        return null;
    }

    public UserSession getUserSession() {
        Subject subject = SecurityUtils.getSubject();
        UserSession userSession = (UserSession)subject.getPrincipal();
        if (ObjectUtils.isNotEmpty(userSession)) {
            return userSession;
        }
        return null;
    }

    /**
     * 删除单条数据
     * @param params
     * @return
     */
    public ResultCode deleteById(Map params) {
        String sqlId = (String)params.get("sqlId");
        //批量删除
        String message = null;
        if ("system.common.batchDeleteByIds".equals(sqlId)) {
            message = "批量删除";
        } else {
            message = "删除";
        }
        params.remove("sqlId");
        int result = sqlSessionTemplate.delete(sqlId, params);
        if (result > 0) {
            this.saveSystemLog(message + params.get("operation"));
            return new ResultCode(ResultCode.SUCCESS, "删除成功");
        }
        return new ResultCode(ResultCode.FAIL, "删除失败");
    }
}
