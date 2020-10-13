package org.apache.center.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.center.dao.RoleResourcesMapper;
import org.apache.center.model.RoleResources;
import org.apache.center.service.RoleResourcesService;
import org.apache.playframework.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;
import org.apache.playframework.mybatisplus.mapper.EntityWrapper;
/**
 *
 * RoleResources 表数据服务层接口实现类
 *
 */
@Component  
@Service
public class RoleResourcesServiceImpl extends BaseServiceImpl<RoleResourcesMapper, RoleResources> implements RoleResourcesService {

	@Override
	public List<RoleResources> selectByRoleIds(List<Long> roleIds) {
		EntityWrapper<RoleResources> ew = new EntityWrapper<RoleResources>();
		ew.in("role_id", roleIds);
		return selectList(ew);
	}

	public boolean deleteByRoleId(Long roleId) {
		RoleResources rr = new RoleResources();
		rr.setRoleId(roleId);
		EntityWrapper<RoleResources> ew = new EntityWrapper<RoleResources>(rr);
    	return delete(ew);
    }

	@Override
	public boolean saveRoleResources(Long roleId, Long[] resourcesIds) {
		deleteByRoleId(roleId);
		List<RoleResources> list = new ArrayList<RoleResources>();
    	for (Long resourcesId : resourcesIds) {
			RoleResources rr = new RoleResources();
			rr.setRoleId(roleId);
			rr.setResourcesId(resourcesId);
			list.add(rr);
		}
    	return insertBatch(list);
	}
}


