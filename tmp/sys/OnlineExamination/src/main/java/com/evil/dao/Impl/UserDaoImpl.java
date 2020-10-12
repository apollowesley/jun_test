package com.evil.dao.Impl;
import org.springframework.stereotype.Repository;

import com.evil.dao.UserDao;
import com.evil.pojo.User;
@Repository("userDao")
public class UserDaoImpl extends BaseIpml<User> implements UserDao {

}
