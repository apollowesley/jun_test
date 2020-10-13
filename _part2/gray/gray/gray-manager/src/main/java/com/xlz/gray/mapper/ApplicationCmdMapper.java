package com.xlz.gray.mapper;

import com.xlz.commons.base.mapper.BaseMapper;
import com.xlz.gray.model.ApplicationCmd;

/**
 *
 * ApplicationCmd 表数据库控制层接口
 *
 */
public interface ApplicationCmdMapper extends BaseMapper<ApplicationCmd> {

	Integer findMaxVersion(String applicationCode);

}