package com.laycms.member;



import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.laycms.base.HibernateBaseDao;
import com.laycms.member.entity.Member;

@Repository
@Transactional
public class MemberDao extends HibernateBaseDao<Member,Integer>{

	@Override
	public Class<Member> getEntityClass() {
		return Member.class;
	}
	
}
