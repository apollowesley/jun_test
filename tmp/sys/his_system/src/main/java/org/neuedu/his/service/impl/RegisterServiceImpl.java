package org.neuedu.his.service.impl;

import org.neuedu.his.entity.Register;
import org.neuedu.his.mapper.RegisterMapper;
import org.neuedu.his.service.IRegisterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 现场挂号 服务实现类
 * </p>
 *
 * @author CDHong
 * @since 2020-03-30
 */
@Service
public class RegisterServiceImpl extends ServiceImpl<RegisterMapper, Register> implements IRegisterService {

}
