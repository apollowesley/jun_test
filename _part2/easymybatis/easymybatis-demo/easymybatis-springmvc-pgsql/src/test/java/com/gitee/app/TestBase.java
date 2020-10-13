package com.gitee.app;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml" })
@TransactionConfiguration(defaultRollback = false)
public class TestBase extends AbstractTransactionalJUnit4SpringContextTests {
	public void print(Object o) {
		System.out.println("=================");
		System.out.println(o);
		System.out.println("=================");
	}
}
