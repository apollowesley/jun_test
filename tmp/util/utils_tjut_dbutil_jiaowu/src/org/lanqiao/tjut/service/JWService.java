package org.lanqiao.tjut.service;

import java.util.Scanner;

import org.lanqiao.tjut.entity.JWStudentEntity;
import org.lanqiao.tjut.entity.JWTeacherEntity;
import org.lanqiao.tjut.entity.StuFuction;
import org.lanqiao.tjut.entity.TeaFuction;

public class JWService {
	public void doT() {
		Scanner input = new Scanner(System.in);
		System.out.println("****1 老师登陆*****");
		System.out.println("****2 学生登陆*****");
		System.out.println("****3 退出系统*****");
		int i = input.nextInt();

		switch (i) {
		// 老师登录
		case 1:
			TeaFuction TF =	new TeaFuction();
			TF.t();
			break;
			
			

		case 2:
			StuFuction SF=new StuFuction();
			SF.s();

			
			break;
			
		case 3:
			break;
			}
			
			}
		
	}


