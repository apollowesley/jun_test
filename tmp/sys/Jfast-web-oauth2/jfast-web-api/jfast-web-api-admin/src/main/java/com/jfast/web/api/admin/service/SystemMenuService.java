package com.jfast.web.api.admin.service;

import com.jfast.web.api.admin.mapper.SystemMenuMapper;
import com.jfast.web.common.core.base.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2019/12/28 11:31
 */
@Service
public class SystemMenuService extends BaseService<SystemMenuMapper> {

    public List<Map> findMenuByAdminId(Integer adminId) {
        return mapper.findMenuByAdminId();
    }
}
