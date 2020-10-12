package com.handy.service.service.license.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.handy.service.entity.license.LicenseEvent;
import com.handy.service.mapper.license.LicenseEventMapper;
import com.handy.service.service.license.ILicenseEventService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 流程事项 服务实现类
 * </p>
 *
 * @author handy
 * @since 2019-09-12
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class LicenseEventServiceImpl extends ServiceImpl<LicenseEventMapper, LicenseEvent> implements ILicenseEventService {

}
