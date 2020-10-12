package com.evil.service.Impl;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.evil.dao.BaseDao;
import com.evil.pojo.system.Role;
import com.evil.service.RoleService;
import com.evil.util.Page;
import com.evil.util.PageResult;
import com.evil.util.PageUtil;
import com.evil.util.StringUtil;
import com.evil.util.ValidateUtil;

@Service("roleService")
public class RoleServiceImpl extends BaseServiceImpl<Role> implements
		RoleService {

	@Override
	@Resource(name = "roleDao")
	public void setBaseDao(BaseDao<Role> baseDao) {
		super.setBaseDao(baseDao);
	}

	@Override
	public PageResult findRolesPage(Page page, Map<String, Object> map,
			String... sortfields) {
		String hql = "select count(*) from Role  where 1=1";
		hql = accordingParamHql(map, hql, sortfields);
		Long count = null;
		count = (Long) this.uniqueResult(hql);
		count = count == null ? 0 : count;
		page = PageUtil.createPage(page.getNumPerPage(), count,
				page.getCurrentPage());// 根据总记录数创建分页信息
		hql = "from Role where 1=1";
		hql = accordingParamHql(map, hql, sortfields);
		List<Role> list = this.findEntityByPage(hql,
				page.getCurrentPage(), page.getNumPerPage());// 通过分页信息取得试题
		PageResult result = new PageResult(page, list);// 封装分页信息和记录信息，返回给调用处
		return result;
	}
	/**
	 * 根据参数拼hql语句
	 */
	private String accordingParamHql(Map<String, Object> map, String hql,
			String... sortfields) {
		if (map != null && !map.isEmpty()) {
			for (Entry<String, Object> entry : map.entrySet()) {
				if (entry.getValue() != null) {
					if (entry.getKey().equals("roleName")) {
						hql += " and roleName like '" + entry.getValue() + "'";
					}else {
						hql += " and " + entry.getKey() + "="
								+ entry.getValue();
					}
				}
			}
			// 拼接排序
			if (!ValidateUtil.isNull(sortfields)) {
				hql += " order by ";
				for (String s : sortfields) {
				   if(!ValidateUtil.isNull(s))
					 hql += s + ",";
				}
				if(hql.endsWith(" order by ")){
					hql.substring(0, hql.length()-10);
				}
				hql = hql.substring(0, hql.length() - 1);
			}
		}
		return hql;
	}

	@Override
	public void batchDeleteRoles(String[] rids) {
		if (!ValidateUtil.isNull(rids)) {
			String ids=StringUtil.arr2SqlStr(rids);
			//删除角色依赖
			String sql="delete from tb_adminUsers_role where roleid in ("+ids+")";
			this.executeSQL(sql);
			//删除权限依赖
			sql="delete from tb_role_right where roleid in ("+ids+")";
			this.executeSQL(sql);
			//删除管理员
			String hql="delete from Role where id in ("+ids+")";
			this.batchEntityByHQL(hql);
		}
	}
	
	

	@Override
	public List<Role> findRolesNotInRange(Set<Role> roles) {
		if(!ValidateUtil.isNull(roles)){
			String hql="from Role r where r.id not in ("+StringUtil.extractEntityIds(roles)+")";
			return this.findEntityByHQL(hql);
		}
		return this.findAllEntities();
	}

	@Override
	public void saveOrUpdateRole(Role model, String[] ownRightIds) {
		this.saveOrUpdateEntity(model);
		String sql = "delete from tb_role_right where roleid=?";
		this.executeSQL(sql, model.getId()); // 先清除管理员的角色
		sql = "insert into tb_role_right (roleid,rightid) values ";
		if (!ValidateUtil.isNull(ownRightIds)) { // 如果具备的角色不为空
			for (String rid : ownRightIds) { // 拼接插入语句
				sql += "('" + model.getId() + "','" + rid + "'),";
			}
			this.executeSQL(sql.substring(0, sql.length()-1));
		}
		
	}

}
