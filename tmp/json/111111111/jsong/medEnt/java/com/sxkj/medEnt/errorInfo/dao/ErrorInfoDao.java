package com.sxkj.medEnt.errorInfo.dao;
import java.util.*;
import com.sxkj.frame.utils.*;
import com.sxkj.frame.utils.PageModel;
import com.sxkj.medEnt.errorInfo.bean.ErrorInfo;
public interface ErrorInfoDao {
public void save(ErrorInfo errorInfo) throws Exception;
public void delete(String id) throws Exception;
public void update(ErrorInfo errorInfo) throws Exception;
public OperateEnterprise search(String id) throws Exception;
public List<OperateEnterprise> searchList(ErrorInfo errorInfo, PageModel pageModel) throws Exception;
}