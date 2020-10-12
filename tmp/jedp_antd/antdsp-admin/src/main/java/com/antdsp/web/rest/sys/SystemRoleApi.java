package com.antdsp.web.rest.sys;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.antdsp.web.dto.RoleDto;
import com.antdsp.common.AntdspResponse;
import com.antdsp.common.annotation.OperationLog;
import com.antdsp.common.pagination.PaginationData;
import com.antdsp.dao.jpa.system.RoleJpa;
import com.antdsp.data.entity.system.SystemRole;

/**
 * 
 * <p>title:SystemRoleApi.java</p>
 * <p>Description: </p>  
 * <p>Copyright: Copyright (c) 2019</p> 
 * 
 * @author jt-lee
 * @date 2019年6月3日
 * @email a496401006@qq.com
 *
 */
@RestController
@RequestMapping("/system/role")
public class SystemRoleApi {
	
	@Autowired
	private RoleJpa roleJpa;
	
	@GetMapping("")
	@OperationLog(name="查询角色列表")
	@RequiresPermissions(value= {"role:list"})
	public PaginationData<SystemRole> list(int page , int count , String roleName){
		
		int start = (page - 1) * count;
		Pageable pageable = PageRequest.of(start, count , Sort.by(Order.desc("created")));
		Specification<SystemRole> specification = new Specification<SystemRole>() {
			@Override
			public Predicate toPredicate(Root<SystemRole> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				
				List<Predicate> predicates = new ArrayList<>();  
				if(!StringUtils.isEmpty(roleName)) {
					predicates.add(builder.like(root.get("roleName"), "%"+roleName+"%"));
				}
				return builder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		};
		
		Page<SystemRole> userList = roleJpa.findAll(specification , pageable);
		
		return new PaginationData<>(userList.getContent() , page , userList.getTotalElements());
	}
	
	@GetMapping("/{id:\\d+}")
	@OperationLog(name="查询角色信息")
	@RequiresPermissions(value= {"role:list"})
	public RoleDto detail(@PathVariable("id") Long id) {
		
		RoleDto dto = new RoleDto();
		SystemRole role = this.roleJpa.findById(id).orElse(null);
		if(role == null) {
			return dto;
		}
		
		dto.setDescription(role.getDescription());
		dto.setId(role.getId());
		dto.setRoleName(role.getRoleName());
		
		List<Long> menuIds = this.roleJpa.queryRoleMenuId(id);
		dto.setMenuIds(menuIds);
		return dto;
	}
	
	@PostMapping("")
	@Transactional
	@OperationLog(name="添加角色")
	@RequiresPermissions(value= {"role:add"})
	public AntdspResponse add(@RequestBody RoleDto role) {
		
		SystemRole data = new SystemRole();
		
		data.setId(null);
		this.dtoToData(role, data);
		data.setCreator("");
		data.setModifier("");
		data.onPreInsert();

		data = this.roleJpa.save(data);
		
		List<Long> menuIds = role.getMenuIds();
		
		this.saveRoleMenu(menuIds, data.getId());
		
		return AntdspResponse.success();
	}
	
	@PutMapping("")
	@Transactional
	@OperationLog(name="修改角色")
	@RequiresPermissions(value= {"role:update"})
	public AntdspResponse update(@RequestBody RoleDto role) {
		
		Long roleId = role.getId();
		SystemRole data = this.roleJpa.findById(roleId).orElse(null);
		if(data == null) {
			return AntdspResponse.error("未找到角色信息");
		}
		
		this.dtoToData(role, data);
		data.onPreUpdate();
		this.roleJpa.save(data);
		
		this.roleJpa.deleteRoleMenuByRoleId(roleId);
		this.saveRoleMenu(role.getMenuIds(), roleId);
		
		return AntdspResponse.success();
	}
	
	@DeleteMapping("/{id:\\d+}")
	@Transactional()
	@OperationLog(name="删除角色")
	@RequiresPermissions(value= {"role:delete"})
	public AntdspResponse delete(@PathVariable("id") Long id) {
		
		SystemRole role = this.roleJpa.findById(id).orElse(null);
		if(role == null) {
			return AntdspResponse.error("未找到角色信息");
		}
		this.roleJpa.delete(role);
		this.roleJpa.deleteRoleMenuByRoleId(role.getId());
		return AntdspResponse.success();
	}
	
	@GetMapping("queryRoleNameAndIds")
	public List<SystemRole> queryRoleNameAndIds(){
		return this.roleJpa.findRoleNameAndIds();
	}
	
	private int saveRoleMenu(List<Long> menuIds , Long roleId) {
		if(menuIds != null &&menuIds.size() > 0) {
			StringBuffer sqlStr = new StringBuffer("INSERT INTO `tb_role_menu` (`role_id`,`menu_id`) VALUES ");
			sqlStr.append("("+roleId+" , "+ menuIds.get(0)+" ) ");
			if(menuIds.size() > 2) {
				for(int i = 1 ; i< menuIds.size() ; i++) {
					sqlStr.append(",("+roleId+" , "+ menuIds.get(i)+" ) ");
				}
			}
			return this.roleJpa.executeSQL(sqlStr.toString() , null);
		}
		return 0;
	}
	
	private void dtoToData(RoleDto role , SystemRole data) {
		data.setDescription(role.getDescription());
		data.setRoleName(role.getRoleName());
	}

}
