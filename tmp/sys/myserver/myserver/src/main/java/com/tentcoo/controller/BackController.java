package com.tentcoo.controller;



import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tentcoo.dao.UserDao;
import com.tentcoo.entity.Address;
import com.tentcoo.entity.User;
import com.tentcoo.entity.UserMessage;
import com.tentcoo.service.AddressRecordService;
import com.tentcoo.service.MessageService;
import com.tentcoo.service.UserService;
import com.tentcoo.util.CheckPcOrPhone;
import com.tentcoo.util.SecurityUtil;
import com.tentcoo.util.SendmailUtil;
import com.tentcoo.util.UUIDUtils;




@Controller
public class BackController {
	
	@Resource
	UserService userService;

	@Resource
	AddressRecordService addressRecordService;
	
	@Resource
	MessageService messageService;
	
	
	@Resource
	CheckPcOrPhone checkPcOrPhone;
	@RequestMapping(produces = "application/json;charset=UTF-8",value="checkStuNo",method = {RequestMethod.GET})
	@ResponseBody
	public Map Check(String userNo) {
		
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		if(userService.getUserByUserNo(userNo)!=null) {
			
			map.put("value", userService.getUserByUserNo(userNo));
			map.put("status", "success");
		}else {
			map.put("value", userService.getUserByUserNo(userNo));
			map.put("status", "fail");
		}
			ObjectMapper json = new ObjectMapper();
			String params = null;
			       try {
							params = json.writeValueAsString(map);
						} catch (JsonProcessingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		            System.out.println(params);
			return map;
		
	
	
	}
	
	@RequestMapping(produces = "application/json;charset=UTF-8",value="record",method = {RequestMethod.POST})
	public String addSeverlet(ServletRequest request, ServletResponse response) {
	
		
	
		User per=new User();
		
		String userNo=request.getParameter("userNo");
		String userName=request.getParameter("userName");
		String userBirth=request.getParameter("userBirth");
		String userSex=request.getParameter("userSex");
		String userEmail=request.getParameter("userEmail");
		String userPassword=request.getParameter("userPassword");
		
		String userState=request.getParameter("userState");
		String userWord=request.getParameter("userWord");
		/*if(userPassword=="") {
       System.out.println("是空格");}
		if(userPassword==null) System.out.println("null");
		else System.out.println("不等");*/

	
		if(userNo!=""&&userName!=""&&userPassword!=""&&userBirth!=""&&userSex!=""&&userEmail!="") {
							
			
			if(userService.getUserByUserNo(userNo)!=null) {
				
				System.out.println("添加失败，账号已经注册！");
				request.setAttribute( "message","添加失败，账号已经注册！");
				return "error";
				}
			
			
			
			
							per.setUserNo(userNo);
							
							per.setUserName(userName);
						
							per.setUserBirth(userBirth);
							
							String userCode=UUIDUtils.UUID()+UUIDUtils.UUID();
							try {
								SendmailUtil.sendEmail(userEmail, userCode);
								per.setUserPassword(SecurityUtil.encryptPassword(userPassword));
								
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
							per.setUserSex(userSex);
							
							per.setUserEmail(userEmail);
							
							
							
							
							
							per.setUserCode(userCode);
							
							
							per.setUserState(0);
							
							per.setUserWord(userWord);
							
						
							
							if(userService.addUserInfo(per))return "exlogin";
							else {
								request.setAttribute( "message","添加失败，系统异常！");
								return "error";
							
							}
							
		//request.setAttribute("userRecord", "true");
		
		
		
		}
		System.out.println("添加失败，信息不能为空！");
		request.setAttribute( "message","添加失败，信息不能为空！");
		return "error";
	}
	

	@RequestMapping(value="judge")
	public String getPcOrPhone(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String requestHeader = request.getHeader("User-Agent");
		
		String remoteIp=request.getRemoteAddr();
		
		String userNo=request.getParameter("userNo");
		
		String Password=request.getParameter("userPassword");
		
		System.out.println(requestHeader);
		
		System.out.println(remoteIp);
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
		Date date=new Date();
		String Time = sdf.format(date);
		
		Address address=new Address();
		
		address.setDevices(requestHeader);
		
		address.setUserIp(remoteIp);
		
		address.setTime(Time);
		
		addressRecordService.addUserAddressInfo(address);
	
		User user=userService.getUserByUserNo(userNo);
		
		String userPassword=SecurityUtil.encryptPassword(Password);
		
		if(user!=null&&user.getUserPassword().equals(userPassword)&&user.getUserState()!=0) {
			System.out.println("user"+user.getUserNo());
			System.out.println("存储的密码！"+user.getUserPassword());
			System.out.println("输入的密码！"+userPassword);
			if(checkPcOrPhone.PcOrPhone(requestHeader)) {
				
				
				System.out.println("成功登陆！");
				
				
				List<UserMessage> list=messageService.getAllMessageInfo();
				
				
				request.setAttribute("messageData", list);
				request.setAttribute("userInfo", user);
				
				
				
				
				return "home";//true是移动端
				
				
			}

			
		
		}
		
		request.setAttribute("message", "账号未激活或账号密码有误！");
		return "error";//pc端
		

		
		
	}
	
	@RequestMapping(value="addUserMessage")
	public String recordUserMessage(HttpServletRequest request, HttpServletResponse response) {
		
		
		String userNo=request.getParameter("userNo");
		String userName=request.getParameter("userName");

		String userWords=request.getParameter("userWords");
		
		UserMessage message=new UserMessage();
		
		System.out.println(userNo);
		System.out.println(userName);
		System.out.println(userWords);
		
		if(userNo!=null&&userName!=null&&userWords!=null){
			
			message.setUserNo(userNo);
			message.setUserName(userName);
			message.setUserWords(userWords);
			SimpleDateFormat sdf=new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
			Date date=new Date();
			String Time = sdf.format(date);
			message.setUserTime(Time);
			messageService.addMessageInfo(message);
		
			request.setAttribute( "message","可以啊！添加成功，再接再厉挤爆它！");
			return "success";
		}
		request.setAttribute( "message","可以啊！添加失败，再接再厉！");
		return "error";
		
		
		
		
	}
	

}