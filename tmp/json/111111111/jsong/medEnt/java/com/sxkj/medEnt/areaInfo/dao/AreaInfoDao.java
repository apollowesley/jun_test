package com.sxkj.medEnt.areaInfo.dao;
import java.util.*;
import com.sxkj.frame.utils.*;
import com.sxkj.frame.utils.PageModel;
import com.sxkj.medEnt.areaInfo.bean.AreaInfo;
public interface AreaInfoDao {
public void save(AreaInfo areaInfo) throws Exception;
public void delete(String id) throws Exception;
public void update(AreaInfo areaInfo) throws Exception;
public OperateEnterprise search(String id) throws Exception;
public List<OperateEnterprise> searchList(AreaInfo areaInfo, PageModel pageModel) throws Exception;
}