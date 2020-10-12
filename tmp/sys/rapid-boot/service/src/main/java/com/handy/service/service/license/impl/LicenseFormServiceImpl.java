package com.handy.service.service.license.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.handy.service.entity.license.LicenseForm;
import com.handy.service.mapper.license.LicenseFormMapper;
import com.handy.service.service.license.ILicenseFormService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 请假表单 服务实现类
 * </p>
 *
 * @author handy
 * @since 2019-09-12
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class LicenseFormServiceImpl extends ServiceImpl<LicenseFormMapper, LicenseForm> implements ILicenseFormService {

}
