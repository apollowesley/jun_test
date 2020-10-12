package com.evil.service.Impl;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.evil.dao.BaseDao;
import com.evil.pojo.system.AdminUser;
import com.evil.service.AdminUserService;
import com.evil.util.Page;
import com.evil.util.PageResult;
import com.evil.util.PageUtil;
import com.evil.util.StringUtil;
import com.evil.util.ValidateUtil;

@Service("adminUserService")
public class AdminUserServiceImpl extends BaseServiceImpl<AdminUser> implements
		AdminUserService {

	@Override
	@Resource(name = "adminUserDao")
	public void setBaseDao(BaseDao<AdminUser> baseDao) {
		super.setBaseDao(baseDao);
	}

	@Override
	public AdminUser LoginCheck(String name, String password) {
		String hql = "from AdminUser where name=? and password=?";
		List<AdminUser> list = this.findEntityByHQL(hql, name, password);
		return ValidateUtil.isNull(list) ? null : list.get(0);
	}

	@Override
	public PageResult findAdminUserPage(Page page, Map<String, Object> map,
			String... sortfields) {
		String hql = "select count(*) from AdminUser  where 1=1";
		hql = accordingParamHql(map, hql, sortfields);
		Long count = null;
		count = (Long) this.uniqueResult(hql);
		count = count == null ? 0 : count;
		page = PageUtil.createPage(page.getNumPerPage(), count,
				page.getCurrentPage());// 根据总记录数创建分页信息
		hql = "from AdminUser where 1=1";
		hql = accordingParamHql(map, hql, sortfields);
		List<AdminUser> list = this.findEntityByPage(hql,
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
					if (entry.getKey().equals("name")) {
						hql += " and name like '" + entry.getValue() + "'";
					} else {
						hql += " and " + entry.getKey() + "="
								+ entry.getValue();
					}
				}
			}
			// 拼接排序
			if (!ValidateUtil.isNull(sortfields)) {
				hql += " order by ";
				for (String s : sortfields) {
					if (!ValidateUtil.isNull(s))
						hql += s + ",";
				}
				if (hql.endsWith(" order by ")) {
					hql.substring(0, hql.length() - 10);
				}
				hql = hql.substring(0, hql.length() - 1);
			}
		}
		return hql;
	}

	@Override
	public void batchDeleteAdminUsers(String[] auids) {
		if (!ValidateUtil.isNull(auids)) {
			String ids = StringUtil.arr2SqlStr(auids);
			// 删除角色依赖
			String sql = "delete from tb_adminUsers_role where adminUserid in ("
					+ ids + ")";
			this.executeSQL(sql);
			// 删除管理员
			String hql = "delete from AdminUser where id in (" + ids + ")";
			this.batchEntityByHQL(hql);
		}
	}

	@Override
	public boolean isNameOccupy(String name) {
		String hql = "from AdminUser where name=?";
		List<AdminUser> adminUsers = this.findEntityByHQL(hql, name);
		return !ValidateUtil.isNull(adminUsers);
	}

	@Override
	public void updateAmdinUserAuthorize(AdminUser model, String[] ownRoleIds) {

		String sql = "delete from tb_adminUsers_role where adminUserid=?";
		this.executeSQL(sql, model.getId()); // 先清除管理员的角色
		sql = "insert into tb_adminUsers_role (roleid,adminUserid) values ";
		if (!ValidateUtil.isNull(ownRoleIds)) { // 如果具备的角色不为空
			for (String rid : ownRoleIds) { // 拼接插入语句
				sql += "('" + rid + "','" + model.getId() + "'),";
			}
			this.executeSQL(sql.substring(0, sql.length()-1));
		}
	}

}
