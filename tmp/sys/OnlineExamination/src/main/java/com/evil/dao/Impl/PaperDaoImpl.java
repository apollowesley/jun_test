package com.evil.dao.Impl;

import org.springframework.stereotype.Repository;

import com.evil.dao.PaperDao;
import com.evil.pojo.Paper;
@Repository("paperDao")
public class PaperDaoImpl extends BaseIpml<Paper> implements PaperDao {

}
