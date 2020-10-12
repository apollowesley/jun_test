package com.mario.demo;

import com.junicorn.mario.common.Bootstrap;
import com.junicorn.mario.common.Mario;
import com.junicorn.mario.db.MarioDb;
import com.mario.demo.controller.Index;
import com.mario.demo.controller.UserController;

public class App implements Bootstrap {

	@Override
	public void init(Mario mario) {
		Index index = new Index();
		UserController userController = new UserController();
		mario.addRoute("/", "index", index);
		mario.addRoute("/hello", "hello", index);
		mario.addRoute("/html", "html", index);
		
		mario.addRoute("/users", "users", userController);
		mario.addRoute("/user/add", "show_add", userController);
		mario.addRoute("/user/save", "save", userController);
		mario.addRoute("/user/edit", "edit", userController);
		mario.addRoute("/user/update", "update", userController);
		mario.addRoute("/user/del", "delete", userController);
		
		try {
			// 解决tomcat下报错
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// 配置数据库
		MarioDb.init("jdbc:mysql://127.0.0.1:3306/mario_sample", "root", "root");
		
	}
	
}
