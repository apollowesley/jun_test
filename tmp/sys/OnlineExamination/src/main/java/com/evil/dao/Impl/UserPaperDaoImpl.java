package com.evil.dao.Impl;

import org.springframework.stereotype.Repository;

import com.evil.dao.UserPaperDao;
import com.evil.pojo.UserPaper;
@Repository("userPaperDao")
public class UserPaperDaoImpl extends BaseIpml<UserPaper> implements UserPaperDao {

}
