package com.jfast.web.api.admin.mapper;

import com.jfast.web.common.core.base.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2019/12/28 11:32
 */
public interface SystemMenuMapper extends BaseMapper {

    List<Map> findMenuByAdminId();
}
