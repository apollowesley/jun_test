package com.cdh.springboot.service.impl;

import com.cdh.springboot.entity.Log;
import com.cdh.springboot.mapper.LogMapper;
import com.cdh.springboot.service.ILogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 权限更新日志表 服务实现类
 * </p>
 *
 * @author CDHong
 * @since 2018-11-25
 */
@Service
public class LogServiceImpl extends ServiceImpl<LogMapper, Log> implements ILogService {

}
