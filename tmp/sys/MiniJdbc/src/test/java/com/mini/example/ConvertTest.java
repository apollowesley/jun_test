package com.mini.example;

import com.mini.example.bean.Demo;
import com.mini.example.bean.User;
import com.mini.jdbc.utils.ConvertKit;

public class ConvertTest {
	public static void main(String[] args) {
		User u = new User();
		u.setName("苏小妹");
		ConvertKit.toEntity(u, Demo.class);
		System.out.println();
	}
}
