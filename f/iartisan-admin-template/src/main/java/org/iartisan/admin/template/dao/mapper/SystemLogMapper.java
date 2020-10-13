package org.iartisan.admin.template.dao.mapper;


import org.iartisan.admin.template.dao.model.SystemLogDO;
import org.iartisan.runtime.jdbc.MybatisBaseMapper;

import java.util.List;
import java.util.Map;

/**
 * system_log 表操作接口
 *
 * @author King
 */
public interface SystemLogMapper extends MybatisBaseMapper<SystemLogDO> {

    List<Map<String, String>> selectGroupByMethod();

}