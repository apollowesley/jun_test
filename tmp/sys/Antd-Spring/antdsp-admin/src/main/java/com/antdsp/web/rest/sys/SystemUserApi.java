package com.antdsp.web.rest.sys;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
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

import com.antdsp.web.dto.SystemUserDto;
import com.antdsp.common.AntdspResponse;
import com.antdsp.common.annotation.OperationLog;
import com.antdsp.common.pagination.PaginationData;
import com.antdsp.dao.jpa.UserJpa;
import com.antdsp.dao.jpa.system.RoleJpa;
import com.antdsp.data.entity.User;
import com.antdsp.data.entity.system.SystemRole;
import com.antdsp.data.entityeenum.UserStatus;
import com.antdsp.utils.ShiroUtils;

@RestController
@RequestMapping("/system/user")
public class SystemUserApi {
	
	@Autowired
	private UserJpa userJpa;
	
	@Autowired
	private RoleJpa roleJpa;
	
	@GetMapping("")
	@OperationLog(name="查询用户列表")
	@RequiresPermissions(value= {"user:list"})
	public PaginationData<User> list(int page , int count , String loginname , UserStatus status){
		
		Specification<User> specification = new Specification<User>() {
			@Override
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				
				List<Predicate> predicates = new ArrayList<>();  
				if(!StringUtils.isEmpty(loginname)) {
					predicates.add(builder.like(root.get("loginname"), "%"+loginname+"%"));
				}
				if(status != null) {
					predicates.add(builder.equal(root.get("status"), status));
				}
				return builder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		};
		
		PaginationData<User> pagination = this.userJpa.list(specification, page, count);
		return pagination;
	}
	
	@GetMapping("/{id:\\d+}")
	@OperationLog(name="查询用户信息")
	@RequiresPermissions(value= {"user:list"})
	public SystemUserDto detail(@PathVariable("id") Long userId) {
		
		List<SystemRole> roles = this.roleJpa.findRoleNameAndIds();
		SystemUserDto result = new SystemUserDto();
		result.setRoles(roles);
		
		if(userId == 0) {
			return result;
		}
		
		 User user = this.userJpa.findById(userId).orElse(null);
		 this.userJpa.detach(user);
		 
		if(user == null) {
			return result;
		}
		
		List<Long> roleIds = this.userJpa.queryUserRoleId(userId);
		result.setRoleIds(roleIds);
		user.setPassword("");
		result.setUser(user);
		result.setRoles(roles);
		
		return result;
	}
	
	@PostMapping("")
	@Transactional
	@OperationLog(name="添加用户")
	@RequiresPermissions(value= {"user:add"})
	public AntdspResponse add(@RequestBody SystemUserDto dto) {
		
		User user = dto.getUser();
		User current = current();
		List<Long> roleIds = dto.getRoleIds();
		
		User olduser = userJpa.queryUserByLoginName(user.getLoginname());
		if(olduser != null) {
			return AntdspResponse.error("登录名重复");
		}
		
		user.setCreator(current.getLoginname());
		user.setModifier(current.getLoginname());
		user.setStatus(UserStatus.NORMAL);
		user.onPreInsert();
		
		Long userId = this.userJpa.save(user).getId();
		this.saveRoleUser(userId, roleIds);
		
		return AntdspResponse.success("保存成功");
	}
	
	@PutMapping("/{id:\\d+}")
	@Transactional
	@OperationLog(name="修改用户信息")
	@RequiresPermissions(value= {"user:update"})
	public AntdspResponse update(@RequestBody SystemUserDto dto) {
		
		User user = dto.getUser();
		User current = current();
		List<Long> roleIds = dto.getRoleIds();
		
		User oldUser = userJpa.findById(user.getId()).orElse(null);
		
		oldUser.setEmail(user.getEmail());
		oldUser.setQq(user.getQq());
		oldUser.setRealname(user.getRealname());
		oldUser.setModifier(current.getLoginname());
		oldUser.setAvatar(user.getAvatar());
		oldUser.onPreUpdate();
		this.userJpa.save(oldUser);
		this.userJpa.deleteUserRoleId(user.getId());
		
		this.saveRoleUser(oldUser.getId(), roleIds);
		
		return AntdspResponse.success("保存成功");
	}
	
	@DeleteMapping("/{id:\\d+}")
	@RequiresPermissions(value= {"user:delete"})
	@Transactional
	@OperationLog(name="删除用户")
	public AntdspResponse delete(@PathVariable("id") Long id) {
		
		User oldUser = userJpa.findById(id).orElse(null);
		if(oldUser == null) {
			return AntdspResponse.error("删除失败，未找到用户信息");
		}
		this.userJpa.delete(oldUser);
		this.userJpa.deleteUserRoleId(id);
		
		return AntdspResponse.success();
	}
	
	@GetMapping("/current")
	public User current() {
		User current = ShiroUtils.currentUser();
		current.setPassword("");
		return current;
	}
	
	private int saveRoleUser(Long userId, List<Long> roleIds) {
		if(roleIds != null && roleIds.size() > 0) {
			StringBuffer sqlStr = new StringBuffer("INSERT INTO `tb_role_user` (`role_id`, `user_id`) VALUES ");
			sqlStr.append("("+roleIds.get(0)+ ", " + userId+")");
			if(roleIds.size()>=2) {
				for(int i= 1 ; i< roleIds.size(); i++) {
					sqlStr.append(",("+roleIds.get(i)+ ", " + userId+")");
				}
			}
			return this.userJpa.executeSQL(sqlStr.toString(), null);
		}
		return 0;
	}
}
