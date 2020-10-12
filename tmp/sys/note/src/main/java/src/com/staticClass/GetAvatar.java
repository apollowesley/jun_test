package com.staticClass;

import com.hibernate.voDao.Users;
import com.hibernate.voDao.UsersDAO;

public class GetAvatar {

	public static String GetfileUrl(String uid) {
		UsersDAO usersDao = new UsersDAO();
		Users user = new Users();
		user = usersDao.findById(Integer.parseInt(uid));
		String avatarpath = "";
		if (user.getAvatarstatus()) {
			avatarpath = "image/avatar/avatar_uid_" + uid + ".jpg";
		} else {
			avatarpath = "image/avatar/noavatar.gif";
		}
		return avatarpath;
	}
}
