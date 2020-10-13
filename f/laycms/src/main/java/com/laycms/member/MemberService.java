package com.laycms.member;

import java.sql.SQLException;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.laycms.base.EntityView;
import com.laycms.config.SystemConfig;
import com.laycms.member.entity.Member;
import com.laycms.sys.dao.RoleDao;

/** 
* @author 作者 zbb: 
* @version 创建时间：2016年9月29日 下午3:51:05 
* 类说明 
*/
@Service
@Transactional
public class MemberService {

	private Logger logger = LoggerFactory.getLogger(MemberService.class);
	@Autowired
	private MemberDao memberDao;
	@Autowired
	private SystemConfig systemConfig;
	@Autowired
	private RoleDao roleDao;
	public MemberDao getMemberDao() {
		return memberDao;
	}

	private boolean isMobile(String mobile){
		Pattern p = Pattern.compile("^(1[0-9])\\d{9}$");
		Matcher m = p.matcher(mobile);
		return m.matches();
	}
	
	/**
	 * 根据登录帐号(邮箱或昵称或手机号码)获取用户对象
	 * @param loginName
	 * @return
	 * @throws SQLException
	 */
	public Member findByLoginName(String loginName) {
		Member member = null;
		boolean isMobile = isMobile(loginName);
		if(isMobile) {
			member = memberDao.findUniq("mobile", loginName);
		} else {
			member = memberDao.findUniq("username", loginName);
		}
		return member;
	}
	
	public boolean checkExists(String propertyName, String propertyValue) {
		boolean exists = false;
		if(StringUtils.isNotBlank(propertyName)) {
			EntityView ev = new EntityView();
			ev.add(Restrictions.eq(propertyName, propertyValue));
			exists = memberDao.exist(ev);
		}
		return exists;
	}
	
	public Set<String> getRoleName(Member user) {
		Set<String> roleNames=null;
		try {
			roleNames = roleDao.getRoleNamesByNickname(user.getUsername());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return roleNames;
	} 
}
