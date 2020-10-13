package com.laycms.member;

import org.springframework.stereotype.Repository;

import com.laycms.base.HibernateBaseDao;
import com.laycms.member.entity.MemberProfile;

/** 
* @author 作者 zbb: 
* @version 创建时间：2016年6月23日 下午2:29:48 
* 类说明 
*/
@Repository
public class MemberProfileDao extends HibernateBaseDao<MemberProfile,Integer>{

	@Override
	public Class<MemberProfile> getEntityClass() {
		return MemberProfile.class;
	}

}
