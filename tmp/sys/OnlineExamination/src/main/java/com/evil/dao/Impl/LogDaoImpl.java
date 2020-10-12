package com.evil.dao.Impl;

import org.springframework.stereotype.Repository;

import com.evil.dao.LogDao;
import com.evil.pojo.system.Log;
@Repository("logDao")
public class LogDaoImpl extends BaseIpml<Log> implements LogDao {

}
