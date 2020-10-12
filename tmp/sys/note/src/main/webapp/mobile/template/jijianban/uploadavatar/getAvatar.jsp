<%@ page language="java" import="java.util.*,com.hibernate.voDao.*"
	pageEncoding="UTF-8"%>
<%
	String avatarfilename = request.getSession().getAttribute("UOID")
	.toString();
	UsersDAO usersDao = new UsersDAO();
	users user = new users();
	user = usersDao.findById(Integer.parseInt(avatarfilename));
	if (user.getAvatarstatus()) {
		out.println("image/avatar/avatar_uid_" + avatarfilename + ".jpg");
	} else {
		out.println("image/avatar/noavatar.gif");
	}
%>