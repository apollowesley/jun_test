package org.apache.center.service.impl;

import org.springframework.stereotype.Component;
import com.alibaba.dubbo.config.annotation.Service;

import org.apache.playframework.service.impl.BaseServiceImpl;
import org.apache.center.service.ParamService;
import org.apache.center.model.Param;
import org.apache.center.dao.ParamMapper;
/**
 *
 * Param 表数据服务层接口实现类
 *
 */
@Component  
@Service
public class ParamServiceImpl extends BaseServiceImpl<ParamMapper, Param> implements ParamService {


}


