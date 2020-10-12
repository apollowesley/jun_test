package com.hrrm.kiss.user.dao.impl;
import com.hrrm.kiss.dao.impl.DefaultGenericDaoImpl;
import com.hrrm.kiss.user.entity.User;
import com.hrrm.kiss.user.dao.IUserDao;
import org.springframework.stereotype.Repository;

@Repository("UserDao");
public class UserDaoImpl extends DefaultGenericDaoImpl<User> implements IUserDao {







}
