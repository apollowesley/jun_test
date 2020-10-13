package org.idata.service.sys;

import org.idata.core.support.BaseService;
import org.idata.mybatis.generator.model.SysPermission;
import org.idata.provider.sys.SysPermissionProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ShenHuaJie
 * @version 2016年5月20日 下午3:16:13
 */
@Service
public class SysPermissionService extends BaseService<SysPermissionProvider, SysPermission> {
	@Autowired
	public void setProvider(SysPermissionProvider provider) {
		this.provider = provider;
	}

	public boolean doCheckPermissionByUserId(Integer userId, String url) {
		return provider.doCheckPermissionByUserId(userId, url);
	}
}
