package com.javasrc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.globalClass.GlobalVariable;
import com.globalInterface.RequestParameter2;
import com.hibernate.voDao.RelationshipFriends;
import com.hibernate.voDao.RelationshipFriendsDAO;
import com.hibernate.voDao.Users;
import com.hibernate.voDao.UsersDAO;
import com.javasrc.service.FriendsService;

public class Friends extends RequestParameter2 {
	private RelationshipFriends relationshipFriends;
	private RelationshipFriendsDAO relationshipFriendsDao;
	private Users users;
	private UsersDAO usersDao;
	private FriendsService friendsService;

	public RelationshipFriends getRelationshipFriends() {
		return relationshipFriends;
	}

	public void setRelationshipFriends(RelationshipFriends relationshipFriends) {
		this.relationshipFriends = relationshipFriends;
	}

	public RelationshipFriendsDAO getRelationshipFriendsDao() {
		return relationshipFriendsDao;
	}

	public void setRelationshipFriendsDao(RelationshipFriendsDAO relationshipFriendsDao) {
		this.relationshipFriendsDao = relationshipFriendsDao;
	}

	public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	public UsersDAO getUsersDao() {
		return usersDao;
	}

	public void setUsersDao(UsersDAO usersDao) {
		this.usersDao = usersDao;
	}

	public FriendsService getFriendsService() {
		return friendsService;
	}

	public void setFriendsService(FriendsService friendsService) {
		this.friendsService = friendsService;
	}

	/**
	 * Constructor of the object.
	 */
	public Friends() {
		super();
	}

	@Override
	public void initClass() {
		setRelationshipFriends(new RelationshipFriends());
		setRelationshipFriendsDao(new RelationshipFriendsDAO());
		setUsers(new Users());
		setUsersDao(new UsersDAO());
		setFriendsService(new FriendsService());
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	@Override
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		super.service(request, response);
		String a = getParameter("a");
		if ("search".equals(a)) {
			searchPerson(request, response);
			return;
		} else if ("addFriends".equals(a)) {
			addFriends(request, response);
			return;
		} else if ("confirmFriends".equals(a)) {
			confirmFriends(request, response);
			return;
		} else if ("myFriends".equals(a)) {
			myFriends(request, response);
			return;
		} else if ("deleteFriends".equals(a)) {
			deleteFriends(request, response);
			return;
		} else if ("refuseFriends".equals(a)) {
			refuseFriends(request, response);
			return;
		}

	}

	private void searchPerson(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String searchWord = getParameter("s_wd");
		List list1 = getFriendsService().searchPerson(searchWord);
		request.setAttribute("list1", list1);
		request.getRequestDispatcher(GlobalVariable.getWebsite_template() + "/jsp/searchPerson.jsp").forward(request, response);
	}

	private void addFriends(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		int uid1 = Integer.valueOf(getParameter("fuid"));
		int uid2 = (Integer) request.getSession().getAttribute("UOID");
		int b = getFriendsService().addFriends(uid1, uid2);
		JSONObject jsono = new JSONObject();
		if (b == 3) {// 成功
			jsono.put("code", "200");
			jsono.put("status", "success");
			jsono.put("msg", "申请好友成功");
			returnJson(jsono.toJSONString());
		} else if (b == 1) {// 已经申请好
			jsono.put("code", "200");
			jsono.put("status", "fail");
			jsono.put("msg", "已经申请好友\n    等待确认");
			returnJson(jsono.toJSONString());
		} else if (b == 2) {// 已经是好友
			jsono.put("code", "200");
			jsono.put("status", "fail");
			jsono.put("msg", "你们已经是好友");
			returnJson(jsono.toJSONString());
		} else if (b == 0) {// 不能加自己
			jsono.put("code", "200");
			jsono.put("status", "fail");
			jsono.put("msg", "不能加自己好友");
			returnJson(jsono.toJSONString());
		} else {// 失败
			jsono.put("code", "200");
			jsono.put("status", "fail");
			jsono.put("msg", "申请好友失败");
			returnJson(jsono.toJSONString());
		}
	}

	private void confirmFriends(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		int uid1 = Integer.valueOf(getParameter("uid1"));
		int uid2 = (Integer) request.getSession().getAttribute("UOID");
		boolean b = getFriendsService().confirmFriends(uid1, uid2);
		if (b) {
			returnJson(SUCCESS);
		} else {
			returnJson(FAIL.toString());
		}
	}

	private void refuseFriends(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		int uid1 = Integer.valueOf(getParameter("uid1"));
		int uid2 = (Integer) request.getSession().getAttribute("UOID");
		boolean b = getFriendsService().confirmFriends(uid1, uid2);
		if (b) {
			returnJson(SUCCESS);
		} else {
			returnJson(FAIL.toString());
		}
	}

	private void myFriends(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		int uid1 = Integer.valueOf((Integer) request.getSession().getAttribute("UOID"));
		List<Users> list = getFriendsService().myFriends(uid1);
		if (list != null) {
			request.setAttribute("list1", list);
		} else {
			request.setAttribute("list1", new ArrayList());
		}
		request.setAttribute("page", "5");
		request.getRequestDispatcher(GlobalVariable.getWebsite_template() + "/jsp/userCenterLeft.jsp").forward(request, response);
	}

	private void deleteFriends(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		int uid1 = Integer.valueOf(getParameter("uid1"));
		int uid2 = Integer.valueOf((Integer) request.getSession().getAttribute("UOID"));
		boolean b = getFriendsService().refuseFriends(uid1, uid2);
		if (b) {
			returnJson(SUCCESS);
		} else {
			returnJson(FAIL.toString());
		}
	}

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occurs
	 */
	@Override
	public void init() throws ServletException {
		// Put your code here
	}

}
