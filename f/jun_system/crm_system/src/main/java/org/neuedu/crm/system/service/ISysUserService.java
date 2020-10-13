package org.neuedu.crm.system.service;

import org.neuedu.crm.system.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

/**
 * <p>
 * 用户 服务类
 * </p>
 *
 * @author CDHong
 * @since 2019-12-13
 */
public interface ISysUserService extends IService<SysUser>, UserDetailsService {

    List<SysUser> findUserByRoleId(Integer roleId);
}
