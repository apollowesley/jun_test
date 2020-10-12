package com.handy.service.service.license.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.handy.service.entity.license.LicenseSetting;
import com.handy.service.mapper.license.LicenseSettingMapper;
import com.handy.service.service.license.ILicenseSettingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 流程信息 服务实现类
 * </p>
 *
 * @author handy
 * @since 2019-09-12
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class LicenseSettingServiceImpl extends ServiceImpl<LicenseSettingMapper, LicenseSetting> implements ILicenseSettingService {

}
