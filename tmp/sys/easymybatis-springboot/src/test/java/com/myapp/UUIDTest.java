package com.myapp;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.myapp.dao.AddressDao;
import com.myapp.entity.Address;

public class UUIDTest extends EasymybatisSpringbootApplicationTests {

	@Autowired
	AddressDao addressDao;
	
	@Test
	public void testInsert() {
		Address addr = new Address();
		addr.setAddress("address。。");
		
		addressDao.save(addr);
		
		String uuid = addr.getId();
		
		System.out.println("uuid:" + uuid);
	}
}
