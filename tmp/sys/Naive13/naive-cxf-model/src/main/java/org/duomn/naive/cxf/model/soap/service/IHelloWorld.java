package org.duomn.naive.cxf.model.soap.service;

import java.util.List;

import javax.jws.WebService;

import org.duomn.naive.cxf.model.soap.entity.UserInfo;



@WebService
public interface IHelloWorld {

	String sayHello(String text);
	
	String sayHelloToAll(List<UserInfo> users);
}
