package com.javasrc;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.beans.MessageBean;
import com.globalClass.GlobalVariable;
import com.globalInterface.RequestParameter2;
import com.hibernate.voDao.RelationshipFriends;
import com.hibernate.voDao.RelationshipFriendsDAO;
import com.hibernate.voDao.Users;
import com.hibernate.voDao.UsersDAO;
import com.javasrc.service.FriendsService;

public class Message extends RequestParameter2 {
	private RelationshipFriends relationshipFriends;
	private RelationshipFriendsDAO relationshipFriendsDao;
	private Users users;
	private UsersDAO usersDao;
	private MessageBean messageBean;
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

	public MessageBean getMessageBean() {
		return messageBean;
	}

	public void setMessageBean(MessageBean messageBean) {
		this.messageBean = messageBean;
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
	public Message() {
		super();
	}

	@Override
	public void initClass() {
		setRelationshipFriends(new RelationshipFriends());
		setRelationshipFriendsDao(new RelationshipFriendsDAO());
		setUsers(new Users());
		setUsersDao(new UsersDAO());
		setMessageBean(new MessageBean());
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
		if ("news".equals(a)) {
			getMessage(request, response);
			return;
		} else if ("haveRead".equals(a)) {
			haveRead(request, response);
			return;
		}

	}

	/** 获取所有消息的入口 **/
	private void getMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<MessageBean> list1 = getFriendsService().applyFriends((Integer) request.getSession().getAttribute("UOID"));
		request.setAttribute("list1", list1);
		request.setAttribute("page", "4");
		request.getRequestDispatcher(GlobalVariable.getWebsite_template() + "/jsp/userCenterLeft.jsp").forward(request, response);
	}

	private void haveRead(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {

			returnJson(FAIL.toString());
		} catch (Exception e) {
			e.printStackTrace();
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
