package org.apache.center.service.impl;

import org.springframework.stereotype.Component;
import com.alibaba.dubbo.config.annotation.Service;

import org.apache.playframework.service.impl.BaseServiceImpl;
import org.apache.center.service.StatusCodeService;
import org.apache.center.model.StatusCode;
import org.apache.center.dao.StatusCodeMapper;
/**
 *
 * StatusCode 表数据服务层接口实现类
 *
 */
@Component  
@Service
public class StatusCodeServiceImpl extends BaseServiceImpl<StatusCodeMapper, StatusCode> implements StatusCodeService {


}


