package com.evil.service.Impl;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.evil.dao.BaseDao;
import com.evil.pojo.User;
import com.evil.service.UserService;
import com.evil.util.Page;
import com.evil.util.PageResult;
import com.evil.util.PageUtil;
import com.evil.util.StringUtil;
import com.evil.util.ValidateUtil;

@Service("UserService")
public class UserServiceImpl extends BaseServiceImpl<User> implements
		UserService {

	@Override
	@Resource(name = "userDao")
	public void setBaseDao(BaseDao<User> baseDao) {
		super.setBaseDao(baseDao);
	}

	@Override
	public User findByEmail(String email) {
		String hql = "from User where emailAddress=? and isDelete=1";
		List<User> list = this.findEntityByHQL(hql, email);
		return ValidateUtil.isNull(list) ? null : list.get(0);
	}

	@Override
	public PageResult findPageUsers(Page page, Map<String, Object> map,
			String... sortfields) {
		String hql = "select count(*) from User  where 1=1";
		hql = accordingParamHql(map, hql, sortfields);
		Long count = null;
		count = (Long) this.uniqueResult(hql);
		count = count == null ? 0 : count;
		page = PageUtil.createPage(page.getNumPerPage(), count,
				page.getCurrentPage());// 根据总记录数创建分页信息
		hql = "from User where 1=1";
		hql = accordingParamHql(map, hql, sortfields);
		List<User> list = this.findEntityByPage(hql, page.getCurrentPage(),
				page.getNumPerPage());// 通过分页信息取得试题
		PageResult result = new PageResult(page, list);// 封装分页信息和记录信息，返回给调用处
		return result;
	}

	@Override
	public User LoginCheck(String email, String md5pass) {
		String hql = "from User where emailAddress=? and password=? and isDelete=1";
		List<User> list = this.findEntityByHQL(hql, email, md5pass);
		return ValidateUtil.isNull(list) ? null : list.get(0);
	}

	@Override
	public List<User> findAll() {
		String hql = "from User where isDelete=?";
		return this.findEntityByHQL(hql, "1");
	}

	/**
	 * 判断邮箱是否被占用
	 */
	public boolean isRegisted(String emailAddress) {
		String hql = "from User  where emailAddress=? and isDelete=1";
		List<User> list = this.findEntityByHQL(hql, emailAddress);
		return !ValidateUtil.isNull(list);
	}

	/**
	 * 根据参数拼hql语句
	 */
	private String accordingParamHql(Map<String, Object> map, String hql,
			String... sortfields) {
		if (map != null && !map.isEmpty()) {
			for (Entry<String, Object> entry : map.entrySet()) {
				if (entry.getValue() != null) {
					if (entry.getKey().equals("userName")) {
						hql += " and userName like '" + entry.getValue() + "'";
					}else {
						hql += " and " + entry.getKey() + "="
								+ entry.getValue();
					}
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
			hql = hql.substring(0, hql.length() - 1);
		}
		return hql;
	}

	@Override
	public void batchDeleteUsers(String[] uids) {
		if (!ValidateUtil.isNull(uids)) {
			String ids=StringUtil.arr2SqlStr(uids);
			//删除答案
			String sql="delete from tb_answers where userid in ("+ids+")";
			this.executeSQL(sql);
			//删除错题
			sql="delete from tb_wrongquestions where userId in ("+ids+")";
			this.executeSQL(sql);
			//删除tb_user_paer
			sql = "delete from tb_user_paper where userID in ("+ids+")";
			this.executeSQL(sql);
			// 删除User
			String hql = "update User u set u.isDelete=0 where u.id in ("+ids+")";
			this.batchEntityByHQL(hql);
		}
	}
	@Override
	public void toggleUserStatus(User user) {
		String hql="update User set isEnabled=? where id=?";
		this.batchEntityByHQL(hql, !user.getIsEnabled(),user.getId());
	}

}
