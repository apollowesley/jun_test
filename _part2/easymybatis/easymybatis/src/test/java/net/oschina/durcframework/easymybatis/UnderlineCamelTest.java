package net.oschina.durcframework.easymybatis;

import org.junit.Test;

import junit.framework.TestCase;
import net.oschina.durcframework.easymybatis.ext.code.util.FieldUtil;

public class UnderlineCamelTest extends TestCase {
	
	@Test
	public void testcamelToUnderline() {
		String[] arr = {"username","userName","openTheDoor","showDBConfig","YourNameIsJim"};
		System.out.println("---------testcamelToUnderline---------");
		for (String field : arr) {
			System.out.println(field + " -> " + FieldUtil.camelToUnderline(field));
//			userName -> user_name
//			openTheDoor -> open_the_door
//			showDBConfig -> show_dB_config
//			YourNameIsJim -> your_name_is_jim

		}
	}
	
	@Test
	public void testunderlineToCamel() {
		String[] arr = {"username","user_name","open_The_door","show_DB_config","my_name_is_jim"
				,"_your_name"};
		System.out.println("---------testunderlineToCamel---------");
		for (String field : arr) {
			System.out.println(field + " -> " + FieldUtil.underlineToCamel(field));
//			user_name -> userName
//			open_The_door -> openTheDoor
//			show_DB_config -> showDBConfig
//			my_name_is_jim -> myNameIsJim
		}
	}
	
	

}
