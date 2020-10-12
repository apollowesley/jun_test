package org.konghao.test;

import org.hibernate.Session;
import org.junit.Assert;
import org.junit.Test;
import org.konghao.util.HibernateUtil;
import org.konghao.vo.User;

public class TestUser {

	@Test
	public void testAdd() {
		Session session = HibernateUtil.openSession();
		session.beginTransaction();
		
		
		session.getTransaction().commit();
	}
	
	@Test
	public void testLoad() {
		Session session = HibernateUtil.openSession();
		session.beginTransaction();
		
		User u = (User)session.load(User.class, 1);
		
		Assert.assertEquals(1, u.getId());
		
		session.getTransaction().commit();
	}
}
