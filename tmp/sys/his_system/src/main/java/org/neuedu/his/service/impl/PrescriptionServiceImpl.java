package org.neuedu.his.service.impl;

import org.neuedu.his.entity.Prescription;
import org.neuedu.his.mapper.PrescriptionMapper;
import org.neuedu.his.service.IPrescriptionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 处方 服务实现类
 * </p>
 *
 * @author CDHong
 * @since 2020-03-30
 */
@Service
public class PrescriptionServiceImpl extends ServiceImpl<PrescriptionMapper, Prescription> implements IPrescriptionService {

}
